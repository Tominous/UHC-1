package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GoldlessListener implements Listener {

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        if(Scenarios.GOLDLESS.isEnabled()) {
            if(event.getBlock().getType() == Material.GOLD_ORE) {
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
                event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(4);
            }
        }
    }

}
