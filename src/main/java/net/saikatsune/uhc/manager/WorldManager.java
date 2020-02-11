package net.saikatsune.uhc.manager;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.handler.FileHandler;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

public class WorldManager {

    private Game game = Game.getInstance();

    private String prefix = game.getPrefix();

    private String mColor = game.getmColor();
    private String sColor = game.getsColor();

    private FileHandler fileHandler = game.getFileHandler();

    public void createWorld(String worldName, World.Environment environment, WorldType worldType) {
        Bukkit.broadcastMessage(prefix + sColor + "Started creating the world " + mColor + worldName + sColor + "...");
        World world = Bukkit.createWorld(new WorldCreator(worldName).environment(environment).type(worldType));
        world.setDifficulty(Difficulty.EASY);
        world.setTime(0);
        world.setThundering(false);
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        Bukkit.broadcastMessage(prefix + sColor + "Finished creating the world " + mColor + worldName + sColor + "!");
    }

    public void loadWorld(String worldName, int worldRadius, int loadingSpeed) {
        Bukkit.broadcastMessage(prefix + sColor + "Started loading the world " + mColor + worldName + sColor + "...");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb shape square");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + worldName + " set " + worldRadius + " " + worldRadius + " 0 0");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + worldName + " fill " + loadingSpeed);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + worldName + " fill confirm");
    }

    public void shrinkBorder(String worldName, int size) {

        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + worldName + " set " + size + " " + size + " 0 0");
    }

    public void createTotalShrink() {
        if (game.getConfigManager().getBorderSize() == 3500) {
            game.getConfigManager().setBorderSize(3000);
            game.getWorldManager().shrinkBorder("uhc_world", 3000);
            game.getWorldManager().createBorderLayer("uhc_world",3000, 4, null);

            game.getWorldManager().shrinkBorder("uhc_nether", 3000);
        } else if (game.getConfigManager().getBorderSize() == 3000) {
            game.getConfigManager().setBorderSize(2500);
            game.getWorldManager().shrinkBorder("uhc_world", 2500);
            game.getWorldManager().createBorderLayer("uhc_world",2500, 4, null);

            game.getWorldManager().shrinkBorder("uhc_nether", 2500);
        } else if (game.getConfigManager().getBorderSize() == 2500) {
            game.getConfigManager().setBorderSize(2000);
            game.getWorldManager().shrinkBorder("uhc_world", 2000);
            game.getWorldManager().createBorderLayer("uhc_world",2000, 4, null);

            game.getWorldManager().shrinkBorder("uhc_nether", 2000);
        } else if (game.getConfigManager().getBorderSize() == 2000) {
            game.getConfigManager().setBorderSize(1500);
            game.getWorldManager().shrinkBorder("uhc_world", 1500);
            game.getWorldManager().createBorderLayer("uhc_world",1500, 4, null);

            game.getWorldManager().shrinkBorder("uhc_nether", 1500);
        } else if (game.getConfigManager().getBorderSize() == 1500) {
            game.getConfigManager().setBorderSize(1000);
            game.getWorldManager().shrinkBorder("uhc_world", 1000);
            game.getWorldManager().createBorderLayer("uhc_world",1000, 4, null);

            game.getWorldManager().shrinkBorder("uhc_nether", 1000);
        } else if (game.getConfigManager().getBorderSize() == 1000) {
            game.getConfigManager().setBorderSize(500);
            game.getWorldManager().shrinkBorder("uhc_world", 500);
            game.getWorldManager().createBorderLayer("uhc_world",500, 4, null);

            game.getWorldManager().shrinkBorder("uhc_nether", 500);
        } else if (game.getConfigManager().getBorderSize() == 500) {
            game.getConfigManager().setBorderSize(100);
            game.getWorldManager().shrinkBorder("uhc_world", 100);
            game.getWorldManager().createBorderLayer("uhc_world",100, 4, null);

            if(game.getConfigManager().isNether()) {
                for (UUID players : game.getPlayers()) {

                    Player allPlayers = Bukkit.getPlayer(players);

                    if(allPlayers.getWorld().getName().equalsIgnoreCase("uhc_nether")) {

                        Random randomLocation = new Random();

                        int x = randomLocation.nextInt(game.getConfigManager().getBorderSize() - 1);
                        int z = randomLocation.nextInt(game.getConfigManager().getBorderSize() - 1);
                        int y = Bukkit.getWorld("uhc_world").getHighestBlockYAt(x, z);

                        Location location = new Location(Bukkit.getWorld("uhc_world"), x, y ,z);

                        game.getGameManager().setScatterLocation(allPlayers, location);
                        
                        allPlayers.teleport(game.getScatterLocation().get(allPlayers));
                    }
                }
            }

            game.getConfigManager().setNether(false);

            game.getWorldManager().shrinkBorder("uhc_nether", 100);
        } else if(game.getConfigManager().getBorderSize() == 100) {
            game.getConfigManager().setBorderSize(50);
            game.getWorldManager().shrinkBorder("uhc_world", 50);
            game.getWorldManager().createBorderLayer("uhc_world",50, 4, null);

            game.getWorldManager().shrinkBorder("uhc_nether", 50);
        } else if(game.getConfigManager().getBorderSize() == 50) {
            game.getConfigManager().setBorderSize(25);
            game.getWorldManager().shrinkBorder("uhc_world", 25);
            game.getWorldManager().createBorderLayer("uhc_world",25, 4, null);

            game.getWorldManager().shrinkBorder("uhc_nether", 25);
        }
        game.getGameManager().playSound();
        Bukkit.broadcastMessage(prefix + sColor + "The border has shrunken to " + mColor + game.getConfigManager().getBorderSize() + "x" +
                game.getConfigManager().getBorderSize() + sColor + " blocks!");
        if(game.getConfigManager().getBorderSize() > 25) {
            Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + game.getTimeTask().getNextBorder() + "x" +
                    game.getTimeTask().getNextBorder() + " blocks in 5 minutes!");
        }
    }

    public void createBorderLayer(String borderWorld, int radius, int amount, Consumer<String> done) {
        World world = Bukkit.getWorld(borderWorld);
        if (world == null) return;
        LinkedList<Location> locations = new LinkedList<>();

        for (int i = 0; i < amount; i++) {
            for (int z = -radius; z <= radius; z++) {
                Location location = new Location(world, radius, world.getHighestBlockYAt(radius, z) + i, z);
                locations.add(location);
            }
            for (int z = -radius; z <= radius; z++) {
                Location location = new Location(world, -radius, world.getHighestBlockYAt(-radius, z) + i, z);
                locations.add(location);
            }
            for (int x = -radius; x <= radius; x++) {
                Location location = new Location(world, x, world.getHighestBlockYAt(x, radius) + i, radius);
                locations.add(location);
            }
            for (int x = -radius; x <= radius; x++) {
                Location location = new Location(world, x, world.getHighestBlockYAt(x, -radius) + i, -radius);
                locations.add(location);
            }
        }

        new BukkitRunnable() {

            private int max = 50;
            @Override
            public void run() {
                for (int i = 0; i < max; i++) {
                    if (locations.isEmpty()) {
                        if (done != null) done.accept("done");
                        this.cancel();
                        break;
                    }
                    locations.remove().getBlock().setType(Material.BEDROCK);
                }
            }
        }.runTaskTimer(game, 0, 1);
    }

    private void unloadWorld(World worldName) {
        if(worldName != null) {
            Bukkit.unloadWorld(worldName, false);
        }
    }

    public void deleteWorld(String worldName) {
        Bukkit.broadcastMessage(prefix + sColor + "Started deleting the world " + mColor + worldName + sColor + "...");
        this.unloadWorld(Bukkit.getWorld(worldName));
        fileHandler.deleteFiles(new File(Bukkit.getWorldContainer(), worldName));
        Bukkit.broadcastMessage(prefix + sColor + "Finished deleting the world " + mColor + worldName + sColor + "!");
    }

}
