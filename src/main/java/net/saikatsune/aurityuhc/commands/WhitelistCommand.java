package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@SuppressWarnings("deprecation")
public class WhitelistCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("whitelist")) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("on")) {
                    if(!aurityUHC.getGameManager().isWhitelisted()) {
                        aurityUHC.getGameManager().setWhitelisted(true);
                        Bukkit.broadcastMessage(prefix + sColor + "The whitelist has been " + ChatColor.GREEN + "enabled" + sColor + "!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The server is already whitelisted!");
                    }
                } else if(args[0].equalsIgnoreCase("off")) {
                    if(aurityUHC.getGameManager().isWhitelisted()) {
                        aurityUHC.getGameManager().setWhitelisted(false);
                        Bukkit.broadcastMessage(prefix + sColor + "The whitelist has been " + ChatColor.RED + "disabled" + sColor + "!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The server is not whitelisted!");
                    }
                }
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("add")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if(!aurityUHC.getWhitelisted().contains(target.getUniqueId())) {
                        aurityUHC.getWhitelisted().add(target.getUniqueId());
                        player.sendMessage(prefix + mColor + target.getName() + sColor + " has been whitelisted!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + target.getName() + " is already whitelisted!");
                    }
                } else if(args[0].equalsIgnoreCase("remove")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if(aurityUHC.getWhitelisted().contains(target.getUniqueId())) {
                        aurityUHC.getWhitelisted().remove(target.getUniqueId());
                        player.sendMessage(prefix + mColor + target.getName() + sColor + " has been un-whitelisted!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + target.getName() + " is not whitelisted!");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /whitelist (on/off)");
                player.sendMessage(ChatColor.RED + "Usage: /whitelist (add/remove) (player)");
            }
        }
        return false;
    }
}
