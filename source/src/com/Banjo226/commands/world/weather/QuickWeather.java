/**
 *  QuickWeather.java
 *  BottomLine
 *
 *  Created by Banjo226 on 1 Jan 2016 at 1:56:33 pm AEST
 *  Copyright © 2016 Banjo226. All rights reserved.
 */

package com.Banjo226.commands.world.weather;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class QuickWeather implements Listener {
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player sender = e.getPlayer();
		String time = e.getMessage().replaceAll("/", "");

		if (e.getMessage().equalsIgnoreCase("/rain") || e.getMessage().equalsIgnoreCase("/thunder") || e.getMessage().equalsIgnoreCase("/sun")) {
			e.setCancelled(true);
			Bukkit.dispatchCommand(sender, "weather " + time);
		}
	}
}