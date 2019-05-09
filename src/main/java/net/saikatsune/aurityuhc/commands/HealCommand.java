package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("heal")) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("all")) {
                    for (Player allPlayers : aurityUHC.getPlayers()) {
                        allPlayers.setHealth(20);
                        allPlayers.sendMessage(prefix + sColor + "You have been healed by " + mColor + player.getName() + sColor + "!");
                    }
                    player.sendMessage(prefix + mColor + "You have healed all players!");
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null) {
                        target.setHealth(20);
                        target.sendMessage(prefix + sColor + "You have been healed by " + mColor + player.getName() + sColor + "!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + args[0] + " is currently offline!");
                    }
                    player.sendMessage(prefix + sColor + "You have healed " + mColor + player.getName() + sColor + "!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /heal (player)");
                player.sendMessage(ChatColor.RED + "Usage: /heal (all)");
            }
        }
        return false;
    }
}
