package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class KillsTopCommand implements CommandExecutor {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("killstop")) {
            player.sendMessage("§8§m----------------------------");
            player.sendMessage(mColor + "Top 10 Kills: ");

            player.sendMessage("");

            Map<UUID, Integer> unsortedkills = new HashMap<>();
            for(UUID players : game.getPlayers()) {

                Player allPlayers = Bukkit.getPlayer(players);

                if(game.getPlayers().contains(players)) {
                    unsortedkills.put(allPlayers.getUniqueId(), game.getPlayerKills().get(allPlayers.getUniqueId()));
                }
            }
            Map<UUID, Integer> kills = sortByValue(unsortedkills);
            int x = 1;
            for(Object object : kills.keySet()) {
                if(x != 11) {
                    UUID uuid = (UUID) object;
                    if(kills.get(uuid) != 0) {
                        player.sendMessage(sColor + "- " + mColor + Bukkit.getOfflinePlayer(uuid).getName() + sColor + ": " + kills.get(uuid));
                    }
                    x++;
                } else {
                    break;
                }
            }
            player.sendMessage("§8§m----------------------------");
        }
        return false;
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}
