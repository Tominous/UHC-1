package net.saikatsune.aurityuhc.listener;

import me.uhc.worldborder.Events.WorldBorderFillFinishedEvent;
import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockChangeListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(aurityUHC.getSpectators().contains(player)) event.setCancelled(true);

        if(!(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
            event.setCancelled(true);
        } else {
            if(aurityUHC.getPlayers().contains(player)) {
                if(event.getBlock().getType() == Material.DIAMOND_ORE) {
                    for (Player allPlayers : aurityUHC.getSpectators()) {
                        if(allPlayers.hasPermission("uhc.host")) {
                           if (aurityUHC.getReceiveDiamondAlerts().contains(allPlayers.getUniqueId())) {
                                allPlayers.sendMessage(prefix + mColor + player.getName() + sColor + " has mined diamonds!");
                           }
                        }
                    }
                } else if(event.getBlock().getType() == Material.GOLD_ORE) {
                    for (Player allPlayers : aurityUHC.getSpectators()) {
                        if(allPlayers.hasPermission("uhc.host")) {
                            if (aurityUHC.getReceiveGoldAlerts().contains(allPlayers.getUniqueId())) {
                                allPlayers.sendMessage(prefix + mColor + player.getName() + sColor + " has mined gold!");
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void handleBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(aurityUHC.getSpectators().contains(player)) event.setCancelled(true);

        if(!(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleChunkLoadFinishEvent(WorldBorderFillFinishedEvent event) {
        Bukkit.broadcastMessage(prefix + sColor + "Finished loading the world " + mColor + event.getWorld().getName() + sColor + "!");
        Bukkit.broadcastMessage(prefix + ChatColor.RED + "Restarting server in 10 seconds!");

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(aurityUHC, 10 * 20);
    }

    @EventHandler
    public void handleChunkUnloadEvent(ChunkUnloadEvent event) {
        event.setCancelled(true);
    }

}
