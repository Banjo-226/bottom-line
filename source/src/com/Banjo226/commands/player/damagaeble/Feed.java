package com.Banjo226.commands.player.damagaeble;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;

public class Feed extends Cmd {

	public Feed() {
		super("feed", Permissions.FEED);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please specify a player");
				return;
			}

			Player player = (Player) sender;
			player.setFoodLevel(20);

			sender.sendMessage("§6Feed: §eRestored food level to full.");
		}

		if (args.length == 1 && sender.hasPermission(Permissions.FEED_OTHERS)) {
			String name;
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "Feed", args[0]);
				return;
			}

			if (!(sender instanceof Player)) {
				name = "Console";
			} else {
				PlayerData pl = new PlayerData(sender.getName());

				if (pl.getDisplayName() == null) {
					name = sender.getName();
				} else {
					name = pl.getDisplayName();
				}
			}

			target.setFoodLevel(20);

			sender.sendMessage("§6Feed: §eSet " + target.getDisplayName() + "§e's food level to full.");
			target.sendMessage("§6Feed: §eFood level restored to full by §l" + name);
		}
	}
}