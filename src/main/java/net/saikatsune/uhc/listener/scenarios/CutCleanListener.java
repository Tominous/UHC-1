package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CutCleanListener implements Listener {

    private Game game = Game.getInstance();

    String prefix = game.getPrefix();

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(game.getSpectators().contains(player)) event.setCancelled(true);

        if(Scenarios.CUTCLEAN.isEnabled()) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }

            if (event.isCancelled()) return;

            switch (event.getBlock().getType()) {
                case IRON_ORE:
                    if ((player.getItemInHand().getType() != Material.DIAMOND_PICKAXE) && (player.getItemInHand().getType() != Material.IRON_PICKAXE) &&
                            (player.getItemInHand().getType() != Material.STONE_PICKAXE)) {
                        event.getBlock().setType(Material.AIR);
                        return;
                    }
                    event.getBlock().setType(Material.AIR);
                    if(!Scenarios.LIMITATIONS.isEnabled()) {
                        if((!Scenarios.DOUBLEORES.isEnabled()) && !Scenarios.TRIPLEORES.isEnabled()) {
                            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));
                        } else if(Scenarios.DOUBLEORES.isEnabled()) {
                            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 2));
                        } else {
                            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 3));
                        }
                    } else {
                        game.getLimitationsListener().getIronMined().putIfAbsent(player.getUniqueId(), 0);
                        if(game.getLimitationsListener().getIronMined().get(player.getUniqueId()) >= 64) {
                            event.getBlock().setType(Material.AIR);
                            player.sendMessage(prefix + ChatColor.RED + "You can only mine 64 iron!");
                            return;
                        } else if(game.getLimitationsListener().getIronMined().get(player.getUniqueId()) < 64) {
                            if((!Scenarios.DOUBLEORES.isEnabled()) && !Scenarios.TRIPLEORES.isEnabled()) {
                                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));
                            } else if(Scenarios.DOUBLEORES.isEnabled()) {
                                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 2));
                            } else {
                                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 3));
                            }
                            game.getLimitationsListener().getIronMined().put(player.getUniqueId(), game.getLimitationsListener().getIronMined().get(player.getUniqueId()) + 1);
                        }
                    }
                    if((!Scenarios.DOUBLEXP.isEnabled()) && !Scenarios.TRIPLEXP.isEnabled()) {
                        event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(2);
                    } else if(Scenarios.DOUBLEXP.isEnabled()) {
                        event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(4);
                    } else {
                        event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(6);
                    }
                    break;
                case GOLD_ORE:
                    if ((player.getItemInHand().getType() != Material.DIAMOND_PICKAXE) && (player.getItemInHand().getType() != Material.IRON_PICKAXE)) {
                        event.getBlock().setType(Material.AIR);
                        return;
                    }
                    event.getBlock().setType(Material.AIR);
                    if(!Scenarios.LIMITATIONS.isEnabled()) {
                        if(!Scenarios.BAREBONES.isEnabled()) {
                            if((!Scenarios.DOUBLEORES.isEnabled()) && !Scenarios.TRIPLEORES.isEnabled()) {
                                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
                            } else if(Scenarios.DOUBLEORES.isEnabled()) {
                                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 2));
                            } else {
                                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 3));
                            }
                        } else {
                            event.getBlock().setType(Material.AIR);
                            player.sendMessage(prefix + ChatColor.RED + "You can only mine iron!");
                            return;
                        }
                    } else {
                        game.getLimitationsListener().getGoldMined().putIfAbsent(player.getUniqueId(), 0);
                        if(game.getLimitationsListener().getGoldMined().get(player.getUniqueId()) >= 32) {
                            event.getBlock().setType(Material.AIR);
                            player.sendMessage(prefix + ChatColor.RED + "You can only mine 32 gold!");
                            return;
                        } else if(game.getLimitationsListener().getGoldMined().get(player.getUniqueId()) < 32) {
                            if((!Scenarios.DOUBLEORES.isEnabled()) && !Scenarios.TRIPLEORES.isEnabled()) {
                                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
                            } else if(Scenarios.DOUBLEORES.isEnabled()) {
                                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 2));
                            } else {
                                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 3));
                            }
                            game.getLimitationsListener().getGoldMined().put(player.getUniqueId(), game.getLimitationsListener().getGoldMined().get(player.getUniqueId()) + 1);
                        }
                    }
                    if((!Scenarios.DOUBLEXP.isEnabled()) && !Scenarios.TRIPLEXP.isEnabled()) {
                        event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(4);
                    } else if(Scenarios.DOUBLEXP.isEnabled()) {
                        event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(8);
                    } else {
                        event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(12);
                    }
                    break;
            }
        }
    }

    @EventHandler
    public void handleEntityDeathEvent(EntityDeathEvent event) {
        if(Scenarios.CUTCLEAN.isEnabled()) {
            switch (event.getEntityType()) {
                case COW:
                case MUSHROOM_COW:
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(Material.LEATHER, 1));
                    event.getDrops().add(new ItemStack(Material.COOKED_BEEF, 3));
                    break;
                case PIG:
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(Material.GRILLED_PORK, 3));
                    break;
                case CHICKEN:
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, 1));
                    event.getDrops().add(new ItemStack(Material.FEATHER, 1));
                    break;
                case HORSE:
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(Material.LEATHER, 1));
                    break;
                case SHEEP:
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(Material.LEATHER, 1));
                    break;
            }
        }
    }

}
