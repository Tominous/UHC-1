package net.saikatsune.aurityuhc.tasks;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.Scenarios;
import net.saikatsune.aurityuhc.gamestate.GameState;
import net.saikatsune.aurityuhc.handler.TeamHandler;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

@SuppressWarnings("deprecation")
public class ScatterTask {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    private int taskID;

    public void runTask() {
        aurityUHC.setChatMuted(true);

        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage("");
        }

        for (Player allPlayers : aurityUHC.getPlayers()) {
            Random randomLocation = new Random();

            int x = randomLocation.nextInt(aurityUHC.getConfigManager().getBorderSize() - 1);
            int z = randomLocation.nextInt(aurityUHC.getConfigManager().getBorderSize() - 1);
            int y = Bukkit.getWorld("uhc_world").getHighestBlockYAt(x, z);

            Location location = new Location(Bukkit.getWorld("uhc_world"), x, y ,z);

            aurityUHC.getGameManager().setScatterLocation(allPlayers, location);
        }

        Bukkit.broadcastMessage(prefix + sColor + "Starting scatter of all players!");

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(aurityUHC, new BukkitRunnable() {
            @Override
            public void run() {
                int playerNumber = new Random().nextInt(Bukkit.getWorld("world").getPlayers().size());
                Player random = (Player) Bukkit.getOnlinePlayers().toArray()[playerNumber];

                if (Bukkit.getWorld("world").getPlayers().contains(random)) {

                    random.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -5));
                    random.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 127));

                    random.teleport(aurityUHC.getScatterLocation().get(random));
                }

                if (Bukkit.getWorld("world").getPlayers().size() == 0) {
                    Bukkit.getScheduler().cancelTask(taskID);
                    Bukkit.broadcastMessage(prefix + ChatColor.GREEN + "Finished scatter of all players!");

                    if(aurityUHC.getGameManager().isTeamGame()) {
                        for (Player noTeams : aurityUHC.getPlayers()) {
                            if(aurityUHC.getTeamNumber().get(noTeams.getUniqueId()) == -1) {
                                aurityUHC.getTeamManager().createTeam(noTeams.getUniqueId());
                            }
                        }

                        for (TeamHandler teamHandler : aurityUHC.getTeamManager().getTeams().values()) {
                            for (UUID toTeleport : teamHandler.getTeamMembers()) {
                                Player playerToTeleport = Bukkit.getPlayer(toTeleport);
                                if(teamHandler.getTeamLeader() != null) {
                                    if(teamHandler.getTeamMembers().contains(teamHandler.getTeamLeader())) {
                                        playerToTeleport.teleport(Bukkit.getPlayer(teamHandler.getTeamLeader()));
                                    }
                                }
                            }
                        }

                        Bukkit.broadcastMessage(prefix + sColor + "All teams have been teleported to their leaders!");
                    }

                    for (Player allPlayers : aurityUHC.getPlayers()) {
                        aurityUHC.getLoggedPlayers().add(allPlayers.getUniqueId());
                        aurityUHC.getWhitelisted().add(allPlayers.getUniqueId());
                    }

                    aurityUHC.getGameManager().setWhitelisted(true);

                    Bukkit.broadcastMessage(prefix + sColor + "All players have been whitelisted!");
                    Bukkit.broadcastMessage(prefix + mColor + "The game starts in 10 seconds!");

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            aurityUHC.getGameStateManager().setGameState(GameState.INGAME);
                            Bukkit.broadcastMessage(prefix + mColor + "The game has started. Good Luck!");

                            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                                for (PotionEffect potionEffect : allPlayers.getActivePotionEffects()) {
                                    allPlayers.removePotionEffect(potionEffect.getType());
                                }
                            }

                            if(Scenarios.GONEFISHING.isEnabled()) {
                                for (Player allPlayers : aurityUHC.getPlayers()) {
                                    allPlayers.getInventory().addItem(new ItemStack(Material.ANVIL, 20));

                                    allPlayers.setLevel(30000);

                                    ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
                                    fishingRod.addUnsafeEnchantment(Enchantment.LUCK, 250);
                                    fishingRod.addUnsafeEnchantment(Enchantment.LURE, 3);
                                    fishingRod.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
                                    allPlayers.getInventory().addItem(fishingRod);
                                }
                            }

                            if(Scenarios.INFINITEENCHANT.isEnabled()) {
                                for (Player allPlayers : aurityUHC.getPlayers()) {
                                    allPlayers.setLevel(30000);
                                    allPlayers.getInventory().addItem(new ItemStack(Material.ENCHANTMENT_TABLE, 64));
                                    allPlayers.getInventory().addItem(new ItemStack(Material.ANVIL, 64));
                                    allPlayers.getInventory().addItem(new ItemStack(Material.BOOKSHELF, 64));
                                    allPlayers.getInventory().addItem(new ItemStack(Material.BOOKSHELF, 64));
                                }
                            }
                        }
                    }.runTaskLater(aurityUHC, 10 * 20);
                }
            }
        }, 0, 20);
    }

    public void cancelTask() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
