package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.enums.Scenarios;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class OreFrenzyListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void handleBlockBreakEvent(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if(Scenarios.OREFRENZY.isEnabled()) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }

            if (event.isCancelled()) return;
            switch (event.getBlock().getType()) {
                case LAPIS_ORE:
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.POTION, 1, (short) 16389));
                    event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(3);
                    break;
                case EMERALD_ORE:
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.ARROW, 32));
                    event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(4);
                    break;
                case REDSTONE_ORE:
                case GLOWING_REDSTONE_ORE:
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.BOOK));
                    event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(2);
                    break;
                case DIAMOND_ORE:
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.DIAMOND));
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.EXP_BOTTLE, 4));
                    event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(5);
                    break;
                case QUARTZ_ORE:
                    event.setCancelled(true);
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.TNT));
                    event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(3);
                    break;
            }
        }

    }

}
