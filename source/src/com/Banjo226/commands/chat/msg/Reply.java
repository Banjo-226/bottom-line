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

public class Reply extends Cmd {
	BottomLine pl = BottomLine.getInstance();

	public Reply() {
		super("reply", Permissions.REPLY);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Util.invalidArgCount(sender, "Reply", "Reply to a recent message.", "/reply [message...]");
			return;
		}

		String msg = "";
		for (int i = 0; i < args.length; i++) {
			msg = msg + args[i] + " ";
		}

		String name;

		if (sender instanceof Player) {
			Util.playSound((Player) sender);
			name = new PlayerData(((Player) sender).getUniqueId()).getDisplayName();
		} else if (!(sender instanceof Player)) {
			name = sender.getName();
		} else {
			name = "unknown";
		}

		String send;
		String player;

		if (!Store.reply.containsKey(sender.getName())) {
			try {
				if (Store.reply.get(sender.getName()).equals("CONSOLE")) {
					if (Store.msgtoggle.contains("CONSOLE")) {
						sender.sendMessage("§cMessage: §4Console has messages disabled!");
						return;
					}
					
					if (sender.hasPermission(Permissions.MSG_COLOUR)) {
						send = Util.colour(pl.getConfig().getString("msg.format-sender").replaceAll("%target%", "Console")).replaceAll("%message%", Util.parseColours(msg));
						player = Util.colour(pl.getConfig().getString("msg.format-target").replaceAll("%sender%", name)).replaceAll("%message%", Util.parseColours(msg));
					} else if (sender.hasPermission(Permissions.MSG_FORMAT)) {
						send = Util.colour(pl.getConfig().getString("msg.format-sender").replaceAll("%target%", "Console")).replaceAll("%message%", Util.parseFormat(msg));
						player = Util.colour(pl.getConfig().getString("msg.format-target").replaceAll("%sender%", name)).replaceAll("%message%", Util.parseFormat(msg));
					} else if (sender.hasPermission(Permissions.MSG_MAGIC)) {
						send = Util.colour(pl.getConfig().getString("msg.format-sender").replaceAll("%target%", "Console")).replaceAll("%message%", Util.parseMagic(msg));
						player = Util.colour(pl.getConfig().getString("msg.format-target").replaceAll("%sender%", name)).replaceAll("%message%", Util.parseMagic(msg));
					} else if (sender.hasPermission("bottomline.message.*")) {
						send = Util.colour(pl.getConfig().getString("msg.format-sender").replaceAll("%target%", "Console")).replaceAll("%message%", Util.colour(msg));
						player = Util.colour(pl.getConfig().getString("msg.format-target").replaceAll("%sender%", name)).replaceAll("%message%", Util.colour(msg));
					} else {
						send = Util.colour(pl.getConfig().getString("msg.format-sender").replaceAll("%target%", "Console")).replaceAll("%message%", msg);
						player = Util.colour(pl.getConfig().getString("msg.format-target").replaceAll("%sender%", name)).replaceAll("%message%", msg);
					}

					Bukkit.getConsoleSender().sendMessage(player);
					return;
				}
			} catch (Exception e) {
				sender.sendMessage("§cReply: §4You have nobody to reply to.");
				return;
			}

			sender.sendMessage("§cReply: §4You have nobody to reply to.");
			return;
		}

		Player target = Bukkit.getPlayer(Store.reply.get(sender.getName()));
		if (target == null) {
			sender.sendMessage("§cReply: §4The player that you last messaged is offline.");
			Store.reply.remove(sender.getName());
			return;
		}
		
		if (Store.msgtoggle.contains(target.getName())) {
			sender.sendMessage("§cMessage: §4" + new PlayerData(target.getUniqueId()).getDisplayName() + " §4has messages disabled!");
			return;
		}

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

		Util.playSound(target);

		sender.sendMessage(send);
		target.sendMessage(player);
	}
}