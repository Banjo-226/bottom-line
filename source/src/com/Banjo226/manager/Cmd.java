/**
 *  Cmd.java
 *  BottomLine
 *
 *  Created by Banjo226 on 26 Nov 2015 at 10:10 am AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.manager;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;

public abstract class Cmd implements Listener, TabCompleter {

	String name;
	Permission perm;

	public Cmd(String name, Permission perm) {
		this.name = name;
		this.perm = perm;
	}

	public String getName() {
		return name;
	}

	public Permission getPermission() {
		return perm;
	}

	public abstract void run(CommandSender sender, String[] args) throws Exception;

	public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
		return null;
	}

	public boolean isAuthorised(CommandSender sender) {
		if (sender.hasPermission(perm)) return true;

		return false;
	}

	public boolean isAuthorised(CommandSender sender, Permission perm) {
		if (sender.hasPermission(perm)) return true;

		return false;
	}

	public boolean isAuthorised(CommandSender sender, String perm) {
		if (sender.hasPermission(perm)) return true;

		return false;
	}
}