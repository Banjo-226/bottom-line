package com.Banjo226.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.Banjo226.BottomLine;

public class DeathListener implements Listener {
	BottomLine pl = BottomLine.getInstance();

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (pl.getConfig().getBoolean("allow-death-messages") == false) {
			e.setDeathMessage(null);
		}
	}
}