package com.Banjo226.commands.player.gamemode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.PermissionMessages;
import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class Gamemode extends Cmd {

	public Gamemode() {
		super("gamemode", Permissions.GAMEMODE);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (args.length == 0) {
			if (!(sender instanceof Player)) throw new ConsoleSenderException("Please specify a gamemode, then a player!", getName());

			Util.invalidArgCount(sender, "Gamemode", "Change the current gamemode of a player.", "/gamemode [survival|creative|adventure]", "/gamemode [survival|creative|advenute] <player>");
			return;
		}

		args[0] = args[0].toLowerCase();

		if (args.length == 1) {
			if (!(sender instanceof Player)) throw new ConsoleSenderException("Please specify a gamemode, then a player!", getName());

			Player player = (Player) sender;
			if (args[0].startsWith("s")) {
				if (isAuthorised(sender, Permissions.SURVIVAL)) {
					player.setGameMode(GameMode.SURVIVAL);
					sender.sendMessage("§6Gamemode: §eSet gamemode to survival!");
				} else {
					sender.sendMessage(PermissionMessages.SURVIVAL.toString());
					return;
				}
			} else if (args[0].startsWith("c")) {
				if (isAuthorised(sender, Permissions.CREATIVE)) {
					player.setGameMode(GameMode.CREATIVE);
					sender.sendMessage("§6Gamemode: §eSet gamemode to creative!");
				} else {
					sender.sendMessage(PermissionMessages.CREATIVE.toString());
					return;
				}
			} else if (args[0].startsWith("a")) {
				if (isAuthorised(sender, Permissions.ADVENTURE)) {
					player.setGameMode(GameMode.ADVENTURE);
					sender.sendMessage("§6Gamemode: §eSet gamemode to adventure!");
				} else {
					sender.sendMessage(PermissionMessages.ADVENTURE.toString());
					return;
				}
			} else {
				sender.sendMessage("§cGamemode: §4" + args[0] + " is not a valid gamemode!");
			}
			return;
		}

		Player target = Bukkit.getPlayer(args[1]);
		if (target == null) {
			Util.offline(sender, "Gamemode", args[1]);
			return;
		}

		if (target.getName().equalsIgnoreCase(sender.getName())) {
			Bukkit.dispatchCommand(sender, "gamemode " + args[0]);
			return;
		}

		PlayerData pd = new PlayerData(target.getName());
		PlayerData ps = new PlayerData(sender.getName(), false);

		String name;
		try {
			name = ps.getDisplayName();
		} catch (Exception e) {
			name = sender.getName();
		}

		if (isAuthorised(sender, Permissions.GAMEMODE_OTHERS)) {
			if (args[0].startsWith("s")) {
				target.setGameMode(GameMode.SURVIVAL);
				sender.sendMessage("§6Gamemode: §eSet " + pd.getDisplayName() + "§e's gamemode to survival!");
				target.sendMessage("§6Gamemode: §e" + name + "§e set your gamemode to survival!");
			} else if (args[0].startsWith("c")) {
				target.setGameMode(GameMode.CREATIVE);
				sender.sendMessage("§6Gamemode: §eSet " + pd.getDisplayName() + "§e's gamemode to creative!");
				target.sendMessage("§6Gamemode: §e" + name + "§e set your gamemode to creative!");
			} else if (args[0].startsWith("a")) {
				target.setGameMode(GameMode.ADVENTURE);
				sender.sendMessage("§6Gamemode: §eSet " + pd.getDisplayName() + "§e's gamemode to adventure!");
				target.sendMessage("§6Gamemode: §e" + name + "§e set your gamemode to adventure!");
			} else {
				sender.sendMessage("§cGamemode: §4" + args[0] + " is not a valid gamemode!");
			}
		} else {
			sender.sendMessage("§cYou are not allowed to change others' gamemode!");
		}
	}
}