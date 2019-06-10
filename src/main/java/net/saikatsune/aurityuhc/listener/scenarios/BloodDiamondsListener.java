package net.saikatsune.aurityuhc.listener.scenarios;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.Scenarios;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BloodDiamondsListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            if(event.getBlock().getType() == Material.DIAMOND_ORE) {
                if(Scenarios.BLOODDIAMONDS.isEnabled()) {
                    player.setHealth(player.getHealth() - 1);
                }
            }
        }
    }

}
