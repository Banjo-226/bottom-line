package com.Banjo226.events.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;

import com.Banjo226.commands.Permissions;

public class FormatChatListener extends BottomLineChat {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		BottomLine pl = BottomLine.getInstance();
		String message = e.getMessage();

		if (pl.getConfig().getBoolean("law.block.smartChecking") == false) {
			for (String word : message.split(" ")) {
				if (Store.swearlist.contains(word.toLowerCase())) {
					if (!player.hasPermission(Permissions.EXCEPTION)) {
						e.setCancelled(true);
						player.sendMessage(Util.colour(pl.getConfig().getString("law.block.blocked").replaceAll("%word%", word)));
					}
				}
			}
		}

		if (pl.getConfig().getBoolean("law.block.smartChecking") == true) {
			for (int i = 0; i < Store.swearlist.size(); i++) {
				if (!player.hasPermission(Permissions.EXCEPTION)) {
					if (message.toLowerCase().contains(Store.swearlist.get(i).toLowerCase())) {
						e.setCancelled(true);
						player.sendMessage(Util.colour(pl.getConfig().getString("law.block.blocked").replaceAll("%word%", Store.swearlist.get(i))));
					}
				}
			}
		}

		if (pl.getConfig().getBoolean("chat.grammar") == true) {
			if (!player.hasPermission(Permissions.GRAMMAR_OVERRIDE)) {
				if (e.getMessage().length() > 4) {
					if (!message.endsWith(".") && !(message.endsWith("!") || message.endsWith("?") || message.endsWith(","))) {
						if (!(message.endsWith(")") || message.endsWith("(") || message.endsWith(":") || message.endsWith(";"))) {
							message += ".";
						}
					}

					if (!Character.isUpperCase(message.charAt(0))) {
						char upper = Character.toUpperCase(e.getMessage().charAt(0));
						message = upper + message.substring(1);
					}
				}
			}
		}

		if (player.hasPermission(Permissions.CHATCOLOURS)) {
			message = Util.parseColours(message);
		}

		if (player.hasPermission(Permissions.CHATFORMAT)) {
			message = Util.parseFormat(message);
		}

		if (player.hasPermission(Permissions.CHATMAGIC)) {
			message = Util.parseMagic(message);
		}

		e.setMessage(message);
	}
}