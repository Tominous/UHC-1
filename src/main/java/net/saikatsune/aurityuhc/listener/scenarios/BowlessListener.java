package net.saikatsune.aurityuhc.listener.scenarios;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.Scenarios;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BowlessListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            if(Scenarios.BOWLESS.isEnabled()) {
                if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) &&
                        event.getPlayer().getItemInHand().getType() == Material.BOW) {
                    event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                    event.setCancelled(true);
                }
            }
        }
    }

}
