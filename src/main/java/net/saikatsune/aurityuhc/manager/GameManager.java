package net.saikatsune.aurityuhc.manager;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.PlayerState;
import net.saikatsune.aurityuhc.gamestate.GameState;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@SuppressWarnings("deprecation")
public class GameManager {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();

    private boolean whitelisted;
    private boolean teamGame;

    public void resetPlayer(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setLevel(0);
        player.setTotalExperience(0);
        player.setExp(0);
    }

    public void setPlayerState(Player player, PlayerState playerState) {
        aurityUHC.getPlayerState().put(player, playerState);
        if(playerState == PlayerState.PLAYER) {
            player.setGameMode(GameMode.SURVIVAL);

            for (Player allPlayers : aurityUHC.getPlayers()) {
                allPlayers.showPlayer(player);
            }

            for (Player allSpectators : aurityUHC.getSpectators()) {
                player.hidePlayer(allSpectators);
            }

            for (Player allPlayers : aurityUHC.getSpectators()) {
                allPlayers.showPlayer(player);
            }

            aurityUHC.getSpectators().remove(player);
            aurityUHC.getPlayers().add(player);
        } else if(playerState == PlayerState.SPECTATOR) {
            player.setGameMode(GameMode.CREATIVE);

            for (Player allPlayers : aurityUHC.getPlayers()) {
                allPlayers.hidePlayer(player);
            }

            for (Player allPlayers : aurityUHC.getSpectators()) {
                allPlayers.hidePlayer(player);
                allPlayers.hidePlayer(allPlayers);
            }

            aurityUHC.getPlayers().remove(player);
            aurityUHC.getSpectators().add(player);

            player.teleport(new Location(Bukkit.getWorld("uhc_world"), 0 , 100, 0));
        }
    }

    public void scatterPlayer(Player player, World world) {

        Random random = new Random();

        int x = random.nextInt(aurityUHC.getConfigManager().getBorderSize() - 1);
        int z = random.nextInt(aurityUHC.getConfigManager().getBorderSize() - 1);
        int y = Bukkit.getWorld(world.getName()).getHighestBlockYAt(x, z);

        Location location = new Location(Bukkit.getWorld(world.getName()), x, y ,z);

        player.teleport(location);
    }

    public void checkWinner() {
        if(!this.isTeamGame()) {
            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                if(aurityUHC.getPlayers().size() == 1) {
                    aurityUHC.getGameStateManager().setGameState(GameState.ENDING);

                    for (Player allPlayers : aurityUHC.getPlayers()) {
                        Bukkit.broadcastMessage(prefix + mColor + "Congratulations to " + allPlayers.getName() + " for winning this game!");

                        if(aurityUHC.isDatabaseActive()) {
                            aurityUHC.getDatabaseManager().addWins(allPlayers, 1);
                        }

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                allPlayers.getWorld().spawn(allPlayers.getLocation(), Firework.class);
                            }
                        }.runTaskTimer(aurityUHC, 0, 20);
                    }

                    Bukkit.broadcastMessage(prefix + ChatColor.RED + "The server restarts in 1 minute!");

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.shutdown();
                        }
                    }.runTaskLater(aurityUHC, 20 * 60) ;
                }
            }
        } else {
            if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                if(aurityUHC.getTeamManager().getTeams().size() == 1) {
                    aurityUHC.getGameStateManager().setGameState(GameState.ENDING);

                    List<String> winnerNames = new ArrayList<>();

                    for (Player allPlayers : aurityUHC.getPlayers()) {

                        winnerNames.add(allPlayers.getName());

                        if(aurityUHC.isDatabaseActive()) {
                            aurityUHC.getDatabaseManager().addWins(allPlayers, 1);
                        }

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                allPlayers.getWorld().spawn(allPlayers.getLocation(), Firework.class);
                            }
                        }.runTaskTimer(aurityUHC, 0, 20);
                    }

                    String[] stringArray = winnerNames.toArray(new String[winnerNames.size()]);

                    Bukkit.broadcastMessage(prefix + mColor + "Congratulations to " + Arrays.toString(stringArray) + " for winning this game!");

                    Bukkit.broadcastMessage(prefix + ChatColor.RED + "The server restarts in 1 minute!");

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.shutdown();
                        }
                    }.runTaskLater(aurityUHC, 20 * 60);
                }
            }
        }
    }

    public void playSound() {
        for (Player allPlayers : Bukkit.getOnlinePlayers()) {
            allPlayers.playSound(allPlayers.getLocation(), Sound.ORB_PICKUP, 1, 1);
        }
    }

    public void executeFinalHeal() {
        for (Player allPlayers : aurityUHC.getPlayers()) {
            allPlayers.setHealth(20);
            allPlayers.setFoodLevel(20);
        }
        this.playSound();
        Bukkit.broadcastMessage(prefix + mColor + "All players have been healed!");
    }

    public void endGracePeriod() {
        aurityUHC.setInGrace(false);
        this.playSound();
        Bukkit.broadcastMessage(prefix + mColor + "The grace period has ended!");
    }

    public Player getRandomPlayer() {
        int playerNumber = new Random().nextInt(aurityUHC.getPlayers().size());
        return (Player) aurityUHC.getPlayers().toArray()[playerNumber];
    }

    public void setWhitelisted(boolean whitelisted) {
        this.whitelisted = whitelisted;
    }

    public boolean isWhitelisted() {
        return whitelisted;
    }

    public void setTeamGame(boolean teamGame) {
        this.teamGame = teamGame;
    }

    public boolean isTeamGame() {
        return teamGame;
    }
}
