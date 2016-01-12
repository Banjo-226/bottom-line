/**
 *  Extinguish.java
 *  BottomLine
 *
 *  Created by Banjo226 on 18 Dec 2015 at 2:48:59 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.commands.player.damagaeble;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class Extinguish extends Cmd {
	
	public Extinguish() {
		super("extinguish", Permissions.EXTINGUISH);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (args.length == 0) {
			if (!(sender instanceof Player))
				throw new ConsoleSenderException("Select a player.", getName());
			
			Player player = (Player) sender;
			player.setFireTicks(0);
			sender.sendMessage("§6Extinguish: §eYou extinguished youself.");
			return;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		if (!target.isOnline()) {
			Util.offline(sender, "Extinguish", args[0]);
			return;
		}
		
		PlayerData pd = new PlayerData(target.getUniqueId());
		
		if (target.getFireTicks() == 0) {
			sender.sendMessage("§cExtinguish: §4" + pd.getDisplayName() + "§4 is not on fire!");
			return;
		}
	
		target.setFireTicks(0);
		target.sendMessage("§6Extinguish: §eYou've been set on extinguished!");
		sender.sendMessage("§6Extinguish: §eSuccessfully extinguished " + pd.getDisplayName() + "§e.");
	}
}