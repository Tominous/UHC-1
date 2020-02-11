package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.PlayerState;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ScatterCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("scatter")) {
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                        if(game.getSpectators().contains(target)) {
                            game.getGameManager().resetPlayer(target);
                            game.getGameManager().setPlayerState(target, PlayerState.PLAYER);

                            Random randomLocation = new Random();

                            int x = randomLocation.nextInt(game.getConfigManager().getBorderSize() - 1);
                            int z = randomLocation.nextInt(game.getConfigManager().getBorderSize() - 1);
                            int y = Bukkit.getWorld("uhc_world").getHighestBlockYAt(x, z);

                            Location location = new Location(Bukkit.getWorld("uhc_world"), x, y ,z);

                            game.getGameManager().setScatterLocation(target, location);

                            target.teleport(game.getScatterLocation().get(target));

                            target.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, game.getConfigManager().getStarterFood()));

                            if(game.getGameManager().isTeamGame()) {
                                game.getTeamManager().createTeam(target.getUniqueId());
                            }

                            game.getLoggedPlayers().add(target.getUniqueId());
                            game.getWhitelisted().add(target.getUniqueId());

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
