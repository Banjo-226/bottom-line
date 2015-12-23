package com.Banjo226.events.block;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.Banjo226.BottomLine;
import com.Banjo226.events.custom.OverClickEvent;
import com.Banjo226.util.Store;

import com.Banjo226.commands.Permissions;

public class ClicksPerSecondListener implements Listener {
	BottomLine pl = BottomLine.getInstance();

	@EventHandler
	public void onClickEvent(final PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
			int count = 1;

			if (Store.clickCount.get(e.getPlayer().getName()) != null) {
				count = count + Store.clickCount.get(e.getPlayer().getName());
			}

			Store.clickCount.put(e.getPlayer().getName(), count);

			final int call = count;

			if (count >= pl.getConfig().getInt("law.cps.max-clicks") && !e.getPlayer().hasPermission(Permissions.CPS_EXCEPTION)) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
					public void run() {
						Bukkit.getPluginManager().callEvent(new OverClickEvent(e.getPlayer(), call));
					}
				}, 40L);
			}
		}
	}

	@EventHandler
	public void onCpsWarning(OverClickEvent e) {
		if (e.getKicked() == true) {
			e.setKicked(true, pl.getConfig().getString("law.cps.kickMessage").replaceAll("%max%", String.valueOf(pl.getConfig().getInt("law.cps.max-clicks"))).replaceAll("%clicks%", String.valueOf(e.getClickAmount())));
			e.runCommands();
			e.getPlayer().setHealth(e.getPlayer().getHealth() - pl.getConfig().getDouble("law.cps.takeHealth"));
		} else {
			e.runCommands();
			e.getPlayer().setHealth(e.getPlayer().getHealth() - pl.getConfig().getDouble("law.cps.takeHealth"));
		}
	}
}