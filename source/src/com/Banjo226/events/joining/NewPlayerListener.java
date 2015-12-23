package com.Banjo226.events.joining;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Money;
import com.Banjo226.util.files.PlayerData;
import com.Banjo226.util.files.Spawns;

public class NewPlayerListener implements Listener {
	BottomLine pl = BottomLine.getInstance();
	Money econ = Money.getInstance();
	Spawns s = Spawns.getInstance();
	PlayerData con;

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e) {
		final Player player = e.getPlayer();

		con = new PlayerData(player.getName(), false);

		if (!con.dataExists(player.getName()) || !player.hasPlayedBefore()) {
			con.createData();

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				public void run() {
					if (pl.getConfig().getBoolean("newbies.broadcastWelcome") == true) {
						Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("newbies.message")).replaceAll("%player%", player.getDisplayName()));
					}
				}
			}, 10L);

			if (pl.getConfig().getBoolean("newbies.kit.enabled") == true) {
				Bukkit.dispatchCommand(player, "kit " + pl.getConfig().getString("newbies.kit.kit"));
			}

			if (s.spawnExists(pl.getConfig().getString("newbies-spawn")))
				player.teleport(s.getSpawn(pl.getConfig().getString("newbies-spawn")));
			else if (s.spawnExists(pl.getConfig().getString("default-spawn"))) player.teleport(s.getSpawn(pl.getConfig().getString("default-spawn")));
		}

		if (!econ.hasPlayerData(player)) {
			double amount = pl.getConfig().getDouble("economy.default-amount");
			econ.setBalance(player, amount);
		}
	}
}