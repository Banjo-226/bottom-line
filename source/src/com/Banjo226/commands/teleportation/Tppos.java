package com.Banjo226.commands.teleportation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class Tppos extends Cmd {

	public Tppos() {
		super("tppos", Permissions.TPPOS);
	}

	public void run(CommandSender sender, String[] args) {
		if (args.length < 3) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please specify coordinates and a player!");
				return;
			}

			Util.invalidArgCount(sender, "TPPOS", "Teleport to specific coordinates.", "/tppos [x] [y] [z]", "/tppos [x] [y] [z] <player>");
			return;
		}

		double x = Double.parseDouble(args[0]);
		double y = Double.parseDouble(args[1]);
		double z = Double.parseDouble(args[2]);

		if (y < 1) {
			sender.sendMessage("§cTPPOS: §4Please choose a y coordinate above the void!");
			return;
		}

		if (args.length == 3) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please specify a player!");
				return;
			}

			Player player = (Player) sender;
			PlayerData pd = new PlayerData(player.getUniqueId());
			pd.setBackLocation(player.getLocation());

			World w = player.getWorld();
			Location loc = new Location(w, x, y, z);

			player.teleport(loc);
			Util.playSound(player);

			sender.sendMessage("§eTPPOS: §6Teleporting to X: " + x + ", Y: " + y + ", Z: " + z + "...");
		}

		if (sender.hasPermission(Permissions.TPPOS_OTHERS) && args.length == 4) {
			Player target = Bukkit.getPlayer(args[3]);
			if (target == null) {
				Util.offline(sender, "TPPOS", args[3]);
				return;
			}
			
			PlayerData pd = new PlayerData(target.getUniqueId());
			pd.setBackLocation(target.getLocation());

			World w = target.getWorld();
			Location loc = new Location(w, x, y, z);

			target.teleport(loc);
			Util.playSound(target);

			sender.sendMessage("§eTPPOS: §6Teleporting " + target.getDisplayName() + " to X: " + x + ", Y: " + y + ", Z: " + z + "...");
			target.sendMessage("§eTPPOS: §6Teleporting to X: " + x + ", Y: " + y + ", Z: " + z + " by " + sender.getName() + ".");
		}
	}
}