package com.Banjo226.commands.teleportation.warp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Warps;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class Warp extends Cmd implements TabCompleter {
	Warps w = Warps.getInstance();

	public Warp() {
		super("warp", Permissions.WARP);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException();

		if (args.length == 0) {
			Util.invalidArgCount(sender, "Warp", "Teleport to a set place.", "/warp [name|list]");
			return;
		}

		args[0] = args[0].toLowerCase();

		if (args[0].equalsIgnoreCase("list")) {
			if (sender.hasPermission(Permissions.WARP_LIST)) {
				sender.sendMessage(getWarps());
			} else {
				sender.sendMessage("§cSorry, but you cannot view the warps on the server.");
			}
			return;
		}

		if (!w.warpExists(args[0])) {
			sender.sendMessage("§cWarp: §4Sorry, but the warp §o(" + args[0] + ") §4does not exist!");
			return;
		}

		Player player = (Player) sender;
		player.teleport(w.getWarp(args[0]));
		Util.playSound(player);

		sender.sendMessage("§6Warp: §eSuccessfully warped to §o" + args[0] + "§e.");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warp")) {
			if (sender.hasPermission(Permissions.WARP_LIST)) {
				if (args.length == 1) {
					ArrayList<String> warps = new ArrayList<>();

					if (!args[0].equalsIgnoreCase("")) {
						for (String warp : w.getConfig().getStringList("warplist")) {
							if (warp.toLowerCase().startsWith(args[0].toLowerCase())) {
								warps.add(warp);
							}
						}
					} else {
						for (String warp : w.getConfig().getStringList("warplist")) {
							warps.add(warp);
						}
					}

					Collections.sort(warps);
					return warps;
				}
			}
		}
		return null;
	}

	public String getWarps() {
		StringBuilder warps = new StringBuilder();
		String header = "";

		if (w.warps.size() > 0) {
			header = "§6Warp: §eAll existing warps §6(§e" + w.getConfig().getStringList("warplist").size() + "§6)§e:\n";

			for (int i = 0; i < w.warps.size(); i++) {
				if (warps.length() > 0) {
					warps.append("§6, ");
				}

				warps.append("§e" + w.getConfig().getStringList("warplist").get(i));
			}
		} else {
			warps.append("§cWarp: §4No existing warps on the server.");
		}

		return header + warps.toString();
	}
}