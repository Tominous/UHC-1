package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.PlayerState;
import net.saikatsune.uhc.gamestate.states.LobbyState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("staff")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;

                if(game.getPlayerState().get(player) != PlayerState.SPECTATOR) {

                    if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        if(game.getGameManager().isTeamGame()) {
                            if(game.getTeamNumber().get(player.getUniqueId()) != -1) {
                                game.getTeamManager().removePlayerFromTeam(game.getTeamNumber().get(player.getUniqueId()), player.getUniqueId());
                                game.getTeamNumber().put(player.getUniqueId(), -1);
                            }
                        }

                        game.getGameManager().setPlayerState(player, PlayerState.SPECTATOR);
                        game.getInventoryHandler().handleStaffInventory(player);

                        game.getReceivePvpAlerts().add(player.getUniqueId());
                        game.getReceiveDiamondAlerts().add(player.getUniqueId());
                        game.getReceiveGoldAlerts().add(player.getUniqueId());
                    } else {
                        player.damage(20);
                    }

                    player.sendMessage(prefix + ChatColor.GREEN + "You are now in Staff-Mode!");
                } else {
                    if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        game.getGameManager().setPlayerState(player, PlayerState.PLAYER);
                        game.getGameManager().resetPlayer(player);

                        game.getReceivePvpAlerts().remove(player.getUniqueId());
                        game.getReceiveDiamondAlerts().remove(player.getUniqueId());
                        game.getReceiveGoldAlerts().remove(player.getUniqueId());

                        player.teleport(game.getLocationManager().getLocation("Spawn-Location"));
                        player.sendMessage(prefix + ChatColor.RED + "You are no longer in Staff-Mode!");
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                    }
                }
            }
        }
        return false;
    }
}
