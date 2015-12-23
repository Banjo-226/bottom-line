package com.Banjo226.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Data;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;

public class CommandSpy extends Cmd {
	Data d = Data.getInstance();
	BottomLine pl = BottomLine.getInstance();

	public CommandSpy() {
		super("cmdspy", Permissions.CMDSPY);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please specify a player.");
				return;
			}
			Player player = (Player) sender;

			if (!Store.cmdspy.contains(sender.getName())) {
				Store.cmdspy.add(sender.getName());
				d.getConfig().set("spy.cmd.toggle", Store.cmdspy);
				d.saveConfig();

				sender.sendMessage("§6Command Spy: §eEnabled command spy");

				Util.playSound(player);
				return;
			} else {
				Store.cmdspy.remove(sender.getName());
				d.getConfig().set("spy.cmd.toggle", Store.cmdspy);
				d.saveConfig();

				sender.sendMessage("§6Command Spy: §eDisabled command spy");

				Util.playSound(player);
				return;
			}
		}

		if (args.length == 1 && sender.hasPermission(Permissions.CMDSPY_OTHERS)) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "Command Spy", args[0]);
				return;
			}

			PlayerData pd = new PlayerData(target.getName());
			PlayerData ps = new PlayerData(sender.getName());

			if (!Store.cmdspy.contains(target.getName())) {
				Store.cmdspy.add(sender.getName());
				d.getConfig().set("spy.cmd.toggle", Store.cmdspy);
				d.saveConfig();

				sender.sendMessage("§6Command Spy: §eEnabled command spy for " + pd.getDisplayName());
				target.sendMessage("§6Command Spy: §e" + ps.getDisplayName() + " §eenabled your command spy!");

				Util.playSound(target);
				return;
			} else {
				Store.cmdspy.remove(sender.getName());
				d.getConfig().set("spy.cmd.toggle", Store.cmdspy);
				d.saveConfig();

				sender.sendMessage("§6Command Spy: §eDisabled command spy for " + pd.getDisplayName());
				target.sendMessage("§6Command Spy: §e" + ps.getDisplayName() + " §edisabled your command spy!");

				Util.playSound(target);
				return;
			}
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		String cmd = e.getMessage();

		for (Player admin : Bukkit.getOnlinePlayers()) {
			for (int i = 0; i < Store.cmdBlacklist.size(); i++) {
				if (cmd.contains(Store.cmdBlacklist.get(i))) {
					return;
				}
			}

			if (Store.cmdspy.contains(admin.getName())) {
				admin.sendMessage(Util.colour(pl.getConfig().getString("cmdspy.format").replaceAll("%player%", player.getDisplayName()).replaceAll("%command%", cmd)));
			}
		}
	}
}