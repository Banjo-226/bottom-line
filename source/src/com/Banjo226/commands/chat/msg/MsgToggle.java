/**
 *  MsgToggle.java
 *  BottomLine
 *
 *  Created by Banjo226 on 3 Jan 2016 at 4:47:15 pm AEST
 *  Copyright © 2016 Banjo226. All rights reserved.
 */

package com.Banjo226.commands.chat.msg;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Data;

public class MsgToggle extends Cmd {
	Data d = Data.getInstance();

	public MsgToggle() {
		super("msgtoggle", Permissions.MSGTOGGLE);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (args.length == 0) {
			Player player = (Player) sender;

			if (!Store.cmdspy.contains(sender.getName())) {
				Store.msgtoggle.add(sender.getName());
				d.getConfig().set("msg.toggle", Store.msgtoggle);
				d.saveConfig();

				sender.sendMessage("§6Message Toggle: §eEnabled message toggle");

				Util.playSound(player);
				return;
			} else {
				Store.msgtoggle.remove(sender.getName());
				d.getConfig().set("msg.toggle", Store.msgtoggle);
				d.saveConfig();

				sender.sendMessage("§6Message Toggle: §eDisabled message toggle");

				Util.playSound(player);
				return;
			}
		}
	}
}