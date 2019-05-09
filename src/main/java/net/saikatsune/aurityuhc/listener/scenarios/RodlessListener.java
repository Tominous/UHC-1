package net.saikatsune.aurityuhc.listener.scenarios;

import net.saikatsune.aurityuhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RodlessListener implements Listener {

    @EventHandler
    public void handlePlayerInteractEvent(PlayerInteractEvent event) {
        if(Scenarios.RODLESS.isEnabled()) {
            if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) &&
                    event.getPlayer().getItemInHand().getType() == Material.FISHING_ROD) {
                event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                event.setCancelled(true);
            }
        }
    }

}
