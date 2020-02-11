package net.saikatsune.uhc.support;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class LegacySupport implements Listener {

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();

        if(block.getType() == Material.STONE) {
            if(block.getData() == (byte) 1 || (block.getData() == (byte) 2) ||
                    block.getData() == (byte) 3 || block.getData() == (byte) 4 ||
                    block.getData() == (byte) 5 || block.getData() == (byte) 6) {
                event.setCancelled(true);
                block.setType(Material.AIR);
                block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.COBBLESTONE));
            }
        }
    }

}
