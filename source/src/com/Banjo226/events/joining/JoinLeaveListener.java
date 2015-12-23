package com.Banjo226.events.joining;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Util;

public class JoinLeaveListener implements Listener {
	BottomLine pl = BottomLine.getInstance();

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e) {
		final Player player = e.getPlayer();

		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		final Date date = new Date();

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
			public void run() {
				if (pl.getConfig().getBoolean("join.enabled") == true) {
					e.setJoinMessage(Util.colour(pl.getConfig().getString("join.message").replaceAll("%player%", player.getName()).replaceAll("%time%", sdf.format(date))));
				} else {
					e.setJoinMessage(null);
				}
			}
		}, 10L);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		Date date = new Date();

		if (pl.getConfig().getBoolean("leave.enabled") == true) {
			e.setQuitMessage(Util.colour(pl.getConfig().getString("leave.message").replaceAll("%player%", player.getName()).replaceAll("%time%", sdf.format(date))));
		} else {
			e.setQuitMessage(null);
		}
	}
}