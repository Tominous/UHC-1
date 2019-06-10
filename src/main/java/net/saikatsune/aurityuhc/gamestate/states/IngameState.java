package net.saikatsune.aurityuhc.gamestate.states;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IngameState extends GameState {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    public void start() {
        aurityUHC.getWorldManager().createBorderLayer("uhc_world", aurityUHC.getConfigManager().getBorderSize(),4, null);

        aurityUHC.getGameManager().playSound();

        aurityUHC.setChatMuted(false);

        for(Player allPlayers : aurityUHC.getPlayers()) {
            allPlayers.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, aurityUHC.getConfigManager().getStarterFood()));
        }

        if(aurityUHC.getGameManager().isTeamGame()) {
            Bukkit.broadcastMessage(prefix + ChatColor.RED + "NOTE: Your team gets disbanded, when all team members leave the game!");
        }

        aurityUHC.getTimeTask().runTask();

        for (Player allPlayers : Bukkit.getOnlinePlayers()) {
            aurityUHC.getScoreboardManager().createScoreboard(allPlayers);
        }

    }

    public void stop() {

    }
}
