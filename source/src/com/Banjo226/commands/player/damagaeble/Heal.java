package com.Banjo226.commands.player.damagaeble;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;

public class Heal extends Cmd {

	public Heal() {
		super("heal", Permissions.HEAL);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please specify a player");
				return;
			}

			Player player = (Player) sender;
			Damageable d = (Damageable) player;
			player.setFoodLevel(20);
			d.setHealth(20);
			d.setFireTicks(0);

			sender.sendMessage("§6Heal: §eRestored health to full.");
		}

		if (args.length == 1 && sender.hasPermission(Permissions.HEAL_OTHERS)) {
			String name;
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "Heal", args[0]);
				return;
			}

			if (!(sender instanceof Player)) {
				name = "Console";
			} else {
				PlayerData pl = new PlayerData(((Player) sender).getUniqueId());

				if (pl.getDisplayName() == null) {
					name = sender.getName();
				} else {
					name = pl.getDisplayName();
				}
			}

			target.setFoodLevel(20);
			target.setHealth(20);
			target.setFireTicks(0);

			sender.sendMessage("§6Heal: §eSet " + target.getDisplayName() + "§e's health to full.");
			target.sendMessage("§6Heal: §eHealth restored to full by §l" + name);
		}
	}
}