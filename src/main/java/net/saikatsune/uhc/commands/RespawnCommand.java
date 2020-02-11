package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.PlayerState;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RespawnCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("respawn")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null) {
                        if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                            if(game.getSpectators().contains(target)) {
                                if(game.isDeathRegistered(target)) {
                                    game.getGameManager().setPlayerState(target, PlayerState.PLAYER);

                                    if(game.isDatabaseActive()) {
                                        game.getDatabaseManager().removeDeaths(target, 1);
                                    }

                                    target.teleport(game.getDeathLocation().get(target.getUniqueId()));
                                    target.getInventory().setContents(game.getDeathInventory().get(target.getUniqueId()));
                                    target.getInventory().setArmorContents(game.getDeathArmor().get(target.getUniqueId()));
                                    target.setLevel(game.getDeathLevels().get(target.getUniqueId()));

                                    game.getLoggedPlayers().add(target.getUniqueId());
                                    game.getWhitelisted().add(target.getUniqueId());

                                    if(game.getGameManager().isTeamGame()) {
                                        if(game.getTeamManager().getTeams().containsKey(game.getDeathTeamNumber().get(target.getUniqueId()))) {
                                            game.getTeamManager().addPlayerToTeam(game.getDeathTeamNumber().get(target.getUniqueId()), target.getUniqueId());
                                        } else {
                                            game.getTeamManager().createTeam(target.getUniqueId());
                                        }
                                    }

                                    Bukkit.broadcastMessage(prefix + mColor + target.getName() + sColor + " has been re-spawned!");
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + target.getName() + " hasn't died in this game!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + target.getName() + " is already in game!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "You cannot respawn players right now!");
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + args[0] + " is currently offline!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /respawn (player)");
                }
            }
        }
        return false;
    }
}
