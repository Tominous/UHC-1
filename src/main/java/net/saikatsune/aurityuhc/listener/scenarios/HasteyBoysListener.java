package net.saikatsune.aurityuhc.listener.scenarios;

import net.saikatsune.aurityuhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class HasteyBoysListener implements Listener {

    @EventHandler
    public void handlePrepareCraftEvent(PrepareItemCraftEvent event) {
        if(Scenarios.HASTEYBOYS.isEnabled()) {
            Set<Material> enchantmentList = new HashSet<>();

            enchantmentList.add(Material.WOOD_SPADE);
            enchantmentList.add(Material.WOOD_AXE);
            enchantmentList.add(Material.WOOD_PICKAXE);

            enchantmentList.add(Material.STONE_SPADE);
            enchantmentList.add(Material.STONE_AXE);
            enchantmentList.add(Material.STONE_PICKAXE);

            enchantmentList.add(Material.IRON_SPADE);
            enchantmentList.add(Material.IRON_AXE);
            enchantmentList.add(Material.IRON_PICKAXE);

            enchantmentList.add(Material.DIAMOND_SPADE);
            enchantmentList.add(Material.DIAMOND_AXE);
            enchantmentList.add(Material.DIAMOND_PICKAXE);

            enchantmentList.add(Material.GOLD_SPADE);
            enchantmentList.add(Material.GOLD_AXE);
            enchantmentList.add(Material.GOLD_PICKAXE);

            if(enchantmentList.contains(event.getRecipe().getResult().getType())) {
                ItemStack newItem = new ItemStack(event.getRecipe().getResult().getType());
                newItem.addEnchantment(Enchantment.DIG_SPEED, 3);
                newItem.addEnchantment(Enchantment.DURABILITY, 3);
                event.getInventory().setResult(newItem);
            }
        }
    }

}
