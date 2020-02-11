package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("list")) {

            List<String> moderators = new ArrayList<>();

            for (Player allSpectators : game.getSpectators()) {
                if(allSpectators.hasPermission("uhc.host")) {
                    moderators.add(allSpectators.getName());
                }
            }

            String[] stringArray = moderators.toArray(new String[moderators.size()]);

            player.sendMessage("§8§m---------------------------");
            player.sendMessage(prefix + sColor + "Online Players: " + mColor + Bukkit.getOnlinePlayers().size());
            player.sendMessage(prefix + sColor + "Alive Players: " + mColor + game.getPlayers().size());
            player.sendMessage("");
            player.sendMessage(prefix + sColor + "Spectators: " + mColor + game.getSpectators().size());
            player.sendMessage(prefix + sColor + "Moderators: " + mColor + Arrays.toString(stringArray));
            player.sendMessage("§8§m---------------------------");
        }
        return false;
    }
}
