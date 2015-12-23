package com.Banjo226.events.chat;

import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.Banjo226.BottomLine;

public abstract class BottomLineChat implements Listener {
	BottomLineChat blc;
	BottomLine bl = BottomLine.getInstance();

	public BottomLineChat() {
		blc = this;
	}

	public abstract void onPlayerChat(AsyncPlayerChatEvent e);

	public boolean cancelled(final AsyncPlayerChatEvent e) {
		if (e.isCancelled()) {
			return true;
		} else {
			return false;
		}
	}

	public BottomLine getPlugin() {
		return bl;
	}
}