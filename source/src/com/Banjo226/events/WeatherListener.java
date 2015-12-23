package com.Banjo226.events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Store;

public class WeatherListener implements Listener {
	BottomLine pl = BottomLine.getInstance();

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		if (pl.getConfig().getBoolean("weatherchange.change") == false) {
			for (int i = 0; i < Store.weatherChange.size(); i++) {
				World world = Bukkit.getWorld(Store.weatherChange.get(i));
				if (world != null) {
					for (World w : Bukkit.getWorlds()) {
						if (w == world) {
							if (w.hasStorm() || w.isThundering()) {
								e.setCancelled(false);
							} else {
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
}