package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class WebCageListener implements Listener {

    private Game game = Game.getInstance();

    @EventHandler
    public void handlePlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            if(Scenarios.WEBCAGE.isEnabled()) {
                if(player.getKiller() != null) {
                    List<Location> locations = this.getSphere(player.getLocation(), 5, true);
                    for(Location blocks : locations) {
                        if(blocks.getBlock().getType() == Material.AIR) {
                            blocks.getBlock().setType(Material.WEB);
                        }
                    }
                }
            }
        }
    }

    private List<Location> getSphere(Location centerBlock, int centerRadius, boolean centerHollow) {

        List<Location> circleBlocks = new ArrayList<>();

        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();

        for(int x = bx - centerRadius; x <= bx + centerRadius; x++) {
            for(int y = by - centerRadius; y <= by + centerRadius; y++) {
                for(int z = bz - centerRadius; z <= bz + centerRadius; z++) {

                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));

                    if(distance < centerRadius * centerRadius && !(centerHollow && distance < ((centerRadius - 1) * (centerRadius - 1)))) {

                        Location l = new Location(centerBlock.getWorld(), x, y, z);

                        circleBlocks.add(l);
                    }
                }
            }
        }

        return circleBlocks;
    }

}
