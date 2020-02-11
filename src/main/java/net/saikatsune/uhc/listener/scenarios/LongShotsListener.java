package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LongShotsListener implements Listener {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @EventHandler
    public void handleEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            if(event.getDamager() instanceof Arrow) {

                Arrow arrow = (Arrow) event.getDamager();

                if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                    if(Scenarios.LONGSHOTS.isEnabled()) {
                        if(arrow.getShooter() instanceof Player) {
                            Player shooter = (Player) arrow.getShooter();
                            Player shot = (Player) event.getEntity();

                            Location shooterLocation = shooter.getLocation();
                            Location shotLocation = shot.getLocation();

                            if(shooterLocation.distance(shotLocation) >= 50) {
                                event.setDamage(event.getDamage() * 1.5);

                                if(shooter.getHealth() > 18) {
                                    shooter.setHealth(20);
                                } else {
                                    shooter.setHealth(shooter.getHealth() + 2.0);
                                }
                                Bukkit.broadcastMessage(prefix + mColor + shooter.getName() + sColor + " hit a long shot of " + mColor +
                                        Math.round(shooterLocation.distance(shotLocation)) + sColor + " blocks!");
                            }
                        }
                    }
                }

            }
        }
    }

}
