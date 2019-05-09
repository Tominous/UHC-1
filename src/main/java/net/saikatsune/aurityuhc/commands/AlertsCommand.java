package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AlertsCommand implements CommandExecutor, Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("alerts")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                aurityUHC.getInventoryHandler().handleAlertsInventory(player);
            }
        }
        return false;
    }

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getClickedInventory() != null) {
            if(event.getCurrentItem() != null) {
                if(event.getClickedInventory().getName().equals(sColor + "Alerts: " + mColor + player.getName())) {
                    if(event.getCurrentItem().getType() == Material.IRON_SWORD) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "PvP Alerts")) {
                            event.setCancelled(true);

                            if(aurityUHC.getReceivePvpAlerts().contains(player.getUniqueId())) {
                                aurityUHC.getReceivePvpAlerts().remove(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.RED + "You will no longer receive pvp alerts!");
                            } else {
                                aurityUHC.getReceivePvpAlerts().add(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.GREEN + "You will now receive pvp alerts!");
                            }

                            aurityUHC.getInventoryHandler().handleAlertsInventory(player);
                        }
                    } else if(event.getCurrentItem().getType() == Material.DIAMOND_ORE) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Diamond Alerts")) {
                            event.setCancelled(true);

                            if(aurityUHC.getReceiveDiamondAlerts().contains(player.getUniqueId())) {
                                aurityUHC.getReceiveDiamondAlerts().remove(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.RED + "You will no longer receive diamond alerts!");
                            } else {
                                aurityUHC.getReceiveDiamondAlerts().add(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.GREEN + "You will now receive diamond alerts!");
                            }

                            aurityUHC.getInventoryHandler().handleAlertsInventory(player);
                        }
                    } else if(event.getCurrentItem().getType() == Material.GOLD_ORE) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Gold Alerts")) {
                            event.setCancelled(true);

                            if(aurityUHC.getReceiveGoldAlerts().contains(player.getUniqueId())) {
                                aurityUHC.getReceiveGoldAlerts().remove(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.RED + "You will no longer receive gold alerts!");
                            } else {
                                aurityUHC.getReceiveGoldAlerts().add(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.GREEN + "You will now receive gold alerts!");
                            }

                            aurityUHC.getInventoryHandler().handleAlertsInventory(player);
                        }
                    } else if(event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

}
