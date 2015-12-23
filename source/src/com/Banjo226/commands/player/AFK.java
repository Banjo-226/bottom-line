package com.Banjo226.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class AFK extends Cmd {
	BottomLine pl = BottomLine.getInstance();

	public AFK() {
		super("afk", Permissions.AFK);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		Player player = (Player) sender;
		if (args.length == 0) {
			if (!Store.away.contains(sender.getName())) {
				Store.away.add(sender.getName());

				sender.sendMessage("§6AFK: §eYou have now gone AFK.");

				if (pl.getConfig().getBoolean("afk.broadcast") == true) {
					Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("afk.afk").replaceAll("%player%", player.getDisplayName())));
				}
			} else {
				Store.away.remove(sender.getName());

				sender.sendMessage("§6AFK: §eYou are no longer AFK.");

				if (pl.getConfig().getBoolean("afk.broadcast") == true) {
					Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("afk.no-afk").replaceAll("%player%", player.getDisplayName())));
				}
			}
		}

		if (args.length == 1 && sender.hasPermission(Permissions.AFK_OTHERS)) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "AFK", args[0]);
				return;
			}

			if (!Store.away.contains(sender.getName())) {
				Store.away.add(sender.getName());

				sender.sendMessage("§6AFK: §eYou have now gone AFK.");

				if (pl.getConfig().getBoolean("afk.broadcast") == true) {
					Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("afk.afk").replaceAll("%player%", target.getDisplayName())));
				}
			} else {
				Store.away.remove(sender.getName());

				sender.sendMessage("§6AFK: §eYou are no longer AFK.");

				if (pl.getConfig().getBoolean("afk.broadcast") == true) {
					Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("afk.no-afk").replaceAll("%player%", target.getDisplayName())));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();

		if (!e.getMessage().contains("/msg") || !e.getMessage().contains("/m") || !e.getMessage().contains("/message") || !e.getMessage().contains("/reply") || !e.getMessage().contains("/r")) {
			if (Store.away.contains(player.getName())) {
				Store.away.remove(player.getName());

				player.sendMessage("§6AFK: §eYou are no longer AFK.");

				if (pl.getConfig().getBoolean("afk.broadcast") == true) {
					Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("afk.no-afk").replaceAll("%player%", player.getDisplayName())));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();

		if (!e.getMessage().contains("/msg") || !e.getMessage().contains("/m") || !e.getMessage().contains("/message") || !e.getMessage().contains("/reply") || !e.getMessage().contains("/r")) {
			if (Store.away.contains(player.getName())) {
				Store.away.remove(player.getName());

				player.sendMessage("§6AFK: §eYou are no longer AFK.");

				if (pl.getConfig().getBoolean("afk.broadcast") == true) {
					Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("afk.no-afk").replaceAll("%player%", player.getDisplayName())));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();

		if (Store.away.contains(player.getName())) {
			Store.away.remove(player.getName());

			player.sendMessage("§6AFK: §eYou are no longer AFK.");

			if (pl.getConfig().getBoolean("afk.broadcast") == true) {
				Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("afk.no-afk").replaceAll("%player%", player.getDisplayName())));
			}
		}
	}
}