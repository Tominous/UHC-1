package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

@SuppressWarnings("deprecation")
public class FlowerPowerListener implements Listener {

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {

        Material material = event.getBlock().getType();

        Random random = new Random();

        if(Scenarios.FLOWERPOWER.isEnabled()) {
            if(material == Material.getMaterial(38) || material == Material.YELLOW_FLOWER || material == Material.CROPS ||
                material == Material.DOUBLE_PLANT) {
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);

                ItemStack toDrop = new ItemStack(Material.values()
                        [random.nextInt(Material.values().length)], random.nextInt(64));

                if(!toDrop.equals(new ItemStack(Material.AIR))) {
                    if(!toDrop.equals(new ItemStack(Material.BEDROCK))) {
                        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), toDrop);
                    }
                }
            }
        }
    }

}
