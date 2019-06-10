package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.states.LobbyState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PracticeCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("practice")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(args.length != 1) {
                    if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        if(aurityUHC.isArenaEnabled()) {
                            if(!aurityUHC.getArenaPlayers().contains(player.getUniqueId())) {
                                aurityUHC.getArenaPlayers().add(player.getUniqueId());
                                player.getInventory().clear();
                                aurityUHC.getInventoryHandler().handlePracticeInventory(player);
                                aurityUHC.getGameManager().scatterPlayer(player, Bukkit.getWorld("uhc_practice"), 100);
                                player.sendMessage(prefix + sColor + "You have joined the arena!");
                            } else {
                                aurityUHC.getArenaPlayers().remove(player.getUniqueId());
                                player.getInventory().clear();
                                player.getInventory().setArmorContents(null);
                                //Set lobby inventory
                                player.teleport(aurityUHC.getLocationManager().getLocation("Spawn-Location"));
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
                            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if(aurityUHC.isArenaEnabled()) {
                                    aurityUHC.setArenaEnabled(false);

                                    for (Player allPlayers : aurityUHC.getPlayers()) {
                                        if(allPlayers.getWorld().getName().equalsIgnoreCase("uhc_practice")) {
                                            allPlayers.teleport(aurityUHC.getLocationManager().getLocation("Spawn-Location"));

                                            allPlayers.getInventory().clear();
                                            allPlayers.getInventory().setArmorContents(null);
                                            allPlayers.setHealth(20);
                                        }
                                    }

                                    aurityUHC.getArenaPlayers().clear();

                                    Bukkit.broadcastMessage(prefix + ChatColor.RED + "The arena has been disabled!");
                                } else {
                                    aurityUHC.setArenaEnabled(true);

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
