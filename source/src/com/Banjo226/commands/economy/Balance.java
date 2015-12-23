package com.Banjo226.commands.economy;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.files.Money;
import com.Banjo226.commands.Permissions;

public class Balance extends Cmd {
	Money econ = Money.getInstance();
	BottomLine pl = BottomLine.getInstance();

	public Balance() {
		super("balance", Permissions.BALANCE);
	}

	public void run(CommandSender sender, String[] args) {
		String symbol = pl.getConfig().getString("economy.money-symbol");
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please specify a player.");
				return;
			}

			DecimalFormat form = new DecimalFormat("#.00");

			sender.sendMessage("§6Economy: §e§lBalance: §e" + symbol + form.format(econ.getBalance(sender)));
			return;
		}

		if (sender.hasPermission(Permissions.BALANCE_OTHERS) && args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			DecimalFormat form = new DecimalFormat("#.00");
			if (target == null) {
				@SuppressWarnings("deprecation")
				OfflinePlayer offline = Bukkit.getServer().getOfflinePlayer(args[0]);

				if (offline.hasPlayedBefore()) {
					sender.sendMessage("§6Economy: §e§lBalance of " + offline.getName() + ": " + symbol + form.format(econ.getBalance(offline.getName())));
				} else {
					sender.sendMessage("§4Economy: §c" + args[0] + " has not played before and has no money.");
				}
				return;
			}

			sender.sendMessage("§6Economy: §e§lBalance of " + target.getName() + ": §e" + symbol + form.format(econ.getBalance(target)));
		}
	}
}