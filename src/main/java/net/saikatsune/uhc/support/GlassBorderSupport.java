package net.saikatsune.uhc.support;

import net.saikatsune.uhc.Game;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class GlassBorderSupport implements Listener {

    private Game game = Game.getInstance();

    private Map<Player, List<Location>> players = new WeakHashMap();
    private byte color = 14;

    private static boolean isInBetween(int xone, int xother, int mid) {
        int distance = Math.abs(xone - xother);
        return (distance == Math.abs(mid - xone) + Math.abs(mid - xother));
    }

    private static int closestNumber(int from, int... numbers) {
        int distance = Math.abs(numbers[0] - from);
        int idx = 0;
        for (int c = 1; c < numbers.length; c++) {
            int cdistance = Math.abs(numbers[c] - from);
            if (cdistance < distance) {
                idx = c;
                distance = cdistance;
            }
        }
        return numbers[idx];
    }

    @EventHandler
    public void handlePlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            Location to = event.getTo();
            if (Math.abs(to.getBlockX()) > game.getConfigManager().getBorderSize() || Math.abs(to.getBlockZ())
                    > game.getConfigManager().getBorderSize()) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You can't pearl to outside of the border.");
                return;
            }
            handlePlayerMovement(event);
        }
    }

    @EventHandler
    public void handlePlayerMovement(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (!from.getWorld().getName().equalsIgnoreCase("uhc_world"))
            return;

        if (from.getWorld().getName().equalsIgnoreCase("uhc_world")) {

            if (from.getBlockX() != to.getBlockX() || to.getBlockZ() != from.getBlockZ()) {
                placeGlass(event.getPlayer(), to, -game.getConfigManager().getBorderSize() - 1,
                        game.getConfigManager().getBorderSize(), -game.getConfigManager().getBorderSize() - 1,
                        game.getConfigManager().getBorderSize());
            }
        }
    }

    private boolean placeGlass(Player player, Location to, int minX, int maxX, int minZ, int maxZ) {
        int closerx = closestNumber(to.getBlockX(), minX, maxX);
        int closerz = closestNumber(to.getBlockZ(), minZ, maxZ);

        boolean updateX = (Math.abs(to.getX() - closerx) < 15.0D);
        boolean updateZ = (Math.abs(to.getZ() - closerz) < 15.0D);

        if (!updateX && !updateZ) {
            removeGlass(player);
            return false;
        }

        List<Location> toUpdate = new ArrayList<Location>();

        if (updateX) {
            for (int y = -3; y < 7; y++) {
                for (int x = -5; x < 5; x++) {
                    if (isInBetween(minZ, maxZ, to.getBlockZ() + x)) {
                        Location location = new Location(to.getWorld(), closerx, (to.getBlockY() + y),
                                (to.getBlockZ() + x));
                        if (!toUpdate.contains(location) && !location.getBlock().getType().isOccluding()) {
                            toUpdate.add(location);
                        }
                    }
                }
            }
        }

        if (updateZ) {
            for (int y = -3; y < 7; y++) {
                for (int x = -5; x < 5; x++) {
                    if (isInBetween(minX, maxX, to.getBlockX() + x)) {
                        Location location = new Location(to.getWorld(), (to.getBlockX() + x), (to.getBlockY() + y),
                                closerz);
                        if (!toUpdate.contains(location) && !location.getBlock().getType().isOccluding()) {
                            toUpdate.add(location);
                        }
                    }
                }
            }
        }
        updateGlass(player, toUpdate);
        return !toUpdate.isEmpty();
    }

    @SuppressWarnings("deprecation")
    public void removeGlass(Player player) {
        if (this.players.containsKey(player)) {
            for (Location location : players.get(player)) {
                Block block = location.getBlock();
                player.sendBlockChange(location, block.getTypeId(), block.getData());
            }
            this.players.remove(player);
        }
    }

    @SuppressWarnings("deprecation")
    public void updateGlass(Player player, List<Location> toUpdate) {
        if (this.players.containsKey(player)) {
            for (Location location : players.get(player)) {
                Block block = location.getBlock();
                player.sendBlockChange(location, block.getTypeId(), block.getData());
            }
            for (Location location2 : toUpdate) {
                player.sendBlockChange(location2, 95, this.color);
            }
            this.players.put(player, toUpdate);
        } else {
            for (Location location2 : toUpdate) {
                player.sendBlockChange(location2, 95, this.color);
            }
            this.players.put(player, toUpdate);
        }
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equalsIgnoreCase("uhc_world"))
            return;
        if (!this.players.containsKey(player))
            return;
        updateGlass(player, this.players.get(player));
    }

}
