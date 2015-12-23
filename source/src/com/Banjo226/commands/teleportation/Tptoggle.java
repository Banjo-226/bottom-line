package com.Banjo226.commands.teleportation;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Data;

import com.Banjo226.commands.Permissions;

public class Tptoggle extends Cmd {
	Data d = Data.getInstance();

	public Tptoggle() {
		super("tptoggle", Permissions.TELEPORT_TOGGLE);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please sepcify a player.");
				return;
			}

			if (!Store.tptoggle.contains(sender.getName())) {
				sender.sendMessage("§6Tptoggle: §eEnabled teleport toggle!");

				Store.tptoggle.add(sender.getName());
				d.getConfig().set("tp.toggle", Store.tptoggle);
				Util.playSound((Player) sender);
			} else {
				sender.sendMessage("§6Tptoggle: §eDisabled teleport toggle!");

				Store.tptoggle.remove(sender.getName());
				d.getConfig().set("tp.toggle", Store.tptoggle);
				Util.playSound((Player) sender);
			}
		}

		if (sender.hasPermission(Permissions.TELEPORT_TOGGLE_OTHERS) && args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "Tptoggle", args[0]);
				return;
			}

			if (!Store.god.contains(target.getName())) {
				sender.sendMessage("§6Tptoggle: §eEnabled teleport toggle for " + target.getDisplayName());

				Store.tptoggle.add(target.getName());
				d.getConfig().set("tp.toggle", Store.tptoggle);

				target.sendMessage("§6Tptoggle: §eEnabled teleport toggle by §l" + sender.getName());
				Util.playSound(target);
			} else {
				sender.sendMessage("§6Tptoggle: §eDisabled teleport toggle mode for " + target.getDisplayName());

				target.sendMessage("§6Tptoggle: §eDisabled teleport toggle by §l" + sender.getName());
				Store.tptoggle.remove(target.getName());
				d.getConfig().set("tp.toggle", Store.tptoggle);
				Util.playSound(target);
			}
		}
	}
}