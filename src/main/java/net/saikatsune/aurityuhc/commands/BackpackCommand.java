package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.Scenarios;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import net.saikatsune.aurityuhc.handler.TeamHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackpackCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("backpack")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(Scenarios.BACKPACKS.isEnabled()) {
                    if(aurityUHC.getGameManager().isTeamGame()) {
                        if(aurityUHC.getPlayers().contains(player)) {
                            if(aurityUHC.getTeamNumber().get(player.getUniqueId()) != -1) {
                                if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                                    TeamHandler teamHandler = aurityUHC.getTeamManager().getTeams().get(aurityUHC.getTeamNumber().get(player.getUniqueId()));
                                    player.openInventory(teamHandler.getTeamInventory());
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "You cannot use your backpack right now!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "You are not in a team!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Only players can use backpacks!");
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "Team Management is currently disabled!");
                    }
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Backpacks are not enabled!");
                }
            }
        }
        return false;
    }
}
