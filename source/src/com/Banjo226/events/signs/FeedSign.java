package com.Banjo226.events.signs;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.Banjo226.BottomLine;

import com.Banjo226.commands.Permissions;

public class FeedSign implements Listener {
	BottomLine pl = BottomLine.getInstance();

	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		Player player = e.getPlayer();
		if (player.hasPermission(Permissions.MODIFY_FEED_SIGN)) {
			if (pl.getConfig().getBoolean("signs.feed") == true) {
				if (e.getLine(0).equalsIgnoreCase("[feed]")) {
					e.setLine(0, "§8[§4Feed§8]");
				}
			}
		}
	}

	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR) return;

		if (e.getClickedBlock().getState() instanceof Sign) {
			Sign s = (Sign) e.getClickedBlock().getState();
			if (pl.getConfig().getBoolean("signs.feed") == true) {
				if (s.getLine(0).equalsIgnoreCase("§8[§4Feed§8]")) {
					Bukkit.dispatchCommand(player, "feed");
				}
			}
		}
	}
}