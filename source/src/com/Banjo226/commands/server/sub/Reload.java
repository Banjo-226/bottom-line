package com.Banjo226.commands.server.sub;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.*;
import com.Banjo226.commands.PermissionMessages;
import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.server.CoreCommand;

public class Reload extends CoreCommand {
	BottomLine pl = BottomLine.getInstance();
	Data d = Data.getInstance();
	Jails j = Jails.getInstance();
	Money e = Money.getInstance();
	Warps w = Warps.getInstance();
	Spawns s = Spawns.getInstance();

	public Reload() {
		super("reload", "Reload the configuration files on the server.", "", Arrays.asList("rl", "rel", "loadconf", "load"));
	}

	public void run(CommandSender sender, String[] args) {
		if (sender.hasPermission(Permissions.RELOADCONFIG)) {
			long time = System.currentTimeMillis();
			if (!pl.file.exists()) {
				sender.sendMessage("§cCore: §4Creating new configuration file (doesnt exist)");
				pl.saveDefaultConfig();
			}

			pl.reloadConfig();
			d.reloadConfig();
			j.reloadConfig();
			e.reloadConfig();
			s.reloadConfig();

			long now = System.currentTimeMillis() - time;
			sender.sendMessage("§6Core: §eSuccessfully reloaded the configuration files on the server. Took §6" + now + " milliseconds.");
			sender.sendMessage("§eFiles include; §6" + pl.file.getName() + "§e, §6" + d.getName() + "§e, §6" + j.getName() + "§e, §6" + e.getName() + "§e, §6" + s.getName());

			String name = "Console";
			if (sender instanceof Player) {
				name = new PlayerData(((Player) sender).getUniqueId()).getDisplayName();
			}
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.getName().equalsIgnoreCase(sender.getName())) if (player.hasPermission(Permissions.RELOADCONFIG)) player.sendMessage("§8§o[§6§oCore: §e§oConfiguration files successfully reloaded by " + Util.colour(name) + "§8§o]");
			}

			BottomLine.debug("Reloaded configuration file by " + sender.getName() + " in " + now + " ms");
		} else {
			sender.sendMessage(PermissionMessages.CMD.toString());
		}
	}
}