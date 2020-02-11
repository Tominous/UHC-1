package net.saikatsune.uhc.listener.config;

import net.saikatsune.uhc.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    private Game game = Game.getInstance();

    @EventHandler
    public void handlePlayerTeleportEvent(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            if(!game.getConfigManager().isEnderpearlDamage()) {
                event.setCancelled(true);
                player.teleport(event.getTo());
            }
        }
    }

}
