/**
 *  Hat.java
 *  BottomLine
 *
 *  Created by Banjo226 on 18 Dec 2015 at 10:45:54 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.commands.player;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;

public class Hat extends Cmd {

	public Hat() {
		super("hat", Permissions.HAT);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player))
			throw new ConsoleSenderException(getName());
		
		Player player = (Player) sender;
		ItemStack item = player.getItemInHand();
		
		if (item.getType().equals(Material.AIR)) {
			sender.sendMessage("§cHat: §4You cannot set your hat as air!");
			return;
		}
		
		for (String ite : Store.disabledHats) {
			ItemStack i = new ItemStack(Material.matchMaterial(ite));
			if (i.equals(item)) {
				sender.sendMessage("§cHat: §4That hat is not allowed on this server!");
				return;
			}
		}
		
		player.getInventory().setHelmet(item);
		player.getInventory().setItemInHand(new ItemStack(Material.AIR));
		
		sender.sendMessage("§6Hat: §eEnjoy your new hat!");
	}
}