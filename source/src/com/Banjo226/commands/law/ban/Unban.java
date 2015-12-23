package com.Banjo226.commands.law.ban;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;

public class Unban extends Cmd {
	BottomLine pl = BottomLine.getInstance();
	PlayerData con;
	String prefix = "§8[§eLaw§8] ";

	public Unban() {
		super("unban", Permissions.UNBAN);
	}

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Util.invalidArgCount(sender, "Unban", "Allow a player to play the server again.", "/unban [player]");
			return;
		}

		OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);
		con = new PlayerData(offline.getName(), false);
		if (con.dataExists(offline.getName())) {
			if (con.isBanned() || offline.isBanned()) {
				con.setOfflineBanned(offline, false, null, null);

				Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §eunbanned §c" + con.getDisplayName() + "§e!");
			} else {
				sender.sendMessage("§cUnban: §4The player §o(" + offline.getName() + ") §4is not banned!");
			}
		} else {
			sender.sendMessage("§cUnban: §4The player §o(" + args[0] + ") §4has not played before.");
		}
	}
}