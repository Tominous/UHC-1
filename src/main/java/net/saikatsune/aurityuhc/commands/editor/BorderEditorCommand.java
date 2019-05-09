package net.saikatsune.aurityuhc.commands.editor;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BorderEditorCommand implements CommandExecutor, Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("bordereditor")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                aurityUHC.getInventoryHandler().handleBorderEditorInventory(player);
            }
        }
        return false;
    }

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getClickedInventory() != null) {
            if(event.getClickedInventory().getName().equals(mColor + "Border Editor")) {
                if(event.getCurrentItem() != null) {
                    if(event.getCurrentItem().getType().equals(Material.BEDROCK)) {
                        if (!event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Border Editor")) {
                            event.setCancelled(true);
                            player.closeInventory();
                            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "3500 Border")) {
                                aurityUHC.getConfigManager().setBorderSize(3500);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world", 3500);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world_nether", 3500);
                            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "3000 Border")) {
                                aurityUHC.getConfigManager().setBorderSize(3000);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world", 3000);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world_nether", 3000);
                            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "2500 Border")) {
                                aurityUHC.getConfigManager().setBorderSize(2500);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world", 2500);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world_nether", 2500);
                            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "2000 Border")) {
                                aurityUHC.getConfigManager().setBorderSize(2000);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world", 2000);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world_nether", 2000);
                            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "1500 Border")) {
                                aurityUHC.getConfigManager().setBorderSize(1500);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world", 1500);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world_nether", 1500);
                            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "1000 Border")) {
                                aurityUHC.getConfigManager().setBorderSize(1000);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world", 1000);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world_nether", 1000);
                            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "500 Border")) {
                                aurityUHC.getConfigManager().setBorderSize(500);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world", 500);
                                aurityUHC.getWorldManager().shrinkBorder("uhc_world_nether", 500);
                            }
                            Bukkit.broadcastMessage(prefix + mColor + "Border Size " + sColor + "has changed to " + mColor + aurityUHC.getConfigManager().getBorderSize() + sColor + "!");
                        }
                    } else if(event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
