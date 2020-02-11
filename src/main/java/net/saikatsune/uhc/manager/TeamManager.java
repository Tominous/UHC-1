package net.saikatsune.uhc.manager;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.gamestate.states.LobbyState;
import net.saikatsune.uhc.handler.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TeamManager {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    private HashMap<Integer, TeamHandler> teams = new HashMap<>();

    private int teamNumber = 0;
    private int teamSize = 2;

    public void createTeam(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        teamNumber += 1;
        TeamHandler teamHandler = new TeamHandler(teamNumber, uuid);
        game.getTeamNumber().put(uuid, teamNumber);
        teams.put(teamNumber, teamHandler);
        player.sendMessage(prefix + sColor + "You have created " + mColor + "Team #" + teamNumber + sColor + "!");
    }

    public void addPlayerToTeam(int teamNumber, UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        TeamHandler teamHandler = teams.get(teamNumber);
        teamHandler.getTeamMembers().add(uuid);
        game.getTeamNumber().put(uuid, teamNumber);
        player.sendMessage(prefix + sColor + "You have joined " + mColor + "Team #" + teamNumber + sColor + "!");

        for (UUID players : game.getPlayers()) {

            Player allPlayers = Bukkit.getPlayer(players);

            if(game.getTeamNumber().get(allPlayers.getUniqueId()) == teamNumber) {
                allPlayers.sendMessage(prefix + mColor + player.getName() + sColor + " has joined the team!");
            }
        }
    }

    public void removePlayerFromTeam(int teamNumber, UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        TeamHandler teamHandler = teams.get(teamNumber);
        teamHandler.getTeamMembers().remove(uuid);
        player.sendMessage(prefix + sColor + "You have left " + mColor + "Team #" + teamNumber + sColor + "!");

        if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
            game.getTeamNumber().put(uuid, -1);
        }

        for (UUID players : game.getPlayers()) {

            Player allPlayers = Bukkit.getPlayer(players);

            if(game.getTeamNumber().get(allPlayers.getUniqueId()) == teamNumber) {
                allPlayers.sendMessage(prefix + mColor + player.getName() + sColor + " has left the team!");
            }
        }

        if(teamHandler.getTeamMembers().size() == 0) {
            game.getTeamManager().getTeams().remove(teamNumber);
        }
    }

    public void kickPlayerFromTeam(int teamNumber, UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        TeamHandler teamHandler = teams.get(teamNumber);
        teamHandler.getTeamMembers().remove(uuid);
        player.sendMessage(prefix + sColor + "You were kicked from " + mColor + "Team #" + teamNumber + sColor + "!");

        if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
            game.getTeamNumber().put(uuid, -1);
        }

        for (UUID players : game.getPlayers()) {

            Player allPlayers = Bukkit.getPlayer(players);

            if(game.getTeamNumber().get(allPlayers.getUniqueId()) == teamNumber) {
                allPlayers.sendMessage(prefix + mColor + player.getName() + sColor + " were kicked from the team!");
            }
        }

        if(teamHandler.getTeamMembers().size() == 0) {
            game.getTeamManager().getTeams().remove(teamNumber);
        }
    }

    public HashMap<Integer, TeamHandler> getTeams() {
        return teams;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }
}
