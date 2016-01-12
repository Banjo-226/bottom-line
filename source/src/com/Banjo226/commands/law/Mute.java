package com.Banjo226.commands.law;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.law.history.Types;

public class Mute extends Cmd implements TabCompleter {
	BottomLine pl = BottomLine.getInstance();
	PlayerData con;
	String prefix = "§8[§eLaw§8] ";

	public Mute() {
		super("mute", Permissions.MUTE);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length < 2) {
			Util.invalidArgCount(sender, "Mute", "Silence an individual on the server.", "/mute [player] [timestamp]", "/mute [player] [timestamp] <reason>", "/mute unmute [player]");
			return;
		}

		if (args[0].equalsIgnoreCase("unmute")) {
			if (args.length == 1) {
				Util.invalidArgCount(sender, "Mute", "Unmute a muted player.", "/mute unmute [player]");
				return;
			}

			Player target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				Util.offline(sender, "Mute", args[1]);
				return;
			}

			if (!(Store.muted.contains(target.getName()))) {
				sender.sendMessage("§cMute: §4The chosen player §o(" + target.getName() + ") §4is not muted!");
				return;
			} else if (Store.muted.contains(target.getName())) {
				Store.muted.remove(target.getName());
				Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + "§e unmuted §c" + target.getDisplayName());
			}
			return;
		}

		args[1] = args[1].toLowerCase();

		final Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			Util.offline(sender, "Mute", args[0]);
			return;
		}

		if (target.isOp()) {
			Util.punishOps(sender, "Mute");
			return;
		}

		if (Store.muted.contains(target.getName())) {
			sender.sendMessage("§cMute: §4The selected player §o(" + target.getName() + ") §cis already muted!");
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
						Store.muted.remove(target.getName());
						target.sendMessage("§cMute: §4You've been unmuted with a warning.");
					}
				}, time);
			} else if (m.group(2) != null) {
				long time = Integer.parseInt(timeValue) * 1200;
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

					@Override
					public void run() {
						Store.muted.remove(target.getName());
						target.sendMessage("§cMute: §4You've been unmuted with a warning.");
					}
				}, time);
			} else if (m.group(3) != null) {
				long time = Integer.parseInt(timeValue) * 20;
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

					@Override
					public void run() {
						Store.muted.remove(target.getName());
						target.sendMessage("§cMute: §4You've been unmuted with a warning.");
					}
				}, time);
			} else if (m.group(4) != null) {
				long time = Integer.parseInt(timeValue) * 1728000;
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

					@Override
					public void run() {
						Store.muted.remove(target.getName());
						target.sendMessage("§cMute: §4You've been unmuted with a warning.");
					}
				}, time);
			}
		} else {
			Util.invalidTimestamp(sender, "Mute", args[1]);
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("(z) dd/MM/yy hh:mm:ss a");
		Date date = new Date();

		if (args.length == 2) {
			con = new PlayerData(target.getUniqueId());
			con.addHistory(Types.MUTE, "Misconduct", sender.getName(), sdf.format(date), args[1]);

			Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §emuted §c" + con.getDisplayName() + " §efor " + args[1] + "!");
		} else {
			String msg = "";
			for (int i = 2; i < args.length; i++) {
				msg = msg + args[i] + " ";
			}

			con = new PlayerData(target.getUniqueId());
			con.addHistory(Types.MUTE, msg.trim(), sender.getName(), sdf.format(date), args[1]);

			Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §emuted §c" + con.getDisplayName() + " §efor " + args[1] + " because " + msg.trim() + "!");
		}

		Store.muted.add(target.getName());

		Util.sendActionBar(target, prefix + "§eMute expires in " + args[1] + "!");
	}

	@EventHandler
	public void blockCommands(PlayerCommandPreprocessEvent e) {
		if (Store.muted.contains(e.getPlayer().getName())) {
			if (e.getMessage().toLowerCase().contains("/")) {
				if (Store.muteBlocked.contains("**")) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(Util.colour(pl.getConfig().getString("law.crimeMessage")));
				} else {
					for (int i = 0; i < Store.muteBlocked.size(); i++) {
						if (e.getMessage().toLowerCase().contains(Store.muteBlocked.get(i))) {
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
		if (Store.muted.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Util.colour(pl.getConfig().getString("law.mute.noSpeak")));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mute") || cmd.getName().equalsIgnoreCase("unmute")) {
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