package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BarebonesListener implements Listener {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            if(Scenarios.BAREBONES.isEnabled()) {
                if(event.getBlock().getType() == Material.DIAMOND_ORE || event.getBlock().getType() == Material.GOLD_ORE) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    player.sendMessage(prefix + ChatColor.RED + "You can only mine iron!");
                }
            }
        }
    }

}
