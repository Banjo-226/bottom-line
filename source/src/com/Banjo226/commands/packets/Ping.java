package com.Banjo226.commands.packets;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class Ping extends Cmd {
	BottomLine pl = BottomLine.getInstance();
	String server = pl.servervs;

	public Ping() {
		super("ping", Permissions.PING);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		try {
			Player player = (Player) sender;

			if (args.length == 0) {
				if (ping(player) >= 1000) {
					sender.sendMessage("§cPing: §4Your ping: " + ping(player) + "ms");
				} else {
					sender.sendMessage("§6Ping: §eYour ping: " + ping(player) + "ms");
				}
				return;
			}

			if (args.length == 1 && sender.hasPermission(Permissions.PING_OTHERS)) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					Util.offline(sender, "Ping", args[0]);
					return;
				}

				if (ping(player) >= 1000) {
					sender.sendMessage("§cPing: §4" + target.getDisplayName() + "§4's ping: " + ping(player) + "ms");
				} else {
					sender.sendMessage("§6Ping: §e" + target.getDisplayName() + "§e's ping: " + ping(player) + "ms");
				}
			}
		} catch (Exception e) {
			sender.sendMessage("§cAn exception occured.");
			e.printStackTrace();
		}
	}

	public int ping(Player player) throws Exception {
		Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + server + ".entity.CraftPlayer");
		Object converted = craftPlayer.cast(player);
		Method handle = converted.getClass().getMethod("getHandle", new Class[0]);
		Object entityPlayer = handle.invoke(converted, new Object[0]);
		Field pingField = entityPlayer.getClass().getField("ping");
		return pingField.getInt(entityPlayer);
	}
}