/**
 *  QuickTime.java
 *  BottomLine
 *
 *  Created by Banjo226 on 1 Jan 2016 at 1:19:09 pm AEST
 *  Copyright © 2016 Banjo226. All rights reserved.
 */

package com.Banjo226.commands.world.time;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class QuickTime implements Listener {
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player sender = e.getPlayer();
		String time = e.getMessage().replaceAll("/", "");

		if (e.getMessage().equalsIgnoreCase("/day") || e.getMessage().equalsIgnoreCase("/night") || e.getMessage().equalsIgnoreCase("/dusk") || e.getMessage().equalsIgnoreCase("/dawn")) {
			e.setCancelled(true);
			Bukkit.dispatchCommand(sender, "time " + time);
		}
	}
}