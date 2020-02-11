package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TimeCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("time")) {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("healtime")) {
                    if(game.getFileHandler().isNumeric(args[1])) {
                        game.getConfigManager().setFinalHeal(Integer.parseInt(args[1]));
                        Bukkit.broadcastMessage(prefix + mColor + "Final Heal" + sColor + " has changed to " + mColor + args[1] + sColor + " minutes!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The argument has to be numeric!");
                    }
                } else if(args[0].equalsIgnoreCase("gracetime")) {
                    if(game.getFileHandler().isNumeric(args[1])) {
                        game.getConfigManager().setGraceTime(Integer.parseInt(args[1]));
                        Bukkit.broadcastMessage(prefix + mColor + "Grace Time" + sColor + " has changed to " + mColor + args[1] + sColor + " minutes!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The argument has to be numeric!");
                    }
                } else if(args[0].equalsIgnoreCase("bordertime")) {
                    if(game.getFileHandler().isNumeric(args[1])) {
                        game.getConfigManager().setBorderTime(Integer.parseInt(args[1]));
                        Bukkit.broadcastMessage(prefix + mColor + "First Shrink" + sColor + " has changed to " + mColor + args[1] + sColor + " minutes!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The argument has to be numeric!");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /time (healtime) (number)");
                player.sendMessage(ChatColor.RED + "Usage: /time (gracetime) (number)");
                player.sendMessage(ChatColor.RED + "Usage: /time (bordertime) (number)");
            }
        }
        return false;
    }
}
