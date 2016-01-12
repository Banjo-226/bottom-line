package com.Banjo226.commands.chat.list;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.Updater;
import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Money;
import com.Banjo226.util.files.PlayerData;
import com.Banjo226.util.files.TextFiles;

public class MOTD extends Cmd {
	BottomLine pl = BottomLine.getInstance();
	Money econ = Money.getInstance();
	TextFiles txt = TextFiles.getInstance();
	
	DecimalFormat form = new DecimalFormat("#.00");

	public MOTD() {
		super("motd", Permissions.MOTD);
	}

	public void run(CommandSender sender, String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		Date date = new Date();

		World w;
		String name;
		String balance;

		if (!(sender instanceof Player)) {
			w = Bukkit.getWorld("world");
			name = "§c§lCONSOLE";
			balance = "0";
		} else {
			Player player = (Player) sender;

			w = player.getWorld();
			name = new PlayerData(player.getUniqueId()).getDisplayName();
			balance = form.format(econ.getBalance(sender));
		}

		String ticks = Long.toString(w.getTime());
		String online = Integer.toString(Bukkit.getOnlinePlayers().size());
		String max = Integer.toString(Bukkit.getMaxPlayers());

		sender.sendMessage("§7§m---------§6 MOTD §7§m---------");

		if (pl.getConfig().getBoolean("motd.enabled", true)) {
			for (String motd : txt.getMotd()) {
				sender.sendMessage(Util.colour(motd.replaceAll("%player%", sender.getName()).replaceAll("%display%", name).replaceAll("%localtime%", sdf.format(date)).replaceAll("%ticks%", ticks).replaceAll("%players%", online).replaceAll("%max%", max)
						.replaceAll("%time%", Util.getWorldTime(w)).replaceAll("%balance%", balance)));
			}
		} else {
			sender.sendMessage("§4The MOTD function is disabled. To enable it, ask an admin to enable it in the configuration.");
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player sender = e.getPlayer();
		final PlayerData pd = new PlayerData(sender.getUniqueId(), false);

		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		final Date date = new Date();

		final World w = sender.getWorld();
		final String ticks = Long.toString(w.getTime());
		final String online = Integer.toString(Bukkit.getOnlinePlayers().size());
		final String max = Integer.toString(Bukkit.getMaxPlayers());
		final String bal = form.format(econ.getBalance(sender));

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
			public void run() {
				if (pl.getConfig().getBoolean("motd.enabled", true)) {
					try {
						for (String motd : txt.getMotd()) {
							sender.sendMessage(Util.colour(motd.replaceAll("%player%", sender.getName()).replaceAll("%display%", pd.getDisplayName()).replaceAll("%localtime%", sdf.format(date)).replaceAll("%ticks%", ticks).replaceAll("%players%", online).replaceAll("%max%", max)
									.replaceAll("%time%", Util.getWorldTime(w)).replaceAll("%balance%", bal)));
						}
					} catch (Exception e) {
						for (String motd : txt.getMotd()) {
							pd.setDisplayName(sender.getDisplayName());
							sender.sendMessage(Util.colour(motd.replaceAll("%player%", sender.getName()).replaceAll("%display%", pd.getDisplayName()).replaceAll("%localtime%", sdf.format(date)).replaceAll("%ticks%", ticks).replaceAll("%players%", online).replaceAll("%max%", max)
									.replaceAll("%time%", Util.getWorldTime(w)).replaceAll("%balance%", bal)));
						}
					}

					if (sender.isOp()) {
						if (BottomLine.update == true) {
							sender.sendMessage("§eBottomLine: §6A new version has been found! You are running " + Updater.oldvers + ", and the new update is " + Updater.newvers + ". Download at https://www.spigotmc.org/resources/bottomline." + Updater.res);
						}
					}
				}
			}
		}, 15L);
	}
}