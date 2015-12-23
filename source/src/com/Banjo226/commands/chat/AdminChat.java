package com.Banjo226.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Data;

import com.Banjo226.commands.Permissions;

public class AdminChat extends Cmd {
	BottomLine pl = BottomLine.getInstance();
	Data d = Data.getInstance();

	public AdminChat() {
		super("amsg", Permissions.ADMINCHAT);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Util.invalidArgCount(sender, "AdminChat", "Send a message to the other admins online.", "/amsg [message...]", "/amsg toggle");
			return;
		}

		if (args[0].equalsIgnoreCase("toggle") && args.length < 2) {
			if (!(sender instanceof Player)) {
				return;
			}
			if (!Store.atoggle.contains(sender.getName())) {
				sender.sendMessage("§6AdminChat: §eEnabled toggling!");

				Store.atoggle.add(sender.getName());
				d.getConfig().set("adminchat.toggle", Store.atoggle);
			} else {
				sender.sendMessage("§6AdminChat: §eDisabling toggling!");

				Store.atoggle.remove(sender.getName());
				d.getConfig().set("adminchat.toggle", Store.atoggle);
			}
			return;
		}

		String msg = "";
		for (int i = 0; i < args.length; i++) {
			msg = msg + args[i] + " ";
		}
		msg.trim();

		for (Player admin : Bukkit.getOnlinePlayers()) {
			if (admin.hasPermission(Permissions.ADMINCHAT)) {
				admin.sendMessage(Util.colour(pl.getConfig().getString("adminchat.format").replaceAll("%message%", msg).replaceAll("%player%", sender.getName())));
			}
		}
		Bukkit.getConsoleSender().sendMessage(Util.colour(pl.getConfig().getString("adminchat.format").replaceAll("%message%", msg).replaceAll("%player%", sender.getName())));
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();

		if (player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
			if (Store.atoggle.contains(player.getName())) {
				e.setCancelled(true);

				Bukkit.dispatchCommand(player, "a " + e.getMessage());
			}
		}
	}
}