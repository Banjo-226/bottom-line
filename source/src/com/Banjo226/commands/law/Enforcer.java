package com.Banjo226.commands.law;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.law.history.Types;

public class Enforcer extends Cmd {
	BottomLine pl = BottomLine.getInstance();
	PlayerData con;
	String msg = "";
	String prefix = "§8[§eLaw§8] ";

	public Enforcer() {
		super("kick", Permissions.KICK);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Util.invalidArgCount(sender, "Kick", "Remove a player from the server.", "/kick [player]", "/kick [player] <reason>");
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			Util.offline(sender, "Kick", args[0]);
			return;
		}

		if (target.isOp()) {
			Util.punishOps(sender, "Kick");
			return;
		}

		con = new PlayerData(target.getUniqueId());
		SimpleDateFormat sdf = new SimpleDateFormat("(z) dd/MM/yy hh:mm:ss a");
		Date date = new Date();

		if (args.length == 1) {
			con.addHistory(Types.KICK, "Misconduct", sender.getName(), sdf.format(date), "");

			target.kickPlayer(Util.colour(pl.getConfig().getString("law.kick.message").replaceAll("%sender%", sender.getName()).replaceAll("%message%", pl.getConfig().getString("law.kick.noMessageProvided"))));
			Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §ekicked §c" + con.getDisplayName() + " §ebecause " + pl.getConfig().getString("law.kick.noMessageProvided") + "!");
		} else {
			for (int i = 1; i < args.length; i++) {
				msg = msg + args[i] + " ";
			}

			con.addHistory(Types.KICK, msg.trim(), sender.getName(), sdf.format(date), "");

			target.kickPlayer(Util.colour(pl.getConfig().getString("law.kick.message").replaceAll("%sender%", sender.getName()).replaceAll("%message%", msg.trim())));
			Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §ekicked §c" + con.getDisplayName() + " §ebecause " + msg.trim() + "!");
		}
	}
}