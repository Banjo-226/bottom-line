package com.Banjo226.commands.teleportation.warp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Warps;

import com.Banjo226.commands.PermissionMessages;
import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class WarpUtil extends Cmd {
	Warps w = Warps.getInstance();

	public WarpUtil() {
		super("warputil", Permissions.WARP_UTIL);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (args.length < 2) {
			Util.invalidArgCount(sender, "Warp", "The utilities for warping; creating, and removing.", "/warputil [create|remove] [name]");
			return;
		}

		args[1] = args[1].toLowerCase();

		if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("set")) {
			if (sender.hasPermission(Permissions.WARP_CREATE)) {
				if (!(sender instanceof Player)) throw new ConsoleSenderException("create warp");

				if (w.warpExists(args[1]) && !sender.hasPermission(Permissions.CREATEOVERWRITE)) {
					sender.sendMessage("§cWarp: §4The warp §o(" + args[1] + ") §4already exists!");
					return;
				}

				Player player = (Player) sender;
				w.setWarp(args[1].toLowerCase(), player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
				Util.playSound(player);

				sender.sendMessage("§6Warp: §eSuccessfully made the warp §o" + args[1] + "§e!");
			} else {
				sender.sendMessage(PermissionMessages.CMD.toString());
			}
		} else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
			if (sender.hasPermission(Permissions.WARP_REMOVE)) {
				if (!w.warpExists(args[1])) {
					sender.sendMessage("§cWarp: §4The warp §o(" + args[1] + ") §4doesn't exist!");
					return;
				}

				w.removeWarp(args[1]);
				sender.sendMessage("§6Warp: §eSuccessfully deleted the warp §o" + args[1] + "§e!");
			} else {
				sender.sendMessage(PermissionMessages.CMD.toString());
			}
		} else {
			sender.sendMessage("§cWarp: §4That command doesnt exist!");
		}
	}
}