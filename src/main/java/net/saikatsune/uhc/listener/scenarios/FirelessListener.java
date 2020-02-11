package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FirelessListener implements Listener {

    private Game game = Game.getInstance();

    @EventHandler
    public void handleEntityDamageEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {

            if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
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
