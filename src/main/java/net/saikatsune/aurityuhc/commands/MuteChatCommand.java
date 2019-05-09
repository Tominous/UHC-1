package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteChatCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("mutechat")) {
            if(sender instanceof Player) {
                if(aurityUHC.isChatMuted()) {
                    aurityUHC.setChatMuted(false);
                    Bukkit.broadcastMessage(prefix + mColor + "Global Chat " + sColor + "has been " + ChatColor.GREEN + "enabled" + sColor + "!");
                } else {
                    aurityUHC.setChatMuted(true);
                    Bukkit.broadcastMessage(prefix + mColor + "Global Chat " + sColor + "has been " + ChatColor.RED + "disabled" + sColor + "!");
                }
            }
        }
        return false;
    }
}
