package com.Banjo226.commands.chat.list;

import org.bukkit.command.CommandSender;

import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.TextFiles;

public class Rules extends Cmd {
	TextFiles txt = TextFiles.getInstance();

	public Rules() {
		super("rules", Permissions.RULES);
	}

	public void run(CommandSender sender, String[] args) {
		sender.sendMessage("§7§m---------§6 RULES §7§m---------");
		for (String rules : txt.getRules()) {
			sender.sendMessage(Util.colour(rules));
		}
	}
}