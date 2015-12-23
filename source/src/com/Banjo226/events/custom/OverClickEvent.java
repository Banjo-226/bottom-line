package com.Banjo226.events.custom;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.law.history.Types;

public class OverClickEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final BottomLine pl = BottomLine.getInstance();

	private Player p;
	private PlayerData pd;
	private List<String> commands;

	private int clicks;
	private boolean kicked;

	public OverClickEvent(Player p, int clicks) {
		this.p = p;
		this.clicks = clicks;
		kicked = pl.getConfig().getBoolean("law.cps.kick");
		commands = pl.getConfig().getStringList("law.cps.commands");
		pd = new PlayerData(p.getName(), false);
	}

	public Player getPlayer() {
		return p;
	}

	public int getClickAmount() {
		return clicks;
	}

	public void setKicked(boolean b, String msg) {
		kicked = b;
		p.kickPlayer(Util.colour(msg));
		pd.addHistory(Types.KICK, "Over CPS limit", "&cConsole", null, null);
	}

	public void runCommands() {
		PlayerData pd = new PlayerData(p.getName(), false);
		for (int i = 0; i < commands.size(); i++) {
			String command = commands.get(i).replaceAll("%player%", pd.getDisplayName()).replaceAll("%byclicks%", String.valueOf(getClickAmount() - pl.getConfig().getInt("law.cps.max-clicks")));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
		}
	}

	public boolean isKicked() {
		return kicked;
	}

	public boolean getKicked() {
		return pl.getConfig().getBoolean("law.cps.kickPlayer");
	}

	public List<String> getCommands() {
		return commands;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
