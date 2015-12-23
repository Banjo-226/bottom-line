package com.Banjo226.commands.player.whois;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Money;
import com.Banjo226.util.files.PlayerData;

public class Whois extends Cmd {
	Money econ = Money.getInstance();
	BottomLine pl = BottomLine.getInstance();

	public Whois() {
		super("whois", Permissions.WHOIS);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		DecimalFormat form = new DecimalFormat("#.00");
		String symbol = pl.getConfig().getString("economy.money-symbol");

		if (args.length == 0) {
			Util.invalidArgCount(sender, "Whois", "Displays recent information about an online player", "/whois [player]");
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		PlayerData pd;
		if (target == null) {
			pd = new PlayerData(args[0], false);
			if (pd.dataExists(args[0])) {
				sender.sendMessage("§7§m------§e " + pd.getDisplayName() + "§7 §m------");
				sender.sendMessage("§8[§c!§8] §eThis is not all of the information about the player!");
				sender.sendMessage("§e- Real name: §6" + pd.getDefaultName());
				sender.sendMessage("§e- Last location: §6" + pd.getWorld().getName() + "§6, " + pd.getX() + "§6, " + pd.getY() + "§6, " + pd.getZ());
				sender.sendMessage("§e- Money: §6" + symbol + form.format(econ.getBalance(args[0])));
				sender.sendMessage("§e- Last IP: §6" + pd.getIP());
				sender.sendMessage("§e- OP: §6" + pd.getOpStatus().toString().toLowerCase());
				sender.sendMessage("§e- Banned: §6" + (pd.isBanned() || pd.isTempBanned() ? "true" : "false") + (pd.isTempBanned() ? ", temp banned" : ""));
				sender.sendMessage("§e- Last left: §6" + pd.getLastTimeConnected());
			} else {
				Util.offline(sender, "Whois", args[0]);
			}
			return;
		}

		pd = new PlayerData(target.getName());
		sender.sendMessage("§7§m------§e " + pd.getDisplayName() + "§7 §m------");
		sender.sendMessage("§e- Real name: §6" + pd.getDefaultName());
		sender.sendMessage("§e- Current Location: §6" + pd.getWorld().getName() + "§6, " + pd.getX() + "§6, " + pd.getY() + "§6, " + pd.getZ());
		sender.sendMessage("§e- Health: §6" + target.getHealth());
		sender.sendMessage("§e- Hunger: §6" + target.getFoodLevel() + "§6, " + target.getSaturation() + " saturation");
		sender.sendMessage("§e- Money: §6" + symbol + form.format(econ.getBalance(args[0])));
		sender.sendMessage("§e- IP Address: §6" + pd.getIP());
		sender.sendMessage("§e- Gamemode: §6" + target.getGameMode().toString().toLowerCase());
		sender.sendMessage("§e- OP: §6" + pd.getOpStatus().toString().toLowerCase());
		sender.sendMessage("§e- Experience: §6" + target.getTotalExperience() + "§6, level " + target.getLevel());
		sender.sendMessage("§e- God: §6" + (Store.god.contains(target.getName()) ? "true" : "false"));
		sender.sendMessage("§e- Flying: §6" + (target.getAllowFlight() ? "true" : "false") + (target.isFlying() ? ", is flying" : ""));
		sender.sendMessage("§e- AFK: §6" + (Store.away.contains(target.getName()) ? "true" : "false"));
		sender.sendMessage("§e- Jailed: §6" + (Store.jailed.contains(target.getName()) ? "true" : "false"));
		sender.sendMessage("§e- Muted: §6" + (Store.muted.contains(target.getName()) ? "true" : "false"));
		sender.sendMessage("§e- Last left: §6" + pd.getLastTimeConnected());
	}
}