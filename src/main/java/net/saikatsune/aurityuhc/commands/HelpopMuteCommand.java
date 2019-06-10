package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@SuppressWarnings("deprecation")
public class HelpopMuteCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("helpopmute")) {
            if(args.length != 1) {
                player.sendMessage(ChatColor.RED + "Usage: /helpopmute (player)");
            } else {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if(!aurityUHC.getHelpopMuted().contains(target.getUniqueId())) {
                    aurityUHC.getHelpopMuted().add(target.getUniqueId());

                    Bukkit.broadcastMessage(prefix + mColor + target.getName() + sColor + " has been helpop-muted for this game!");
                } else {
                    player.sendMessage(prefix + ChatColor.RED + target.getName() + " is already helpop-muted!");
                }
            }
        } else if(cmd.getName().equalsIgnoreCase("helpopunmute")) {
            if(args.length != 1) {
                player.sendMessage(ChatColor.RED + "Usage: /helpopunmute (player)");
            } else {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if(aurityUHC.getHelpopMuted().contains(target.getUniqueId())) {
                    aurityUHC.getHelpopMuted().remove(target.getUniqueId());

                    player.sendMessage(prefix + mColor + target.getName() + sColor + " has been unmuted!");
                } else {
                    player.sendMessage(prefix + ChatColor.RED + target.getName() + " is not helpop-muted!");
                }
            }
        }
        return false;
    }
}
