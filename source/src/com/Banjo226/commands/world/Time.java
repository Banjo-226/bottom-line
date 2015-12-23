/**
 *  Time.java
 *  BottomLine
 *
 *  Created by Banjo226 on 18 Dec 2015 at 3:29:52 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.commands.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class Time extends Cmd {
	BottomLine pl = BottomLine.getInstance();

	public Time() {
		super("time", Permissions.TIME);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		World w;
		if (!(sender instanceof Player))
			w = Bukkit.getWorld("world");
		else
			w = ((Player) sender).getWorld();

		if (args.length == 0) {
			sender.sendMessage("§6Time: §eCurrently " + Util.getWorldTime(w) + " §ein '" + w.getName() + "§e'");
			return;
		}

		if (args[0].equalsIgnoreCase("dawn") || args[0].equalsIgnoreCase("morning")) {
			w.setTime(0);
			
			args[0] += " or " + Util.getWorldTime(w);
		} else if (args[0].equalsIgnoreCase("night")) {
			w.setTime(18000);
			
			args[0] += " or " + Util.getWorldTime(w);
		} else if (args[0].equalsIgnoreCase("dusk") || args[0].equalsIgnoreCase("evening")) {
			w.setTime(12000);
			
			args[0] += " or " + Util.getWorldTime(w);
		} else if (args[0].equalsIgnoreCase("day") || args[0].equalsIgnoreCase("midday")) {
			w.setTime(6000);
			
			args[0] += " or " + Util.getWorldTime(w);
		} else {
			int ticks = 0;
			try {
				ticks = Integer.parseInt(args[0]);

				w.setTime(ticks);
			} catch (NumberFormatException e) {
				sender.sendMessage("§cTime: §4" + args[0] + " is not a valid time argument!");
				return;
			}

			args[0] = ticks + " ticks or " + Util.getWorldTime(w);
		}

		sender.sendMessage("§6Time: §eSet the time to " + args[0] + "!");

		if (pl.getConfig().getBoolean("broadcast-world.time.enabled") == true) {
			String name = sender.getName();
			if (sender instanceof Player) name = new PlayerData(sender.getName()).getDisplayName();

			Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("broadcast-world.time.message").replaceAll("%player%", name).replaceAll("%time%", args[0])));
		}
	}
}