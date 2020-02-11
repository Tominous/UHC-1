package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SoupListener implements Listener {

    @EventHandler
    public void handlePlayerInteractEvent(PlayerInteractEvent event) {
        if(Scenarios.SOUP.isEnabled()) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getItem() != null && event.getItem().getType() == Material.MUSHROOM_SOUP) {
                    if(event.getPlayer().getHealth() < event.getPlayer().getMaxHealth()) {
                        if (event.getPlayer().getHealth() <= 13) {
                            event.getPlayer().setHealth(event.getPlayer().getHealth() + 7);
                            event.getPlayer().getItemInHand().setType(Material.BOWL);
                            event.getPlayer().getItemInHand().setItemMeta(null);
                        } else {
                            event.getPlayer().setHealth(20);
                            event.getPlayer().getItemInHand().setType(Material.BOWL);
                            event.getPlayer().getItemInHand().setItemMeta(null);
                        }
                    }
                }
            }
        }
    }

}
