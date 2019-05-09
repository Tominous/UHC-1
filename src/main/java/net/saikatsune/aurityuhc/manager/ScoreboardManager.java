package net.saikatsune.aurityuhc.manager;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import net.saikatsune.aurityuhc.gamestate.states.LobbyState;
import net.saikatsune.aurityuhc.gamestate.states.ScatteringState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@SuppressWarnings("deprecation")
public class ScoreboardManager {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    private int taskID;

    public void createScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = scoreboard.registerNewObjective("practice", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        player.setScoreboard(scoreboard);

        changeScoreboard(player);
    }

    private void changeScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }

        Objective oldObj = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        oldObj.unregister();

        if (scoreboard.getObjective(DisplaySlot.BELOW_NAME) != null) {
            scoreboard.getObjective(DisplaySlot.BELOW_NAME).unregister();
        }

        Objective obj = scoreboard.registerNewObjective("practice", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.setDisplayName(aurityUHC.getConfig().getString("SCOREBOARD.HEADER").replace("&", "§"));

        if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
            Team newLine = scoreboard.registerNewTeam(ChatColor.GREEN.toString());
            newLine.addEntry("§1§7§m--------");
            newLine.setSuffix("----------");
            obj.getScore("§1§7§m--------").setScore(4);

            Team players = scoreboard.registerNewTeam(ChatColor.ITALIC.toString());
            players.addEntry(sColor + "Online: ");
            players.setSuffix(mColor + aurityUHC.getPlayers().size());
            obj.getScore(sColor + "Online: ").setScore(3);

            Team gameType = scoreboard.registerNewTeam(ChatColor.BLACK.toString());
            gameType.addEntry(sColor + "Type: ");
            if(aurityUHC.getGameManager().isTeamGame()) {
                gameType.setSuffix(mColor + "To" + aurityUHC.getTeamManager().getTeamSize());
            } else {
                gameType.setSuffix(mColor + "FFA");
            }
            obj.getScore(sColor + "Type: ").setScore(2);

            Team footer = scoreboard.registerNewTeam(ChatColor.DARK_AQUA.toString());
            footer.addEntry("§7§m--------");
            footer.setSuffix("----------");
            obj.getScore("§7§m--------").setScore(1);
        } else if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof ScatteringState) {
            Team newLine = scoreboard.registerNewTeam(ChatColor.GREEN.toString());
            newLine.addEntry("§1§7§m--------");
            newLine.setSuffix("----------");
            obj.getScore("§1§7§m--------").setScore(4);

            Team scattering = scoreboard.registerNewTeam(ChatColor.ITALIC.toString());
            scattering.addEntry(sColor + "Scattering: ");
            scattering.setSuffix(mColor + Bukkit.getWorld("world").getPlayers().size());
            obj.getScore(sColor + "Scattering: ").setScore(3);

            Team scattered = scoreboard.registerNewTeam(ChatColor.BLACK.toString());
            scattered.addEntry(sColor + "Scattered: ");
            scattered.setSuffix(mColor + Bukkit.getWorld("uhc_world").getPlayers().size());
            obj.getScore(sColor + "Scattered: ").setScore(2);

            Team footer = scoreboard.registerNewTeam(ChatColor.DARK_AQUA.toString());
            footer.addEntry("§7§m--------");
            footer.setSuffix("----------");
            obj.getScore("§7§m--------").setScore(1);
        } else if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            Team newLine = scoreboard.registerNewTeam(ChatColor.GREEN.toString());
            newLine.addEntry("§1§7§m--------");
            newLine.setSuffix("----------");
            obj.getScore("§1§7§m--------").setScore(6);

            Team gameTime = scoreboard.registerNewTeam(ChatColor.RED.toString());
            gameTime.addEntry(sColor + "Game Time: ");
            gameTime.setSuffix(mColor + aurityUHC.getTimeTask().getFormattedTime());
            obj.getScore(sColor + "Game Time: ").setScore(5);

            Team remaining = scoreboard.registerNewTeam(ChatColor.BLACK.toString());
            remaining.addEntry(sColor + "Remaining: ");
            remaining.setSuffix(mColor + aurityUHC.getPlayers().size());
            obj.getScore(sColor + "Remaining: ").setScore(4);

            Team kills = scoreboard.registerNewTeam(ChatColor.GOLD.toString());
            kills.addEntry(sColor + "Your Kills: ");
            kills.setSuffix(mColor + aurityUHC.getPlayerKills().get(player.getUniqueId()));
            obj.getScore(sColor + "Your Kills: ").setScore(3);

            Team border = scoreboard.registerNewTeam(ChatColor.STRIKETHROUGH.toString());
            border.addEntry(sColor + "Border Size: ");
            border.setSuffix(mColor + aurityUHC.getConfigManager().getBorderSize());
            obj.getScore(sColor + "Border Size: ").setScore(2);

            Team footer = scoreboard.registerNewTeam(ChatColor.DARK_AQUA.toString());
            footer.addEntry("§7§m--------");
            footer.setSuffix("----------");
            obj.getScore("§7§m--------").setScore(1);

            Objective healthPList = scoreboard.registerNewObjective("h", "health");
            healthPList.setDisplaySlot(DisplaySlot.PLAYER_LIST);

            Objective healthName = scoreboard.registerNewObjective("h1", "health");

            healthName.setDisplayName(ChatColor.DARK_RED + "❤");
            healthName.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
    }

    public void updateScoreboard() {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(aurityUHC, new BukkitRunnable() {
            @Override
            public void run() {
                for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                    Scoreboard scoreboard = allPlayers.getScoreboard();
                    if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        Team players = scoreboard.getTeam(ChatColor.ITALIC.toString());
                        players.setSuffix(mColor + aurityUHC.getPlayers().size());

                        Team gameType = scoreboard.getTeam(ChatColor.BLACK.toString());
                        if(aurityUHC.getGameManager().isTeamGame()) {
                            gameType.setSuffix(mColor + "To" + aurityUHC.getTeamManager().getTeamSize());
                        } else {
                            gameType.setSuffix(mColor + "FFA");
                        }
                    } else if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof ScatteringState) {
                        Team scattering = scoreboard.getTeam(ChatColor.ITALIC.toString());
                        scattering.setSuffix(mColor + Bukkit.getWorld("world").getPlayers().size());

                        Team scattered = scoreboard.getTeam(ChatColor.BLACK.toString());
                        scattered.setSuffix(mColor + Bukkit.getWorld("uhc_world").getPlayers().size());
                    } else if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                        Team gameTime = scoreboard.getTeam(ChatColor.RED.toString());
                        gameTime.setSuffix(mColor + aurityUHC.getTimeTask().getFormattedTime());

                        Team remaining = scoreboard.getTeam(ChatColor.BLACK.toString());
                        remaining.setSuffix(mColor + aurityUHC.getPlayers().size());

                        Team kills = scoreboard.getTeam(ChatColor.GOLD.toString());
                        kills.setSuffix(mColor + aurityUHC.getPlayerKills().get(allPlayers.getUniqueId()));

                        Team border = scoreboard.getTeam(ChatColor.STRIKETHROUGH.toString());
                        border.setSuffix(mColor + aurityUHC.getConfigManager().getBorderSize());
                    }
                }
            }
        }, 0, 20);
    }

    public void cancelUpdates() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
