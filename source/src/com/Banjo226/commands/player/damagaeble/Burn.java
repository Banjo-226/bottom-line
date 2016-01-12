/**
 *  Burn.java
 *  BottomLine
 *
 *  Created by Banjo226 on 18 Dec 2015 at 1:59:02 pm AEST
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

public class Burn extends Cmd {

	public Burn() {
		super("burn", Permissions.BURN);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (args.length == 0) {
			if (!(sender instanceof Player))
				throw new ConsoleSenderException("Select a player.", getName());
			
			Player player = (Player) sender;
			player.setFireTicks(500); // 500 / 20 = 25: 25 seconds the fire will last
			sender.sendMessage("§6Burn: §eYou set yourself on fire! Go quick, find some water!");
			return;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		if (!target.isOnline()) {
			Util.offline(sender, "Burn", args[0]);
			return;
		}
		
		PlayerData pd = new PlayerData(target.getUniqueId());
		
		target.setFireTicks(500);
		target.sendMessage("§6Burn: §eYou've been set on fire! Go quick, find some water!");
		sender.sendMessage("§6Burn: §eSuccessfully set " + pd.getDisplayName() + " §eon fire.");
	}
}