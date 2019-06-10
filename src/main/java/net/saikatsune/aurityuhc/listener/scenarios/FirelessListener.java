package net.saikatsune.aurityuhc.listener.scenarios;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.Scenarios;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FirelessListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    @EventHandler
    public void handleEntityDamageEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {

            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                if(Scenarios.FIRELESS.isEnabled()) {
                    if(event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                            event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

}
