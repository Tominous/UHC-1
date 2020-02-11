package net.saikatsune.uhc.gamestate.states;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.gamestate.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class IngameState extends GameState {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    public void start() {
        game.getWorldManager().createBorderLayer("uhc_world", game.getConfigManager().getBorderSize(),4, null);

        game.getGameManager().playSound();

        game.setChatMuted(false);

        for(UUID players : game.getPlayers()) {

            Player allPlayers = Bukkit.getPlayer(players);

            allPlayers.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, game.getConfigManager().getStarterFood()));
        }

        if(game.getGameManager().isTeamGame()) {
            Bukkit.broadcastMessage(prefix + ChatColor.RED + "NOTE: Your team gets disbanded, when all team members leave the game!");
        }

        game.getTimeTask().runTask();

        game.getButcherTask().run();
        game.getRelogTask().startTask();
    }

    public void stop() {

    }
}
