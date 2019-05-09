package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveAllCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("giveall")) {
            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                if(args.length == 2) {
                    if(Material.getMaterial(args[0]) != null) {
                        if(aurityUHC.getFileHandler().isNumeric(args[1])) {
                            for (Player allPlayers : aurityUHC.getPlayers()) {
                                allPlayers.getInventory().addItem(new ItemStack(Material.getMaterial(args[0]),
                                        Integer.parseInt(args[1])));
                                allPlayers.sendMessage(prefix + sColor + "You received " + mColor + args[1] + "x " +
                                        args[0] + sColor + "!");
                                aurityUHC.getGameManager().playSound();
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
