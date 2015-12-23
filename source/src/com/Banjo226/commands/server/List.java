package com.Banjo226.commands.server;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;

public class List extends Cmd {
	BottomLine pl = BottomLine.getInstance();

	public List() {
		super("list", Permissions.LIST);
	}

	public void run(CommandSender sender, String[] args) {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			StringBuilder players = new StringBuilder();

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (Bukkit.getOnlinePlayers().size() == 0) {
					players.append("§6No players online at the moment.");
				}

				PlayerData pd = new PlayerData(player.getName());

				if (players.length() > 0) {
					players.append("§e, ");
				}

				players.append("§6" + pd.getDisplayName());
			}

			sender.sendMessage("§7§m------------§e §8[§e" + Bukkit.getOnlinePlayers().size() + "§7/§e" + Bukkit.getMaxPlayers() + "§8] §eonline players §7§m------------§e");
			sender.sendMessage("§6Players online§e: §6 " + players.toString());
			return;
		}

		int data = 0;
		PlayerData pd;

		sender.sendMessage("§7§m------------§e §8[§e" + Bukkit.getOnlinePlayers().size() + "§7/§e" + Bukkit.getMaxPlayers() + "§8] §eonline players §7§m------------§e");
		if (pl.getConfig().getBoolean("list-sort") == true) {
			if (Bukkit.getOnlinePlayers().size() > 0) {
				try {
					for (String i : pl.getPerms().getGroups()) {
						StringBuilder group = new StringBuilder();
						StringBuilder header = new StringBuilder();

						header.append("§6" + i + "§e: ");
						for (Player player : Bukkit.getOnlinePlayers()) {
							pd = new PlayerData(player.getName());

							if (i.equalsIgnoreCase(pl.getPerms().getPrimaryGroup(player))) {
								if (data > 0) {
									group.append("§e, ");
								}

								group.append("§6" + pd.getDisplayName());

								data += 1;
							} else {
								continue;
							}
						}

						if (group.length() > 0) {
							sender.sendMessage(header.toString() + group.toString());
						}

						data = 0;
					}
				} catch (Exception e) {
					e.printStackTrace();

					StringBuilder players = new StringBuilder();
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (Bukkit.getOnlinePlayers().size() == 0) {
							players.append("§6No players online at the moment.");
						}

						pd = new PlayerData(player.getName());

						if (players.length() > 0) {
							players.append("§e, ");
						}

						players.append("§6" + pd.getDisplayName());
					}
					sender.sendMessage("§6Players online§e: §6 " + players.toString());
				}
			}

			if (Bukkit.getOnlinePlayers().size() == 0) {
				sender.sendMessage("§6No players online right now :(");
			}
		} else {
			StringBuilder players = new StringBuilder();

			for (Player player : Bukkit.getOnlinePlayers()) {
				if (Bukkit.getOnlinePlayers().size() == 0) {
					players.append("§6No players online at the moment.");
				}

				pd = new PlayerData(player.getName());

				if (players.length() > 0) {
					players.append("§e, ");
				}

				players.append("§6" + pd.getDisplayName());
			}

			sender.sendMessage("§6Players online§e: §6 " + players.toString());
			return;
		}
	}
}