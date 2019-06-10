package net.saikatsune.aurityuhc.listener;

import me.uhc.worldborder.Events.WorldBorderFillFinishedEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

                               TextComponent textComponent = new TextComponent();

                               textComponent.setText(prefix + mColor + player.getName() + sColor + " has mined diamonds!");

                               textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                       new ComponentBuilder(sColor + "Click to teleport to " + mColor + player.getName() + sColor + "!").create()));
                               textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName()));

                               allPlayers.spigot().sendMessage(textComponent);
                           }
                        }
                    }
                } else if(event.getBlock().getType() == Material.GOLD_ORE) {
                    for (Player allPlayers : aurityUHC.getSpectators()) {
                        if(allPlayers.hasPermission("uhc.host")) {
                            if (aurityUHC.getReceiveGoldAlerts().contains(allPlayers.getUniqueId())) {
                                TextComponent textComponent = new TextComponent();

                                textComponent.setText(prefix + mColor + player.getName() + sColor + " has mined gold!");

                                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                        new ComponentBuilder(sColor + "Click to teleport to " + mColor + player.getName() + sColor + "!").create()));
                                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName()));

                                allPlayers.spigot().sendMessage(textComponent);
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
