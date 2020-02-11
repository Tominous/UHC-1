package net.saikatsune.uhc.handler;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamHandler {

    private Game game = Game.getInstance();

    private UUID teamLeader;

    private int teamNumber;

    private List<UUID> teamMembers = new ArrayList<>();

    private Inventory teamInventory = Bukkit.createInventory(null, 9*3, game.getmColor() + "Backpack");

    public TeamHandler(int teamNumber, UUID teamLeader) {
        this.teamNumber = teamNumber;
        this.teamLeader = teamLeader;

        teamMembers.add(teamLeader);
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public UUID getTeamLeader() {
        return teamLeader;
    }

    public List<UUID> getTeamMembers() {
        return teamMembers;
    }

    public Inventory getTeamInventory() {
        return teamInventory;
    }

}
