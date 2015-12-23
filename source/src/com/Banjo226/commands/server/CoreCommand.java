package com.Banjo226.commands.server;

import java.util.List;

import org.bukkit.command.CommandSender;

public abstract class CoreCommand {

	private String name, desc, args;
	private List<String> aliases;

	public CoreCommand(String name, String desc, String args, List<String> aliases) {
		this.name = name;
		this.desc = desc;
		this.args = args;
		this.aliases = aliases;
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

	public abstract void run(CommandSender sender, String[] args);

}