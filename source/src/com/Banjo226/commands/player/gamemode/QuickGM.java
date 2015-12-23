package com.Banjo226.commands.player.gamemode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class QuickGM implements Listener {

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		String[] args = e.getMessage().split(" ");
		Player sender = e.getPlayer();
		String end = e.getMessage().substring(e.getMessage().length() - 1);

		if (e.getMessage().equalsIgnoreCase("/gms") || e.getMessage().equalsIgnoreCase("/gmc") || e.getMessage().equalsIgnoreCase("/gma")) {
			e.setCancelled(true);
			if (args.length == 1) {
				Bukkit.dispatchCommand(sender, "gamemode " + end);
				return;
			}

			Bukkit.dispatchCommand(sender, "gamemode " + end + " " + args[1]);
		}
	}
}