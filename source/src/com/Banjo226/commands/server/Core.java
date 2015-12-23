package com.Banjo226.commands.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.Banjo226.manager.Cmd;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.server.sub.Reload;
import com.Banjo226.commands.server.sub.Status;

public class Core extends Cmd implements TabCompleter {

	private ArrayList<CoreCommand> cmds = new ArrayList<>();

	public Core() {
		super("core", Permissions.CORE);

		cmds.add(new Reload());
		cmds.add(new Status());
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§6Core: §eValid commands:");
			for (CoreCommand c : cmds) {
				sender.sendMessage("§e/core " + c.getName() + " " + c.getArgs() + " - " + c.getDescription());

				if (c.getAliases().size() > 0) {
					StringBuilder str = new StringBuilder();
					for (int i = 0; i < c.getAliases().size(); i++) {
						if (str.length() > 0) {
							str.append("§6, ");
						}

						str.append("§e" + c.getAliases().get(i));
					}

					sender.sendMessage("§6" + c.getName().toUpperCase() + " ALIASES » §e" + str.toString());
				}
			}
			return;
		}

		ArrayList<String> a = new ArrayList<String>(Arrays.asList(args));
		a.remove(0);

		for (CoreCommand c : cmds) {
			for (int i = 0; i < c.getAliases().size(); i++) {
				if (c.getName().equalsIgnoreCase(args[0]) || c.getAliases().get(i).equalsIgnoreCase(args[0])) {
					try {
						c.run(sender, a.toArray(new String[a.size()]));
					} catch (Exception e) {
						sender.sendMessage("§cCore: §4An error has occurred while trying to run the command (check console for stacktrace).");
						e.printStackTrace();
					}
					return;
				}
			}
		}

		sender.sendMessage("§cCore: §4That argument does not exist!");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("core")) {
			if (args.length == 1) {
				ArrayList<String> commands = new ArrayList<>();

				if (!args[0].equalsIgnoreCase("")) {
					for (CoreCommand c : cmds) {
						if (c.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
							commands.add(c.getName());
						}
					}
				} else {
					for (CoreCommand c : cmds) {
						commands.add(c.getName());
					}
				}

				Collections.sort(commands);
				return commands;
			}
		}
		return null;
	}
}