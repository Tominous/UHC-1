package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GiveAllCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("giveall")) {
            if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                if(args.length == 2) {
                    if(Material.getMaterial(args[0]) != null) {
                        if(game.getFileHandler().isNumeric(args[1])) {
                            for (UUID players : game.getPlayers()) {

                                Player allPlayers = Bukkit.getPlayer(players);

                                allPlayers.getInventory().addItem(new ItemStack(Material.getMaterial(args[0]),
                                        Integer.parseInt(args[1])));
                                allPlayers.sendMessage(prefix + sColor + "You received " + mColor + args[1] + "x " +
                                        args[0] + sColor + "!");
                                game.getGameManager().playSound();
                            }
                            player.sendMessage(prefix + sColor + "You gave every player " + mColor + args[1] + "x " +
                                    args[0] + sColor + "!");
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "The argument has to be numeric!");
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The argument has to be an item!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /giveall (item) (number)");
                }
            } else {
                player.sendMessage(prefix + ChatColor.RED + "You cannot do that right now!");
            }
        }
        return false;
    }
}
