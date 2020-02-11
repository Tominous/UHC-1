package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.gamestate.states.LobbyState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PracticeCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("practice")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(args.length != 1) {
                    if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        if(game.isArenaEnabled()) {
                            if(!game.getArenaPlayers().contains(player.getUniqueId())) {
                                game.getArenaPlayers().add(player.getUniqueId());
                                player.getInventory().clear();
                                game.getInventoryHandler().handlePracticeInventory(player);
                                game.getGameManager().scatterPlayer(player, Bukkit.getWorld("uhc_practice"), 49);
                                player.sendMessage(prefix + sColor + "You have joined the arena!");
                            } else {
                                game.getArenaPlayers().remove(player.getUniqueId());
                                player.getInventory().clear();
                                player.getInventory().setArmorContents(null);
                                //Set lobby inventory
                                player.teleport(game.getLocationManager().getLocation("Spawn-Location"));
                                player.setHealth(20);
                                player.sendMessage(prefix + sColor + "You have left the arena!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "The arena is currently disabled!");
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                    }
                } else {
                    if(args[0].equalsIgnoreCase("toggle")) {
                        if(player.hasPermission("uhc.host")) {
                            if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if(game.isArenaEnabled()) {
                                    game.setArenaEnabled(false);

                                    for (UUID players : game.getPlayers()) {

                                        Player allPlayers = Bukkit.getPlayer(players);

                                        if(allPlayers.getWorld().getName().equalsIgnoreCase("uhc_practice")) {
                                            allPlayers.teleport(game.getLocationManager().getLocation("Spawn-Location"));

                                            allPlayers.getInventory().clear();
                                            allPlayers.getInventory().setArmorContents(null);
                                            allPlayers.setHealth(20);
                                        }
                                    }

                                    game.getArenaPlayers().clear();

                                    Bukkit.broadcastMessage(prefix + ChatColor.RED + "The arena has been disabled!");
                                } else {
                                    game.setArenaEnabled(true);

                                    Bukkit.broadcastMessage(prefix + ChatColor.GREEN + "The arena has been enabled!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
