package com.Banjo226.commands.inventory.kits;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.Banjo226.BottomLine;
import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;

public class ShowKits extends Cmd {
	BottomLine pl = BottomLine.getInstance();

	public ShowKits() {
		super("showkit", Permissions.SHOWKITS);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (args.length == 0) {
			Util.invalidArgCount(sender, "Show kits", "Show the items in a kit", "/showkit [kit]");
			return;
		}

		String kit = "";
		boolean existing = false;
		for (String entry : pl.getConfig().getConfigurationSection("kits").getKeys(false)) {
			if (entry.toLowerCase().startsWith(args[0].toLowerCase())) {
				existing = true;
				kit = entry;
			}
		}

		if (existing) {
			List<String> items = pl.getConfig().getStringList("kits." + kit + ".items");
			DecimalFormat form = new DecimalFormat("#.00");
			sender.sendMessage("§6Show kits: §eKit §6" + kit + " §ehas the following items:");

			String symbol = pl.getConfig().getString("economy.money-symbol");
			double price = pl.getConfig().getDouble("kits." + kit + ".cost");

			sender.sendMessage("§e- §6Cost: §a" + symbol + form.format(price));
			for (String item : items) {
				String[] data = item.split(" ");
				if (data.length == 1) {
					sender.sendMessage("§e- §6" + data[0]);
				} else if (data.length == 2) {
					sender.sendMessage("§e- §6" + data[0] + " §e(§eAmount: " + data[1] + "§e)");
				} else if (data.length == 3) {
					sender.sendMessage("§e- §6" + data[0] + " §e(§eAmount: " + data[1] + ", meta: " + Util.parseColours(data[2]) + "§e)");
				} else {
					sender.sendMessage("§e- §6" + item);
				}
			}
		} else {
			sender.sendMessage("§cShow kits: §4" + args[0] + " is not an existing kit!");
		}
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("showkit")) {
			if (args.length == 1) {
				List<String> kits = new ArrayList<>();
				if (!args[0].equalsIgnoreCase("")) {
					for (String entry : pl.getConfig().getConfigurationSection("kits").getKeys(false)) {
						if (entry.toLowerCase().startsWith(args[0].toLowerCase())) {
							kits.add(entry);
						}
					}
				} else {
					for (String entry : pl.getConfig().getConfigurationSection("kits").getKeys(false)) {
						kits.add(entry);
					}
				}

				return kits;
			}
		}
		return null;
	}
}