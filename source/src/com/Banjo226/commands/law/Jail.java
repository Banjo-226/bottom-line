package com.Banjo226.commands.law;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Jails;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.law.history.Types;

public class Jail extends Cmd implements TabCompleter {
	BottomLine pl = BottomLine.getInstance();
	Jails j = Jails.getInstance();
	PlayerData pd;
	String prefix = "§8[§eLaw§8] ";

	public Jail() {
		super("jail", Permissions.JAIL);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length < 3) {
			Util.invalidArgCount(sender, "Jail", "Send a player to a place where they cannot speak, place blocks, etc.", "/jail [player] [timestamp] [jail]", "/jail [player] [timestamp] [jail] <reason>", "/jail unjail [player]");
			return;
		}

		if (args[0].equalsIgnoreCase("unjail")) {
			if (args.length == 1) {
				Util.invalidArgCount(sender, "Jail", "Unjail a jailed player.", "/jail unjail [player]");
				return;
			}

			Player target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				Util.offline(sender, "Jail", args[1]);
				return;
			}
			pd = new PlayerData(target.getName());

			if (!(Store.muted.contains(target.getName()))) {
				sender.sendMessage("§cJail: §4The chosen player §o(" + target.getName() + ") §4is not jailed!");
				return;
			} else if (Store.muted.contains(target.getName())) {
				Store.muted.remove(target.getName());
				Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + "§e unjailed §c" + target.getDisplayName());
			}

			target.teleport(pd.getLocation());
			return;
		}

		args[1] = args[1].toLowerCase();

		final Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			Util.offline(sender, "Jail", args[0]);
			return;
		}

		if (target.isOp()) {
			Util.punishOps(sender, "Jail");
			return;
		}

		if (Store.jailed.contains(target.getName())) {
			sender.sendMessage("§cJail: §4The selected player §o(" + target.getName() + ") §cis already jailed!");
			return;
		}

		String regex = "(?:(?<h>\\d+)h)?(?:(?<m>\\d+)m)?(?:(?<s>\\d+)s)?(?:(?<d>\\d+)d)?";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(args[1].toString());

		String timeValue = args[1].replaceFirst(".*?(\\d+).*", "$1");

		if (m.matches()) {
			if (m.group(1) != null) {
				long time = Integer.parseInt(timeValue) * 72000;
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

					@Override
					public void run() {
						Store.jailed.remove(target.getName());
						target.teleport(pd.getLocation());
						target.sendMessage("§cJail: §4You've been unjailed with a warning.");
					}
				}, time);
			} else if (m.group(2) != null) {
				long time = Integer.parseInt(timeValue) * 1200;
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

					@Override
					public void run() {
						Store.jailed.remove(target.getName());
						target.teleport(pd.getLocation());
						target.sendMessage("§cJail: §4You've been unjailed with a warning.");
					}
				}, time);
			} else if (m.group(3) != null) {
				long time = Integer.parseInt(timeValue) * 20;
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

					@Override
					public void run() {
						Store.jailed.remove(target.getName());
						target.teleport(pd.getLocation());
						target.sendMessage("§cJail: §4You've been unjailed with a warning.");
					}
				}, time);
			} else if (m.group(4) != null) {
				long time = Integer.parseInt(timeValue) * 1728000;
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

					@Override
					public void run() {
						Store.jailed.remove(target.getName());
						target.teleport(pd.getLocation());
						target.sendMessage("§cJail: §4You've been unjailed with a warning.");
					}
				}, time);
			}
		} else {
			Util.invalidTimestamp(sender, "Jail", args[1]);
			return;
		}

		if (j.getConfig().getConfigurationSection(args[2]) == null) {
			sender.sendMessage("§cJail: §4The jail §o(" + args[2] + ") §4does not exist!");
			if (j.getConfig().getStringList("existing").size() == 0) {
				sender.sendMessage("§4There are no existing jails, please create one!");
			} else {
				sender.sendMessage("§4Existing jails:");
				for (int i = 0; i < j.getConfig().getStringList("existing").size(); i++) {
					sender.sendMessage("§c- " + j.getConfig().getStringList("existing").get(i));
				}
			}
			return;
		}

		Location loc = j.getLocation(args[2]);
		target.teleport(loc);

		SimpleDateFormat sdf = new SimpleDateFormat("(z) dd/MM/yy hh:mm:ss a");
		Date date = new Date();

		if (args.length == 3) {
			pd = new PlayerData(target.getName());
			pd.addHistory(Types.JAIL, "Misconduct", sender.getName(), sdf.format(date), args[1]);

			Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §ejailed §c" + pd.getDisplayName() + " §efor " + args[1] + "!");
		} else {
			String msg = "";
			for (int i = 3; i < args.length; i++) {
				msg = msg + args[i] + " ";
			}

			pd = new PlayerData(target.getName());
			pd.addHistory(Types.JAIL, msg.trim(), sender.getName(), sdf.format(date), args[1]);

			Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §ejailed §c" + pd.getDisplayName() + " §efor " + args[1] + " because " + msg.trim() + "!");
		}

		Store.jailed.add(target.getName());

		Util.sendActionBar(target, prefix + "§eJail expires in " + args[1] + "!");
	}

	@EventHandler
	public void blockCommands(PlayerCommandPreprocessEvent e) {
		if (Store.jailed.contains(e.getPlayer().getName())) {
			if (e.getMessage().toLowerCase().contains("/")) {
				if (Store.jailBlocked.contains("**")) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(Util.colour(pl.getConfig().getString("law.crimeMessage")));
				} else {
					for (int i = 0; i < Store.jailBlocked.size(); i++) {
						if (e.getMessage().toLowerCase().contains(Store.jailBlocked.get(i))) {
							e.setCancelled(true);
							e.getPlayer().sendMessage(Util.colour(pl.getConfig().getString("law.crimeMessage")));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (Store.jailed.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Util.colour(pl.getConfig().getString("law.jail.noSpeak")));
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (Store.jailed.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Util.colour(pl.getConfig().getString("law.crimeMessage")));
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (Store.jailed.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Util.colour(pl.getConfig().getString("law.crimeMessage")));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("jail")) {
			if (args.length == 1 && args.length < 2) {
				ArrayList<String> players = new ArrayList<>();

				if (!args[0].equalsIgnoreCase("")) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
							players.add(player.getName());
						}
					}
				} else {
					for (Player player : Bukkit.getOnlinePlayers()) {
						players.add(player.getName());
					}
				}

				Collections.sort(players);
				return players;
			}
		}
		return null;
	}
}