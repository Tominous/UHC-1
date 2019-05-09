package net.saikatsune.aurityuhc.listener.config;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class PlayerCraftListener implements Listener {

    @EventHandler
    public void handlePlayerCraftEvent(PrepareItemCraftEvent event) {
        if (event.getRecipe().getResult().getType() == Material.GOLDEN_APPLE
                && event.getRecipe().getResult().getData().getData() == 1) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

}
