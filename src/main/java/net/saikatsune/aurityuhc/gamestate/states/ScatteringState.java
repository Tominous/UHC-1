package net.saikatsune.aurityuhc.gamestate.states;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScatteringState extends GameState {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    public void start() {
        for (Player allPlayers : Bukkit.getOnlinePlayers()) {
            aurityUHC.getScoreboardManager().createScoreboard(allPlayers);
        }
    }

    public void stop() {

    }
}
