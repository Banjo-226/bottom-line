package com.Banjo226.events.signs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.Banjo226.commands.Permissions;
import com.Banjo226.util.Util;

public class FormatSigns implements Listener {

	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		Player player = e.getPlayer();
		if (player.hasPermission(Permissions.SIGNCOLOURS)) {
			for (int i = 0; i < 4; i++) {
				e.setLine(i, Util.parseColours(e.getLine(i)));
			}
		}

		if (player.hasPermission(Permissions.SIGNFORMAT)) {
			for (int i = 0; i < 4; i++) {
				e.setLine(i, Util.parseFormat(e.getLine(i)));
			}
		}

		if (player.hasPermission(Permissions.SIGNMAGIC)) {
			for (int i = 0; i < 4; i++) {
				e.setLine(i, Util.parseMagic(e.getLine(i)));
			}
		}
	}
}