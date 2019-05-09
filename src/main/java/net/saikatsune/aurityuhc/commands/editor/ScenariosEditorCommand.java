package net.saikatsune.aurityuhc.commands.editor;

import net.saikatsune.aurityuhc.enums.Scenarios;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ScenariosEditorCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("scenarioseditor")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.openInventory(Scenarios.toToggle());
            }
        }
        return false;
    }

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        if(event.getClickedInventory() != null) {
            if(event.getClickedInventory().getName().equalsIgnoreCase("Scenarios Editor")) {
                if(event.getCurrentItem() != null) {
                    if(event.getCurrentItem().getType() == Scenarios.CUTCLEAN.getScenarioItem().getType()) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bCutClean")) {
                            event.setCancelled(true);

                            if(Scenarios.CUTCLEAN.isEnabled()) {
                                Scenarios.CUTCLEAN.setEnabled(false);
                            } else {
                                Scenarios.CUTCLEAN.setEnabled(true);
                            }

                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.TIMEBOMB.getScenarioItem().getType()) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bTimeBomb")) {
                            event.setCancelled(true);

                            if(Scenarios.TIMEBOMB.isEnabled()) {
                                Scenarios.TIMEBOMB.setEnabled(false);
                            } else {
                                Scenarios.TIMEBOMB.setEnabled(true);
                            }

                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.TIMBER.getScenarioItem().getType()) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bTimber")) {
                            event.setCancelled(true);

                            if(Scenarios.TIMBER.isEnabled()) {
                                Scenarios.TIMBER.setEnabled(false);
                            } else {
                                Scenarios.TIMBER.setEnabled(true);
                            }

                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.SOUP.getScenarioItem().getType()) {
                        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bSoup")) {
                            event.setCancelled(true);

                            if(Scenarios.SOUP.isEnabled()) {
                                Scenarios.SOUP.setEnabled(false);
                            } else {
                                Scenarios.SOUP.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.BOWLESS.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bBowless")) {
                            event.setCancelled(true);

                            if (Scenarios.BOWLESS.isEnabled()) {
                                Scenarios.BOWLESS.setEnabled(false);
                            } else {
                                Scenarios.BOWLESS.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.RODLESS.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bRodless")) {
                            event.setCancelled(true);

                            if (Scenarios.RODLESS.isEnabled()) {
                                Scenarios.RODLESS.setEnabled(false);
                            } else {
                                Scenarios.RODLESS.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.NOFALL.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bNoFall")) {
                            event.setCancelled(true);

                            if (Scenarios.NOFALL.isEnabled()) {
                                Scenarios.NOFALL.setEnabled(false);
                            } else {
                                Scenarios.NOFALL.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.BLOODDIAMONDS.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bBloodDiamonds")) {
                            event.setCancelled(true);

                            if (Scenarios.BLOODDIAMONDS.isEnabled()) {
                                Scenarios.BLOODDIAMONDS.setEnabled(false);
                            } else {
                                Scenarios.BLOODDIAMONDS.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.FIRELESS.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bFireless")) {
                            event.setCancelled(true);

                            if (Scenarios.FIRELESS.isEnabled()) {
                                Scenarios.FIRELESS.setEnabled(false);
                            } else {
                                Scenarios.FIRELESS.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.BACKPACKS.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bBackpacks")) {
                            event.setCancelled(true);

                            if (Scenarios.BACKPACKS.isEnabled()) {
                                Scenarios.BACKPACKS.setEnabled(false);
                            } else {
                                Scenarios.BACKPACKS.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.DIAMONDLESS.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bDiamondless")) {
                            event.setCancelled(true);

                            if (Scenarios.DIAMONDLESS.isEnabled()) {
                                Scenarios.DIAMONDLESS.setEnabled(false);
                            } else {
                                Scenarios.DIAMONDLESS.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.GOLDLESS.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bGoldless")) {
                            event.setCancelled(true);

                            if (Scenarios.GOLDLESS.isEnabled()) {
                                Scenarios.GOLDLESS.setEnabled(false);
                            } else {
                                Scenarios.GOLDLESS.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.LONGSHOTS.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bLongShots")) {
                            event.setCancelled(true);

                            if (Scenarios.LONGSHOTS.isEnabled()) {
                                Scenarios.LONGSHOTS.setEnabled(false);
                            } else {
                                Scenarios.LONGSHOTS.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.HASTEYBOYS.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bHasteyBoys")) {
                            event.setCancelled(true);

                            if (Scenarios.HASTEYBOYS.isEnabled()) {
                                Scenarios.HASTEYBOYS.setEnabled(false);
                            } else {
                                Scenarios.HASTEYBOYS.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.LIMITATIONS.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bLimitations")) {
                            event.setCancelled(true);

                            if (Scenarios.LIMITATIONS.isEnabled()) {
                                Scenarios.LIMITATIONS.setEnabled(false);
                            } else {
                                Scenarios.LIMITATIONS.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.BAREBONES.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bBarebones")) {
                            event.setCancelled(true);

                            if (Scenarios.BAREBONES.isEnabled()) {
                                Scenarios.BAREBONES.setEnabled(false);
                            } else {
                                Scenarios.BAREBONES.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.FLOWERPOWER.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bFlowerPower")) {
                            event.setCancelled(true);

                            if (Scenarios.FLOWERPOWER.isEnabled()) {
                                Scenarios.FLOWERPOWER.setEnabled(false);
                            } else {
                                Scenarios.FLOWERPOWER.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.GONEFISHING.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bGoneFishing")) {
                            event.setCancelled(true);

                            if (Scenarios.GONEFISHING.isEnabled()) {
                                Scenarios.GONEFISHING.setEnabled(false);
                            } else {
                                Scenarios.GONEFISHING.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.INFINITEENCHANT.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bInfiniteEnchant")) {
                            event.setCancelled(true);

                            if (Scenarios.INFINITEENCHANT.isEnabled()) {
                                Scenarios.INFINITEENCHANT.setEnabled(false);
                            } else {
                                Scenarios.INFINITEENCHANT.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.DOUBLEXP.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bDoubleXP")) {
                            event.setCancelled(true);

                            if (Scenarios.DOUBLEXP.isEnabled()) {
                                Scenarios.DOUBLEXP.setEnabled(false);
                            } else {
                                Scenarios.DOUBLEXP.setEnabled(true);
                            }
                        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bTripleXP")) {
                            event.setCancelled(true);

                            if (Scenarios.TRIPLEXP.isEnabled()) {
                                Scenarios.TRIPLEXP.setEnabled(false);
                            } else {
                                Scenarios.TRIPLEXP.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.WEBCAGE.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bWebCage")) {
                            event.setCancelled(true);

                            if (Scenarios.WEBCAGE.isEnabled()) {
                                Scenarios.WEBCAGE.setEnabled(false);
                            } else {
                                Scenarios.WEBCAGE.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.NOCLEAN.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bNoClean")) {
                            event.setCancelled(true);

                            if (Scenarios.NOCLEAN.isEnabled()) {
                                Scenarios.NOCLEAN.setEnabled(false);
                            } else {
                                Scenarios.NOCLEAN.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.DOUBLEORES.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bDoubleOres")) {
                            event.setCancelled(true);

                            if (Scenarios.DOUBLEORES.isEnabled()) {
                                Scenarios.DOUBLEORES.setEnabled(false);
                            } else {
                                Scenarios.DOUBLEORES.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.TRIPLEORES.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bTripleOres")) {
                            event.setCancelled(true);

                            if (Scenarios.TRIPLEORES.isEnabled()) {
                                Scenarios.TRIPLEORES.setEnabled(false);
                            } else {
                                Scenarios.TRIPLEORES.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.LUCKYLEAVES.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bLuckyLeaves")) {
                            event.setCancelled(true);

                            if (Scenarios.LUCKYLEAVES.isEnabled()) {
                                Scenarios.LUCKYLEAVES.setEnabled(false);
                            } else {
                                Scenarios.LUCKYLEAVES.setEnabled(true);
                            }
                        }
                    } else if(event.getCurrentItem().getType() == Scenarios.OREFRENZY.getScenarioItem().getType()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bOreFrenzy")) {
                            event.setCancelled(true);

                            if (Scenarios.OREFRENZY.isEnabled()) {
                                Scenarios.OREFRENZY.setEnabled(false);
                            } else {
                                Scenarios.OREFRENZY.setEnabled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
