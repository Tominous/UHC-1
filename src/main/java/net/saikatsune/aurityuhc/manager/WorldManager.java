package net.saikatsune.aurityuhc.manager;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.handler.FileHandler;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.Consumer;

public class WorldManager {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    private FileHandler fileHandler = aurityUHC.getFileHandler();

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
        if (aurityUHC.getConfigManager().getBorderSize() == 3500) {
            aurityUHC.getConfigManager().setBorderSize(3000);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 3000);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world",3000, 4, null);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 3000);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 3000) {
            aurityUHC.getConfigManager().setBorderSize(2500);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 2500);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world",2500, 4, null);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 2500);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 2500) {
            aurityUHC.getConfigManager().setBorderSize(2000);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 2000);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world",2000, 4, null);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 2000);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 2000) {
            aurityUHC.getConfigManager().setBorderSize(1500);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 1500);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world",1500, 4, null);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 1500);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 1500) {
            aurityUHC.getConfigManager().setBorderSize(1000);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 1000);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world",1000, 4, null);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 1000);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 1000) {
            aurityUHC.getConfigManager().setBorderSize(500);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 500);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world",500, 4, null);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 500);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 500) {
            aurityUHC.getConfigManager().setBorderSize(100);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 100);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world",100, 4, null);

            if(aurityUHC.getConfigManager().isNether()) {
                for (Player allPlayers : aurityUHC.getPlayers()) {
                    if(allPlayers.getWorld().getName().equalsIgnoreCase("uhc_nether")) {

                        Random randomLocation = new Random();

                        int x = randomLocation.nextInt(aurityUHC.getConfigManager().getBorderSize() - 1);
                        int z = randomLocation.nextInt(aurityUHC.getConfigManager().getBorderSize() - 1);
                        int y = Bukkit.getWorld("uhc_world").getHighestBlockYAt(x, z);

                        Location location = new Location(Bukkit.getWorld("uhc_world"), x, y ,z);

                        aurityUHC.getGameManager().setScatterLocation(allPlayers, location);
                        
                        allPlayers.teleport(aurityUHC.getScatterLocation().get(allPlayers));
                    }
                }
            }

            aurityUHC.getConfigManager().setNether(false);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 100);
        } else if(aurityUHC.getConfigManager().getBorderSize() == 100) {
            aurityUHC.getConfigManager().setBorderSize(50);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 50);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world",50, 4, null);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 50);
        } else if(aurityUHC.getConfigManager().getBorderSize() == 50) {
            aurityUHC.getConfigManager().setBorderSize(25);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 25);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world",25, 4, null);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 25);
        }
        aurityUHC.getGameManager().playSound();
        Bukkit.broadcastMessage(prefix + sColor + "The border has shrunken to " + mColor + aurityUHC.getConfigManager().getBorderSize() + "x" +
                aurityUHC.getConfigManager().getBorderSize() + sColor + " blocks!");
        if(aurityUHC.getConfigManager().getBorderSize() > 25) {
            Bukkit.broadcastMessage(prefix + sColor + "The border is going to shrink to " + aurityUHC.getTimeTask().getNextBorder() + "x" +
                    aurityUHC.getTimeTask().getNextBorder() + " blocks in 5 minutes!");
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
        }.runTaskTimer(aurityUHC, 0, 1);
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
