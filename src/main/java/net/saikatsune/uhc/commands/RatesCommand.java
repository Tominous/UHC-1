package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RatesCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("rates")) {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("apple")) {
                    try {
                        game.getConfigManager().setAppleRate(Double.parseDouble(args[1]));
                        Bukkit.broadcastMessage(prefix + mColor + "Apple Rate " + sColor + "has changed to " + mColor + args[1] + "%" + sColor + "!");
                    } catch (NumberFormatException ex) {
                        player.sendMessage(prefix + ChatColor.RED + "The argument has to be numeric!");
                    }
                } else if(args[0].equalsIgnoreCase("flint")) {
                    try {
                        game.getConfigManager().setFlintRate(Double.parseDouble(args[1]));
                        Bukkit.broadcastMessage(prefix + mColor + "Flint Rate " + sColor + "has changed to " + mColor + args[1] + "%" + sColor + "!");
                    } catch (NumberFormatException ex) {
                        player.sendMessage(prefix + ChatColor.RED + "The argument has to be numeric!");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /rates (apple) (number)");
                player.sendMessage(ChatColor.RED + "Usage: /rates (flint) (number)");
            }
        }
        return false;
    }
}
