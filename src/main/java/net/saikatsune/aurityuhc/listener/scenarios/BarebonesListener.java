package net.saikatsune.aurityuhc.listener.scenarios;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.Scenarios;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BarebonesListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
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
