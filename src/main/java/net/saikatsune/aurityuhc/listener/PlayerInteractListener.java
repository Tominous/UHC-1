package net.saikatsune.aurityuhc.listener;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import net.saikatsune.aurityuhc.handler.ItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInteractListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @EventHandler
    public void handlePlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(aurityUHC.getSpectators().contains(player)) event.setCancelled(true);

        if(!(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
            if(!aurityUHC.getArenaPlayers().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if(aurityUHC.getSpectators().contains(player)) {
            if(player.getItemInHand().getType() == Material.NETHER_STAR) {
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Teleport to Center")) {
                    player.teleport(new Location(Bukkit.getWorld("uhc_world"), 0 , 100, 0));
                    player.sendMessage(prefix + sColor + "You have been teleported to the " + mColor + "center of the map" + sColor + "!");
                }
            } else if(player.getItemInHand().getType() == Material.BEACON) {
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Random Player")) {
                    if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                        if(aurityUHC.getPlayers().size() >= 2) {
                            if(aurityUHC.getGameManager().getRandomPlayer() != player) {
                                player.teleport(aurityUHC.getGameManager().getRandomPlayer());
                            }
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "There is currently no game running!");
                    }
                }
            } else if(player.getItemInHand().getType() == Material.WATCH) {
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Players")) {
                    if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                        aurityUHC.getInventoryHandler().handlePlayersInventory(player);
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "There is currently no game running!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void handlePlayerInteractAtEntityEvent(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Player) {
            Player player = event.getPlayer();
            Player interacted = (Player) event.getRightClicked();

            if(player.getItemInHand().getType() == Material.BOOK) {
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Inspect Inventory")) {
                    if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                        Inventory inventory = Bukkit.createInventory(null, 54, mColor + "" + interacted.getName() + "'s Inventory");
                        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE);
                        PlayerInventory playerInventory = interacted.getInventory();
                        inventory.setItem(0, pane);
                        inventory.setItem(1, pane);
                        inventory.setItem(2, playerInventory.getHelmet());
                        inventory.setItem(3, playerInventory.getChestplate());
                        inventory.setItem(4, pane);
                        inventory.setItem(5, playerInventory.getLeggings());
                        inventory.setItem(6, playerInventory.getBoots());
                        inventory.setItem(7, pane);
                        inventory.setItem(8, pane);
                        for (int i = 9; i < 45; i++)
                        {
                            int slot = i - 9;
                            inventory.setItem(i, playerInventory.getItem(slot));
                        }
                        ItemStack level = new ItemHandler(Material.EXP_BOTTLE).setDisplayName(mColor + interacted.getLevel() + " levels").build();
                        ItemStack health = new ItemHandler(Material.POTION).setDisplayName(mColor + Math.round(interacted.getHealth()) + "/" + (int) interacted.getMaxHealth()).build();
                        ItemStack head = new ItemHandler(Material.CAKE).setDisplayName(mColor + interacted.getName()).build();
                        ItemStack hunger = new ItemHandler(Material.COOKED_BEEF).setDisplayName(mColor + interacted.getFoodLevel() + "/20").build();
                        inventory.setItem(45, pane);
                        inventory.setItem(46, level);
                        inventory.setItem(47, pane);
                        inventory.setItem(48, health);
                        inventory.setItem(49, pane);
                        inventory.setItem(50, head);
                        inventory.setItem(51, pane);
                        inventory.setItem(52, hunger);
                        inventory.setItem(53, pane);
                        player.openInventory(inventory);
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "There is currently no game running!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void handlePlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if(aurityUHC.getSpectators().contains(player)) event.setCancelled(true);

        if(!(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerPickupEvent(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if(aurityUHC.getSpectators().contains(player)) event.setCancelled(true);

        if(!(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
            if(!aurityUHC.getArenaPlayers().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(aurityUHC.getSpectators().contains(player)) event.setCancelled(true);

        if(event.getClickedInventory() != null) {
            if(event.getCurrentItem() != null) {
                if(event.getClickedInventory().getName().equalsIgnoreCase("Scenarios Explained")) {
                    event.setCancelled(true);
                } else if(event.getClickedInventory().getName().contains(sColor + "Stats")) {
                    event.setCancelled(true);
                }

                if(event.getCurrentItem().getType() == Material.SKULL_ITEM) {
                    if(aurityUHC.getSpectators().contains(player)) {
                        Player target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName());
                        player.teleport(target);
                    }
                }
            }
        }
    }

}
