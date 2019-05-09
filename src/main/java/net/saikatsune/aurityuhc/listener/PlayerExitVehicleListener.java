package net.saikatsune.aurityuhc.listener;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.states.ScatteringState;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class PlayerExitVehicleListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    @EventHandler
    public void handleVehicleExitEvent(VehicleExitEvent event) {
        if(event.getExited() instanceof Player) {
            if(event.getVehicle() instanceof Horse) {
                if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof ScatteringState) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void handleVehicleEnterEvent(VehicleEnterEvent event) {
        if(event.getEntered() instanceof Player) {
            Player player = (Player) event.getEntered();

            if(aurityUHC.getSpectators().contains(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleHorseJumpEvent(HorseJumpEvent event) {
        if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof ScatteringState) {
            event.setCancelled(true);
        }
    }

}
