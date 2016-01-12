package com.Banjo226.commands.teleportation.request;

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

public class Tpahere extends Cmd {

	public Tpahere() {
		super("tpahere", Permissions.TPAHERE);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		if (args.length == 0) {
			Util.invalidArgCount(sender, "TPAHERE", "Request to teleport another player to you, or accept/deny a request.", "/tpahere [player]", "/tpahere [accept|deny]");
			return;
		}

		if (args[0].equalsIgnoreCase("accept")) {
			if (TpahereRequest.hasRequest(sender.getName())) {
				TpahereRequest accept = TpahereRequest.getCurrentRequested(sender.getName());

				Player player = Bukkit.getPlayer(accept.getRequester().getName());
				Player target = Bukkit.getPlayer(accept.getRequestee().getName());

				if (target == null || player == null) {
					sender.sendMessage("§cTPAHERE: §4The requester is not online.");
					Store.tpa.remove(accept);
					return;
				}

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
				player.sendMessage("§6TPAHERE: §eRequest accepted");
				target.sendMessage("§6TPAHERE: §eTeleporting to location...");

				if (loc.getY() <= 1) {
					target.teleport(current);
					player.sendMessage("§cTPHERE: §4The location was beyond the void, so you'll stay where you are.");
					return;
				}

				Store.tpahere.remove(accept);
			} else {
				sender.sendMessage("§cTPAHERE: §4You have no pending requests.");
			}
		} else if (args[0].equalsIgnoreCase("deny")) {
			if (TpahereRequest.hasRequest(sender.getName())) {
				TpahereRequest denied = TpahereRequest.getCurrentRequested(sender.getName());

				Player player = Bukkit.getPlayer(denied.getRequester().getName());
				Player target = Bukkit.getPlayer(denied.getRequestee().getName());

				if (target == null || player == null) {
					sender.sendMessage("§cTPAHERE: §4The requester is not online.");
					Store.tpahere.remove(denied);
					return;
				}

				if (target != null) target.sendMessage("§cTPAHERE: §4" + sender.getName() + " has denied your request.");

				Store.tpahere.remove(denied);
			} else {
				sender.sendMessage("§cTPAHERE: §4You have no pending requests.");
			}
		} else {
			Player player = (Player) sender;
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "TPAHERE", args[0]);
				return;
			}

			if (Store.tptoggle.contains(target.getName())) {
				sender.sendMessage("§cTPAHERE: §4" + target.getDisplayName() + " §4has a TP Toggle enabled.");
				return;
			}

			TpahereRequest request = new TpahereRequest(player, target);
			sender.sendMessage("§6TPAHERE: §eRequest sent!");

			Store.tpahere.add(request);
		}
	}
}