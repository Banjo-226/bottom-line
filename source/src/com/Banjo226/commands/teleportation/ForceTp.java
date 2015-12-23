package com.Banjo226.commands.teleportation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;

public class ForceTp extends Cmd {

	public ForceTp() {
		super("ftp", Permissions.FORCETP);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		if (args.length == 0) {
			Util.invalidArgCount(sender, "Force Teleport", "Teleport to a player.", "/ftp [player]", "/ftp [target a] [target b]");
			return;
		}

		if (args.length == 1) {
			Player player = (Player) sender;
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "Force Teleport", args[0]);
				return;
			}

			Location current = player.getLocation();

			Location loc = null;
			World w = target.getWorld();
			double x = target.getLocation().getX();
			double y = target.getLocation().getY();
			double z = target.getLocation().getZ();
			float yaw = target.getLocation().getYaw();
			float pitch = target.getLocation().getPitch();

			boolean playerSolid = false;
			while (playerSolid == false) {
				loc = new Location(w, x, y, z, yaw, pitch);
				if (loc.getBlock().getType() != Material.AIR) {
					playerSolid = true;
				} else if (player.getAllowFlight() == true && loc.getBlock().getType() == Material.AIR) {
					playerSolid = true;
					player.setFlying(true);
				} else {
					y--;
				}
			}

			loc = new Location(w, x, (y + 1), z, yaw, pitch);

			player.teleport(loc);
			sender.sendMessage("§6Force Teleport: §eTeleporting to location...");

			if (loc.getY() <= 1) {
				player.teleport(current);
				sender.sendMessage("§cForce Teleport: §4The location was beyond the void, so you'll stay where you are.");
				return;
			}
		}

		if (args.length == 2 && isAuthorised(sender, Permissions.TELEPORT_OTHERS)) {
			Player t1 = Bukkit.getPlayer(args[0]);
			Player t2 = Bukkit.getPlayer(args[1]);

			if (t1 == null) {
				sender.sendMessage("§cForce Teleport: §4The first player §o(" + args[0] + ") §4is currently offline.");
				return;
			}

			if (t2 == null) {
				sender.sendMessage("§cForce Teleport: §4The second player §o(" + args[1] + ") §4is currently offline.");
				return;
			}

			Location current = t1.getLocation();

			Location loc = null;
			World w = t2.getWorld();
			double x = t2.getLocation().getX();
			double y = t2.getLocation().getY();
			double z = t2.getLocation().getZ();
			float yaw = t2.getLocation().getYaw();
			float pitch = t2.getLocation().getPitch();

			boolean playerSolid = false;
			while (playerSolid == false) {
				loc = new Location(w, x, y, z, yaw, pitch);
				if (loc.getBlock().getType() != Material.AIR) {
					playerSolid = true;
				} else if (t1.getAllowFlight() == true && loc.getBlock().getType() == Material.AIR) {
					playerSolid = true;
					t1.setFlying(true);
				} else {
					y--;
				}
			}

			loc = new Location(w, x, (y + 1), z, yaw, pitch);

			t1.teleport(loc);
			sender.sendMessage("§6Force Teleport: §eTeleporting " + t1.getDisplayName() + " §eto " + t2.getDisplayName() + "§e's location...");
			t1.sendMessage("§6Force Teleport: §eTeleporting you to " + t2.getDisplayName() + "§e's location by " + sender.getName() + "§e.");
			t2.sendMessage("§6Force Teleport: §e" + sender.getName() + " §eteleported " + t1.getDisplayName() + " §eto you.");

			if (loc.getY() <= 1) {
				t1.teleport(current);
				sender.sendMessage("§cForce Teleport: §4The location was beyond the void, so you'll stay where you are.");
				return;
			}
		}
	}
}