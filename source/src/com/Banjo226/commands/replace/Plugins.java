package com.Banjo226.commands.replace;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;

import com.Banjo226.commands.PermissionMessages;
import com.Banjo226.commands.Permissions;

public class Plugins implements Listener {

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player sender = e.getPlayer();

		if (e.getMessage().equalsIgnoreCase("/plugman")) {
			e.setCancelled(false);
			return;
		}

		if (e.getMessage().equalsIgnoreCase("/plugins") || e.getMessage().equalsIgnoreCase("/pl") || e.getMessage().equalsIgnoreCase("/pls") || e.getMessage().equalsIgnoreCase("/bukkit:plugins") || e.getMessage().equalsIgnoreCase("/bukkit:pl")) {
			e.setCancelled(true);
			if (sender.hasPermission(Permissions.PLUGINS))
				sender.sendMessage("§8» §7Plugins on the server: " + getPlugins());
			else
				sender.sendMessage(PermissionMessages.CMD.toString());
		}
	}

	@EventHandler
	public void consoleCommand(ServerCommandEvent e) {
		String msg = e.getCommand().toLowerCase();
		CommandSender sender = e.getSender();

		if (msg.startsWith("plugman")) {
			e.setCancelled(false);
			return;
		}

		if (msg.startsWith("plugins") || msg.startsWith("pl") || msg.startsWith("pls") || msg.startsWith("bukkit:plugins") || msg.startsWith("bukkit:pl")) {
			e.setCancelled(true);
			sender.sendMessage("§8» §7Plugins on the server: " + getPlugins());
		}
	}
	
	public String getPlugins() {
		StringBuilder plugins = new StringBuilder();
		Plugin[] pl = Bukkit.getPluginManager().getPlugins();

		for (Plugin pls : pl) {
			if (plugins.length() > 0) {
				plugins.append("§7, ");
			}

			plugins.append(pls.isEnabled() ? "§a" : "§c");
			plugins.append(pls.getDescription().getName().replaceAll("_", " "));

			if (!pls.isEnabled()) {
				plugins.append(" §4(plugin disabled)");
			}
		}

		return "§8(§7" + pl.length + "§8) §7" + plugins.toString();
	}
}