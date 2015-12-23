package com.Banjo226.commands.teleportation.request;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Store;

public class TpahereRequest {
	private Player requester;
	private Player requestee;
	private BottomLine pl = BottomLine.getInstance();
	private int time;

	private TpahereRequest instance;

	public TpahereRequest(Player requester, Player requestee) {
		this.requester = requester;
		this.requestee = requestee;
		instance = this;
		this.time = pl.getConfig().getInt("tpa.seconds");

		this.startTimer();
	}

	@SuppressWarnings("deprecation")
	public void startTimer() {
		if (requestee != null) {
			requestee.sendMessage("§e" + requester.getDisplayName() + " §6has requested to teleport!");
			requestee.sendMessage("§6To accept, do §e/tpahere accept §6or §e/tpahere deny §6to deny the request.");
			if (pl.getConfig().getBoolean("tpa.timeout", true)) {
				requestee.sendMessage("§6You have §e" + time + "§6 seconds before the request expires.");

				Store.tpacooldown.put(requester.getName(), System.currentTimeMillis());
			}
		}

		if (pl.getConfig().getBoolean("tpa.timeout", true)) {
			if (Store.tpacooldown.containsKey(requester.getName())) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BottomLine.getInstance(), new BukkitRunnable() {
					@Override
					public void run() {
						if (Store.tpahere.contains(instance)) {
							if (requestee != null) requestee.sendMessage("§cTPAHERE: §4The request timed out.");
							if (requester != null) requester.sendMessage("§cTPAHERE: §4The request timed out.");

							Store.tpacooldown.remove(requester.getName());
							Store.tpahere.remove(instance);
						}
					}
				}, time * 20L);
			}
		}
	}

	public Player getRequester() {
		return requester;
	}

	public Player getRequestee() {
		return requestee;
	}

	public static boolean hasRequest(String i) {
		for (TpahereRequest request : Store.tpahere) {
			if (request.getRequestee().getName().equalsIgnoreCase(i)) return true;
		}
		return false;
	}

	public static boolean hasRequested(String i) {
		for (TpahereRequest request : Store.tpahere) {
			if (request.getRequester().getName().equalsIgnoreCase(i)) return true;
		}
		return false;
	}

	public static TpahereRequest getCurrentRequested(String i) {
		for (TpahereRequest request : Store.tpahere) {
			if (request.getRequestee().getName().equalsIgnoreCase(i)) return request;
		}
		return null;
	}
}