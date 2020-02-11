package net.saikatsune.uhc.listener.config;

import net.saikatsune.uhc.Game;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PlayerConsumeListener implements Listener {

    private Game game = Game.getInstance();

    @EventHandler
    public void handlePlayerConsumeEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        if(event.getItem().getType() == Material.POTION) {
            Potion potion = Potion.fromItemStack(event.getItem());

            if(potion.getType() == PotionType.SPEED) {
                if(potion.getLevel() == 1) {
                    if(!game.getConfigManager().isSpeed1()) {
                        event.setCancelled(true);
                    }
                } else if(potion.getLevel() == 2) {
                    if(!game.getConfigManager().isSpeed2()) {
                        event.setCancelled(true);
                    }
                }
            } else if(potion.getType() == PotionType.STRENGTH) {
                if(potion.getLevel() == 1) {
                    if(!game.getConfigManager().isStrength1()) {
                        event.setCancelled(true);
                    }
                } else if(potion.getLevel() == 2) {
                    if(!game.getConfigManager().isStrength2()) {
                        event.setCancelled(true);
                    }
                }
            }

        } else if ((event.getItem().getType() != null) &&
                (event.getItem().getType() == Material.GOLDEN_APPLE) &&
                (event.getItem().getItemMeta().getDisplayName() != null) &&
                (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Golden Head"))) {
            player.removePotionEffect(PotionEffectType.REGENERATION);
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
        } else if ((event.getItem().getType() != null) &&
                (event.getItem().getType() == Material.GOLDEN_APPLE)) {
            if(event.getItem().getData().getData() == 1) {
                event.setCancelled(true);
            }
        }

    }

}
