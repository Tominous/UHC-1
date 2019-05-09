package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.handler.TeamHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendCoordsCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("sendcoords")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(aurityUHC.getGameManager().isTeamGame()) {
                    if(aurityUHC.getTeamNumber().get(player.getUniqueId()) != -1) {
                        TeamHandler teamHandler = aurityUHC.getTeamManager().getTeams().get(aurityUHC.getTeamNumber().get(player.getUniqueId()));
                        for (Player allPlayers : aurityUHC.getPlayers()) {
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
