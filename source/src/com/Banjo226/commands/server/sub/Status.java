package com.Banjo226.commands.server.sub;

import java.text.DecimalFormat;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import com.Banjo226.util.TicksPerSecond;

import com.Banjo226.commands.PermissionMessages;
import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.server.CoreCommand;

public class Status extends CoreCommand {
	DecimalFormat form = new DecimalFormat("#.00");

	public Status() {
		super("status", "The servers status; The RAM usage and more runtime information.", "", Arrays.asList("ram", "usage", "core"));
	}

	public void run(CommandSender sender, String[] args) {
		if (sender.hasPermission(Permissions.STATUS)) {
			sender.sendMessage("§6Core: §eServer status: [RAM]");
			sender.sendMessage("§8[§eMAXIMUM AMOUNT§8] §6" + Runtime.getRuntime().maxMemory() / 1048576L + " §6MB (megabyte)");
			sender.sendMessage("§8[§eTOTAL AMOUNT§8] §6" + Runtime.getRuntime().totalMemory() / 1048576L + " §6MB");
			sender.sendMessage("§8[§eUSED AMOUNT§8] §6" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L + " §6MB");
			sender.sendMessage("§8[§eFREE AMOUNT§8] §6" + Runtime.getRuntime().freeMemory() / 1048576L + " §6MB");

			sender.sendMessage("\n§6Core: §eServer TPS: [Ticks per second]");

			double tps = TicksPerSecond.getTPS();
			String tpsform = ((tps >= 20) ? "§a" + form.format(tps) + "§a*" : form.format(tps));
			sender.sendMessage("§8[§eTPS§8] §6" + tpsform + "§6 seconds");

			sender.sendMessage("\n§6Core: §eServer Worlds: [with entity amount]");
			for (World w : Bukkit.getWorlds()) {
				sender.sendMessage("§8[§e" + w.getName().toUpperCase() + "§8] §6" + w.getEntities().size() + " §6existing entities");
			}
		} else {
			sender.sendMessage(PermissionMessages.CMD.toString());
		}
	}
}