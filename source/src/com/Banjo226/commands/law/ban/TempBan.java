package com.Banjo226.commands.law.ban;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.law.history.Types;

public class TempBan extends Cmd {
	BottomLine pl = BottomLine.getInstance();
	String prefix = "§8[§eLaw§8] ";
	PlayerData pd;

	public TempBan() {
		super("tempban", Permissions.TEMPBAN);
	}

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) {
		if (args.length < 2) {
			Util.invalidArgCount(sender, "TempBan", "Ban a player for a certain amount of time.", "/tempban [player] [timestamp (y, mon, w, d, h, m)] <reason>");
			return;
		}

		String msg = "";
		if (args.length >= 3) {
			for (int i = 2; i < args.length; i++) {
				msg += args[i] + " ";
			}
		} else {
			msg = "Misconduct";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("(z) dd/MM/yy hh:mm:ss a");
		Date date = new Date();

		String regex = "(?:(?<h>\\d+)h)?(?:(?<m>\\d+)m)?(?:(?<s>\\d+)s)?(?:(?<d>\\d+)d)?(?:(?<w>\\d+)w)?(?:(?<mon>\\d+)mon)?(?:(?<y>\\d+)y)?";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(args[1].toString());

		String timeValue = args[1].replaceFirst(".*?(\\d+).*", "$1");

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			if (pl.getConfig().getBoolean("law.ban.offlineBan") == true) {
				OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);
				pd = new PlayerData(offline.getName(), false);
				if (pd.dataExists(offline.getName())) {
					if (!m.matches()) {
						Util.invalidTimestamp(sender, "TempBan", args[1]);
						return;
					}

					long time;
					if (m.group(1) != null) {
						// Hour
						time = (Integer.parseInt(timeValue) * 3600000) + System.currentTimeMillis();
						pd.setOfflineTempBanned(offline, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
					} else if (m.group(2) != null) {
						// Minute
						time = (Integer.parseInt(timeValue) * 60000) + System.currentTimeMillis();
						pd.setOfflineTempBanned(offline, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
					} else if (m.group(3) != null) {
						// Second
						time = (Integer.parseInt(timeValue) * 1000) + System.currentTimeMillis();
						pd.setOfflineTempBanned(offline, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
					} else if (m.group(4) != null) {
						// Day
						time = (Integer.parseInt(timeValue) * 86400000) + System.currentTimeMillis();
						pd.setOfflineTempBanned(offline, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
					} else if (m.group(5) != null) {
						// Week
						time = (Integer.parseInt(timeValue) * 604800000) + System.currentTimeMillis();
						pd.setOfflineTempBanned(offline, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
					} else if (m.group(6) != null) {
						// Month
						time = (Integer.parseInt(timeValue) * 2620800000L) + System.currentTimeMillis();
						pd.setOfflineTempBanned(offline, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
					} else if (m.group(7) != null) {
						// Year
						time = (Integer.parseInt(timeValue) * 31536000000L) + System.currentTimeMillis();
						pd.setOfflineTempBanned(offline, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
					}

					pd.addHistory(Types.TEMPBAN, msg.trim(), sender.getName(), sdf.format(date), args[1]);
					Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §etemp banned §c" + pd.getDisplayName() + " §efor " + args[1] + " §ebecause " + msg.trim() + "!");
				}
			} else {
				Util.offline(sender, "TempBan", args[0]);
			}
			return;
		}

		if (target.isOp()) {
			Util.punishOps(sender, "TempBan");
			return;
		}

		if (target.getName().equalsIgnoreCase(sender.getName())) {
			sender.sendMessage("§cTempBan: §4You cannot ban yourself!");
			return;
		}

		pd = new PlayerData(target.getName());

		long time;
		if (m.matches()) {
			if (m.group(1) != null) {
				// Hour
				time = (Integer.parseInt(timeValue) * 3600000) + System.currentTimeMillis();
				pd.setTempBanned(target, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
			} else if (m.group(2) != null) {
				// Minute
				time = (Integer.parseInt(timeValue) * 60000) + System.currentTimeMillis();
				pd.setTempBanned(target, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
			} else if (m.group(3) != null) {
				// Second
				time = (Integer.parseInt(timeValue) * 1000) + System.currentTimeMillis();
				pd.setTempBanned(target, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
			} else if (m.group(4) != null) {
				// Day
				time = (Integer.parseInt(timeValue) * 86400000) + System.currentTimeMillis();
				pd.setTempBanned(target, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
			} else if (m.group(5) != null) {
				// Week
				time = (Integer.parseInt(timeValue) * 604800000) + System.currentTimeMillis();
				pd.setTempBanned(target, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
			} else if (m.group(6) != null) {
				// Month
				time = (Integer.parseInt(timeValue) * 2620800000L) + System.currentTimeMillis();
				pd.setTempBanned(target, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
			} else if (m.group(7) != null) {
				// Year
				time = (Integer.parseInt(timeValue) * 31536000000L) + System.currentTimeMillis();
				pd.setTempBanned(target, true, msg, new PlayerData(sender.getName()), System.currentTimeMillis(), time);
			}
		} else {
			Util.invalidTimestamp(sender, "TempBan", args[1]);
			return;
		}

		Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §etemp banned §c" + pd.getDisplayName() + " §efor " + args[1] + " §ebecause " + msg.trim() + "!");
		pd.addHistory(Types.TEMPBAN, msg.trim(), sender.getName(), sdf.format(date), args[1]);
	}
}