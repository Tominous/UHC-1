package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.states.IngameState;
import net.saikatsune.uhc.handler.ItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TimebombListener implements Listener {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @EventHandler
    public void handlePlayerDeathEvent(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Location location = player.getLocation();
        Inventory inventory = player.getInventory();

        if (!Scenarios.TIMEBOMB.isEnabled()) {
            player.getWorld().dropItemNaturally(player.getLocation(), new ItemHandler(Material.GOLDEN_APPLE).setDisplayName(ChatColor.GOLD + "Golden Head").build());

            if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                if(Scenarios.BAREBONES.isEnabled()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.DIAMOND));
                    player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
                    player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.ARROW, 32));
                    player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.STRING, 2));
                }

                if(Scenarios.DIAMONDLESS.isEnabled()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.DIAMOND));
                }

                if(Scenarios.GOLDLESS.isEnabled()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GOLD_ORE, 8));
                }
            }
        } else {
            if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                event.getDrops().clear();
                player.getLocation().getBlock().breakNaturally();
                player.getLocation().getBlock().setType(Material.CHEST);
                player.getLocation().add(1.0D, 0.0D, 0.0D).getBlock().breakNaturally();
                player.getLocation().add(1.0D, 0.0D, 0.0D).getBlock().setType(Material.CHEST);
                player.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().setType(Material.AIR);
                player.getLocation().add(1.0D, 1.0D, 0.0D).getBlock().setType(Material.AIR);
                Chest chest = (Chest) player.getLocation().getBlock().getState();


                chest.getInventory().setContents(inventory.getContents());
                chest.getInventory().addItem(player.getInventory().getArmorContents());
                chest.getInventory().addItem(new ItemHandler(Material.GOLDEN_APPLE).setDisplayName(ChatColor.GOLD + "Golden Head").build());

                if(Scenarios.BAREBONES.isEnabled()) {
                    chest.getInventory().addItem(new ItemStack(Material.DIAMOND));
                    chest.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                    chest.getInventory().addItem(new ItemStack(Material.ARROW, 32));
                    chest.getInventory().addItem(new ItemStack(Material.STRING, 2));
                }

                if(Scenarios.DIAMONDLESS.isEnabled()) {
                    chest.getInventory().addItem(new ItemStack(Material.DIAMOND));
                }

                if(Scenarios.GOLDLESS.isEnabled()) {
                    chest.getInventory().addItem(new ItemStack(Material.GOLD_ORE, 8));
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        location.getWorld().createExplosion(location, 6.0F);
                        location.getWorld().strikeLightning(location);
                        Bukkit.broadcastMessage(prefix + mColor + "[TimeBomb] " + player.getName() + "'s " + sColor + "corpse has exploded!");
                    }
                }.runTaskLater(game, 30 * 20);
            }
        }
    }
}
