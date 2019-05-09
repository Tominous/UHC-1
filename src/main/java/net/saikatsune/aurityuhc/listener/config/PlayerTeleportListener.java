package net.saikatsune.aurityuhc.listener.config;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    @EventHandler
    public void handlePlayerTeleportEvent(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            if(!aurityUHC.getConfigManager().isEnderpearlDamage()) {
                event.setCancelled(true);
                player.teleport(event.getTo());
            }
        }
    }

}
