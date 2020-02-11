package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HealCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("heal")) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("all")) {
                    for (UUID players : game.getPlayers()) {

                        Player allPlayers = Bukkit.getPlayer(players);

                        allPlayers.setHealth(20);
                        allPlayers.sendMessage(prefix + sColor + "You have been healed by " + mColor + player.getName() + sColor + "!");
                    }
                    player.sendMessage(prefix + mColor + "You have healed all players!");
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null) {
                        target.setHealth(20);
                        target.sendMessage(prefix + sColor + "You have been healed by " + mColor + player.getName() + sColor + "!");
                        player.sendMessage(prefix + sColor + "You have healed " + mColor + target.getName() + sColor + "!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + args[0] + " is currently offline!");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /heal (player)");
                player.sendMessage(ChatColor.RED + "Usage: /heal (all)");
            }
        }
        return false;
    }
}
