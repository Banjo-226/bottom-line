package com.Banjo226.commands.chat.msg;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;

public class Message extends Cmd {
	BottomLine pl = BottomLine.getInstance();

	public Message() {
		super("msg", Permissions.MSG);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length < 2) {
			Util.invalidArgCount(sender, "Message", "Send a player a private message.", "/msg [player] [message...]");
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			Util.offline(sender, "Message", args[0]);
			return;
		}
		
		if (Store.msgtoggle.contains(target.getName())) {
			sender.sendMessage("§cMessage: §4" + new PlayerData(target.getUniqueId()).getDisplayName() + " §4has messages disabled!");
			return;
		}

		String msg = "";
		for (int i = 1; i < args.length; i++) {
			msg = msg + args[i] + " ";
		}

		String name;

		if (sender instanceof Player) {
			Util.playSound((Player) sender);
			name = new PlayerData(((Player) sender).getUniqueId(), false).getDisplayName();
		} else if (!(sender instanceof Player)) {
			name = sender.getName();
		} else {
			name = "unknown";
		}

		Util.playSound(target);

		String send;
		String player;

		if (sender.hasPermission(Permissions.MSG_COLOUR)) {
			send = Util.colour(pl.getConfig().getString("msg.format-sender").replaceAll("%target%", target.getDisplayName())).replaceAll("%message%", Util.parseColours(msg));
			player = Util.colour(pl.getConfig().getString("msg.format-target").replaceAll("%sender%", name)).replaceAll("%message%", Util.parseColours(msg));
		} else if (sender.hasPermission(Permissions.MSG_FORMAT)) {
			send = Util.colour(pl.getConfig().getString("msg.format-sender").replaceAll("%target%", target.getDisplayName())).replaceAll("%message%", Util.parseFormat(msg));
			player = Util.colour(pl.getConfig().getString("msg.format-target").replaceAll("%sender%", name)).replaceAll("%message%", Util.parseFormat(msg));
		} else if (sender.hasPermission(Permissions.MSG_MAGIC)) {
			send = Util.colour(pl.getConfig().getString("msg.format-sender").replaceAll("%target%", target.getDisplayName())).replaceAll("%message%", Util.parseMagic(msg));
			player = Util.colour(pl.getConfig().getString("msg.format-target").replaceAll("%sender%", name)).replaceAll("%message%", Util.parseMagic(msg));
		} else if (sender.hasPermission("bottomline.message.*")) {
			send = Util.colour(pl.getConfig().getString("msg.format-sender").replaceAll("%target%", target.getDisplayName())).replaceAll("%message%", Util.colour(msg));
			player = Util.colour(pl.getConfig().getString("msg.format-target").replaceAll("%sender%", name)).replaceAll("%message%", Util.colour(msg));
		} else {
			send = Util.colour(pl.getConfig().getString("msg.format-sender").replaceAll("%target%", target.getDisplayName())).replaceAll("%message%", msg);
			player = Util.colour(pl.getConfig().getString("msg.format-target").replaceAll("%sender%", name)).replaceAll("%message%", msg);
		}

		if (Store.away.contains(target.getName())) {
			sender.sendMessage("§c" + target.getDisplayName() + " §cis currently AFK and may not respond.");
		}

		sender.sendMessage(send);
		target.sendMessage(player);

		if (Store.reply.containsKey(sender.getName())) {
			Store.reply.remove(sender.getName());
		}

		Store.reply.put(sender.getName(), target.getName());
		Store.reply.put(target.getName(), sender.getName());
	}
}