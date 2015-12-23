package com.Banjo226.events.chat;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.Banjo226.util.Store;

import com.Banjo226.commands.Permissions;

public class SpamChecker extends BottomLineChat {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();

		if (getPlugin().getConfig().getBoolean("spam") == true) {
			if (Store.spam.containsKey(player.getName())) {
				try {
					for (Map.Entry<String, String> entry : Store.spam.entrySet()) {
						String name = entry.getKey();
						String message = entry.getValue();

						if (player.getName().equalsIgnoreCase(name)) {
							if (e.getMessage().equalsIgnoreCase(message)) {
								if (!player.hasPermission(Permissions.EXCEPTION)) {
									e.setCancelled(true);
									player.sendMessage("§cSpam: §4You cannot send the same message more than once!");
								} else {
									Store.spam.remove(player.getName());
								}
							} else {
								Store.spam.remove(player.getName());
							}
						}
					}
				} catch (Exception ex) {
					return;
				}
			} else
				Store.spam.put(player.getName(), e.getMessage());
		}
	}
}