package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.PlayerState;
import net.saikatsune.aurityuhc.enums.Scenarios;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ScatterCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("scatter")) {
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                        if(aurityUHC.getSpectators().contains(target)) {
                            aurityUHC.getGameManager().resetPlayer(target);
                            aurityUHC.getGameManager().setPlayerState(target, PlayerState.PLAYER);
                            aurityUHC.getGameManager().scatterPlayer(target, Bukkit.getWorld("uhc_world"), aurityUHC.getConfigManager().getBorderSize());

                            target.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, aurityUHC.getConfigManager().getStarterFood()));

                            if(aurityUHC.getGameManager().isTeamGame()) {
                                aurityUHC.getTeamManager().createTeam(target.getUniqueId());
                            }

                            aurityUHC.getLoggedPlayers().add(target.getUniqueId());
                            aurityUHC.getWhitelisted().add(target.getUniqueId());

                            if(Scenarios.GONEFISHING.isEnabled()) {
                                target.getInventory().addItem(new ItemStack(Material.ANVIL, 20));

                                target.setLevel(30000);

                                ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
                                fishingRod.addUnsafeEnchantment(Enchantment.LUCK, 250);
                                fishingRod.addUnsafeEnchantment(Enchantment.LURE, 3);
                                fishingRod.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
                                target.getInventory().addItem(fishingRod);
                            }

                            if(Scenarios.INFINITEENCHANT.isEnabled()) {
                                target.setLevel(30000);
                                target.getInventory().addItem(new ItemStack(Material.ENCHANTMENT_TABLE, 64));
                                target.getInventory().addItem(new ItemStack(Material.ANVIL, 64));
                                target.getInventory().addItem(new ItemStack(Material.BOOKSHELF, 64));
                                target.getInventory().addItem(new ItemStack(Material.BOOKSHELF, 64));
                            }

                            Bukkit.broadcastMessage(prefix + mColor + target.getName() + sColor + " has been scattered!");
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + target.getName() + " is already in game!");
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "You cannot scatter this player right now!");
                    }
                } else {
                    player.sendMessage(prefix + ChatColor.RED + args[0] + " is currently offline!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /scatter (player)");
            }
        }
        return false;
    }
}
