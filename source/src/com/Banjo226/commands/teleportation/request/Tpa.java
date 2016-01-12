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

public class Tpa extends Cmd {

	public Tpa() {
		super("tpa", Permissions.TPA);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		if (args.length == 0) {
			Util.invalidArgCount(sender, "TPA", "Request to teleport to another player, or accept/deny a request.", "/tpa [player]", "/tpa [accept|deny]");
			return;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("accept")) {
				if (Request.hasRequest(sender.getName())) {
					Request accept = Request.getCurrentRequested(sender.getName());

					Player player = Bukkit.getPlayer(accept.getRequester().getName());
					Player target = Bukkit.getPlayer(accept.getRequestee().getName());

					if (target == null || player == null) {
						sender.sendMessage("§cTPA: §4The requester is not online.");
						Store.tpa.remove(accept);
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

					PlayerData pd = new PlayerData(player.getUniqueId());
					pd.setBackLocation(player.getLocation());					
					player.teleport(loc);
					sender.sendMessage("§6TPA: §eTeleporting to location...");
					target.sendMessage("§6TPA: §eRequest accepted");

					if (loc.getY() <= 1) {
						player.teleport(current);
						sender.sendMessage("§cTPA: §4The location was beyond the void, so you'll stay where you are.");
						return;
					}

					Store.tpa.remove(accept);
				} else {
					sender.sendMessage("§cTPA: §4You have no pending requests.");
				}
			} else if (args[0].equalsIgnoreCase("deny")) {
				if (Request.hasRequest(sender.getName())) {
					Request denied = Request.getCurrentRequested(sender.getName());

					Player player = Bukkit.getPlayer(denied.getRequester().getName());
					Player target = Bukkit.getPlayer(denied.getRequestee().getName());

					if (target == null || player == null) {
						sender.sendMessage("§cTPA: §4The requester is not online.");
						Store.tpa.remove(denied);
						return;
					}

					if (target != null) target.sendMessage("§cTPA: §4" + sender.getName() + " has denied your request.");

					Store.tpa.remove(denied);
				} else {
					sender.sendMessage("§cTPA: §4You have no pending requests.");
				}
			} else {
				Player player = (Player) sender;
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					Util.offline(sender, "TPA", args[0]);
					return;
				}

				if (target.getName().equalsIgnoreCase(sender.getName())) {
					sender.sendMessage("§cTPA: §4You cannot request to tp to yourself!");
					return;
				}

				if (Store.tptoggle.contains(target.getName())) {
					sender.sendMessage("§cTPA: §4" + target.getDisplayName() + " §4has a TP Toggle enabled.");
					return;
				}

				Request request = new Request(player, target);
				sender.sendMessage("§6TPA: §eRequest sent!");

				Store.tpa.add(request);
			}
		}
	}
}