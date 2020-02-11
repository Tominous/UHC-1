package net.saikatsune.uhc.listener;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.gamestate.states.IngameState;
import net.saikatsune.uhc.handler.ItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInteractListener implements Listener {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @EventHandler
    public void handlePlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(game.getSpectators().contains(player)) event.setCancelled(true);

        if(!(game.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
            if(!game.getArenaPlayers().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if(game.getSpectators().contains(player)) {
            if(player.getItemInHand().getType() == Material.NETHER_STAR) {
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Teleport to Center")) {
                    player.teleport(new Location(Bukkit.getWorld("uhc_world"), 0 , 100, 0));
                    player.sendMessage(prefix + sColor + "You have been teleported to the " + mColor + "center of the map" + sColor + "!");
                }
            } else if(player.getItemInHand().getType() == Material.BEACON) {
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Random Player")) {
                    if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                        if(game.getPlayers().size() >= 2) {
                            if(game.getGameManager().getRandomPlayer() != player) {
                                try {
                                    player.teleport(game.getGameManager().getRandomPlayer());
                                } catch (Exception exception) {
                                    player.sendMessage(prefix + ChatColor.RED + "Teleporting to a random player has failed..");
                                }
                            }
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "There is currently no game running!");
                    }
                }
            } else if(player.getItemInHand().getType() == Material.WATCH) {
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Players")) {
                    if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                        try {
                            game.getInventoryHandler().handlePlayersInventory(player);
                        } catch (Exception exception) {
                            player.sendMessage(prefix + ChatColor.RED + "Finding players to teleport has failed..");
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "There is currently no game running!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void handlePlayerMovementEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (this.game.getSpectators().contains(player) && (
                !player.hasPermission("uhc.host") || !player.hasPermission("uhc.donator")) && (
                player.getLocation().getBlockX() > 50 || player.getLocation().getBlockZ() > 50 || player
                        .getLocation().getBlockX() < -50 || player.getLocation().getBlockZ() < -50)) {
            player.teleport(new Location(Bukkit.getWorld("uhc_world"), 0.0D, 100.0D, 0.0D));
            player.sendMessage(this.prefix + ChatColor.RED + "You may only spectate 50x50 blocks!");
        }
    }

    @EventHandler
    public void handlePlayerInteractAtEntityEvent(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Player) {
            Player player = event.getPlayer();
            Player interacted = (Player) event.getRightClicked();

            if(player.getItemInHand().getType() == Material.BOOK) {
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Inspect Inventory")) {
                    if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
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
        } else if(event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();

            if(villager.getName().contains("[CombatLogger] ")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handlePlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if(game.getSpectators().contains(player)) event.setCancelled(true);

        if(!(game.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePlayerPickupEvent(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        if(game.getSpectators().contains(player)) event.setCancelled(true);

        if(!(game.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
            if(!game.getArenaPlayers().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(game.getSpectators().contains(player)) event.setCancelled(true);

        if(event.getClickedInventory() != null) {
            if(event.getCurrentItem() != null) {
                if(event.getClickedInventory().getName().equalsIgnoreCase("Scenarios Explained")) {
                    event.setCancelled(true);
                } else if(event.getClickedInventory().getName().contains(sColor + "Stats")) {
                    event.setCancelled(true);
                }

                if(event.getCurrentItem().getType() == Material.SKULL_ITEM) {
                    if(game.getSpectators().contains(player)) {
                        Player target = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName());
                        player.teleport(target);
                    }
                }
            }
        }
    }

}
