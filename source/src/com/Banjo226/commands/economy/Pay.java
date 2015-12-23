package com.Banjo226.commands.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Money;
import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class Pay extends Cmd {
	Money econ = Money.getInstance();
	BottomLine pl = BottomLine.getInstance();

	public Pay() {
		super("pay", Permissions.PAY);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		if (args.length < 2) {
			Util.invalidArgCount(sender, "Pay", "Pay another player, while removing the amount from your balance.", "/pay [amount] [player]");
			return;
		}

		double amount;
		String symbol;
		try {
			amount = Double.parseDouble(args[0]);
			symbol = pl.getConfig().getString("economy.money-symbol");
		} catch (Exception e) {
			sender.sendMessage("§cPay: §4" + args[0] + " is not a valid number.");
			return;
		}

		if (args.length == 2) {
			Player player = Bukkit.getPlayer(args[1]);
			if (player == null) {
				Util.offline(sender, "Pay", args[1]);
				return;
			}

			if (pl.getConfig().getBoolean("economy.negative-bal") == true) {
				econ.removeBalance(sender, amount);
				econ.addBalance(player, amount);
			} else {
				if (econ.getBalance(sender) < amount && !sender.isOp()) {
					sender.sendMessage("§cPay: §4Insufficient money to send!");
					return;
				} else {
					econ.removeBalance(sender, amount);
					econ.addBalance(player, amount);
				}
			}

			sender.sendMessage("§6Pay: §eTransaction successful! Added §a" + symbol + amount + " §eto " + player.getDisplayName() + "'s account.");
			player.sendMessage("§6Pay: §eTransaction successful! Received §a" + symbol + amount + " §efrom " + ((Player) sender).getDisplayName() + "!");
		}
	}
}