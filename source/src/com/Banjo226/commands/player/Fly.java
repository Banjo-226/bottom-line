package com.Banjo226.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;

import com.Banjo226.commands.Permissions;

public class Fly extends Cmd {

	public Fly() {
		super("fly", Permissions.FLY);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Specify a player.");
				return;
			}

			Player player = (Player) sender;

			if (player.getAllowFlight() == false) {
				sender.sendMessage("§6Fly: §eEnabled flying");

				player.setAllowFlight(true);
				Util.playSound(player);
			} else {
				sender.sendMessage("§6Fly: §eDisabled flying");

				player.setAllowFlight(false);
			}
		}

		if (sender.hasPermission(Permissions.FLY_OTHERS) && args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "Fly", args[0]);
				return;
			}

			if (target.getAllowFlight() == false) {
				sender.sendMessage("§6Fly: §eEnabled flying for " + target.getDisplayName());

				target.setAllowFlight(true);
				target.sendMessage("§6Fly: §eEnabled flying by §l" + sender.getName());
				Util.playSound(target);
			} else {
				sender.sendMessage("§6Fly: §eDisabled flying for " + target.getDisplayName());

				target.sendMessage("§6Fly: §eDisabled flying by §l" + sender.getName());
				target.setAllowFlight(false);
			}
		}
	}
}