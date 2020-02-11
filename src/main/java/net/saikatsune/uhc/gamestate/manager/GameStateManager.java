package net.saikatsune.uhc.gamestate.manager;

import net.saikatsune.uhc.gamestate.GameState;
import net.saikatsune.uhc.gamestate.states.EndingState;
import net.saikatsune.uhc.gamestate.states.IngameState;
import net.saikatsune.uhc.gamestate.states.LobbyState;
import net.saikatsune.uhc.gamestate.states.ScatteringState;

public class GameStateManager {

    private GameState[] gameStates = new GameState[4];
    private GameState currentGameState;

    public GameStateManager() {
        gameStates[0] = new LobbyState();
        gameStates[1] = new ScatteringState();
        gameStates[2] = new IngameState();
        gameStates[3] = new EndingState();
    }

    public void setGameState(int gameStateIndex) {
        if(currentGameState != null) {
            currentGameState.stop();
        }
        currentGameState = gameStates[gameStateIndex];
        currentGameState.start();
    }

    public void stopCurrentGameState() {
        currentGameState.stop();
        currentGameState = null;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

}
