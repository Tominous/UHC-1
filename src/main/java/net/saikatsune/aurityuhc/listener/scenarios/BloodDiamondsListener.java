package net.saikatsune.aurityuhc.listener.scenarios;

import net.saikatsune.aurityuhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BloodDiamondsListener implements Listener {

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(event.getBlock().getType() == Material.DIAMOND_ORE) {
            if(Scenarios.BLOODDIAMONDS.isEnabled()) {
                player.setHealth(player.getHealth() - 1);
            }
        }
    }

}
