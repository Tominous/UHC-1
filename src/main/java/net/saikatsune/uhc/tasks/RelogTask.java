package net.saikatsune.uhc.tasks;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

@SuppressWarnings("deprecation")
public class RelogTask {

    private Game game = Game.getInstance();

    private int taskID;

    public void startTask() {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(game, new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    for (UUID loggedOutPlayers : game.getLoggedOutPlayers()) {
                        game.getLogoutTimer().put(loggedOutPlayers, game.getLogoutTimer().get(loggedOutPlayers) - 1);

                        if(game.getLogoutTimer().get(loggedOutPlayers) == 0) {
                            game.getLoggedOutPlayers().remove(loggedOutPlayers);

                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(loggedOutPlayers);

                            Bukkit.broadcastMessage(ChatColor.RED + offlinePlayer.getName() + ChatColor.GRAY +
                                    "[" + game.getPlayerKills().get(offlinePlayer.getUniqueId()) + "] " + ChatColor.YELLOW +
                                    "was disconnected for too long and has been disqualified.");

                            game.getGameManager().removeCombatVillager(game.getCombatVillagerUUID().get(offlinePlayer.getUniqueId()));
                            game.getLoggedPlayers().remove(offlinePlayer.getUniqueId());
                            game.getWhitelisted().remove(offlinePlayer.getUniqueId());
                            game.getPlayers().remove(offlinePlayer.getUniqueId());

                            if(game.isDatabaseActive()) {
                                game.getDatabaseManager().addDeaths(offlinePlayer, 1);
                            }

                            game.getGameManager().checkWinner();
                        }
                    }
                } catch (Exception ignored) {

                }
            }
        }, 0, 20);
    }

    public void cancelTask() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
