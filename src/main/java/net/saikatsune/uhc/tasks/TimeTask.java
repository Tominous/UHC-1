package net.saikatsune.uhc.tasks;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("deprecation")
public class TimeTask {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    private int taskID;

    private int uptimeHours;
    private int uptimeMinutes;
    private int uptimeSeconds;

    private int borderMinutes;

    public int getNextBorder() {
        if(game.getConfigManager().getBorderSize() == 3500) {
            return 3000;
        } else if(game.getConfigManager().getBorderSize() == 3000) {
            return 2500;
        } else if(game.getConfigManager().getBorderSize() == 2500) {
            return 2000;
        } else if(game.getConfigManager().getBorderSize() == 2000) {
            return 1500;
        } else if(game.getConfigManager().getBorderSize() == 1500) {
            return 1000;
        } else if(game.getConfigManager().getBorderSize() == 1000) {
            return 500;
        } else if(game.getConfigManager().getBorderSize() == 500) {
            return 100;
        } else if(game.getConfigManager().getBorderSize() == 100) {
            return 50;
        } else if(game.getConfigManager().getBorderSize() == 50) {
            return 25;
        }
        return 0;
    }

    public void runTask() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(game, new BukkitRunnable() {
            @Override
            public void run() {
                uptimeSeconds++;

                if (uptimeSeconds == 60) {
                    uptimeSeconds = 0;
                    uptimeMinutes += 1;
                    borderMinutes += 1;
                    if (uptimeMinutes == 60) {
                        uptimeMinutes = 0;
                        uptimeHours += 1;
                    }
                }

                if (uptimeMinutes == game.getConfigManager().getFinalHeal()) {
                    if (uptimeSeconds == 0) {
                        game.getGameManager().executeFinalHeal();
                    }
                }

                if (uptimeMinutes == game.getConfigManager().getGraceTime()) {
                    if (uptimeSeconds == 0) {
                        game.getGameManager().endGracePeriod();
                    }
                }

                if (borderMinutes == game.getConfigManager().getBorderTime() - 5) {
                    if (uptimeSeconds == 0) {
                        Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink by 500 blocks every 5 minutes now!");
                        game.getGameManager().playSound();
                    }
                }

                if(game.getConfigManager().getBorderSize() > 25) {
                    if (borderMinutes == game.getConfigManager().getBorderTime() || borderMinutes == game.getConfigManager().getBorderTime() + 5 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 10 || borderMinutes == game.getConfigManager().getBorderTime() + 15 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 20 || borderMinutes == game.getConfigManager().getBorderTime() + 25 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 30 || borderMinutes == game.getConfigManager().getBorderTime() + 35 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 40) {
                        if (uptimeSeconds == 0) {
                            game.getWorldManager().createTotalShrink();
                        }
                    } else if (borderMinutes == game.getConfigManager().getBorderTime() - 1 || borderMinutes == game.getConfigManager().getBorderTime() + 4 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 9 || borderMinutes == game.getConfigManager().getBorderTime() + 14 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 19 || borderMinutes == game.getConfigManager().getBorderTime() + 24 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 29 || borderMinutes == game.getConfigManager().getBorderTime() + 34 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 39) {
                        switch (uptimeSeconds) {
                            case 30:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 30 seconds!");
                                break;
                            case 40:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 20 seconds!");
                                break;
                            case 50:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 10 seconds!");
                                break;
                            case 51:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 9 seconds!");
                                break;
                            case 52:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 8 seconds!");
                                break;
                            case 53:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 7 seconds!");
                                break;
                            case 54:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 6 seconds!");
                                break;
                            case 55:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 5 seconds!");
                                break;
                            case 56:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 4 seconds!");
                                break;
                            case 57:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 3 seconds!");
                                break;
                            case 58:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 2 seconds!");
                                break;
                            case 59:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + getNextBorder() + "x" + getNextBorder() +
                                        " blocks in 1 second!");
                                break;
                        }
                    }
                }

            }
        }, 0, 20);
    }

    public void cancelTask() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public String getFormattedTime() {
        String formattedTime = "";

        if(uptimeHours >= 1) {
            if(uptimeHours < 10)
                formattedTime += "0";
            formattedTime += uptimeHours + ":";
        }

        if (uptimeMinutes < 10)
            formattedTime += "0";
        formattedTime += uptimeMinutes + ":";

        if (uptimeSeconds < 10)
            formattedTime += "0";
        formattedTime += uptimeSeconds;

        return formattedTime;
    }

}


