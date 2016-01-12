package com.Banjo226.commands.law;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.law.history.Types;

public class Freeze extends Cmd {
	BottomLine pl = BottomLine.getInstance();
	PlayerData con;
	String prefix = "§8[§eLaw§8] ";

	public Freeze() {
		super("freeze", Permissions.FREEZE);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length < 2) {
			Util.invalidArgCount(sender, "Freeze", "Freeze an individual on the server.", "/freeze [player] [timestamp]", "/freeze [player] [timestamp] <reason>", "/freeze unfreeze [player]");
			return;
		}

		if (args[0].equalsIgnoreCase("unfreeze")) {
			if (args.length == 1) {
				Util.invalidArgCount(sender, "freeze", "Unmute a frozen player.", "/unfreeze [player]");
				return;
			}

			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "Freeze", args[0]);
				return;
			}

			if (!(Store.freeze.contains(target.getName()))) {
				sender.sendMessage("§cFreeze: §4The chosen player §o(" + target.getName() + ") §4is not frozen!");
				return;
			} else if (Store.freeze.contains(target.getName())) {
				Store.freeze.remove(target.getName());
				Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + "§e unfroze §c" + target.getDisplayName());
			}
			return;
		}

		args[1] = args[1].toLowerCase();

		final Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			Util.offline(sender, "Freeze", args[0]);
			return;
		}

		if (target.isOp()) {
			Util.punishOps(sender, "Freeze");
			return;
		}

		if (Store.freeze.contains(target.getName())) {
			sender.sendMessage("§cFreeze: §4The selected player §o(" + target.getName() + ") §cis already frozen!");
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
						Store.freeze.remove(target.getName());
						target.sendMessage("§cFreeze: §4You've been unfrozen with a warning.");
					}
				}, time);
			} else if (m.group(2) != null) {
				long time = Integer.parseInt(timeValue) * 1200;
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

					@Override
					public void run() {
						Store.freeze.remove(target.getName());
						target.sendMessage("§cFreeze: §4You've been unfrozen with a warning.");
					}
				}, time);
			} else if (m.group(3) != null) {
				long time = Integer.parseInt(timeValue) * 20;
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

					@Override
					public void run() {
						Store.freeze.remove(target.getName());
						target.sendMessage("§cFreeze: §4You've been unfrozen with a warning.");
					}
				}, time);
			} else if (m.group(4) != null) {
				long time = Integer.parseInt(timeValue) * 1728000;
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

					@Override
					public void run() {
						Store.freeze.remove(target.getName());
						target.sendMessage("§cFreeze: §4You've been unfrozen with a warning.");
					}
				}, time);
			}
		} else {
			Util.invalidTimestamp(sender, "Freeze", args[1]);
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("(z) dd/MM/yy hh:mm:ss a");
		Date date = new Date();

		if (args.length == 2) {
			con = new PlayerData(target.getUniqueId());
			con.addHistory(Types.FREEZE, "Misconduct", sender.getName(), sdf.format(date), args[1]);

			Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §efroze §c" + con.getDisplayName() + " §efor " + args[1] + "!");
		} else {
			String msg = "";
			for (int i = 2; i < args.length; i++) {
				msg = msg + args[i] + " ";
			}

			con = new PlayerData(target.getUniqueId());
			con.addHistory(Types.FREEZE, msg.trim(), sender.getName(), sdf.format(date), args[1]);

			Bukkit.broadcastMessage(prefix + "§ePlayer §c" + sender.getName() + " §efroze §c" + con.getDisplayName() + " §efor " + args[1] + " because " + msg.trim() + "!");
		}

		Store.freeze.add(target.getName());

		Util.sendActionBar(target, prefix + "§eFreeze expires in " + args[1] + "!");
	}

	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent e) {
		if (Store.freeze.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Util.colour(pl.getConfig().getString("law.freeze.noMove")));
		}
	}
}