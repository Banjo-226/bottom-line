package com.Banjo226.commands.player;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;

public class Strike extends Cmd {
	BottomLine pl = BottomLine.getInstance();

	public Strike() {
		super("strike", Permissions.STRIKE);
	}

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) {
		String name = "Console";
		if (sender instanceof Player) {
			name = new PlayerData(((Player) sender).getUniqueId()).getDisplayName();
		}

		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please specify a player");
				return;
			}

			Player player = (Player) sender;
			Block block = player.getTargetBlock((HashSet<Byte>) null, 100);
			Location bl = block.getLocation();
			World world = bl.getWorld();

			sender.sendMessage("§6Strike: §eStriked the location of §oX: " + bl.getBlockX() + "§e§o, Y: " + bl.getBlockY() + "§e§o, Z: " + bl.getBlockZ());
			world.strikeLightning(bl);
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			Util.offline(sender, "Strike", args[0]);
			return;
		}

		PlayerData td = new PlayerData(target.getUniqueId(), false);

		if (pl.getConfig().getBoolean("strike.strike-ops") == false) {
			if (target.isOp()) {
				Util.punishOps(sender, "Strike");
				return;
			}
		}

		if (pl.getConfig().getBoolean("strike.warn-player") == true) {
			target.sendMessage("§cStrike: §4" + name + " §4has striked you!");
		}
		sender.sendMessage("§cStrike: §4Successfully striked " + td.getDisplayName());

		Location loc = target.getLocation();
		World world = loc.getWorld();
		world.strikeLightning(loc);
	}
}