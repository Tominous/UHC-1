package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
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

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("alerts")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                game.getInventoryHandler().handleAlertsInventory(player);
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

                            if(game.getReceivePvpAlerts().contains(player.getUniqueId())) {
                                game.getReceivePvpAlerts().remove(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.RED + "You will no longer receive pvp alerts!");
                            } else {
                                game.getReceivePvpAlerts().add(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.GREEN + "You will now receive pvp alerts!");
                            }

                            game.getInventoryHandler().handleAlertsInventory(player);
                        }
                    } else if(event.getCurrentItem().getType() == Material.DIAMOND_ORE) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Diamond Alerts")) {
                            event.setCancelled(true);

                            if(game.getReceiveDiamondAlerts().contains(player.getUniqueId())) {
                                game.getReceiveDiamondAlerts().remove(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.RED + "You will no longer receive diamond alerts!");
                            } else {
                                game.getReceiveDiamondAlerts().add(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.GREEN + "You will now receive diamond alerts!");
                            }

                            game.getInventoryHandler().handleAlertsInventory(player);
                        }
                    } else if(event.getCurrentItem().getType() == Material.GOLD_ORE) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Gold Alerts")) {
                            event.setCancelled(true);

                            if(game.getReceiveGoldAlerts().contains(player.getUniqueId())) {
                                game.getReceiveGoldAlerts().remove(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.RED + "You will no longer receive gold alerts!");
                            } else {
                                game.getReceiveGoldAlerts().add(player.getUniqueId());
                                player.sendMessage(prefix + ChatColor.GREEN + "You will now receive gold alerts!");
                            }

                            game.getInventoryHandler().handleAlertsInventory(player);
                        }
                    } else if(event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

}
