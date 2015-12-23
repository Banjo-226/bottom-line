package com.Banjo226.commands.inventory;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public abstract class InvCommand {

	private String name, desc, args;
	private List<String> aliases;
	private Permission permission;

	public InvCommand(String name, String desc, String args, List<String> aliases, Permission permission) {
		this.name = name;
		this.desc = desc;
		this.args = args;
		this.aliases = aliases;
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return desc;
	}

	public String getArgs() {
		return args;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public Permission getPermission() {
		return permission;
	}

	public abstract void run(CommandSender sender, String[] args);

}