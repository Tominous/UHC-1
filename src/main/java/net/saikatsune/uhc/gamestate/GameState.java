package net.saikatsune.uhc.gamestate;

public abstract class GameState {

    public static final int LOBBY = 0,
                            SCATTERING = 1,
                            INGAME = 2,
                            ENDING = 3;

    public abstract void start();
    public abstract void stop();
}
