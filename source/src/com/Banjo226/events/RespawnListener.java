package com.Banjo226.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.util.files.Spawns;

public class RespawnListener implements Listener {
	Spawns s = Spawns.getInstance();
	BottomLine pl = BottomLine.getInstance();

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		String spawn = (s.spawnExists(pl.getConfig().getString("default-spawn"))) ? pl.getConfig().getString("default-spawn") : "default";

		if (s.spawnExists(spawn)) player.teleport(s.getSpawn(spawn));
	}
}