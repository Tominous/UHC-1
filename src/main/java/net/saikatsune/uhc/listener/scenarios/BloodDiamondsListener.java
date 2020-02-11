package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BloodDiamondsListener implements Listener {

    private Game game = Game.getInstance();

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            if(event.getBlock().getType() == Material.DIAMOND_ORE) {
                if(Scenarios.BLOODDIAMONDS.isEnabled()) {
                    player.setHealth(player.getHealth() - 1);
                }
            }
        }
    }

}
