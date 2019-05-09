package net.saikatsune.aurityuhc.manager;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.handler.FileHandler;
import org.bukkit.*;
import org.bukkit.block.Block;

import java.io.File;

public class WorldManager {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    private FileHandler fileHandler = aurityUHC.getFileHandler();

    public void createWorld(String worldName, World.Environment environment) {
        Bukkit.broadcastMessage(prefix + sColor + "Started creating the world " + mColor + worldName + sColor + "...");
        World world = Bukkit.createWorld(new WorldCreator(worldName).environment(environment).type(WorldType.NORMAL));
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
            aurityUHC.getWorldManager().createBorderLayer("uhc_world", 3000);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 3000);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 3000) {
            aurityUHC.getConfigManager().setBorderSize(2500);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 2500);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world", 2500);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 2500);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 2500) {
            aurityUHC.getConfigManager().setBorderSize(2000);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 2000);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world", 2000);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 2000);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 2000) {
            aurityUHC.getConfigManager().setBorderSize(1500);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 1500);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world", 1500);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 1500);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 1500) {
            aurityUHC.getConfigManager().setBorderSize(1000);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 1000);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world", 1000);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 1000);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 1000) {
            aurityUHC.getConfigManager().setBorderSize(500);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 500);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world", 500);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 500);
        } else if (aurityUHC.getConfigManager().getBorderSize() == 500) {
            aurityUHC.getConfigManager().setBorderSize(100);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 100);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world", 100);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 100);
        } else if(aurityUHC.getConfigManager().getBorderSize() == 100) {
            aurityUHC.getConfigManager().setBorderSize(50);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 50);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world", 50);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 50);
        } else if(aurityUHC.getConfigManager().getBorderSize() == 50) {
            aurityUHC.getConfigManager().setBorderSize(25);
            aurityUHC.getWorldManager().shrinkBorder("uhc_world", 25);
            aurityUHC.getWorldManager().createBorderLayer("uhc_world", 25);

            aurityUHC.getWorldManager().shrinkBorder("uhc_nether", 25);
        }
        aurityUHC.getGameManager().playSound();
        Bukkit.broadcastMessage(prefix + sColor + "The border has shrunk to " + mColor + aurityUHC.getConfigManager().getBorderSize() + sColor + "!");
        if(aurityUHC.getConfigManager().getBorderSize() > 25) {
            Bukkit.broadcastMessage(prefix + sColor + "The next shrink is in 5 minutes!");
        }
    }

    public void createBorderLayer(String worldName, int worldRadius) {
        World world = Bukkit.getWorld(worldName);

        Material mat = Material.BEDROCK;
        for (int i = 0; i < 1; i++) {
            for (int x = -worldRadius; x < worldRadius; x++) {
                int z = worldRadius;
                int y = world.getHighestBlockYAt(x, z);

                Block block = world.getBlockAt(x, y, z);

                block.setType(mat);
            }

            for (int z = worldRadius; z > -worldRadius; z--) {
                int x = worldRadius;
                int y = world.getHighestBlockYAt(x, z);

                Block block = world.getBlockAt(x, y, z);

                block.setType(mat);
            }

            for (int x = worldRadius; x > -worldRadius; x--) {
                int z = -worldRadius;
                int y = world.getHighestBlockYAt(x, z);

                Block block = world.getBlockAt(x, y, z);

                block.setType(mat);
            }

            for (int z = -worldRadius; z < worldRadius; z++) {
                int x = -worldRadius;
                int y = world.getHighestBlockYAt(x, z);

                Block block = world.getBlockAt(x, y, z);

                block.setType(mat);
            }
        }
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
