package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class HelpopCommand implements CommandExecutor {

    private ArrayList<UUID> helpopCooldown = new ArrayList<>();

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length != 0) {
                if(!helpopCooldown.contains(player.getUniqueId())) {
                    if(!game.getHelpopMuted().contains(player.getUniqueId())) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (String strings : args) {
                            stringBuilder.append(strings).append(" ");
                        }

                        for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                            if(allPlayers.hasPermission("uhc.host")) {
                                allPlayers.sendMessage(prefix + ChatColor.DARK_PURPLE + "Request by " + player.getName() + ": " + ChatColor.AQUA + stringBuilder);
                            }
                        }
                        player.sendMessage(prefix + mColor + "Your request has been sent!");
                        helpopCooldown.add(player.getUniqueId());

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                helpopCooldown.remove(player.getUniqueId());
                                player.sendMessage(prefix + sColor + "You can now send help requests again!");
                            }
                        }.runTaskLater(game, 10 * 20);
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "You are helpop-muted for this game!");
                    }
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "You have to wait 10 seconds to send a help request again!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /helpop (message)");
            }
        }
        return false;
    }
}
