package com.Banjo226.commands.teleportation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;
import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class Tphere extends Cmd {

	public Tphere() {
		super("tphere", Permissions.TELEPORT_HERE);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		if (args.length == 0) {
			Util.invalidArgCount(sender, "Teleport", "Teleport a player to your location.", "/tphere [player]");
			return;
		}

		if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage("§cTeleport: §4The player §o(" + args[0] + ") §4is currently offline.");
				return;
			}

			if (Store.tptoggle.contains(target.getName())) {
				sender.sendMessage("§cTeleport: §4" + target.getDisplayName() + " §4has a TP Toggle enabled.");
				return;
			}

			Player player = (Player) sender;
			Location current = target.getLocation();

			Location loc = null;
			World w = player.getWorld();
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			float yaw = player.getLocation().getYaw();
			float pitch = player.getLocation().getPitch();

			boolean playerSolid = false;
			while (playerSolid == false) {
				loc = new Location(w, x, (y + 1), z, yaw, pitch);
				if (loc.getBlock().getType() != Material.AIR) {
					playerSolid = true;
				} else if (target.getAllowFlight() == true && loc.getBlock().getType() == Material.AIR) {
					playerSolid = true;
					target.setFlying(true);

					loc = new Location(w, x, y, z, yaw, pitch);
				} else {
					y--;
				}
			}

			PlayerData pd = new PlayerData(target.getUniqueId());
			pd.setBackLocation(target.getLocation());
			target.teleport(loc);
			sender.sendMessage("§6Teleport: §eTeleporting " + target.getName() + " to you...");

			if (loc.getY() <= 1) {
				target.teleport(current);
				sender.sendMessage("§cTeleport: §4The location was beyond the void, so they'll stay they were.");
				return;
			}
		}
	}
}