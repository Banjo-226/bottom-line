package com.Banjo226.commands.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.ItemStack;

import com.Banjo226.manager.Cmd;

import com.Banjo226.commands.PermissionMessages;
import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.inventory.sub.Clear;
import com.Banjo226.commands.inventory.sub.Open;
import com.Banjo226.commands.inventory.sub.SearchItem;

public class Inventory extends Cmd implements TabCompleter {

	private ArrayList<InvCommand> cmds = new ArrayList<>();

	public Inventory() {
		super("inv", Permissions.INVENTORY);

		cmds.add(new Open());
		cmds.add(new SearchItem());
		cmds.add(new Clear());
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§6Inventory: §eValid commands:");
			for (InvCommand c : cmds) {
				sender.sendMessage("§e/inv " + c.getName() + " " + c.getArgs() + " - " + c.getDescription());

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

		for (InvCommand c : cmds) {
			for (int i = 0; i < c.getAliases().size(); i++) {
				if (c.getName().equalsIgnoreCase(args[0]) || c.getAliases().get(i).equalsIgnoreCase(args[0])) {
					try {
						if (sender.hasPermission(c.getPermission())) {
							c.run(sender, a.toArray(new String[a.size()]));
						} else {
							sender.sendMessage(PermissionMessages.CMD.toString());
						}
					} catch (Exception e) {
						sender.sendMessage("§cInventory: §4An error has occurred while trying to run the command (check console for stacktrace).");
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
		if (cmd.getName().equalsIgnoreCase("inv")) {
			if (args.length == 1) {
				ArrayList<String> commands = new ArrayList<>();

				if (!args[0].equalsIgnoreCase("")) {
					for (InvCommand c : cmds) {
						if (c.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
							commands.add(c.getName());
						}
					}
				} else {
					for (InvCommand c : cmds) {
						commands.add(c.getName());
					}
				}

				Collections.sort(commands);
				return commands;
			}

			if (args.length == 2) {
				for (InvCommand c : cmds) {
					for (int i = 0; i < c.getAliases().size(); i++) {
						if (c.getName().equalsIgnoreCase("search")) {
							if (args[0].equalsIgnoreCase(c.getAliases().get(i)) || args[0].equalsIgnoreCase(c.getName())) {
								ArrayList<String> ids = new ArrayList<>();

								if (!args[1].equalsIgnoreCase("")) {
									for (Material m : Material.values()) {
										ItemStack ite = new ItemStack(m);
										if (args[1].toLowerCase().startsWith(ite.getType().toString())) {
											ids.add(ite.getType().toString().toLowerCase());
										}
									}
								} else {
									for (Material m : Material.values()) {
										ItemStack ite = new ItemStack(m);
										ids.add(ite.getType().toString().toLowerCase());
									}
								}

								Collections.sort(ids);
								return ids;
							}
						}
					}
				}
			}
		}
		return null;
	}
}