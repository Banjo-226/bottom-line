package com.Banjo226.events.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.commands.Permissions;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class ManipulateChatListener extends BottomLineChat {
	BottomLine pl = BottomLine.getInstance();

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		PlayerData pd = new PlayerData(e.getPlayer().getUniqueId());
		Player player = e.getPlayer();

		String message = e.getMessage();
		String group;
		String name;
		String nick;

		try {
			group = pl.getPerms().getPrimaryGroup(player).toLowerCase();
		} catch (Exception ex) {
			group = "default";
		}

		try {
			name = Util.colour(pd.getDisplayName());
		} catch (Exception ex) {
			name = player.getDisplayName();
		}

		try {
			nick = Util.colour(pd.getNick());
		} catch (Exception ex) {
			pd.setDefaultName(player.getName());
			nick = Util.colour(pd.getDefaultName());
		}

		if (e.getFormat().contains("§f<%s>")) {
			e.setFormat(Util.colour(pl.getConfig().getString("chat.format").replaceAll("%player%", "%s").replaceAll("%displayname%", pd.getDisplayName()).replaceAll("%message%", message).replaceAll("%nickname%", pd.getNick())));
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

		if (pl.getConfig().contains("format-groups." + group) && pl.getConfig().getBoolean("format-groups.enabled") == true) {
			e.setFormat(Util.colour(pl.getConfig().getString("format-groups." + group).replaceAll("%player%", e.getPlayer().getName()).replaceAll("%displayname%", name).replaceAll("%message%", message).replaceAll("%nickname%", nick)));
		} else {
			e.setFormat(Util.colour(pl.getConfig().getString("chat.format").replaceAll("%player%", e.getPlayer().getName()).replaceAll("%displayname%", name).replaceAll("%message%", message).replaceAll("%nickname%", nick)));
		}
	}
}