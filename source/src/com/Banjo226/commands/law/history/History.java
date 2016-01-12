package com.Banjo226.commands.law.history;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class History extends Cmd {
	PlayerData pl;

	public History() {
		super("history", Permissions.HISTORY);
	}

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Util.invalidArgCount(sender, "History", "Check the history of a player, Eg: their mutes.", "/history [player]", "/history clear [player]", "/history add [player] [mute|jail|ban|freeze|kick] <reason>");
			return;
		}

		if (args.length == 2 && args[0].equalsIgnoreCase("clear")) {
			Player target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
				pl = new PlayerData(op.getUniqueId(), false);

				if (!pl.dataExists(pl.file)) {
					Util.offline(sender, "History", args[1]);
					return;
				}

				if (pl.historySize == 0) {
					sender.sendMessage("§cHistory: §4" + pl.getDisplayName() + " §4has no valid history to clear!");
					return;
				}

				sender.sendMessage("§6History: §eClearing history of " + pl.getDisplayName());
				pl.clearHistory();
				return;
			}

			pl = new PlayerData(target.getUniqueId());

			if (pl.historySize == 0) {
				sender.sendMessage("§cHistory: §4" + pl.getDisplayName() + " §4has no valid history to clear!");
				return;
			}

			sender.sendMessage("§6History: §eClearing history of " + pl.getDisplayName());
			pl.clearHistory();
			return;
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("add")) {
			if (args.length == 1) {
				Util.invalidArgCount(sender, "History", "Add a history entry.", "/history add [player] [mutes|jails|bans|tempbans|freezes|kicks] <timestamp (only for timed punishments)> <reason>");
				return;
			}

			String msg = "";
			if (args.length >= 5) {
				for (int i = 4; i < args.length; i++) {
					msg += args[i] + " ";
				}
			} else
				msg = "Misconduct";

			Player target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
				PlayerData pd = new PlayerData(op.getUniqueId(), false);

				if (!pd.dataExists(pd.file)) {
					Util.offline(sender, "History", args[1]);
					return;
				}

				boolean type = false;
				for (Types t : Types.values()) {
					if (args[2].equalsIgnoreCase(t.toString())) {
						type = true;
					}
				}

				if (!type) {
					sender.sendMessage("§cHistory: §4No history type for " + args[2] + "!");
					return;
				}

				SimpleDateFormat sdf = new SimpleDateFormat("(z) dd/MM/yy hh:mm:ss a");
				Date date = new Date();

				pd.addHistory(Types.valueOf(args[2]), msg, sender.getName(), sdf.format(date), null);
				sender.sendMessage("§6History: §eAdded history entry.");
			}

			PlayerData pd = new PlayerData(target.getUniqueId(), false);

			boolean type = false;
			Types ts = null;
			for (Types t : Types.values()) {
				if (args[2].toLowerCase().equalsIgnoreCase(t.toString().toLowerCase())) {
					type = true;
					ts = t;
				}
			}

			if (!type) {
				sender.sendMessage("§cHistory: §4No history type for " + args[2] + "!");
				return;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("(z) dd/MM/yy hh:mm:ss a");
			Date date = new Date();

			pd.addHistory(ts, msg.trim(), sender.getName(), sdf.format(date), args[3]);
			sender.sendMessage("§6History: §eAdded history entry.");
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
			pl = new PlayerData(op.getUniqueId(), false);

			if (!pl.dataExists(pl.file)) {
				Util.offline(sender, "History", args[0]);
				return;
			}

			if (pl.historySize == 0) {
				sender.sendMessage("§cHistory: §4" + pl.getDisplayName() + " §4has no valid history!");
				return;
			}

			sender.sendMessage("§6History: §eHistory of " + pl.getDisplayName());
			if (pl.mutes.size() > 0) {
				sender.sendMessage("§c- MUTES");
				for (int i = 0; i < pl.mutes.size(); i++) {
					sender.sendMessage("§e- " + pl.mutes.get(i));
				}
				sender.sendMessage(" ");
			}

			if (pl.jails.size() > 0) {
				sender.sendMessage("§c- JAILS");
				for (int i = 0; i < pl.jails.size(); i++) {
					sender.sendMessage("§e- " + pl.jails.get(i));
				}
				sender.sendMessage(" ");
			}

			if (pl.kicks.size() > 0) {
				sender.sendMessage("§c- KICKS");
				for (int i = 0; i < pl.kicks.size(); i++) {
					sender.sendMessage("§e- " + pl.kicks.get(i));
				}
				sender.sendMessage(" ");
			}

			if (pl.bans.size() > 0) {
				sender.sendMessage("§c- BANS");
				for (int i = 0; i < pl.bans.size(); i++) {
					sender.sendMessage("§e- " + pl.bans.get(i));
				}
				sender.sendMessage(" ");
			}

			if (pl.freezes.size() > 0) {
				sender.sendMessage("§c- FREEZES");
				for (int i = 0; i < pl.freezes.size(); i++) {
					sender.sendMessage("§e- " + pl.freezes.get(i));
				}
				sender.sendMessage(" ");
			}
			return;
		}

		pl = new PlayerData(target.getUniqueId());

		if (pl.historySize == 0) {
			sender.sendMessage("§cHistory: §4" + pl.getDisplayName() + " §4has no valid history!");
			return;
		}

		sender.sendMessage("§6History: §eHistory of " + pl.getDisplayName());
		if (pl.mutes.size() > 0) {
			sender.sendMessage("§c- MUTES");
			for (int i = 0; i < pl.mutes.size(); i++) {
				sender.sendMessage("§e- " + pl.mutes.get(i));
			}
			sender.sendMessage(" ");
		}

		if (pl.jails.size() > 0) {
			sender.sendMessage("§c- JAILS");
			for (int i = 0; i < pl.jails.size(); i++) {
				sender.sendMessage("§e- " + pl.jails.get(i));
			}
			sender.sendMessage(" ");
		}

		if (pl.kicks.size() > 0) {
			sender.sendMessage("§c- KICKS");
			for (int i = 0; i < pl.kicks.size(); i++) {
				sender.sendMessage("§e- " + pl.kicks.get(i));
			}
			sender.sendMessage(" ");
		}

		if (pl.bans.size() > 0) {
			sender.sendMessage("§c- BANS");
			for (int i = 0; i < pl.bans.size(); i++) {
				sender.sendMessage("§e- " + pl.bans.get(i));
			}
			sender.sendMessage(" ");
		}

		if (pl.freezes.size() > 0) {
			sender.sendMessage("§c- FREEZES");
			for (int i = 0; i < pl.freezes.size(); i++) {
				sender.sendMessage("§e- " + pl.freezes.get(i));
			}
			sender.sendMessage(" ");
		}
	}
}