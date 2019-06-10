package net.saikatsune.aurityuhc.listener;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.PlayerState;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import net.saikatsune.aurityuhc.gamestate.states.LobbyState;
import net.saikatsune.aurityuhc.handler.ItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityDamageListener implements Listener {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @EventHandler
    public void handleEntityDamageEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if(aurityUHC.getSpectators().contains(player)) event.setCancelled(true);

            if(!(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
                if(!aurityUHC.getArenaPlayers().contains(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        } else {
            if(!(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(event.getDamager() instanceof Player) {
                Player attacker = (Player) event.getDamager();

                if(aurityUHC.getSpectators().contains(player)) event.setCancelled(true);
                if(aurityUHC.getSpectators().contains(attacker)) event.setCancelled(true);

                if(!(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
                    if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        if(!aurityUHC.isArenaEnabled()) {
                            if(!aurityUHC.getArenaPlayers().contains(player.getUniqueId()) &&
                                    !aurityUHC.getArenaPlayers().contains(attacker.getUniqueId())) {
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        event.setCancelled(true);
                    }
                } else {
                    if(aurityUHC.isInGrace()) {
                        event.setCancelled(true);
                    }

                    if(aurityUHC.getPlayers().contains(player)) {
                        if(aurityUHC.getPlayers().contains(attacker)) {
                            for (Player allPlayers : aurityUHC.getSpectators()) {
                                if(allPlayers.hasPermission("uhc.host")) {
                                    if(aurityUHC.getReceivePvpAlerts().contains(allPlayers.getUniqueId())) {

                                        TextComponent textComponent = new TextComponent();

                                        textComponent.setText(prefix + mColor + attacker.getName() + sColor + " has attacked " + mColor + player.getName() + sColor + "!");

                                        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                new ComponentBuilder(sColor + "Click to teleport to " + mColor + attacker.getName() + sColor + "!").create()));
                                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + attacker.getName()));

                                        allPlayers.spigot().sendMessage(textComponent);
                                    }
                                }
                            }
                        }
                    }

                    if(!aurityUHC.isInGrace()) {
                        if(!aurityUHC.getCombatLoggedPlayers().contains(player.getUniqueId())) {
                            if(aurityUHC.getPlayers().contains(player)) {
                                if(aurityUHC.getPlayers().contains(attacker)) {
                                    aurityUHC.getCombatLoggedPlayers().add(player.getUniqueId());
                                    player.sendMessage(prefix + ChatColor.RED + "You are now combat-logged! You will be removed after 30 seconds!");
                                    player.sendMessage(prefix + ChatColor.RED + "You will get killed if you log out now!");

                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            if(aurityUHC.getCombatLoggedPlayers().contains(player.getUniqueId())) {
                                                aurityUHC.getCombatLoggedPlayers().remove(player.getUniqueId());
                                                player.sendMessage(prefix + ChatColor.GREEN + "You are no longer combat-logged! You can now relog again!");
                                            }
                                        }
                                    }.runTaskLater(aurityUHC, 30 * 20);
                                }
                            }
                        }

                        if(!aurityUHC.getCombatLoggedPlayers().contains(attacker.getUniqueId())) {
                            if(aurityUHC.getPlayers().contains(attacker)) {
                                if(aurityUHC.getPlayers().contains(player)) {
                                    aurityUHC.getCombatLoggedPlayers().add(attacker.getUniqueId());
                                    attacker.sendMessage(prefix + ChatColor.RED + "You are now combat-logged! You will be removed after 30 seconds!");
                                    attacker.sendMessage(prefix + ChatColor.RED + "You will get killed if you log out now!");

                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            if(aurityUHC.getCombatLoggedPlayers().contains(attacker.getUniqueId())) {
                                                aurityUHC.getCombatLoggedPlayers().remove(attacker.getUniqueId());
                                                attacker.sendMessage(prefix + ChatColor.GREEN + "You are no longer combat-logged! You can now relog again!");
                                            }
                                        }
                                    }.runTaskLater(aurityUHC, 30 * 20);
                                }
                            }
                        }
                    }
                }
            } else {
                if(aurityUHC.isInGrace()) {
                    if(!aurityUHC.getArenaPlayers().contains(player.getUniqueId())) {
                        event.setCancelled(true);
                    }
                }
            }
        } else {
            if(event.getDamager() instanceof Player || event.getDamager() instanceof Projectile) {
                Player attacker = (Player) event.getDamager();

                if(aurityUHC.getSpectators().contains(attacker)) event.setCancelled(true);
                if(!(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void handlePlayerPracticeDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
            if(aurityUHC.getArenaPlayers().contains(player.getUniqueId())) {
                if(player.getWorld().getName().equalsIgnoreCase("uhc_practice")) {
                    event.setKeepInventory(true);
                    event.setDeathMessage("");

                    if(player.getKiller() != null) {
                        player.getKiller().sendMessage(prefix + ChatColor.GREEN + "You have slain " + player.getName() + "!");
                        player.sendMessage(prefix + ChatColor.RED + "You have been slain by " + player.getKiller().getName() + "!");

                        player.getKiller().getInventory().addItem(new ItemHandler(Material.GOLDEN_APPLE).
                                setDisplayName(ChatColor.GOLD + "Golden Head").build());
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.spigot().respawn();

                            aurityUHC.getInventoryHandler().handlePracticeInventory(player);
                            aurityUHC.getGameManager().scatterPlayer(player, Bukkit.getWorld("uhc_practice"), 100);
                        }
                    }.runTaskLater(aurityUHC, 20);
                }
            }
        }
    }

    @EventHandler
    public void handlePlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            aurityUHC.registerPlayerDeath(player);

            event.setKeepInventory(false);

            if(player.getKiller() != null) {
                aurityUHC.getPlayerKills().put(player.getKiller().getUniqueId(), aurityUHC.getPlayerKills().get(player.getKiller().getUniqueId()) + 1);

                if(aurityUHC.isDatabaseActive()) {
                    aurityUHC.getDatabaseManager().addKills(player.getKiller(), 1);
                }

                if(aurityUHC.getCombatLoggedPlayers().contains(player.getKiller().getUniqueId())) {
                    aurityUHC.getCombatLoggedPlayers().remove(player.getKiller().getUniqueId());
                    player.getKiller().sendMessage(prefix + ChatColor.GREEN + "You are no longer combat-logged!");
                }

                Player killer = player.getKiller();
                event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + "[" +
                        aurityUHC.getPlayerKills().get(player.getUniqueId()) + "] " + ChatColor.YELLOW + "was" +
                        " slain by " + ChatColor.RED + killer.getName() + ChatColor.GRAY + "[" + aurityUHC.getPlayerKills().get(killer.getUniqueId()) + "]");
            } else {
                event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + "[" +
                        aurityUHC.getPlayerKills().get(player.getUniqueId()) + "] " + ChatColor.YELLOW + "has" +
                        " died mysteriously.");
            }

            if(aurityUHC.getGameManager().isTeamGame()) {
                if(aurityUHC.getTeamNumber().get(player.getUniqueId()) != -1) {
                    aurityUHC.getTeamManager().removePlayerFromTeam(aurityUHC.getTeamNumber().get(player.getUniqueId()), player.getUniqueId());
                    aurityUHC.getTeamNumber().put(player.getUniqueId(), -1);
                }
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.spigot().respawn();

                    aurityUHC.getGameManager().setPlayerState(player, PlayerState.SPECTATOR);

                    aurityUHC.getWhitelisted().remove(player.getUniqueId());
                    aurityUHC.getLoggedPlayers().remove(player.getUniqueId());

                    aurityUHC.getCombatLoggedPlayers().remove(player.getUniqueId());

                    if(player.hasPermission("uhc.host")) {
                        aurityUHC.getInventoryHandler().handleStaffInventory(player);
                    }

                    if(aurityUHC.isDatabaseActive()) {
                        aurityUHC.getDatabaseManager().addDeaths(player, 1);
                    }

                    aurityUHC.getGameManager().checkWinner();

                    if(!player.hasPermission("uhc.host")) {
                        player.sendMessage(prefix + ChatColor.RED + "You will get kicked in 30 seconds!");

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.kickPlayer(ChatColor.RED + "You are dead! Thank's for playing!");
                            }
                        }.runTaskLater(aurityUHC, 20 * 30);
                    }
                }
            }.runTaskLater(aurityUHC, 20);
        }
    }


    @EventHandler
    public void handleFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        if(!(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleProjectileHitEvent(ProjectileHitEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(player.getKiller() != null) {
                Player killer = player.getKiller();

                killer.sendMessage(prefix + mColor + player.getName() + "'s " + sColor + "health is at "
                        + ChatColor.DARK_RED + player.getHealthScale() + " ❤" + sColor + "!");
            }
        }
    }

}
