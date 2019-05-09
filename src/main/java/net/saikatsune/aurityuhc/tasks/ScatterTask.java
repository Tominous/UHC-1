package net.saikatsune.aurityuhc.tasks;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.Scenarios;
import net.saikatsune.aurityuhc.gamestate.GameState;
import net.saikatsune.aurityuhc.handler.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

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

        Bukkit.broadcastMessage(prefix + sColor + "Starting scatter of all players!");

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(aurityUHC, new BukkitRunnable() {
            @Override
            public void run() {
                int playerNumber = new Random().nextInt(Bukkit.getWorld("world").getPlayers().size());
                Player random = (Player) Bukkit.getOnlinePlayers().toArray()[playerNumber];

                if (Bukkit.getWorld("world").getPlayers().contains(random)) {
                    aurityUHC.getGameManager().scatterPlayer(random, Bukkit.getWorld("uhc_world"));

                    random.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -5));
                    random.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 99999));
                    random.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 99999));
                }

                if (Bukkit.getWorld("world").getPlayers().size() < 1) {
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
                        Bukkit.broadcastMessage(prefix + ChatColor.RED + "You haven't been teleported if your team leader is not in your" +
                                " team or offline!");
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
        }, 0, 10);
    }

    public void cancelTask() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
