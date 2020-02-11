package net.saikatsune.uhc.tasks;
import net.saikatsune.uhc.gamestate.states.IngameState;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class ButcherTask extends BukkitRunnable {

    private Game game = Game.getInstance();

    private double ticksPerSecond = game.getServer().spigot().getTPS()[0];

    public void run() {
        if (this.game.getGameStateManager().getCurrentGameState() instanceof IngameState) {
            stabilizeTPS();
            for (World world : Bukkit.getWorlds()) {
                int maximumEntities = 2000;
                if (world.getLivingEntities().size() > maximumEntities)
                    for (int i = 0; i < world.getLivingEntities().size() - maximumEntities; i++) {
                        Entity entity = world.getLivingEntities().get(i);
                        if ((entity instanceof Creature || entity instanceof Ambient) &&
                                !(entity instanceof Player))
                            entity.remove();
                    }
            }
        }
    }

    private void stabilizeTPS() {
        if (this.ticksPerSecond < 15.0D) {
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getLivingEntities()) {
                    if (entity instanceof Player)
                        continue;
                    entity.remove();
                }
            }
        } else if (this.ticksPerSecond < 16.0D) {
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getLivingEntities()) {
                    if (entity instanceof Player || entity instanceof Cow ||
                            entity instanceof Chicken || entity instanceof Pig)
                        continue;
                    entity.remove();
                }
            }
        } else if (this.ticksPerSecond < 18.0D) {
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getLivingEntities()) {
                    if (entity instanceof Bat || entity instanceof Sheep || entity instanceof Squid)
                        entity.remove();
                }
            }
        }
    }
}
