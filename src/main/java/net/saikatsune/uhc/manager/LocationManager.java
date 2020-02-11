package net.saikatsune.uhc.manager;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationManager {

    private Game game = Game.getInstance();

    private File file = new File("plugins/" + game.getDescription().getName() + "/locations.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLocation(String name, Location location) {
        config.set(name + ".world", location.getWorld().getName());
        config.set(name + ".x", location.getX());
        config.set(name + ".y", location.getY());
        config.set(name + ".z", location.getZ());
        config.set(name + ".yaw", location.getYaw());
        config.set(name + ".pitch", location.getPitch());
        saveConfig();

        if(!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Location getLocation(String name) {
        World world = Bukkit.getWorld(config.getString(name + ".world"));
        double x = config.getDouble(name + ".x");
        double y = config.getDouble(name + ".y");
        double z = config.getDouble(name + ".z");
        Location location = new Location(world, x, y, z);
        location.setYaw(config.getInt(name + ".yaw"));
        location.setPitch(config.getInt(name + ".pitch"));
        return location;
    }

}
