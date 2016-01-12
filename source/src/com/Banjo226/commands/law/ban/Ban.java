package com.Banjo226.commands.law.ban;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.law.history.Types;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class Ban extends Cmd {
	BottomLine pl = BottomLine.getInstance();
	PlayerData con;
	String prefix = "§8[§eLaw§8] ";

	public Ban() {
		super("ban", Permissions.BAN);
	}

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Util.invalidArgCount(sender, "Ban", "Banish a player from the server.", "/ban [player]", "/ban [player] <reason>");
			return;
		}

		String msg = "";
		if (args.length >= 2) {
			for (int i = 1; i < args.length; i++) {
				msg += args[i] + " ";
			}
		} else {
			msg = "Misconduct";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("(z) dd/MM/yy hh:mm:ss a");
		Date date = new Date();

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			if (pl.getConfig().getBoolean("law.ban.offlineBan") == true) {
				OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);
				con = new PlayerData(offline.getUniqueId(), false);
				if (con.dataExists(con.file)) {
					if (offline.isOp()) {
						Util.punishOps(sender, "Ban");
						return;
					}

					con.setOfflineBanned(offline, true, msg.trim(), new PlayerData(((Player) sender).getUniqueId()));
					con.addHistory(Types.BAN, msg.trim(), sender.getName(), sdf.format(date), null);

					Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §ebanned §c" + con.getDisplayName() + " §efor " + msg.trim() + "!");
				}
			} else {
				Util.offline(sender, "Ban", args[0]);
			}
			return;
		}

		if (target.isOp()) {
			Util.punishOps(sender, "Ban");
			return;
		}

		if (target.getName().equalsIgnoreCase(sender.getName())) {
			sender.sendMessage("§cBan: §4You cannot ban yourself!");
			return;
		}

		con = new PlayerData(target.getUniqueId());
		con.setBanned(target, true, msg.trim(), new PlayerData(((Player) sender).getUniqueId()));
		con.addHistory(Types.BAN, msg.trim(), sender.getName(), sdf.format(date), null);

		Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §ebanned §c" + con.getDisplayName() + " §efor " + msg.trim() + "!");
	}
}