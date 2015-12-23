package com.Banjo226.events.signs;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.commands.Permissions;

public class KitSign implements Listener {

	BottomLine pl = BottomLine.getInstance();

	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		Player player = e.getPlayer();
		if (player.hasPermission(Permissions.MODIFY_KIT_SIGN)) {
			if (pl.getConfig().getBoolean("signs.kits") == true) {
				boolean existing = false;
				String kit = "";
				for (String entry : pl.getConfig().getConfigurationSection("kits").getKeys(false)) {
					if (entry.toLowerCase().startsWith(e.getLine(1).toLowerCase())) {
						existing = true;
						kit = entry;
					}
				}

				if (e.getLine(0).equalsIgnoreCase("[kits]") && e.getLine(1) != null) {
					if (existing) {
						e.setLine(0, "§8[§4Kits§8]");
						e.setLine(1, kit);
					} else {
						e.setLine(0, "§4[§cKits§4]");
						e.setLine(1, "Kit does not exist!");
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR) return;

		if (e.getClickedBlock().getState() instanceof Sign) {
			Sign s = (Sign) e.getClickedBlock().getState();
			if (pl.getConfig().getBoolean("signs.kits") == true) {
				boolean existing = false;
				String kit = "";
				for (String entry : pl.getConfig().getConfigurationSection("kits").getKeys(false)) {
					if (entry.toLowerCase().startsWith(s.getLine(1).toLowerCase())) {
						existing = true;
						kit = entry;
					}
				}

				if (existing) {
					if (s.getLine(0).equalsIgnoreCase("§8[§4Kits§8]")) {
						Bukkit.dispatchCommand(player, "kit " + kit);
					}
				}
			}
		}
	}
}