package net.saikatsune.aurityuhc.listener.config;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.Scenarios;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerDecayListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    @EventHandler
    public void handleLeavesDecayEvent(LeavesDecayEvent event) {
        Block block = event.getBlock();
        Location location = new Location(block.getWorld(),
                block.getLocation().getBlockX() + 0.0D,
                block.getLocation().getBlockY() + 0.0D,
                block.getLocation().getBlockZ() + 0.0D);

        Random random = new Random();

        if ((random.nextDouble() <= aurityUHC.getConfigManager().getAppleRate() * 0.01D) && (block.getType() ==
                Material.LEAVES || block.getType() == Material.LEAVES_2)) {

            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(location, new ItemStack(Material.APPLE, 1));
        }

        if ((random.nextDouble() <= 0.5 * 0.01D) && (block.getType() ==
                Material.LEAVES || block.getType() == Material.LEAVES_2)) {

            if(Scenarios.LUCKYLEAVES.isEnabled()) {
                block.setType(Material.AIR);
                block.getWorld().dropItemNaturally(location, new ItemStack(Material.GOLDEN_APPLE, 1));
            }
        }

    }

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Block block = event.getBlock();
        Location location = new Location(block.getWorld(),
                block.getLocation().getBlockX() + 0.0F,
                block.getLocation().getBlockY() + 0.0F,
                block.getLocation().getBlockZ() + 0.0F);

        Random random = new Random();

        if(aurityUHC.getConfigManager().isShears()) {
            if((random.nextDouble() <= aurityUHC.getConfigManager().getAppleRate() * 0.005D) && block.getType() ==
                    Material.LEAVES || block.getType() == Material.LEAVES_2 && player.getItemInHand().getType().
                    equals(Material.SHEARS)) {

                block.setType(Material.AIR);
                block.getWorld().dropItemNaturally(location, new ItemStack(Material.APPLE, 1));

            }
        }

        if((random.nextDouble() <= aurityUHC.getConfigManager().getAppleRate() * 0.01D) && block.getType() ==
                Material.LEAVES || block.getType() == Material.LEAVES_2 && !player.getItemInHand().getType().
                equals(Material.SHEARS)) {

            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(location, new ItemStack(Material.APPLE, 1));
        }

        if((random.nextDouble() <= aurityUHC.getConfigManager().getFlintRate() * 0.01D) && block.getType() ==
                Material.GRAVEL) {

            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(location, new ItemStack(Material.FLINT, 1));

        }
    }

}
