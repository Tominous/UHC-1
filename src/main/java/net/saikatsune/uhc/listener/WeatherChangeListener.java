package net.saikatsune.uhc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    public void handleWeatherChangeEvent(WeatherChangeEvent event) {
        if(event.toWeatherState()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleThunderChangeEvent(ThunderChangeEvent event) {
        if(event.toThunderState()) {
            event.setCancelled(true);
        }
    }

}
