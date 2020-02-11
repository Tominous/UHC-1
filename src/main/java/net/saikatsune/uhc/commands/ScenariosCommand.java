package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.enums.Scenarios;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScenariosCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("scenarios")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.openInventory(Scenarios.getExplanations());
            }
        }
        return false;
    }
}
