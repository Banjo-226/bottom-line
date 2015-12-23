package com.Banjo226.commands.replace;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import com.Banjo226.commands.PermissionMessages;
import com.Banjo226.commands.Permissions;

public class Version implements Listener {

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player sender = e.getPlayer();
		String[] args = e.getMessage().toLowerCase().split(" ");

		if (e.getMessage().toLowerCase().startsWith("/ver") || e.getMessage().toLowerCase().startsWith("/version") || e.getMessage().toLowerCase().startsWith("/about") || e.getMessage().toLowerCase().startsWith("/icanhasbukkit")
				|| e.getMessage().toLowerCase().startsWith("/bukkit:vers") || e.getMessage().toLowerCase().startsWith("/bukkit:version") || e.getMessage().toLowerCase().startsWith("/bukkit:about") || e.getMessage().toLowerCase().startsWith("/bukkit:icanhasbukkit")
				|| e.getMessage().toLowerCase().startsWith("/bukkit:ver")) {
			e.setCancelled(true);
			if (sender.hasPermission(Permissions.VERSION)) {
				if (args.length == 1) {
					sender.sendMessage("§8» §7This server is running §a" + Bukkit.getName() + "§7, API version §a" + Bukkit.getBukkitVersion() + "§7 (Version §a" + Bukkit.getVersion() + "§7)");
					sender.sendMessage("§7The Operating System that this server is running is §a" + System.getProperty("os.name"));
				} else {
					String name = args[1];
					Plugin plugin = Bukkit.getPluginManager().getPlugin(name);

					if (plugin != null) {
						sendPluginDesc(plugin, sender);
					} else {
						name = name.toLowerCase();

						boolean notNull = false;
						for (Plugin plugins : Bukkit.getPluginManager().getPlugins()) {
							if (plugins.getName().toLowerCase().startsWith(name)) {
								sendPluginDesc(plugins, sender);
								notNull = true;
							}
						}

						if (notNull == false) {
							sender.sendMessage("§8» §7No plugin found with the name of §8" + name + "§7!");
						}
					}
				}
			} else {
				sender.sendMessage(PermissionMessages.CMD.toString());
			}
		}
	}

	@EventHandler
	public void consoleCommand(ServerCommandEvent e) {
		CommandSender sender = e.getSender();
		String[] args = e.getCommand().split(" ");

		if (e.getCommand().startsWith("vers") || e.getCommand().startsWith("version") || e.getCommand().startsWith("about") || e.getCommand().startsWith("icanhasbukkit") || e.getCommand().startsWith("bukkit:vers") || e.getCommand().startsWith("bukkit:version")
				|| e.getCommand().startsWith("bukkit:about") || e.getCommand().startsWith("bukkit:icanhasbukkit")) {
			e.setCancelled(true);
			if (sender.hasPermission(Permissions.VERSION)) {
				if (args.length == 1) {
					sender.sendMessage("§8» §7This server is running §a" + Bukkit.getName() + "§7, API version §a" + Bukkit.getBukkitVersion() + "§7 (Version §a" + Bukkit.getVersion() + "§7)");
				} else {
					String name = args[1];
					Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(name);

					if (plugin != null) {
						sendPluginDesc(plugin, sender);
					} else {
						name = name.toLowerCase();

						boolean notNull = false;
						for (Plugin plugins : Bukkit.getPluginManager().getPlugins()) {
							if (plugins.getName().toLowerCase().startsWith(name)) {
								sendPluginDesc(plugins, sender);
								notNull = true;
							}
						}

						if (notNull == false) {
							sender.sendMessage("§8» §7No plugin found with the name of §8" + name + "§7!");
						}
					}
				}
			} else {
				sender.sendMessage(PermissionMessages.CMD.toString());
			}
		}
	}

	private void sendPluginDesc(Plugin plugin, CommandSender sender) {
		PluginDescriptionFile pdf = plugin.getDescription();
		String en = plugin.isEnabled() ? "§a" : "§c";
		en += pdf.getName().replaceAll("_", " ");
		sender.sendMessage("§8» " + en + "§7 version §a" + pdf.getVersion());

		if (pdf.getDescription() != null) {
			sender.sendMessage("§7" + pdf.getDescription());
		}

		if (pdf.getWebsite() != null) {
			sender.sendMessage("§7Website: §a" + pdf.getWebsite());
		}

		if (pdf.getMain() != null) {
			if (sender.isOp()) {
				sender.sendMessage("§7Main class: §a" + pdf.getMain());
			}
		}

		if (!pdf.getAuthors().isEmpty()) {
			if (pdf.getAuthors().size() == 1) {
				sender.sendMessage("§7Author: " + getAuthors(pdf));
			} else {
				sender.sendMessage("§7Authors: " + getAuthors(pdf));
			}
		}
	}

	private String getAuthors(PluginDescriptionFile pdf) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < pdf.getAuthors().size(); i++) {
			if (result.length() > 0) {
				result.append("§7, ");
			}

			result.append("§a" + pdf.getAuthors().get(i));
		}
		return result.toString();
	}
}