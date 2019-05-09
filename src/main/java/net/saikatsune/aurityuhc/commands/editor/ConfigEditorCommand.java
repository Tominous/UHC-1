package net.saikatsune.aurityuhc.commands.editor;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ConfigEditorCommand implements CommandExecutor, Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String mColor = aurityUHC.getmColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("configeditor")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                aurityUHC.getInventoryHandler().handleConfigEditorInventory(player);
            }
        }
        return false;
    }

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getClickedInventory() != null) {
            if(event.getClickedInventory().getName().equals(mColor + "Config Editor")) {
                if(event.getCurrentItem() != null) {
                    if(event.getCurrentItem().getType().equals(Material.OBSIDIAN)) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Nether §7§l➡ §aTRUE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setNether(false);
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Nether §7§l➡ §cFALSE")){
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setNether(true);
                        } else {
                            return;
                        }
                        aurityUHC.getInventoryHandler().handleConfigEditorInventory(player);
                    } else if(event.getCurrentItem().getType().equals(Material.SHEARS)) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Shears §7§l➡ §aTRUE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setShears(false);
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Shears §7§l➡ §cFALSE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setShears(true);
                        } else {
                            return;
                        }
                        aurityUHC.getInventoryHandler().handleConfigEditorInventory(player);
                    } else if(event.getCurrentItem().getType().equals(Material.SUGAR)) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Speed I §7§l➡ §aTRUE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setSpeed1(false);
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Speed I §7§l➡ §cFALSE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setSpeed1(true);
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Speed II §7§l➡ §aTRUE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setSpeed2(false);
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Speed II §7§l➡ §cFALSE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setSpeed2(true);
                        } else {
                            return;
                        }
                        aurityUHC.getInventoryHandler().handleConfigEditorInventory(player);

                    } else if(event.getCurrentItem().getType().equals(Material.ENDER_PEARL)) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Enderpearl Damage §7§l➡ §aTRUE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setEnderpearlDamage(false);
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Enderpearl Damage §7§l➡ §cFALSE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setEnderpearlDamage(true);
                        } else {
                            return;
                        }
                        aurityUHC.getInventoryHandler().handleConfigEditorInventory(player);
                    } else if(event.getCurrentItem().getType().equals(Material.BLAZE_POWDER)) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Strength I §7§l➡ §aTRUE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setStrength1(false);
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Strength I §7§l➡ §cFALSE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setStrength1(true);
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Strength II §7§l➡ §aTRUE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setStrength2(false);
                        } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Strength II §7§l➡ §cFALSE")) {
                            event.setCancelled(true);
                            aurityUHC.getConfigManager().setStrength2(true);
                        } else {
                            return;
                        }
                        aurityUHC.getInventoryHandler().handleConfigEditorInventory(player);
                    } else if(event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
