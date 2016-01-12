package com.Banjo226.commands.inventory.sub;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.inventory.InvCommand;

public class SearchItem extends InvCommand {

	public SearchItem() {
		super("search", "Search all inventories for the specified item", "[item id]", Arrays.asList("hasitem", "hasi", "has"), Permissions.HASITEM);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Util.invalidArgCount(sender, "Search Item", "Search all inventories for the specified item.", "/inv search [item id]");
		} else {
			StringBuilder str = new StringBuilder();
			StringBuilder header = new StringBuilder();

			Material material = null;
			ItemStack ite = null;
			int count = 1;

			try {
				int id = Integer.parseInt(args[0]);

				if (args.length > 0) {
					String[] gData = args[0].split(":");
					material = Material.matchMaterial(gData[0]);

					try {
						ite = new ItemStack(material);
					} catch (NullPointerException e) {
						sender.sendMessage("§cSearch Item: §4Unknown item '" + args[0] + "'");
						return;
					}
				}

				if (args.length > 1) {
					try {
						count = Integer.parseInt(args[1]);
					} catch (NumberFormatException ex) {
						return;
					}
				}

				if (material == null) {
					sender.sendMessage("§cSearch Item: §4Unknown item '" + args[1] + "'");
				} else {
					header.append("§6Search Item: §ePlayers with the item of: ");
					header.append("§e" + ite.getType().toString().toLowerCase() + "\n");

					for (Player player : Bukkit.getOnlinePlayers()) {
						if (player.getInventory().contains(id, count)) {
							if (str.length() > 0) {
								str.append("§6, ");
							}

							PlayerData pd = new PlayerData(player.getUniqueId(), false);
							str.append("§e" + pd.getDisplayName());
						}
					}
				}

				if (str.length() > 0) {
					sender.sendMessage(header.toString());
					sender.sendMessage(str.toString());
				} else
					sender.sendMessage("§cSearch Item: §4No players found with the item of " + ite.getType().toString().toLowerCase());
			} catch (NumberFormatException e) {
				material = Material.matchMaterial(args[0]);

				try {
					if (args.length > 0) {
						String[] gData = args[0].split(":");
						material = Material.matchMaterial(gData[0]);

						try {
							ite = new ItemStack(material);
						} catch (NullPointerException ex) {
							sender.sendMessage("§cSearch Item: §4Unknown item '" + args[0] + "'");
							return;
						}
					}

					header.append("§6Search Item: §ePlayers with the item of: ");
					header.append("§e" + ite.getType().toString().toLowerCase() + "\n");

					for (Player player : Bukkit.getOnlinePlayers()) {
						if (player.getInventory().contains(material, count)) {
							if (str.length() > 0) {
								str.append("§6, ");
							}

							PlayerData pd = new PlayerData(player.getUniqueId(), false);
							str.append("§e" + pd.getDisplayName());
						}
					}

					if (str.length() > 0) {
						sender.sendMessage(header.toString());
						sender.sendMessage(str.toString());
					} else
						sender.sendMessage("§cSearch Item: §4No players found with the item of " + ite.getType().toString().toLowerCase());
				} catch (NullPointerException ex) {
					sender.sendMessage("§cSearch Item: §4Unknown item '" + args[0] + "'");
				}
				return;
			}
		}
	}
}