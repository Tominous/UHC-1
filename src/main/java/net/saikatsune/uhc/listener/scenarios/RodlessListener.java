package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RodlessListener implements Listener {

    private Game game = Game.getInstance();

    @EventHandler
    public void handlePlayerInteractEvent(PlayerInteractEvent event) {
        if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            if(Scenarios.RODLESS.isEnabled()) {
                if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) &&
                        event.getPlayer().getItemInHand().getType() == Material.FISHING_ROD) {
                    event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                    event.setCancelled(true);
                }
            }
        }
    }

}
