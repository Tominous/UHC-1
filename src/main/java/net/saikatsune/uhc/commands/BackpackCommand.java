package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.states.IngameState;
import net.saikatsune.uhc.handler.TeamHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackpackCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("backpack")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(Scenarios.BACKPACKS.isEnabled()) {
                    if(game.getGameManager().isTeamGame()) {
                        if(game.getPlayers().contains(player)) {
                            if(game.getTeamNumber().get(player.getUniqueId()) != -1) {
                                if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                                    TeamHandler teamHandler = game.getTeamManager().getTeams().get(game.getTeamNumber().get(player.getUniqueId()));
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
