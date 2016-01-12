package com.Banjo226.commands.player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class Nick extends Cmd {
	BottomLine pl = BottomLine.getInstance();

	public Nick() {
		super("nick", Permissions.NICKNAME);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		if (args.length == 0) {
			Util.invalidArgCount(sender, "Nickname", "Change the displayname of a player.", "/nick [nick]", "/nick [nick] <player>");
			return;
		}

		if (args.length == 2 && sender.hasPermission(Permissions.NICKNAME_OTHERS)) {
			Player target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				Util.offline(sender, "Nickname", args[1]);
				return;
			}

			PlayerData ps = new PlayerData(((Player) sender).getUniqueId());
			PlayerData pd = new PlayerData(target.getUniqueId());
			if (args[0].equalsIgnoreCase("off")) {
				sender.sendMessage("§6Nickname: §eRemoved your nickname.");
				pd.setNick(null);
				target.setPlayerListName(target.getName());
				return;
			}

			if (!target.isOp()) {
				if (args[0].length() < pl.getConfig().getInt("nick.min")) {
					sender.sendMessage("§cNickname: §4The amount of characters in your nickname is below the requirement!");
					return;
				} else if (args[0].length() > pl.getConfig().getInt("nick.max") && args[0].length() > sender.getName().length() + 2) {
					sender.sendMessage("§cNickname: §4The amount of characters in your nickname is above the limit!");
					return;
				}
			}

			Pattern pattern = Pattern.compile("[$+,:;=?@#|'<>.^*()%!-]");
			Matcher m = pattern.matcher(args[0]);
			if (m.find()) {
				sender.sendMessage("§cNickname: §4One of the characters specified is not a valid letter; §o" + m.group());
				return;
			}

			pd.setNick(args[0]);
			target.setPlayerListName(Util.colour(args[0]));
			updateDisplayName(target, pd);
			sender.sendMessage("§6Nickname: §eSuccessfully changed " + Util.colour(pd.getDisplayName()) + "§e's nickname to §o" + Util.colour(args[0]) + "§e!");
			target.sendMessage("§6Nickname: §e" + Util.colour(ps.getDisplayName()) + " §echanged your nickname to §o" + Util.colour(args[0]) + "§e!");
			return;
		}

		PlayerData pd = new PlayerData(((Player) sender).getUniqueId());
		Player player = (Player) sender;
		if (args[0].equalsIgnoreCase("off")) {
			sender.sendMessage("§6Nickname: §eRemoved your nickname.");
			pd.setNick(null);
			player.setPlayerListName(sender.getName());
			return;
		}

		if (!sender.isOp()) {
			if (args[0].length() < pl.getConfig().getInt("nick.min")) {
				sender.sendMessage("§cNickname: §4The amount of characters in your nickname is below the requirement!");
				return;
			} else if (args[0].length() > pl.getConfig().getInt("nick.max") && args[0].length() > sender.getName().length() + 2) {
				sender.sendMessage("§cNickname: §4The amount of characters in your nickname is above the limit!");
				return;
			}
		}

		Pattern pattern = Pattern.compile("[$+,:;=?@#|'<>.^*()%!-]");
		Matcher m = pattern.matcher(args[0]);
		if (m.find()) {
			sender.sendMessage("§cNickname: §4One of the characters specified is not a valid letter; §o" + m.group());
			return;
		}

		pd.setNick(args[0]);
		player.setPlayerListName(Util.colour(args[0]));
		updateDisplayName(player, pd);
		sender.sendMessage("§6Nickname: §eSuccessfully changed your nickname to §o" + Util.colour(args[0]) + "§e!");
	}

	public void updateDisplayName(Player player, PlayerData pd) {
		String name;

		try {
			name = pd.getNick();
		} catch (Exception ex) {
			name = player.getName();
		}

		if (player.isOp()) {
			try {
				name = Util.colour(pl.getConfig().getString("ops-colour")) + pd.getNick();
			} catch (Exception ex) {
				name = Util.colour(pl.getConfig().getString("ops-colour")) + player.getName();
			}
		}

		try {
			pd.setDisplayName(pl.getChat().getPlayerPrefix(player) + name + pl.getChat().getPlayerSuffix(player) + "§f");
		} catch (Exception ex) {
			pd.setDisplayName(name);
		}

		player.setDisplayName(pd.getDisplayName());
	}
}