package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.GameType;
import net.saikatsune.uhc.gamestate.states.LobbyState;
import net.saikatsune.uhc.handler.TeamHandler;
import net.saikatsune.uhc.Game;
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

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

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
                            if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if (game.getGameManager().isTeamGame()) {
                                    game.getGameManager().setTeamGame(false);
                                    game.getGameType().put("GameType", GameType.SOLO);
                                    game.setTeamSizeInString("FFA");
                                    Bukkit.broadcastMessage(prefix + mColor + "Team Management " + sColor + "has been " + ChatColor.RED + "disabled" + sColor + "!");
                                } else {
                                    game.getGameManager().setTeamGame(true);
                                    game.getGameType().put("GameType", GameType.TEAMS);
                                    game.setTeamSizeInString("To" + game.getTeamManager().getTeamSize());
                                    Bukkit.broadcastMessage(prefix + mColor + "Team Management " + sColor + "has been " + ChatColor.GREEN + "enabled" + sColor + "!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("create")) {
                        if (game.getGameManager().isTeamGame()) {
                            if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if (game.getTeamNumber().get(player.getUniqueId()) == -1) {
                                    game.getTeamManager().createTeam(player.getUniqueId());
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
                        if (game.getGameManager().isTeamGame()) {
                            if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if (game.getTeamNumber().get(player.getUniqueId()) != -1) {
                                    game.getTeamManager().removePlayerFromTeam(game.getTeamNumber().get(player.getUniqueId()), player.getUniqueId());
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
                            if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if (game.getFileHandler().isNumeric(args[1])) {
                                    int size = Integer.parseInt(args[1]);
                                    if (size > 1) {
                                        if (game.getGameManager().isTeamGame()) {
                                            game.getTeamManager().setTeamSize(Integer.parseInt(args[1]));
                                            game.setTeamSizeInString("To" + game.getTeamManager().getTeamSize());
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
                                if(game.getGameManager().isTeamGame()) {
                                    if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                        if(game.getTeamNumber().get(player.getUniqueId()) != -1) {
                                            TeamHandler teamHandler = game.getTeamManager().getTeams().get(game.getTeamNumber().get(player.getUniqueId()));
                                            if(teamHandler.getTeamLeader().equals(player.getUniqueId())) {
                                                if(teamHandler.getTeamMembers().size() < game.getTeamManager().getTeamSize()) {
                                                    if(game.getTeamNumber().get(target.getUniqueId()) == -1) {
                                                        if(game.getTeamInvitation().get(player) != target) {
                                                            game.getTeamInvitation().put(player, target);
                                                            game.getTeamInvitation().put(target, player);
                                                            player.sendMessage(prefix + sColor + "You have sent an invitation to " + mColor + target.getName() + sColor + "!");
                                                            target.sendMessage(prefix + mColor + player.getName() + sColor + " has sent a team invitation to you!");
                                                            target.sendMessage(prefix + sColor + "Use " + mColor + "/team accept " + player.getName() + sColor + " to accept!");

                                                            new BukkitRunnable() {
                                                                @Override
                                                                public void run() {
                                                                    game.getTeamInvitation().put(player, null);
                                                                    game.getTeamInvitation().put(target, null);
                                                                }
                                                            }.runTaskLater(game, 20 * 20);
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
                        if(game.getGameManager().isTeamGame()) {
                            if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if (target != null) {
                                    if (game.getTeamNumber().get(player.getUniqueId()) == -1) {
                                        if (game.getTeamInvitation().get(player) == target) {
                                            TeamHandler teamHandler = game.getTeamManager().getTeams().get(game.getTeamNumber().get(target.getUniqueId()));
                                            if(teamHandler.getTeamMembers().size() < game.getTeamManager().getTeamSize()) {
                                                game.getTeamManager().addPlayerToTeam(game.getTeamNumber().get(target.getUniqueId()), player.getUniqueId());
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
                        if(game.getGameManager().isTeamGame()) {
                            if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                                if(game.getTeamNumber().get(player.getUniqueId()) != -1) {
                                    TeamHandler teamHandler = game.getTeamManager().getTeams().get(game.getTeamNumber().get(player.getUniqueId()));
                                    if(teamHandler.getTeamLeader().equals(player.getUniqueId())) {
                                        if(teamHandler.getTeamMembers().contains(target.getUniqueId())) {
                                            game.getTeamManager().kickPlayerFromTeam(game.getTeamNumber().get(player.getUniqueId()), target.getUniqueId());
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
                        if(game.getGameManager().isTeamGame()) {
                            if(target != null) {
                                if(game.getTeamNumber().get(target.getUniqueId()) != -1) {
                                    TeamHandler teamHandler = game.getTeamManager().getTeams().get(game.getTeamNumber().get(target.getUniqueId()));

                                    player.sendMessage("§8§m--------------------------");
                                    player.sendMessage(mColor + "Members of Team #" + teamHandler.getTeamNumber() + ":");

                                    player.sendMessage("");

                                    for (UUID allUUID : teamHandler.getTeamMembers()) {
                                        OfflinePlayer allMembers = Bukkit.getOfflinePlayer(allUUID);

                                        player.sendMessage(sColor + "- " + allMembers.getName() + ChatColor.GRAY +
                                                " [" + game.getPlayerKills().get(allMembers.getUniqueId()) + "]");
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