package com.Banjo226.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Data;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class God extends Cmd {
	BottomLine pl = BottomLine.getInstance();
	Data d = Data.getInstance();

	public God() {
		super("god", Permissions.GOD);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		if (args.length == 0) {
			Player player = (Player) sender;

			if (!Store.god.contains(player.getName())) {
				sender.sendMessage("§6God: §eEnabled god mode");

				Store.god.add(player.getName());
				d.getConfig().set("god.toggle", Store.god);

				player.setHealth(20);
				player.setFoodLevel(20);

				Util.playSound(player);
			} else {
				Store.god.remove(player.getName());
				d.getConfig().set("god.toggle", Store.god);

				sender.sendMessage("§6God: §eDisabled god mode");
			}
		}

		if (sender.hasPermission(Permissions.GOD_OTHERS) && args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "God", args[0]);
				return;
			}

			if (!Store.god.contains(target.getName())) {
				sender.sendMessage("§6God: §eEnabled god mode for " + target.getDisplayName());

				Store.god.add(target.getName());
				d.getConfig().set("god.toggle", Store.god);

				target.setHealth(20);
				target.setFoodLevel(20);

				target.sendMessage("§6God: §eEnabled god mode by §l" + sender.getName());
				Util.playSound(target);
			} else {
				sender.sendMessage("§6God: §eDisabled god mode for " + target.getDisplayName());

				target.sendMessage("§6God: §eDisabled god mode by §l" + sender.getName());
				Store.god.remove(target.getName());
				d.getConfig().set("god.toggle", Store.god);
			}
		}
	}

	@EventHandler
	public void onDamageEvent(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();

			if (Store.god.contains(player.getName())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player player = e.getPlayer();

		if (pl.getConfig().getBoolean("god.notify") == true) {
			if (Store.god.contains(player.getName())) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
					public void run() {
						player.sendMessage(Util.colour(pl.getConfig().getString("god.message")));
					}
				}, 20L);
			}
		}
	}
}