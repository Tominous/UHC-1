package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class DiamondlessListener implements Listener {

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.DIAMOND_ORE) {
            if(Scenarios.DIAMONDLESS.isEnabled()) {
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
                event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(5);
            }
        }
    }

}
