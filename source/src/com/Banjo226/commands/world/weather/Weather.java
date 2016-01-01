/**
 *  Weather.java
 *  BottomLine
 *
 *  Created by Banjo226 on 1 Jan 2016 at 1:24:27 pm AEST
 *  Copyright © 2016 Banjo226. All rights reserved.
 */

package com.Banjo226.commands.world.weather;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class Weather extends Cmd {
	BottomLine pl = BottomLine.getInstance();
	
	public Weather() {
		super("weather", Permissions.WEATHER);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		World w;
		
		if (!(sender instanceof Player))
			w = Bukkit.getWorld("world");
		else
			w = ((Player) sender).getWorld();
		
		if (args.length == 0) {
			String condition = "";
			if (w.isThundering())
				condition = "thundering";
			if (w.hasStorm())
				condition = "storming";
			if (!w.hasStorm() && !w.isThundering())
				condition = "clear";
			
			sender.sendMessage("§6Weather: §eCurrently " + condition + " in " + w.getName());
			return;
		}
		
		String con = "";
		
		if (args[0].equalsIgnoreCase("sun") || args[0].equalsIgnoreCase("sunny") || args[0].equalsIgnoreCase("clear")) {
			con = "sunny";
			
			w.setStorm(false);
			w.setThundering(false);
			w.setThunderDuration(0);
		} else if (args[0].equalsIgnoreCase("storm") || args[0].equalsIgnoreCase("rain")) {
			con = "storming";
			
			w.setStorm(true);
			w.setThundering(false);
		} else if (args[0].equalsIgnoreCase("thunder") || args[0].equalsIgnoreCase("thundering")) {
			con = "thundering";
			
			w.setStorm(true);
			w.setThundering(true);
		} else {
			sender.sendMessage("§6Weather: §eThat weather condition does not exist!");
			return;
		}
		
		sender.sendMessage("§6Weather: §eSet weather condition to: " + con);
		
		if (pl.getConfig().getBoolean("broadcast-world.weather.enabled") == true) {
			String name = sender.getName();
			if (sender instanceof Player) name = new PlayerData(sender.getName()).getDisplayName();

			Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("broadcast-world.weather.message").replaceAll("%player%", name).replaceAll("%weather%", con)));
		}
	}
}