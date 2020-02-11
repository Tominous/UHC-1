package net.saikatsune.uhc.commands.editor;

import net.saikatsune.uhc.Game;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WorldEditorCommand implements CommandExecutor, Listener {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("worldeditor")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                game.getInventoryHandler().handleWorldEditorInventory(player);
            }
        }
        return false;
    }

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getClickedInventory() != null) {
            if(event.getClickedInventory().getName().equals(mColor + "World Editor")) {
                if(event.getCurrentItem() != null) {
                    if(event.getCurrentItem().getType() == Material.GRASS) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Create World")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if(Bukkit.getWorld("uhc_world") == null) {
                                game.getWorldManager().createWorld("uhc_world", World.Environment.NORMAL, WorldType.NORMAL);
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The UHC world does already exist!");
                            }
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Load World")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if(Bukkit.getWorld("uhc_world") != null) {
                                game.getWorldManager().loadWorld("uhc_world", game.getConfigManager().getBorderSize(), 1000);
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The UHC world does not exist!");
                            }
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Delete World")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if(Bukkit.getWorld("uhc_world") != null) {
                                if(Bukkit.getWorld("uhc_world").getPlayers().size() < 1) {
                                    game.getWorldManager().deleteWorld("uhc_world");
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "Cannot delete world because there are players on it!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The UHC world does not exist!");
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Material.NETHERRACK) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Create Nether")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if(Bukkit.getWorld("uhc_nether") == null) {
                                game.getWorldManager().createWorld("uhc_nether", World.Environment.NETHER, WorldType.NORMAL);
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The UHC nether does already exist!");
                            }
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Load Nether")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if(Bukkit.getWorld("uhc_nether") != null) {
                                game.getWorldManager().loadWorld("uhc_nether", game.getConfigManager().getBorderSize(), 1000);
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The UHC nether does not exist!");
                            }
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Delete Nether")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if(Bukkit.getWorld("uhc_nether") != null) {
                                if(Bukkit.getWorld("uhc_nether").getPlayers().size() < 1) {
                                    game.getWorldManager().deleteWorld("uhc_nether");
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + "Cannot delete nether because there are players on it!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The UHC nether does not exist!");
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
