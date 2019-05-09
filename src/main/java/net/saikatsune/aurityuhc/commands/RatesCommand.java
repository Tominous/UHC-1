package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RatesCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("rates")) {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("apple")) {
                    try {
                        aurityUHC.getConfigManager().setAppleRate(Double.valueOf(args[1]));
                        Bukkit.broadcastMessage(prefix + mColor + "Apple Rate " + sColor + "has changed to " + mColor + args[1] + "%" + sColor + "!");
                    } catch (NumberFormatException ex) {
                        player.sendMessage(prefix + ChatColor.RED + "The argument has to be numeric!");
                    }
                } else if(args[0].equalsIgnoreCase("flint")) {
                    try {
                        aurityUHC.getConfigManager().setFlintRate(Double.valueOf(args[1]));
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
