package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SetupCommand implements CommandExecutor, Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("setup")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                aurityUHC.getInventoryHandler().handleSetupInventory(player);
            }
        }
        return false;
    }

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getClickedInventory() != null) {
            if(event.getClickedInventory().getName().equals(mColor + "Server Setup")) {
                if(event.getCurrentItem() != null) {
                    if(event.getCurrentItem().getType() == Material.FLINT_AND_STEEL) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Game Setup")) {
                            event.setCancelled(true);

                            aurityUHC.getInventoryHandler().handleGameSetupInventory(player);
                        }
                    } else if(event.getCurrentItem().getType() == Material.DIAMOND_PICKAXE) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Lobby Setup")) {
                            event.setCancelled(true);

                            aurityUHC.getInventoryHandler().handleLobbySetupInventory(player);
                        }
                    } else if(event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                        event.setCancelled(true);
                    }
                }
            } else if(event.getClickedInventory().getName().equals(mColor + "Lobby Setup")) {
                if(event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                    event.setCancelled(true);
                } else if(event.getCurrentItem().getType() == Material.ANVIL) {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Spawn-Location")) {
                        event.setCancelled(true);
                        player.closeInventory();
                        aurityUHC.getLocationManager().setLocation("Spawn-Location", player.getLocation());
                        player.sendMessage(prefix + sColor + "You successfully" + mColor + " set " + sColor + "the " + mColor + "Spawn-Location" + sColor + "!");
                    }
                }
            } else if(event.getClickedInventory().getName().equals(mColor + "Game Setup")) {
                if(event.getCurrentItem().getType() == Material.GRASS) {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "World Editor")) {
                        event.setCancelled(true);
                        aurityUHC.getInventoryHandler().handleWorldEditorInventory(player);
                    }
                } else if(event.getCurrentItem().getType() == Material.BLAZE_ROD) {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Scenarios Editor")) {
                        event.setCancelled(true);
                        player.openInventory(Scenarios.toToggle());
                    }
                } else if(event.getCurrentItem().getType() == Material.BOOK_AND_QUILL) {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Config Editor")) {
                        event.setCancelled(true);
                        aurityUHC.getInventoryHandler().handleConfigEditorInventory(player);
                    }
                } else if(event.getCurrentItem().getType() == Material.BEDROCK) {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(mColor + "Border Editor")) {
                        event.setCancelled(true);
                        aurityUHC.getInventoryHandler().handleBorderEditorInventory(player);
                    }
                } else if(event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
