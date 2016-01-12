package com.Banjo226.events;

import java.text.SimpleDateFormat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class ConfigListener implements Listener {
	BottomLine bl = BottomLine.getInstance();
	PlayerData pl;

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();

		pl = new PlayerData(player.getUniqueId());
		String name;

		try {
			name = pl.getNick();
		} catch (Exception ex) {
			name = player.getName();
		}

		if (player.isOp()) {
			try {
				name = Util.colour(bl.getConfig().getString("ops-colour")) + pl.getNick();
			} catch (Exception ex) {
				name = Util.colour(bl.getConfig().getString("ops-colour")) + player.getName();
			}
		}

		try {
			pl.setDisplayName(bl.getChat().getPlayerPrefix(player) + name + bl.getChat().getPlayerSuffix(player) + "§f");
		} catch (Exception ex) {
			pl.setDisplayName(name);
		}

		player.setDisplayName(pl.getDisplayName());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();

		pl = new PlayerData(player.getUniqueId());
		pl.setIPAddress(player);
		pl.setUUID(player.getUniqueId());
		pl.setOpStatus(player.isOp());
		pl.setDefaultName(player.getName());

		String name;
		if (pl.getNick() == null) {
			pl.setNick(player.getName());
		}

		try {
			pl.setDisplayName(pl.getDisplayName());
		} catch (NullPointerException ex) {
			pl.setDisplayName(player.getDisplayName());
		}

		name = pl.getNick();

		player.setPlayerListName(Util.colour(name));

		if (!player.hasPlayedBefore()) {
			try {
				pl.setDisplayName(bl.getChat().getPlayerPrefix(player) + pl.getDefaultName() + bl.getChat().getPlayerSuffix(player) + "§f");
			} catch (NullPointerException ex) {
				pl.setDisplayName(player.getDisplayName());
			}

			pl.clearHistory();
		}

		if (pl.getDisplayName() == null) {
			pl.setDisplayName(player.getDisplayName());
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();

		if (!Store.jailed.contains(player.getName())) {
			pl = new PlayerData(player.getUniqueId());
			pl.setLocation(player.getLocation());
			
			if (e.getFrom().distance(e.getTo()) > 10) {
				pl.setBackLocation(e.getFrom());
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss a z");

		pl = new PlayerData(player.getUniqueId());
		pl.setLastTimeConnected(sdf);
	}
}