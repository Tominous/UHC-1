package net.saikatsune.uhc.listener;

import net.saikatsune.uhc.Game;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    @EventHandler
    public void handleASyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if(game.isChatMuted()) {
            if(!player.hasPermission("uhc.host")) {
                event.setCancelled(true);
                player.sendMessage(prefix + ChatColor.RED + "Global Chat is currently disabled!");
            }
        }
    }
}
