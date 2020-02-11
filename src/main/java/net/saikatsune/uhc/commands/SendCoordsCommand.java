package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.handler.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SendCoordsCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("sendcoords")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(game.getGameManager().isTeamGame()) {
                    if(game.getTeamNumber().get(player.getUniqueId()) != -1) {
                        TeamHandler teamHandler = game.getTeamManager().getTeams().get(game.getTeamNumber().get(player.getUniqueId()));
                        for (UUID players : game.getPlayers()) {

                            Player allPlayers = Bukkit.getPlayer(players);

                            if(teamHandler.getTeamMembers().contains(allPlayers.getUniqueId())) {
                                allPlayers.sendMessage(prefix + mColor + player.getName() + "'s coordinates: " + sColor +
                                        player.getLocation().getBlockX() + "/" + player.getLocation().getBlockY() + "/" + player.getLocation().getBlockZ() + "!");
                            }
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "You are not in a team!");
                    }
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Team Management is currently disabled!");
                }
            }
        }
        return false;
    }
}
