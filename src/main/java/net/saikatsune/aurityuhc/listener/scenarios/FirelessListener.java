package net.saikatsune.aurityuhc.listener.scenarios;

import net.saikatsune.aurityuhc.enums.Scenarios;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FirelessListener implements Listener {

    @EventHandler
    public void handleEntityDamageEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            if(Scenarios.FIRELESS.isEnabled()) {
                if(event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                        event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
