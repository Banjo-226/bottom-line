package com.Banjo226.commands.economy;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Money;
import com.Banjo226.commands.Permissions;

public class Eco extends Cmd {
	Money econ = Money.getInstance();
	BottomLine pl = BottomLine.getInstance();

	public Eco() {
		super("eco", Permissions.ECONOMY);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Util.invalidArgCount(sender, "Economy", "Change the balance of an individual.", "/eco [take|give|reset|set] [amount]", "/eco [take|give|reset|set] [amount] <player>");
			return;
		}

		double amount;
		DecimalFormat form;
		String sym;
		try {
			if (args.length > 1 && !args[0].equalsIgnoreCase("reset")) {
				amount = Double.parseDouble(args[1]);
				form = new DecimalFormat("#.00");
				form.format(amount);
			} else {
				amount = 0.00;
				form = new DecimalFormat("#.00");
				form.format(amount);
			}

			sym = "§a" + pl.getConfig().getString("economy.money-symbol");
		} catch (Exception e) {
			sender.sendMessage("§cEconomy: §4" + args[1] + " is not a valid amount!");
			return;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reset")) {
				sender.sendMessage("§6Economy: §eReseting your balance...");

				econ.resetBalance(sender);
			} else {
				sender.sendMessage("§cEconomy: §4Please enter the following arguements:");
				sender.sendMessage("        §4/eco [take|give|reset|set] [amount (leave empty if reset)]");
				if (sender.hasPermission(Permissions.ECONOMY_OTHERS)) {
					sender.sendMessage("        §4/eco [take|give|reset|set] [amount (this is player if reset)] <player>");
				}
			}
		}

		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("reset")) {
				Player player = Bukkit.getPlayer(args[1]);

				if (player == null) {
					Util.offline(sender, "Economy", args[1]);
					return;
				}

				econ.resetBalance(player);
				sender.sendMessage("§6Economy: §eReseting balance of " + player.getDisplayName() + "§e...");
				return;
			}

			if (!(sender instanceof Player)) {
				sender.sendMessage("Please specify a player.");
				return;
			}

			if ((econ.getBalance(sender) + amount) > pl.getConfig().getDouble("economy.bal-top") && args[0].equalsIgnoreCase("give")) {
				sender.sendMessage("§cEconomy: §4The amount is over the top balance.");
				return;
			} else if (pl.getConfig().getBoolean("economy.negative-bal") == false && args[0].equalsIgnoreCase("take")) {
				if ((econ.getBalance(sender) - amount) < 0) {
					sender.sendMessage("§cEconomy: §4The amount is under the negative balance.");
					return;
				}
			} else if (amount > pl.getConfig().getDouble("economy.bal-top") && args[0].equalsIgnoreCase("set")) {
				sender.sendMessage("§cEconomy: §4The amount is over the top balance.");
				return;
			}

			if (args[0].equalsIgnoreCase("take")) {
				sender.sendMessage("§6Economy: §eTaking " + sym + amount + "§e from your balance...");

				econ.removeBalance(sender, amount);
			} else if (args[0].equalsIgnoreCase("give")) {
				sender.sendMessage("§6Economy: §eAdding " + sym + amount + "§e to your balance...");

				econ.addBalance(sender, amount);
			} else if (args[0].equalsIgnoreCase("set")) {
				sender.sendMessage("§6Economy: §eSetting your balance to " + sym + amount + "§e...");

				econ.setBalance(sender, amount);
			} else {
				sender.sendMessage("§cEconomy: §4" + args[0] + " is not a valid arguement!");
			}
		} else if (sender.hasPermission(Permissions.ECONOMY_OTHERS) && args.length == 3) {
			Player player = Bukkit.getPlayer(args[2]);

			if (player == null) {
				Util.offline(sender, "Economy", args[2]);
				return;
			}

			if ((econ.getBalance(sender) + amount) > pl.getConfig().getDouble("economy.bal-top") && args[0].equalsIgnoreCase("give")) {
				sender.sendMessage("§cEconomy: §4The amount is over the top balance.");
				return;
			} else if (pl.getConfig().getBoolean("economy.negative-bal") == false && args[0].equalsIgnoreCase("take")) {
				if ((econ.getBalance(sender) - amount) < 0) {
					sender.sendMessage("§cEconomy: §4The amount is under the negative balance.");
					return;
				}
			} else if (amount > pl.getConfig().getDouble("economy.bal-top") && args[0].equalsIgnoreCase("set")) {
				sender.sendMessage("§cEconomy: §4The amount is over the top balance.");
				return;
			}

			if (args[0].equalsIgnoreCase("take")) {
				sender.sendMessage("§6Economy: §eTaking " + sym + amount + "§e from " + player.getDisplayName() + "§e's balance...");

				econ.removeBalance(player, amount);
			} else if (args[0].equalsIgnoreCase("give")) {
				sender.sendMessage("§6Economy: §eAdding " + sym + amount + "§e to " + player.getDisplayName() + "§e's balance...");

				econ.addBalance(player, amount);
			} else if (args[0].equalsIgnoreCase("set")) {
				sender.sendMessage("§6Economy: §eSetting balance of " + player.getDisplayName() + "§e to " + sym + amount + "§e...");

				econ.setBalance(player, amount);
			} else {
				sender.sendMessage("§cEconomy: §4" + args[0] + " is not a valid arguement!");
			}
		}
	}
}