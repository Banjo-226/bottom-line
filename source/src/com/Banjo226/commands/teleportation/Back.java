/**
 *  Back.java
 *  BottomLine
 *
 *  Created by Banjo226 on 12 Jan 2016 at 3:35:19 pm AEST
 *  Copyright © 2016 Banjo226. All rights reserved.
 */

package com.Banjo226.commands.teleportation;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.files.PlayerData;

public class Back extends Cmd {
	
	public Back() {
		super("back", Permissions.BACK);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());
		
		Player player = (Player) sender;
		PlayerData pd = new PlayerData(player.getUniqueId());
		
		if (pd.getConfig().getConfigurationSection("location.back") == null) {
			sender.sendMessage("§cBack: §4You don't have any previous locations!");
			return;
		}
		
		player.teleport(pd.getBackLocation());
		sender.sendMessage("§6Back: §eTeleported to your last location.");
	}
}