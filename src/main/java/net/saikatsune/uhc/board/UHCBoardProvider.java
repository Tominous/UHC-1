package net.saikatsune.uhc.board;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.gamestate.states.LobbyState;
import net.saikatsune.uhc.gamestate.states.ScatteringState;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class UHCBoardProvider implements BoardProvider {

    private Game game;

    public UHCBoardProvider(Game game) {
        this.game = game;
    }

    @Override
    public String getTitle(Player player) {
        return game.getScoreboardManager().getConfig().getString("SCOREBOARDS.TITLE").replace("&", "§");
    }

    @Override
    public List<String> getBoardLines(Player player) {
        List<String> lines = new ArrayList<>();

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy");
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        FileConfiguration config = game.getScoreboardManager().getConfig();

        if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
            for (String string : config.getStringList("SCOREBOARDS.LOBBY.LINES")) {
                string = string.replace("%players%", String.valueOf(game.getPlayers().size())).
                        replace("%gameType%", game.getTeamSizeInString()).
                        replace("%gameTime%", game.getTimeTask().getFormattedTime()).
                        replace("%borderSize%", String.valueOf(game.getConfigManager().getBorderSize())).
                        replace("%kills%", String.valueOf(game.getPlayerKills().get(player.getUniqueId())).
                        replace("&", "§"));
                lines.add(string.replace("&", "§"));
            }
        } else if(game.getGameStateManager().getCurrentGameState() instanceof ScatteringState) {
            lines.clear();
            for (String string : config.getStringList("SCOREBOARDS.SCATTERING.LINES")) {
                string = string.replace("%players%", String.valueOf(game.getPlayers().size())).
                        replace("%gameType%", game.getTeamSizeInString()).
                        replace("%scattering%", String.valueOf(Bukkit.getWorld("world").getPlayers().size())).
                        replace("%scattered%", String.valueOf(Bukkit.getWorld("uhc_world").getPlayers().size())).
                        replace("%gameTime%", game.getTimeTask().getFormattedTime()).
                        replace("%borderSize%", String.valueOf(game.getConfigManager().getBorderSize())).
                        replace("%kills%", String.valueOf(game.getPlayerKills().get(player.getUniqueId())).
                        replace("&", "§"));
                lines.add(string.replace("&", "§"));
            }
        } else {
            lines.clear();
            for (String string : config.getStringList("SCOREBOARDS.INGAME.LINES")) {
                string = string.replace("%players%", String.valueOf(game.getPlayers().size())).
                        replace("%gameType%", game.getTeamSizeInString()).
                        replace("%scattering%", String.valueOf(Bukkit.getWorld("world").getPlayers().size())).
                        replace("%scattered%", String.valueOf(Bukkit.getWorld("uhc_world").getPlayers().size())).
                        replace("%gameTime%", game.getTimeTask().getFormattedTime()).
                        replace("%borderSize%", String.valueOf(game.getConfigManager().getBorderSize())).
                        replace("%kills%", String.valueOf(game.getPlayerKills().get(player.getUniqueId())).
                        replace("&", "§"));
                lines.add(string.replace("&", "§"));
            }
        }

        return lines;
    }
}
