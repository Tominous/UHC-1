package net.saikatsune.uhc.tasks;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.GameState;
import net.saikatsune.uhc.handler.TeamHandler;
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

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    private int taskID;

    public void runTask() {
        game.setChatMuted(true);

        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage("");
        }

        for (UUID players : game.getPlayers()) {

            Player allPlayers = Bukkit.getPlayer(players);

            Random randomLocation = new Random();

            int x = randomLocation.nextInt(game.getConfigManager().getBorderSize() - 1);
            int z = randomLocation.nextInt(game.getConfigManager().getBorderSize() - 1);
            int y = Bukkit.getWorld("uhc_world").getHighestBlockYAt(x, z);

            Location location = new Location(Bukkit.getWorld("uhc_world"), x, y ,z);

            game.getGameManager().setScatterLocation(allPlayers, location);
        }

        Bukkit.broadcastMessage(prefix + sColor + "Starting scatter of all players!");

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(game, new BukkitRunnable() {
            @Override
            public void run() {
                int playerNumber = new Random().nextInt(Bukkit.getWorld("world").getPlayers().size());
                Player random = (Player) Bukkit.getOnlinePlayers().toArray()[playerNumber];

                if (Bukkit.getWorld("world").getPlayers().contains(random)) {

                    random.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -5));
                    random.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 127));

                    random.teleport(game.getScatterLocation().get(random));
                }

                if (Bukkit.getWorld("world").getPlayers().size() == 0) {
                    Bukkit.getScheduler().cancelTask(taskID);
                    Bukkit.broadcastMessage(prefix + ChatColor.GREEN + "Finished scatter of all players!");

                    if(game.getGameManager().isTeamGame()) {
                        for (UUID noTeams : game.getPlayers()) {

                            Player allPlayers = Bukkit.getPlayer(noTeams);

                            if(game.getTeamNumber().get(allPlayers.getUniqueId()) == -1) {
                                game.getTeamManager().createTeam(allPlayers.getUniqueId());
                            }
                        }

                        for (TeamHandler teamHandler : game.getTeamManager().getTeams().values()) {
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

                    for (UUID players : game.getPlayers()) {

                        Player allPlayers = Bukkit.getPlayer(players);

                        game.getLoggedPlayers().add(allPlayers.getUniqueId());
                        game.getWhitelisted().add(allPlayers.getUniqueId());
                    }

                    game.getGameManager().setWhitelisted(true);

                    Bukkit.broadcastMessage(prefix + sColor + "All players have been whitelisted!");
                    Bukkit.broadcastMessage(prefix + mColor + "The game starts in 10 seconds!");

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            game.getGameStateManager().setGameState(GameState.INGAME);
                            Bukkit.broadcastMessage(prefix + mColor + "The game has started. Good Luck!");

                            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                                for (PotionEffect potionEffect : allPlayers.getActivePotionEffects()) {
                                    allPlayers.removePotionEffect(potionEffect.getType());
                                }
                            }

                            if(Scenarios.GONEFISHING.isEnabled()) {
                                for (UUID players : game.getPlayers()) {

                                    Player allPlayers = Bukkit.getPlayer(players);

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
                                for (UUID players : game.getPlayers()) {
                                    Player allPlayers = Bukkit.getPlayer(players);

                                    allPlayers.setLevel(30000);
                                    allPlayers.getInventory().addItem(new ItemStack(Material.ENCHANTMENT_TABLE, 64));
                                    allPlayers.getInventory().addItem(new ItemStack(Material.ANVIL, 64));
                                    allPlayers.getInventory().addItem(new ItemStack(Material.BOOKSHELF, 64));
                                    allPlayers.getInventory().addItem(new ItemStack(Material.BOOKSHELF, 64));
                                }
                            }
                        }
                    }.runTaskLater(game, 10 * 20);
                }
            }
        }, 0, 20);
    }

    public void cancelTask() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
