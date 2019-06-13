package net.saikatsune.aurityuhc.handler;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class InventoryHandler {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    private void fillEmptySlots(Inventory inventory) {
        for(int slot = 0; slot < inventory.getSize(); slot++) {
            if(inventory.getItem(slot) == null) {
                inventory.setItem(slot, new ItemStack(Material.STAINED_GLASS_PANE));
            }
        }
    }

    public void handleSetupInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*1, mColor + "Server Setup");

        inventory.setItem(2, new ItemHandler(Material.FLINT_AND_STEEL).setDisplayName(mColor + "Game Setup").build());
        inventory.setItem(6, new ItemHandler(Material.DIAMOND_PICKAXE).setDisplayName(mColor + "Lobby Setup").build());

        this.fillEmptySlots(inventory);

        player.openInventory(inventory);
    }

    public void handleLobbySetupInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*1, mColor + "Lobby Setup");

        inventory.setItem(4, new ItemHandler(Material.ANVIL).setDisplayName(mColor + "Spawn-Location").build());

        this.fillEmptySlots(inventory);

        player.openInventory(inventory);
    }

    public void handleGameSetupInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*1, mColor + "Game Setup");

        inventory.setItem(1, new ItemHandler(Material.GRASS).setDisplayName(mColor + "World Editor").build());
        inventory.setItem(3, new ItemHandler(Material.BLAZE_ROD).setDisplayName(mColor + "Scenarios Editor").build());
        inventory.setItem(5, new ItemHandler(Material.BOOK_AND_QUILL).setDisplayName(mColor + "Config Editor").build());
        inventory.setItem(7, new ItemHandler(Material.BEDROCK).setDisplayName(mColor + "Border Editor").build());

        this.fillEmptySlots(inventory);

        player.openInventory(inventory);
    }

    public void handleWorldEditorInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, mColor + "World Editor");

        inventory.setItem(2, new ItemHandler(Material.GRASS).setDisplayName(mColor + "Create World").build());
        inventory.setItem(4, new ItemHandler(Material.GRASS).setDisplayName(mColor + "Load World").build());
        inventory.setItem(6, new ItemHandler(Material.GRASS).setDisplayName(mColor + "Delete World").build());

        inventory.setItem(20, new ItemHandler(Material.NETHERRACK).setDisplayName(mColor + "Create Nether").build());
        inventory.setItem(22, new ItemHandler(Material.NETHERRACK).setDisplayName(mColor + "Load Nether").build());
        inventory.setItem(24, new ItemHandler(Material.NETHERRACK).setDisplayName(mColor + "Delete Nether").build());

        this.fillEmptySlots(inventory);

        player.openInventory(inventory);
    }

    public void handleBorderEditorInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*1, mColor + "Border Editor");

        inventory.setItem(1, new ItemHandler(Material.BEDROCK).setDisplayName(mColor + "3500 Border").build());
        inventory.setItem(2, new ItemHandler(Material.BEDROCK).setDisplayName(mColor + "3000 Border").build());
        inventory.setItem(3, new ItemHandler(Material.BEDROCK).setDisplayName(mColor + "2500 Border").build());
        inventory.setItem(4, new ItemHandler(Material.BEDROCK).setDisplayName(mColor + "2000 Border").build());
        inventory.setItem(5, new ItemHandler(Material.BEDROCK).setDisplayName(mColor + "1500 Border").build());
        inventory.setItem(6, new ItemHandler(Material.BEDROCK).setDisplayName(mColor + "1000 Border").build());
        inventory.setItem(7, new ItemHandler(Material.BEDROCK).setDisplayName(mColor + "500 Border").build());

        this.fillEmptySlots(inventory);

        player.openInventory(inventory);
    }

    public void handleConfigEditorInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, mColor + "Config Editor");

        if(aurityUHC.getConfigManager().isNether()) {
            inventory.setItem(1, new ItemHandler(Material.OBSIDIAN).setDisplayName(mColor + "Nether §7§l➡ §aTRUE").build());
        } else {
            inventory.setItem(1, new ItemHandler(Material.OBSIDIAN).setDisplayName(mColor + "Nether §7§l➡ §cFALSE").build());
        }
        if(aurityUHC.getConfigManager().isShears()) {
            inventory.setItem(2, new ItemHandler(Material.SHEARS).setDisplayName(mColor + "Shears §7§l➡ §aTRUE").build());
        } else {
            inventory.setItem(2, new ItemHandler(Material.SHEARS).setDisplayName(mColor + "Shears §7§l➡ §cFALSE").build());
        }
        if(aurityUHC.getConfigManager().isSpeed1()) {
            inventory.setItem(3, new ItemHandler(Material.SUGAR).setDisplayName(mColor + "Speed I §7§l➡ §aTRUE").build());
        } else {
            inventory.setItem(3, new ItemHandler(Material.SUGAR).setDisplayName(mColor + "Speed I §7§l➡ §cFALSE").build());
        }
        if(aurityUHC.getConfigManager().isSpeed2()) {
            inventory.setItem(4, new ItemHandler(Material.SUGAR).setDisplayName(mColor + "Speed II §7§l➡ §aTRUE").build());
        } else {
            inventory.setItem(4, new ItemHandler(Material.SUGAR).setDisplayName(mColor + "Speed II §7§l➡ §cFALSE").build());
        }
        if(aurityUHC.getConfigManager().isStrength1()) {
            inventory.setItem(5, new ItemHandler(Material.BLAZE_POWDER).setDisplayName(mColor + "Strength I §7§l➡ §aTRUE").build());
        } else {
            inventory.setItem(5, new ItemHandler(Material.BLAZE_POWDER).setDisplayName(mColor + "Strength I §7§l➡ §cFALSE").build());
        }
        if(aurityUHC.getConfigManager().isStrength2()) {
            inventory.setItem(6, new ItemHandler(Material.BLAZE_POWDER).setDisplayName(mColor + "Strength II §7§l➡ §aTRUE").build());
        } else {
            inventory.setItem(6, new ItemHandler(Material.BLAZE_POWDER).setDisplayName(mColor + "Strength II §7§l➡ §cFALSE").build());
        }
        if(aurityUHC.getConfigManager().isEnderpearlDamage()) {
            inventory.setItem(7, new ItemHandler(Material.ENDER_PEARL).setDisplayName(mColor + "Enderpearl Damage §7§l➡ §aTRUE").build());
        } else {
            inventory.setItem(7, new ItemHandler(Material.ENDER_PEARL).setDisplayName(mColor + "Enderpearl Damage §7§l➡ §cFALSE").build());
        }
        if(aurityUHC.isDatabaseActive()) {
            inventory.setItem(22, new ItemHandler(Material.NETHER_STAR).setDisplayName(mColor + "Stats §7§l➡ §aTRUE").build());
        } else {
            inventory.setItem(22, new ItemHandler(Material.NETHER_STAR).setDisplayName(mColor + "Stats §7§l➡ §cFALSE").build());
        }

        this.fillEmptySlots(inventory);

        player.openInventory(inventory);
    }

    public void handleStaffInventory(Player player) {
        Inventory inventory = player.getInventory();

        inventory.setItem(0, new ItemHandler(Material.WATCH).setDisplayName(mColor + "Players").build());
        inventory.setItem(1, new ItemHandler(Material.BEACON).setDisplayName(mColor + "Random Player").build());

        inventory.setItem(4, new ItemHandler(Material.NETHER_STAR).setDisplayName(mColor + "Teleport to Center").build());

        inventory.setItem(8, new ItemHandler(Material.BOOK).setDisplayName(mColor + "Inspect Inventory").build());
    }

    public void handlePlayersInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6*9, mColor + "Alive Players");

        for (Player allPlayers : aurityUHC.getPlayers()) {
            ItemStack playerStack = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
            SkullMeta playersMeta = (SkullMeta) playerStack.getItemMeta();
            playersMeta.setOwner(allPlayers.getName());
            playersMeta.setDisplayName(allPlayers.getName());
            playerStack.setItemMeta(playersMeta);
            inventory.addItem(playerStack);
        }

        player.openInventory(inventory);
    }

    public void handleStatsInventory(Player player, OfflinePlayer toWatch) {
        Inventory inventory = Bukkit.createInventory(null, 9*1, sColor + "Stats: " + mColor + toWatch.getName());

        inventory.setItem(2, new ItemHandler(Material.IRON_SWORD).setDisplayName(mColor + "Kills: " + sColor +
                aurityUHC.getDatabaseManager().getKills(toWatch)).build());

        inventory.setItem(4, new ItemHandler(Material.FIREBALL).setDisplayName(mColor + "Deaths: " + sColor +
                aurityUHC.getDatabaseManager().getDeaths(toWatch)).build());

        inventory.setItem(6, new ItemHandler(Material.NETHER_STAR).setDisplayName(mColor + "Wins: " + sColor +
                aurityUHC.getDatabaseManager().getWins(toWatch)).build());

        this.fillEmptySlots(inventory);

        player.openInventory(inventory);
    }

    public void handleAlertsInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, sColor + "Alerts: " + mColor + player.getName());

        if(aurityUHC.getReceivePvpAlerts().contains(player.getUniqueId())) {
            inventory.setItem(11, new ItemHandler(Material.IRON_SWORD).setDisplayName(mColor + "PvP Alerts").setLore(Arrays.asList("§7§m-----------",
                    "§7> §aEnabled", "§7§m-----------")).build());
        } else {
            inventory.setItem(11, new ItemHandler(Material.IRON_SWORD).setDisplayName(mColor + "PvP Alerts").setLore(Arrays.asList("§7§m-----------",
                    "§7> §cDisabled", "§7§m-----------")).build());
        }

        if(aurityUHC.getReceiveDiamondAlerts().contains(player.getUniqueId())) {
            inventory.setItem(13, new ItemHandler(Material.DIAMOND_ORE).setDisplayName(mColor + "Diamond Alerts").setLore(Arrays.asList("§7§m-----------",
                    "§7> §aEnabled", "§7§m-----------")).build());
        } else {
            inventory.setItem(13, new ItemHandler(Material.DIAMOND_ORE).setDisplayName(mColor + "Diamond Alerts").setLore(Arrays.asList("§7§m-----------",
                    "§7> §cDisabled", "§7§m-----------")).build());
        }

        if(aurityUHC.getReceiveGoldAlerts().contains(player.getUniqueId())) {
            inventory.setItem(15, new ItemHandler(Material.GOLD_ORE).setDisplayName(mColor + "Gold Alerts").setLore(Arrays.asList("§7§m-----------",
                    "§7> §aEnabled", "§7§m-----------")).build());
        } else {
            inventory.setItem(15, new ItemHandler(Material.GOLD_ORE).setDisplayName(mColor + "Gold Alerts").setLore(Arrays.asList("§7§m-----------",
                    "§7> §cDisabled", "§7§m-----------")).build());
        }

        this.fillEmptySlots(inventory);

        player.openInventory(inventory);
    }

    public void handlePracticeInventory(Player player) {
        PlayerInventory inventory = player.getInventory();

        inventory.setItem(0, new ItemHandler(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
        inventory.setItem(1, new ItemStack(Material.FISHING_ROD));
        inventory.setItem(2, new ItemHandler(Material.BOW).addEnchantment(Enchantment.ARROW_INFINITE, 1).build());

        inventory.setHelmet(new ItemHandler(Material.IRON_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        inventory.setChestplate(new ItemHandler(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        inventory.setLeggings(new ItemHandler(Material.IRON_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        inventory.setBoots(new ItemHandler(Material.IRON_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());

        inventory.setItem(8, new ItemStack(Material.ARROW));
    }

}
