package com.Banjo226.events.block;

import static com.Banjo226.util.Store.clickCount;
import static com.Banjo226.util.Store.clickRate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClicksPerSecond implements Runnable {

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (clickCount.containsKey(p.getName())) {
				double count = (double) clickCount.get(p.getName());

				double rate = count / 20;

				clickRate.put(p.getName(), rate);
				clickCount.put(p.getName(), 0);
			}
		}
	}
}