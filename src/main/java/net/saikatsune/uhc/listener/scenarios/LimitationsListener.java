package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.UUID;

public class LimitationsListener implements Listener {

    private Game game = Game.getInstance();

    private HashMap<UUID, Integer> diamondsMined = new HashMap<>();
    private HashMap<UUID, Integer> goldMined = new HashMap<>();
    private HashMap<UUID, Integer> ironMined = new HashMap<>();

    private String prefix = game.getPrefix();

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(Scenarios.LIMITATIONS.isEnabled()) {
            diamondsMined.putIfAbsent(player.getUniqueId(), 0);
            goldMined.putIfAbsent(player.getUniqueId(), 0);
            ironMined.putIfAbsent(player.getUniqueId(), 0);

            if(event.getBlock().getType() == Material.DIAMOND_ORE) {
                if(diamondsMined.get(player.getUniqueId()) < 16) {
                    diamondsMined.put(player.getUniqueId(), diamondsMined.get(player.getUniqueId()) + 1);
                } else if(diamondsMined.get(player.getUniqueId()) == 16) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    player.sendMessage(prefix + ChatColor.RED + "You can only mine 16 diamonds!");
                }
            }

            if(event.getBlock().getType() == Material.GOLD_ORE) {
                if(goldMined.get(player.getUniqueId()) < 32) {
                    goldMined.put(player.getUniqueId(), goldMined.get(player.getUniqueId()) + 1);
                } else if(goldMined.get(player.getUniqueId()) == 32) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    player.sendMessage(prefix + ChatColor.RED + "You can only mine 32 gold!");
                }
            }

            if(event.getBlock().getType() == Material.IRON_ORE) {
                if(ironMined.get(player.getUniqueId()) < 64) {
                    ironMined.put(player.getUniqueId(), ironMined.get(player.getUniqueId()) + 1);
                } else if(ironMined.get(player.getUniqueId()) == 64) {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    player.sendMessage(prefix + ChatColor.RED + "You can only mine 64 iron!");
                }
            }
        }
    }

    public HashMap<UUID, Integer> getDiamondsMined() {
        return diamondsMined;
    }

    public HashMap<UUID, Integer> getGoldMined() {
        return goldMined;
    }

    public HashMap<UUID, Integer> getIronMined() {
        return ironMined;
    }
}
