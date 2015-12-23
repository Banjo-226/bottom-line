package com.Banjo226.commands.inventory.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class Item extends Cmd implements TabCompleter {

	public Item() {
		super("item", Permissions.ITEM);
	}

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		if (args.length == 0) {
			Util.invalidArgCount(sender, "Item", "Spawn an item into the player's inventory.", "/i [type id] <amount> <meta...>");
			return;
		}

		Player player = (Player) sender;
		Material material = null;
		ItemStack ite = null;
		int count = 1;

		if (args.length > 1) {
			try {
				count = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage("§cItem: §4Invalid amount '" + args[1] + "'");
				return;
			}
		}

		try {
			if (args[0].contains(":")) {
				String[] gData = args[0].split(":");

				short data = Short.parseShort(gData[1]);
				material = Material.matchMaterial(gData[0]);
				ite = new ItemStack(material, count, data);
			} else {
				material = Material.matchMaterial(args[0]);
				ite = new ItemStack(material, count);
			}
		} catch (NullPointerException ex) {
			sender.sendMessage("§cItem: §4Unknown item '" + args[0] + "'");
			return;
		}

		if (ite.getType() == Material.SKULL) {
			ite = new ItemStack(Material.SKULL_ITEM, count);
		}

		String id = (args[0].contains(":")) ? Integer.toString(ite.getTypeId()) + ":" + ite.getData().getData() : Integer.toString(ite.getTypeId());
		sender.sendMessage("§6Item: §eGave you the item §l'" + ite.getType().toString().toLowerCase().replaceAll("_", " ") + "' §e(ID " + id + ")");

		if (args.length >= 3) {
			String[] metaArgs = args[2].split(";");
			for (String meta : metaArgs) {
				if (meta.toLowerCase().startsWith("name:")) {
					String name = meta.substring(5, meta.length());
					name = Util.colour(name).replaceAll("_", " ");

					ItemMeta im = ite.getItemMeta();
					im.setDisplayName(name);
					ite.setItemMeta(im);
				} else if (meta.toLowerCase().startsWith("lore:")) {
					String lore = meta.substring(5, meta.length());
					lore = Util.colour(lore).replaceAll("_", " ");

					ItemMeta im = ite.getItemMeta();
					im.setLore(Arrays.asList(lore));
					ite.setItemMeta(im);
				} else if (meta.toLowerCase().startsWith("player:")) {
					if (ite.getType() == Material.SKULL_ITEM) {
						String head = meta.substring(7, meta.length());
						OfflinePlayer offline = Bukkit.getOfflinePlayer(head);
						if (offline != null) {
							SkullMeta im = (SkullMeta) ite.getItemMeta();
							im.setOwner(offline.getName());
							im.setDisplayName("§c" + offline.getName());
							ite.setItemMeta(im);
						}

						if (offline.isOnline()) {
							Player target = offline.getPlayer();
							SkullMeta im = (SkullMeta) ite.getItemMeta();
							im.setOwner(offline.getName());
							im.setDisplayName("§c" + target.getName());
							ite.setItemMeta(im);
						}
					}
				}
			}
		}

		player.getInventory().addItem(ite);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("item")) {
			if (args.length == 1) {
				ArrayList<String> ids = new ArrayList<>();

				if (!args[0].equalsIgnoreCase("")) {
					for (Material m : Material.values()) {
						ItemStack ite = new ItemStack(m);
						if (args[0].toLowerCase().startsWith(ite.getType().toString())) {
							ids.add(ite.getType().toString().toLowerCase());
						}
					}
				} else {
					for (Material m : Material.values()) {
						ItemStack ite = new ItemStack(m);
						ids.add(ite.getType().toString().toLowerCase());
					}
				}

				Collections.sort(ids);
				return ids;
			}
		}
		return null;
	}
}