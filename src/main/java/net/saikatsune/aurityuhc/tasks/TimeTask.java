package net.saikatsune.aurityuhc.tasks;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("deprecation")
public class TimeTask {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    private int taskID;

    private int uptimeHours;
    private int uptimeMinutes;
    private int uptimeSeconds;

    private int borderMinutes;

    public void runTask() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(aurityUHC, new BukkitRunnable() {
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

                if (uptimeMinutes == aurityUHC.getConfigManager().getFinalHeal()) {
                    if (uptimeSeconds == 0) {
                        aurityUHC.getGameManager().executeFinalHeal();
                    }
                }

                if (uptimeMinutes == aurityUHC.getConfigManager().getGraceTime()) {
                    if (uptimeSeconds == 0) {
                        aurityUHC.getGameManager().endGracePeriod();
                    }
                }

                if (borderMinutes == aurityUHC.getConfigManager().getBorderTime() - 5) {
                    if (uptimeSeconds == 0) {
                        Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink by 500 blocks every 5 minutes now!");
                        aurityUHC.getGameManager().playSound();
                    }
                }

                if(aurityUHC.getConfigManager().getBorderSize() > 25) {
                    if (borderMinutes == aurityUHC.getConfigManager().getBorderTime() || borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 5 ||
                            borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 10 || borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 15 ||
                            borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 20 || borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 25 ||
                            borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 30 || borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 35 ||
                            borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 40) {
                        if (uptimeSeconds == 0) {
                            aurityUHC.getWorldManager().createTotalShrink();
                        }
                    } else if (borderMinutes == aurityUHC.getConfigManager().getBorderTime() - 1 || borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 4 ||
                            borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 9 || borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 14 ||
                            borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 19 || borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 24 ||
                            borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 29 || borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 34 ||
                            borderMinutes == aurityUHC.getConfigManager().getBorderTime() + 39) {
                        switch (uptimeSeconds) {
                            case 30:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 30 seconds!");
                                break;
                            case 40:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 20 seconds!");
                                break;
                            case 50:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 10 seconds!");
                                break;
                            case 51:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 9 seconds!");
                                break;
                            case 52:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 8 seconds!");
                                break;
                            case 53:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 7 seconds!");
                                break;
                            case 54:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 6 seconds!");
                                break;
                            case 55:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 5 seconds!");
                                break;
                            case 56:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 4 seconds!");
                                break;
                            case 57:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 3 seconds!");
                                break;
                            case 58:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 2 seconds!");
                                break;
                            case 59:
                                aurityUHC.getGameManager().playSound();
                                Bukkit.broadcastMessage(prefix + sColor + "The border shrinks in 1 second!");
                                break;
                        }
                    }
                }

            }
        }, 0, 20);
    }

    public int getUptimeMinutes() {
        return uptimeMinutes;
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


