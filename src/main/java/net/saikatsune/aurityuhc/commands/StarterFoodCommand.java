package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.states.LobbyState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StarterFoodCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("starterfood")) {
            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                if(args.length == 1) {
                    if(aurityUHC.getFileHandler().isNumeric(args[0])) {
                        aurityUHC.getConfigManager().setStarterFood(Integer.parseInt(args[0]));
                        Bukkit.broadcastMessage(prefix + mColor + "Starter Food " + sColor + "has changed to " + mColor +
                                args[0] + sColor + " Steaks!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The argument has to be numeric!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /starterfood (number)");
                }
            } else {
                player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
            }
        }
        return false;
    }
}
