package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.enums.Scenarios;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;

public class DoubleXPListener implements Listener {

    @EventHandler
    public void handleEntityDeathEvent(EntityDeathEvent event) {
        if(Scenarios.DOUBLEXP.isEnabled()) {
            event.setDroppedExp(event.getDroppedExp() * 2);
        }

        if(Scenarios.TRIPLEXP.isEnabled()) {
            event.setDroppedExp(event.getDroppedExp() * 3);
        }
    }

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        switch (event.getBlock().getType()) {
            case LAPIS_ORE: case REDSTONE_ORE: case DIAMOND_ORE:
                if(Scenarios.DOUBLEXP.isEnabled()) {
                    event.setExpToDrop(event.getExpToDrop() * 2);
                }

                if(Scenarios.TRIPLEXP.isEnabled()) {
                    event.setExpToDrop(event.getExpToDrop() * 3);
                }
                break;

            case IRON_ORE:
                if(Scenarios.CUTCLEAN.isEnabled()) {
                    if(Scenarios.DOUBLEXP.isEnabled()) {
                        event.setExpToDrop(event.getExpToDrop() * 2);
                    }

                    if(Scenarios.TRIPLEXP.isEnabled()) {
                        event.setExpToDrop(event.getExpToDrop() * 3);
                    }
                }
                break;

            case GOLD_ORE:
                if(Scenarios.CUTCLEAN.isEnabled()) {
                    if(Scenarios.DOUBLEXP.isEnabled()) {
                        event.setExpToDrop(event.getExpToDrop() * 2);
                    }

                    if(Scenarios.TRIPLEXP.isEnabled()) {
                        event.setExpToDrop(event.getExpToDrop() * 3);
                    }
                }
                break;
        }
    }

    @EventHandler
    public void handleFurnaceSmeltEvent(FurnaceExtractEvent event) {
        if(Scenarios.DOUBLEXP.isEnabled()) {
            event.setExpToDrop(event.getExpToDrop() * 2);
        }

        if(Scenarios.TRIPLEXP.isEnabled()) {
            event.setExpToDrop(event.getExpToDrop() * 3);
        }
    }

}
