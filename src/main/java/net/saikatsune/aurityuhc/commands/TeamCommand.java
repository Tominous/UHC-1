package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.GameType;
import net.saikatsune.aurityuhc.gamestate.states.LobbyState;
import net.saikatsune.aurityuhc.handler.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class TeamCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("team")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    if (player.hasPermission("uhc.host")) {
                        player.sendMessage(ChatColor.RED + "Usage: /team (toggle)");
                        player.sendMessage(ChatColor.RED + "Usage: /team (size) (number)");
                    }
                    player.sendMessage(ChatColor.RED + "Usage: /team (create)");
                    player.sendMessage(ChatColor.RED + "Usage: /team (leave)");
                    player.sendMessage(ChatColor.RED + "Usage: /team (invite) (player)");
                    player.sendMessage(ChatColor.RED + "Usage: /team (accept) (player)");
                    player.sendMessage(ChatColor.RED + "Usage: /team (kick) (player)");
                    player.sendMessage(ChatColor.RED + "Usage: /team (list) (player)");
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("toggle")) {
                        if (player.hasPermission("uhc.host")) {
                            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if (aurityUHC.getGameManager().isTeamGame()) {
                                    aurityUHC.getGameManager().setTeamGame(false);
                                    aurityUHC.getGameType().put("GameType", GameType.SOLO);
                                    Bukkit.broadcastMessage(prefix + mColor + "Team Management " + sColor + "has been " + ChatColor.RED + "disabled" + sColor + "!");
                                } else {
                                    aurityUHC.getGameManager().setTeamGame(true);
                                    aurityUHC.getGameType().put("GameType", GameType.TEAMS);
                                    Bukkit.broadcastMessage(prefix + mColor + "Team Management " + sColor + "has been " + ChatColor.GREEN + "enabled" + sColor + "!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("create")) {
                        if (aurityUHC.getGameManager().isTeamGame()) {
                            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if (aurityUHC.getTeamNumber().get(player.getUniqueId()) == -1) {
                                    aurityUHC.getTeamManager().createTeam(player.getUniqueId());
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "You are already in a team!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Team Management is currently disabled!");
                        }
                    } else if (args[0].equalsIgnoreCase("leave")) {
                        if (aurityUHC.getGameManager().isTeamGame()) {
                            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if (aurityUHC.getTeamNumber().get(player.getUniqueId()) != -1) {
                                    aurityUHC.getTeamManager().removePlayerFromTeam(aurityUHC.getTeamNumber().get(player.getUniqueId()), player.getUniqueId());
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "You are not in a team!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Team Management is currently disabled!");
                        }
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("size")) {
                        if(player.hasPermission("uhc.host")) {
                            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if (aurityUHC.getFileHandler().isNumeric(args[1])) {
                                    int size = Integer.parseInt(args[1]);
                                    if (size > 1) {
                                        if (aurityUHC.getGameManager().isTeamGame()) {
                                            aurityUHC.getTeamManager().setTeamSize(Integer.parseInt(args[1]));
                                            Bukkit.broadcastMessage(prefix + sColor + "The team size has changed to " + mColor + args[1] + sColor + "!");
                                        } else {
                                            player.sendMessage(prefix + ChatColor.RED + "Team Management is currently disabled!");
                                        }
                                    } else {
                                        player.sendMessage(prefix + ChatColor.RED + "The team size has to be higher than 1!");
                                    }
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "The argument has to be numeric!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("invite")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null) {
                            if(target != player) {
                                if(aurityUHC.getGameManager().isTeamGame()) {
                                    if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                        if(aurityUHC.getTeamNumber().get(player.getUniqueId()) != -1) {
                                            TeamHandler teamHandler = aurityUHC.getTeamManager().getTeams().get(aurityUHC.getTeamNumber().get(player.getUniqueId()));
                                            if(teamHandler.getTeamLeader().equals(player.getUniqueId())) {
                                                if(teamHandler.getTeamMembers().size() < aurityUHC.getTeamManager().getTeamSize()) {
                                                    if(aurityUHC.getTeamNumber().get(target.getUniqueId()) == -1) {
                                                        if(aurityUHC.getTeamInvitation().get(player) != target) {
                                                            aurityUHC.getTeamInvitation().put(player, target);
                                                            aurityUHC.getTeamInvitation().put(target, player);
                                                            player.sendMessage(prefix + sColor + "You have sent an invitation to " + mColor + target.getName() + sColor + "!");
                                                            target.sendMessage(prefix + mColor + player.getName() + sColor + " has sent a team invitation to you!");
                                                            target.sendMessage(prefix + sColor + "Use " + mColor + "/team accept " + player.getName() + sColor + " to accept!");

                                                            new BukkitRunnable() {
                                                                @Override
                                                                public void run() {
                                                                    aurityUHC.getTeamInvitation().put(player, null);
                                                                    aurityUHC.getTeamInvitation().put(target, null);
                                                                }
                                                            }.runTaskLater(aurityUHC, 20 * 20);
                                                        } else {
                                                            player.sendMessage(prefix + ChatColor.RED + "You have already sent an invitation to " + target.getName() + "!");
                                                        }
                                                    } else {
                                                        player.sendMessage(prefix + ChatColor.RED + target.getName() + " is already in a team!");
                                                    }
                                                } else {
                                                    player.sendMessage(prefix + ChatColor.RED + "Your team is already full!");
                                                }
                                            } else {
                                                player.sendMessage(prefix + ChatColor.RED + "Only team leaders can send invitations!");
                                            }
                                        } else {
                                            player.sendMessage(prefix + ChatColor.RED + "You haven't created a team yet!");
                                        }
                                    } else {
                                        player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                                    }
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "Team Management is currently disabled!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "You cannot invite yourself!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + args[1] + " is currently offline!");
                        }
                    } else if(args[0].equalsIgnoreCase("accept")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if(aurityUHC.getGameManager().isTeamGame()) {
                            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if (target != null) {
                                    if (aurityUHC.getTeamNumber().get(player.getUniqueId()) == -1) {
                                        if (aurityUHC.getTeamInvitation().get(player) == target) {
                                            TeamHandler teamHandler = aurityUHC.getTeamManager().getTeams().get(aurityUHC.getTeamNumber().get(target.getUniqueId()));
                                            if(teamHandler.getTeamMembers().size() < aurityUHC.getTeamManager().getTeamSize()) {
                                                aurityUHC.getTeamManager().addPlayerToTeam(aurityUHC.getTeamNumber().get(target.getUniqueId()), player.getUniqueId());
                                            } else {
                                                player.sendMessage(prefix + ChatColor.RED + "The team is already full!");
                                            }
                                        } else {
                                            player.sendMessage(prefix + ChatColor.RED + target.getName() + " hasn't invited you or the invitation has expired!");
                                        }
                                    } else {
                                        player.sendMessage(prefix + ChatColor.RED + "You are already in a team!");
                                    }
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + args[1] + " is currently offline!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Team Management is currently disabled!");
                        }
                    } else if(args[0].equalsIgnoreCase("kick")) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if(aurityUHC.getGameManager().isTeamGame()) {
                            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if(aurityUHC.getTeamNumber().get(player.getUniqueId()) != -1) {
                                    TeamHandler teamHandler = aurityUHC.getTeamManager().getTeams().get(aurityUHC.getTeamNumber().get(player.getUniqueId()));
                                    if(teamHandler.getTeamLeader().equals(player.getUniqueId())) {
                                        if(teamHandler.getTeamMembers().contains(target.getUniqueId())) {
                                            aurityUHC.getTeamManager().kickPlayerFromTeam(aurityUHC.getTeamNumber().get(player.getUniqueId()), target.getUniqueId());
                                        } else {
                                            player.sendMessage(prefix + ChatColor.RED + target.getName() + " is not in your team!");
                                        }
                                    } else {
                                        player.sendMessage(prefix + ChatColor.RED + "Only team leaders can kick players!");
                                    }
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "You are not in a team!");
                                }
                            } else{
                                player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Team Management is currently disabled!");
                        }
                    } else if(args[0].equalsIgnoreCase("list")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if(aurityUHC.getGameManager().isTeamGame()) {
                            if(target != null) {
                                if(aurityUHC.getTeamNumber().get(target.getUniqueId()) != -1) {
                                    TeamHandler teamHandler = aurityUHC.getTeamManager().getTeams().get(aurityUHC.getTeamNumber().get(target.getUniqueId()));

                                    player.sendMessage("§8§m--------------------------");
                                    player.sendMessage(mColor + "Members of Team #" + teamHandler.getTeamNumber() + ":");

                                    player.sendMessage("");

                                    for (UUID allUUID : teamHandler.getTeamMembers()) {
                                        OfflinePlayer allMembers = Bukkit.getOfflinePlayer(allUUID);

                                        player.sendMessage(sColor + "- " + allMembers.getName() + ChatColor.GRAY +
                                                " [" + aurityUHC.getPlayerKills().get(allMembers.getUniqueId()) + "]");
                                    }
                                    player.sendMessage("§8§m--------------------------");
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "This player is not in a team!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + args[1] + " is currently offline!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Team Management is currently disabled!");
                        }
                    }
                }
            }
        }
        return false;
    }
}