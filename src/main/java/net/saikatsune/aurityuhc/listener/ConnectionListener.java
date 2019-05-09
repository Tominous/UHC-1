package net.saikatsune.aurityuhc.listener;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.PlayerState;
import net.saikatsune.aurityuhc.gamestate.states.EndingState;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import net.saikatsune.aurityuhc.gamestate.states.LobbyState;
import net.saikatsune.aurityuhc.gamestate.states.ScatteringState;
import net.saikatsune.aurityuhc.handler.TeamHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ConnectionListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    @EventHandler
    public void handlePlayerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage("");

        Player player = event.getPlayer();

        if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {

            aurityUHC.getGameManager().resetPlayer(player);

            aurityUHC.getGameManager().setPlayerState(player, PlayerState.PLAYER);

            player.teleport(aurityUHC.getLocationManager().getLocation("Spawn-Location"));

            aurityUHC.getTeamNumber().putIfAbsent(player.getUniqueId(), -1);
        } else if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {

            if(!aurityUHC.getLoggedPlayers().contains(player.getUniqueId())) {
                aurityUHC.getGameManager().resetPlayer(player);

                aurityUHC.getGameManager().setPlayerState(player, PlayerState.SPECTATOR);

                if(player.hasPermission("uhc.host")) {
                    aurityUHC.getInventoryHandler().handleStaffInventory(player);
                }
            } else {
                if(aurityUHC.getGameManager().isTeamGame()) {
                    if(aurityUHC.getTeamNumber().get(player.getUniqueId()) != -1) {
                        TeamHandler teamHandler = aurityUHC.getTeamManager().getTeams().get(aurityUHC.getTeamNumber().get(player.getUniqueId()));
                        if(aurityUHC.getTeamManager().getTeams().containsValue(teamHandler)) {
                            aurityUHC.getTeamManager().addPlayerToTeam(aurityUHC.getTeamNumber().get(player.getUniqueId()), player.getUniqueId());
                        } else {
                            aurityUHC.getTeamManager().createTeam(player.getUniqueId());
                        }
                    }
                }

                aurityUHC.getGameManager().setPlayerState(player, PlayerState.PLAYER);
            }
        }

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if(potionEffect.getType() == PotionEffectType.SLOW || potionEffect.getType() == PotionEffectType.BLINDNESS
                    || potionEffect.getType() == PotionEffectType.JUMP) {
                player.removePotionEffect(potionEffect.getType());
            }
        }

        if(aurityUHC.isDatabaseActive()) {
            aurityUHC.getDatabaseManager().registerPlayer(player);
        }

        aurityUHC.getPlayerKills().putIfAbsent(player.getUniqueId(), 0);
        aurityUHC.getScoreboardManager().createScoreboard(player);
    }

    @EventHandler
    public void handlePlayerQuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage("");

        Player player = event.getPlayer();

        aurityUHC.getPlayers().remove(player);
        aurityUHC.getSpectators().remove(player);

        if(aurityUHC.getGameManager().isTeamGame()) {
            if(aurityUHC.getTeamNumber().get(player.getUniqueId()) != -1) {
                aurityUHC.getTeamManager().removePlayerFromTeam(aurityUHC.getTeamNumber().get(player.getUniqueId()), player.getUniqueId());
            }
        }


        if(aurityUHC.getCombatLoggedPlayers().contains(player.getUniqueId())) {
            player.damage(20);
        }

        aurityUHC.getGameManager().checkWinner();
    }

    @EventHandler
    public void handlePlayerLoginEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
            if(aurityUHC.getGameManager().isWhitelisted()) {
                if(!aurityUHC.getWhitelisted().contains(player.getUniqueId())) {
                    if(!player.hasPermission("uhc.host")) {
                        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "The game is currently whitelisted!");
                    }
                }
            }
        } else if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof ScatteringState) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "The scatter is currently happening. Try again later!");
        } else if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            if(aurityUHC.getGameManager().isWhitelisted()) {
                if(!aurityUHC.getWhitelisted().contains(player.getUniqueId())) {
                    if(!aurityUHC.getLoggedPlayers().contains(player.getUniqueId())) {
                        if(!player.hasPermission("uhc.host")) {
                            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "The game has already started!");
                        }
                    }
                }
            }
        } else if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof EndingState) {
            if(!player.hasPermission("uhc.host")) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cThe game has already ended!");
            }
        }
    }

}
