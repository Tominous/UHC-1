package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class StatsCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("stats")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(game.isDatabaseActive()) {
                    if(args.length == 1) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        if(game.getDatabaseManager().isPlayerRegistered(target)) {
                            game.getInventoryHandler().handleStatsInventory(player, target);
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + target.getName() + " is not registered in the database!");
                        }
                    } else if(args.length == 0) {
                        game.getInventoryHandler().handleStatsInventory(player, player);
                    } else {
                        player.sendMessage(ChatColor.RED + "Usage: /stats (player)");
                    }
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Stats are currently disabled!");
                }
            }
        }
        return false;
    }
}
