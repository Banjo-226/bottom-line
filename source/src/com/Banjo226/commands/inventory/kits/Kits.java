package com.Banjo226.commands.inventory.kits;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.Banjo226.BottomLine;
import com.Banjo226.commands.PermissionMessages;
import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Money;

public class Kits extends Cmd implements TabCompleter {
	BottomLine pl = BottomLine.getInstance();
	Money econ = Money.getInstance();

	public Kits() {
		super("kit", Permissions.KIT);
	}

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) throws Exception {
		String symbol = pl.getConfig().getString("economy.money-symbol");
		DecimalFormat form = new DecimalFormat("#.00");
		if (args.length == 0) {
			StringBuilder str = new StringBuilder();
			for (String kit : pl.getConfig().getConfigurationSection("kits").getKeys(false)) {
				if (str.length() > 0) {
					str.append("§e, ");
				}

				double amount = pl.getConfig().getDouble("kits." + kit + ".cost");
				String add = (pl.getConfig().getDouble("kits." + kit + ".cost") > 0) ? kit + " §e(§a" + symbol + form.format(amount) + "§e)" : kit;

				str.append("§6" + add);
			}
			sender.sendMessage("§6Kits: §eExisting kits on the server: " + str.toString());
			return;
		}

		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		args[0] = args[0].toLowerCase();
		boolean existing = false;
		String kit = "";
		for (String entry : pl.getConfig().getConfigurationSection("kits").getKeys(false)) {
			if (entry.toLowerCase().startsWith(args[0].toLowerCase())) {
				existing = true;
				kit = entry;
			}
		}

		if (existing) {
			Player player = (Player) sender;
			double amount = pl.getConfig().getDouble("kits." + kit + ".cost");

			if (!isAuthorised(sender, Permissions.ALLKITS) || !isAuthorised(sender, "bottomline.kit." + kit.toLowerCase())) {
				sender.sendMessage(PermissionMessages.KIT.toString());
				return;
			}

			if (pl.getConfig().getBoolean("economy.negative-bal") == false && !isAuthorised(sender, Permissions.COSTOVERRIDE)) {
				if ((econ.getBalance(sender) - amount) < 0) {
					sender.sendMessage("§cKit: §4You cannot afford this kit!");
					return;
				}
			}

			if (!isAuthorised(sender, Permissions.COSTOVERRIDE)) econ.removeBalance(player, amount);

			List<String> items = pl.getConfig().getStringList("kits." + kit + ".items");
			for (String item : items) {
				String[] data = item.split(" ");
				Material material = null;
				ItemStack ite = null;
				int count = 1;

				if (data.length > 1) {
					try {
						count = Integer.parseInt(data[1]);
					} catch (NumberFormatException e) {
						count = 1;
					}
				}

				try {
					if (data[0].contains(":")) {
						String[] gData = data[0].split(":");

						short edata = Short.parseShort(gData[1]);
						material = Material.matchMaterial(gData[0]);
						ite = new ItemStack(material, count, edata);
					} else {
						material = Material.matchMaterial(data[0]);
						ite = new ItemStack(material, count);
					}

					if (ite.getType() == Material.SKULL) {
						ite = new ItemStack(Material.SKULL_ITEM, count);
					}
				} catch (Exception e) {
					sender.sendMessage("§cKit: §4Unknown item '" + data[0] + "', report to admin!");
					continue;
				}

				if (data.length >= 3) {
					String[] metaArgs = data[2].split(";");
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

			sender.sendMessage("§6Kit: §eSuccessfully gave you the kit §o" + kit);
			if (amount > 0 && !isAuthorised(sender, Permissions.COSTOVERRIDE)) {
				sender.sendMessage("§6Kit: §eWithdrew §a" + symbol + form.format(amount) + " §eout of your account!");
			}
		} else {
			sender.sendMessage("§cKit: §4" + args[0] + " is not an existing kit!");
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kit")) {
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