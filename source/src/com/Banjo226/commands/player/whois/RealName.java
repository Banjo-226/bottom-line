package com.Banjo226.commands.player.whois;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class RealName extends Cmd implements TabCompleter {

	public RealName() {
		super("realname", Permissions.REALNAME);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (args.length == 0) {
			Util.invalidArgCount(sender, "Realname", "Find the real name behind a nickname.", "/realname [name]", "/realname [no-colour-coded-nick]");
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			boolean found = false;
			String realname = "";

			for (Player player : Bukkit.getOnlinePlayers()) {
				PlayerData pd = new PlayerData(player.getName());
				String nick = Util.removeColour(pd.getNick()).toLowerCase();
				if (nick.startsWith(args[0].toLowerCase())) {
					found = true;
					realname = player.getName();
					args[0] = pd.getNick();
				}
			}

			if (found) {
				sender.sendMessage("§6Realname: §e" + realname + " owns the nick of " + args[0]);
			} else {
				sender.sendMessage("§cRealname: §4No matches found for " + args[0]);
			}
			return;
		}

		PlayerData pd = new PlayerData(target.getName(), false);
		if (pd.getConfig().contains("nickname")) {
			sender.sendMessage("§6Realname: §e" + target.getName() + "'s nickname is " + pd.getNick());
		} else {
			sender.sendMessage("§cRealname: §4" + target.getName() + " does not have a nickname!");
		}
	}

	public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("realname")) {
			if (sender.hasPermission(getPermission())) {
				if (args.length == 1) {
					ArrayList<String> players = new ArrayList<>();

					if (!args[0].equalsIgnoreCase("")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							PlayerData pd = new PlayerData(player.getName());
							String nick = Util.removeColour(pd.getNick()).toLowerCase();
							if (nick.startsWith(args[0].toLowerCase())) {
								players.add(nick);
							}
						}
					} else {
						for (Player player : Bukkit.getOnlinePlayers()) {
							PlayerData pd = new PlayerData(player.getName());
							players.add(Util.removeColour(pd.getNick()).toLowerCase());
						}
					}

					Collections.sort(players);
					return players;
				}
			}
		}
		return null;
	}
}