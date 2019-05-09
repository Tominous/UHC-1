package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.PlayerState;
import net.saikatsune.aurityuhc.gamestate.states.LobbyState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("staff")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;

                if(aurityUHC.getPlayerState().get(player) != PlayerState.SPECTATOR) {

                    if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        if(aurityUHC.getGameManager().isTeamGame()) {
                            if(aurityUHC.getTeamNumber().get(player.getUniqueId()) != -1) {
                                aurityUHC.getTeamManager().removePlayerFromTeam(aurityUHC.getTeamNumber().get(player.getUniqueId()), player.getUniqueId());
                                aurityUHC.getTeamNumber().put(player.getUniqueId(), -1);
                            }
                        }

                        aurityUHC.getGameManager().setPlayerState(player, PlayerState.SPECTATOR);
                        aurityUHC.getInventoryHandler().handleStaffInventory(player);

                        aurityUHC.getReceivePvpAlerts().add(player.getUniqueId());
                        aurityUHC.getReceiveDiamondAlerts().add(player.getUniqueId());
                        aurityUHC.getReceiveGoldAlerts().add(player.getUniqueId());
                    } else {
                        player.damage(20);
                    }

                    player.sendMessage(prefix + ChatColor.GREEN + "You are now in Staff-Mode!");
                } else {
                    if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        aurityUHC.getGameManager().setPlayerState(player, PlayerState.PLAYER);
                        aurityUHC.getGameManager().resetPlayer(player);

                        aurityUHC.getReceivePvpAlerts().remove(player.getUniqueId());
                        aurityUHC.getReceiveDiamondAlerts().remove(player.getUniqueId());
                        aurityUHC.getReceiveGoldAlerts().remove(player.getUniqueId());

                        player.teleport(aurityUHC.getLocationManager().getLocation("Spawn-Location"));
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
