package com.Banjo226.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;

import com.Banjo226.commands.Permissions;

public class Broadcast extends Cmd {
	BottomLine pl = BottomLine.getInstance();

	public Broadcast() {
		super("broadcast", Permissions.BROADCAST);
	}

	public void run(CommandSender sender, String[] args) {
		if (pl.getConfig().getBoolean("broadcast.timeout") == true) {
			if (!sender.isOp()) {
				int cooldownTime = pl.getConfig().getInt("broadcast.timeoutInSeconds");

				if (Store.cooldown.containsKey(sender.getName())) {
					long secondsLeft = ((Store.cooldown.get(sender.getName()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
					if (secondsLeft >= 1) {
						sender.sendMessage("§cOops! §4You can't broadcast for another " + secondsLeft + " seconds!");
						return;
					}
				}
			}
		}

		if (pl.getConfig().getBoolean("broadcast.timeout") == true) {
			if (!sender.isOp()) {
				Store.cooldown.put(sender.getName(), System.currentTimeMillis());
			}
		}

		if (args.length == 0) {
			Util.invalidArgCount(sender, "Broadcast", "Broadcast a message with supported colour codes to the server.", "/broadcast [message...]");
			return;
		}

		String msg = "";
		for (int i = 0; i < args.length; i++) {
			msg = msg + args[i] + " ";
		}
		msg.trim();

		Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("broadcast.format").replaceAll("%message%", msg)));

		for (Player p : Bukkit.getOnlinePlayers()) {
			Util.sendActionBar(p, Util.colour(pl.getConfig().getString("broadcast.format").replaceAll("%message%", msg)));
		}
	}
}