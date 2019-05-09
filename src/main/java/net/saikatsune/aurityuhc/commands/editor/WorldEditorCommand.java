package net.saikatsune.aurityuhc.commands.editor;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WorldEditorCommand implements CommandExecutor, Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("worldeditor")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                aurityUHC.getInventoryHandler().handleWorldEditorInventory(player);
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
                                aurityUHC.getWorldManager().createWorld("uhc_world", World.Environment.NORMAL);
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The UHC world does already exist!");
                            }
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Load World")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if(Bukkit.getWorld("uhc_world") != null) {
                                aurityUHC.getWorldManager().loadWorld("uhc_world", aurityUHC.getConfigManager().getBorderSize(), 1000);
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The UHC world does not exist!");
                            }
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Delete World")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if(Bukkit.getWorld("uhc_world") != null) {
                                if(Bukkit.getWorld("uhc_world").getPlayers().size() < 1) {
                                    aurityUHC.getWorldManager().deleteWorld("uhc_world");
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
                                aurityUHC.getWorldManager().createWorld("uhc_nether", World.Environment.NETHER);
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The UHC nether does already exist!");
                            }
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Load Nether")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if(Bukkit.getWorld("uhc_nether") != null) {
                                aurityUHC.getWorldManager().loadWorld("uhc_nether", aurityUHC.getConfigManager().getBorderSize(), 1000);
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "The UHC nether does not exist!");
                            }
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Delete Nether")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if(Bukkit.getWorld("uhc_nether") != null) {
                                if(Bukkit.getWorld("uhc_nether").getPlayers().size() < 1) {
                                    aurityUHC.getWorldManager().deleteWorld("uhc_nether");
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
