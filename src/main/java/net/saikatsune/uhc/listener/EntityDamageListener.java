package net.saikatsune.uhc.listener;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.enums.PlayerState;
import net.saikatsune.uhc.gamestate.states.IngameState;
import net.saikatsune.uhc.gamestate.states.LobbyState;
import net.saikatsune.uhc.handler.ItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

public class EntityDamageListener implements Listener {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    @EventHandler
    public void handleEntityDamageEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if(game.getSpectators().contains(player)) event.setCancelled(true);

            if(!(game.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
                if(!game.getArenaPlayers().contains(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        } else {
            if(!(game.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
                event.setCancelled(true);
            } else {
                if(event.getEntity() instanceof Villager) {
                    Villager villager = (Villager) event.getEntity();

                    if(villager.getName().contains("[CombatLogger] ")) {
                        if(game.isInGrace()) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void handleEntityDeathEvent(EntityDeathEvent event) {
        try {
            if(event.getEntity() instanceof Villager) {
                Villager villager = (Villager) event.getEntity();

                if(villager.getName().contains("[CombatLogger] ")) {
                    Player player = event.getEntity().getKiller();

                    game.getPlayerKills().put(player.getUniqueId(), game.getPlayerKills().get(player.getUniqueId()) + 1);

                    Bukkit.broadcastMessage(ChatColor.RED + villager.getName() + ChatColor.YELLOW + " was slain " +
                            "by " + ChatColor.RED + player.getName() + ChatColor.GRAY + "[" + game.getPlayerKills().get(player.getUniqueId())
                            + "].");

                    for (UUID villagerKey : game.getCombatVillagerUUID().keySet()) {
                        OfflinePlayer villagerConnectedToPlayer = Bukkit.getOfflinePlayer(villagerKey);

                        //player.setLevel(player.getLevel() + game.getDeathLevels().get(villagerConnectedToPlayer.getUniqueId()));

                        try {
                            for (ItemStack itemStack : game.getDeathInventory().get(villagerConnectedToPlayer.getUniqueId())) {
                                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                            }

                            for (ItemStack itemStack : game.getDeathArmor().get(villagerConnectedToPlayer.getUniqueId())) {
                                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                            }
                        } catch (Exception ignored) {

                        }
                        game.getLoggedPlayers().remove(villagerConnectedToPlayer.getUniqueId());
                        game.getWhitelisted().remove(villagerConnectedToPlayer.getUniqueId());

                        game.getLoggedOutPlayers().remove(villagerConnectedToPlayer.getUniqueId());

                        if(game.isDatabaseActive()) {
                            game.getDatabaseManager().addDeaths(villagerConnectedToPlayer, 1);
                        }

                        game.getCombatVillagerUUID().remove(villagerKey);

                        try {
                            game.getPlayers().remove(villagerConnectedToPlayer.getUniqueId());
                        } catch (Exception ignored) {
                            //Bukkit.broadcastMessage("§f§l[DEBUG] Was not able to remove a player from the players list. " +
                            //villagerConnectedToPlayer.getName() + "; " + villagerConnectedToPlayer.getUniqueId());
                        }
                    }
                    game.getGameManager().checkWinner();
                }
            }
        } catch (Exception ignored) {

        }
    }

    @EventHandler
    public void handleEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(event.getDamager() instanceof Player) {
                Player attacker = (Player) event.getDamager();

                if(game.getSpectators().contains(player)) event.setCancelled(true);
                if(game.getSpectators().contains(attacker)) event.setCancelled(true);

                if(!(game.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
                    if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                        if(!game.isArenaEnabled()) {
                            if(!game.getArenaPlayers().contains(player.getUniqueId()) &&
                                    !game.getArenaPlayers().contains(attacker.getUniqueId())) {
                                event.setCancelled(true);
                            }
                        }
                    } else {
                        event.setCancelled(true);
                    }
                } else {
                    if(game.isInGrace()) {
                        event.setCancelled(true);
                    }

                    if(game.getPlayers().contains(player.getUniqueId())) {
                        if(game.getPlayers().contains(attacker.getUniqueId())) {
                            for (Player allPlayers : game.getSpectators()) {
                                if(allPlayers.hasPermission("uhc.host")) {
                                    if(game.getReceivePvpAlerts().contains(allPlayers.getUniqueId())) {

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

                    if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow && ((Arrow)event.getDamager()).getShooter() instanceof Player) {
                        if (((Player)event.getEntity()).getHealth() - event.getFinalDamage() > 0.0D) {
                            ((Player)((Arrow)event.getDamager()).getShooter()).sendMessage(game.getPrefix() + game.getmColor() + player.getName() + "'s" + game.getsColor() +
                                    " health is at " + ChatColor.RED + Math.round(player.getHealth() - 1) + "❤!");
                        }
                    }
                }
            } else {
                if(game.isInGrace()) {
                    if(!game.getArenaPlayers().contains(player.getUniqueId())) {
                        event.setCancelled(true);
                    }
                }
            }
        } else {
            if(event.getDamager() instanceof Player || event.getDamager() instanceof Projectile) {
                Player attacker = (Player) event.getDamager();

                if(game.getSpectators().contains(attacker)) event.setCancelled(true);
                if(!(game.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void handlePlayerPracticeDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if(game.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
            if(game.getArenaPlayers().contains(player.getUniqueId())) {
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

                            game.getInventoryHandler().handlePracticeInventory(player);
                            game.getGameManager().scatterPlayer(player, Bukkit.getWorld("uhc_practice"), 49);
                        }
                    }.runTaskLater(game, 20);
                }
            }
        }
    }

    @EventHandler
    public void handlePlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if(game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            game.registerPlayerDeath(player);

            event.setKeepInventory(false);

            if(player.getKiller() != null) {
                game.getPlayerKills().put(player.getKiller().getUniqueId(), game.getPlayerKills().get(player.getKiller().getUniqueId()) + 1);

                if(game.isDatabaseActive()) {
                    game.getDatabaseManager().addKills(player.getKiller(), 1);
                }


                Player killer = player.getKiller();
                event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + "[" +
                        game.getPlayerKills().get(player.getUniqueId()) + "] " + ChatColor.YELLOW + "was" +
                        " slain by " + ChatColor.RED + killer.getName() + ChatColor.GRAY + "[" + game.getPlayerKills().get(killer.getUniqueId()) + "].");
            } else {
                event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + "[" +
                        game.getPlayerKills().get(player.getUniqueId()) + "] " + ChatColor.YELLOW + "has" +
                        " died mysteriously.");
            }

            if(game.getGameManager().isTeamGame()) {
                if(game.getTeamNumber().get(player.getUniqueId()) != -1) {
                    game.getTeamManager().removePlayerFromTeam(game.getTeamNumber().get(player.getUniqueId()), player.getUniqueId());
                    game.getTeamNumber().put(player.getUniqueId(), -1);
                }
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.spigot().respawn();

                    game.getGameManager().setPlayerState(player, PlayerState.SPECTATOR);

                    game.getWhitelisted().remove(player.getUniqueId());
                    game.getLoggedPlayers().remove(player.getUniqueId());

                    if(player.hasPermission("uhc.host")) {
                        game.getInventoryHandler().handleStaffInventory(player);
                    }

                    if(game.isDatabaseActive()) {
                        game.getDatabaseManager().addDeaths(player, 1);
                    }

                    game.getGameManager().checkWinner();

                    /*
                    if(!player.hasPermission("uhc.host")) {
                        player.sendMessage(prefix + ChatColor.RED + "You will get kicked in 30 seconds!");

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.kickPlayer(ChatColor.RED + "You are dead! Thank's for playing!");
                            }
                        }.runTaskLater(game, 20 * 30);
                    }
                     */
                }
            }.runTaskLater(game, 20);
        }
    }


    @EventHandler
    public void handleFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        if(!(game.getGameStateManager().getCurrentGameState() instanceof IngameState)) {
            event.setCancelled(true);
        } else {
            Player player = (Player)event.getEntity();
            if (event.getFoodLevel() < player.getFoodLevel() && (new Random()).nextInt(100) > 4)
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
