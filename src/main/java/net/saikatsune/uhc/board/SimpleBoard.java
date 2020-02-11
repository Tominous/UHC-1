package net.saikatsune.uhc.board;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleBoard {

    private Player player;
    private Scoreboard scoreboard;
    private Objective objective;
    private List<String> oldLines;
    private boolean hidden;

    public SimpleBoard(Player player) {
        this.player = player;
        this.scoreboard = player.getScoreboard();

        if (scoreboard == Bukkit.getScoreboardManager().getMainScoreboard())
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        this.objective = scoreboard.getObjective("silent");

        if (this.objective == null)
            objective = scoreboard.registerNewObjective("silent", "dummy");

        IntStream.range(0, 16).forEach(i -> {
            if (scoreboard.getTeam("team-" + getTeamName(i)) == null)
                scoreboard.registerNewTeam("team-" + getTeamName(i));
        });

        this.oldLines = new ArrayList<>();
        this.player.setScoreboard(scoreboard);
    }

    public void updateTitle(String value) {
        this.objective.setDisplayName(value);
    }

    public void updateLine(int lineNumber, String value) {
        String[] prefixAndSuffix = getPrefixAndSuffix(value);

        Team team = scoreboard.getTeam("team-" + getTeamName(lineNumber));
        if (team == null)
            team = scoreboard.registerNewTeam("team-" + getTeamName(lineNumber));
        team.setPrefix(prefixAndSuffix[0]);
        team.setSuffix(prefixAndSuffix[1]);

        if (!team.getEntries().contains(getTeamName(lineNumber)))
            team.addEntry(getTeamName(lineNumber));

        this.objective.getScore(getTeamName(lineNumber)).setScore(lineNumber);
    }

    public void hide() {
        this.hidden = true;
        scoreboard.clearSlot(DisplaySlot.SIDEBAR);
    }

    public void show() {
        hidden = false;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void update(List<String> list) {
        if (hidden || list == null || player == null || list.size() == 0) return;

        if (list.size() != oldLines.size()) {
            for (int i = 0; i < oldLines.size() + 1; i++) {
                removeLine(i);
            }
        }

        while (list.size() > 15)
            list.remove(list.size() - 1);

        int score = list.size();

        for (String value : list) {
            updateLine(score--, value);
        }

        oldLines = list;
    }

    public void removeLine(int lineNumber) {
        scoreboard.resetScores(getTeamName(lineNumber));
    }

    private String[] getPrefixAndSuffix(String value) {
        String prefix = getPrefix(value);
        String suffix = getPrefix(ChatColor.getLastColors(prefix) + getSuffix(value));

        return new String[]{prefix, suffix};
    }

    private String getPrefix(String value) {
        return value.length() > 16 ? value.substring(0, 16) : value;
    }

    private String getSuffix(String value) {
        value = value.length() > 32 ? value.substring(0, 32) : value;

        return value.length() > 16 ? value.substring(16) : "";
    }

    private String getTeamName(int i) {
        return ChatColor.values()[i].toString();
    }
}