package net.saikatsune.aurityuhc.listener;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    @EventHandler
    public void handleASyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if(aurityUHC.isChatMuted()) {
            if(!player.hasPermission("uhc.host")) {
                event.setCancelled(true);
                player.sendMessage(prefix + ChatColor.RED + "Global Chat is currently disabled!");
            }
        }
    }
}
