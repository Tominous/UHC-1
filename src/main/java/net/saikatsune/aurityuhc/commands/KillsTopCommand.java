package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.states.LobbyState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class KillsTopCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("killstop")) {
            player.sendMessage("§8§m----------------------------");
            player.sendMessage(mColor + "Top 10 Kills: ");

            player.sendMessage("");

            Map<UUID, Integer> unsortedkills = new HashMap<>();
            for(Player players : aurityUHC.getPlayers()) {
                if(aurityUHC.getPlayers().contains(players)) {
                    unsortedkills.put(players.getUniqueId(), aurityUHC.getPlayerKills().get(players.getUniqueId()));
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
