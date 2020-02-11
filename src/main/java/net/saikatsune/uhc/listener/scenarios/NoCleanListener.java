package net.saikatsune.uhc.listener.scenarios;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.Scenarios;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class NoCleanListener implements Listener {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private ArrayList<UUID> noCleanPlayers = new ArrayList<>();

    @EventHandler
    public void handlePlayerDeathEvent(PlayerDeathEvent event) {
        Player death = event.getEntity();
        Player killer = death.getKiller();

        if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            if(Scenarios.NOCLEAN.isEnabled()) {
                if(killer != null) {
                    noCleanPlayers.add(killer.getUniqueId());
                    killer.sendMessage(prefix + ChatColor.GREEN + "[NoClean] You are now protected to any damage for 20 seconds!");

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if(noCleanPlayers.contains(killer.getUniqueId())) {
                                noCleanPlayers.remove(killer.getUniqueId());
                                killer.sendMessage(prefix + ChatColor.RED + "[NoClean] You are no longer protected to any damage!");
                            }
                        }
                    }.runTaskLater(game, 20 * 20);
                }
            }
        }
    }

    @EventHandler
    public void handleEntityDamageEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player damaged = (Player) event.getEntity();

            if(noCleanPlayers.contains(damaged.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            if(event.getDamager() instanceof Player) {
                Player damager = (Player) event.getDamager();
                Player damaged = (Player) event.getEntity();

                if(noCleanPlayers.contains(damaged.getUniqueId())) {
                    event.setCancelled(true);
                    damager.sendMessage(prefix + ChatColor.RED + "[NoClean] This player is protected to any damage!");
                }

                if(noCleanPlayers.contains(damager.getUniqueId())) {
                    event.setCancelled(true);
                    noCleanPlayers.remove(damager.getUniqueId());
                    damager.sendMessage(prefix + ChatColor.RED + "[NoClean] You are no longer protected to any damage!");
                }
            }
        }
    }

}
