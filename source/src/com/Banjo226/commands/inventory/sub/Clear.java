package com.Banjo226.commands.inventory.sub;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Money;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.inventory.InvCommand;

public class Clear extends InvCommand {
	BottomLine pl = BottomLine.getInstance();
	Money econ = Money.getInstance();

	public Clear() {
		super("clear", "Clear a specified inventory, or yours.", "<player>", Arrays.asList("ci", "clearinv", "cleari"), Permissions.CLEAR);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please specify a player.");
				return;
			}

			if (pl.getConfig().getBoolean("inventory.clear.pay") == true) {
				if (!sender.isOp()) {
					double amount = pl.getConfig().getDouble("inventory.clear.amount");
					String symbol = "§a" + pl.getConfig().getString("economy.money-symbol");
					if (pl.getConfig().getBoolean("economy.negative-bal") == false) {
						if ((econ.getBalance(sender) - amount) < 0) {
							sender.sendMessage("§cClear: §4You do not have enough money to clear your inventory.");
							return;
						}
					}

					sender.sendMessage("§6Clear: §eWithdrew " + symbol + amount + "§e!");
					econ.removeBalance(sender, amount);
				}
			}

			Player player = (Player) sender;
			sender.sendMessage("§6Clear: §eCleared your inventory!");
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);

			Util.playSound(player);
		} else if (args.length == 1 && sender.hasPermission(Permissions.CLEAR_OTHERS)) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "Clear", args[0]);
				return;
			}

			if (target == sender) {
				Bukkit.dispatchCommand(sender, "inv clear");
				return;
			}

			PlayerData pd = new PlayerData(target.getUniqueId(), false);
			String name = sender.getName();
			if (sender instanceof Player) {
				name = new PlayerData(((Player) sender).getUniqueId(), false).getDisplayName();
			}

			sender.sendMessage("§6Clear: §eCleared §6" + pd.getDisplayName() + "§e's inventory");
			target.sendMessage("§6Clear: §e" + name + " §ecleared your inventory!");
			target.getInventory().setContents(null);
			target.getInventory().setArmorContents(null);

			Util.playSound(target);
		}
	}
}