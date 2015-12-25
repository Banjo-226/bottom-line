package com.Banjo226.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.commands.Permissions;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;

public class ClearChat extends Cmd {
	BottomLine pl = BottomLine.getInstance();

	public ClearChat() {
		super("ClearChat", Permissions.CLEARCHAT);
	}

	public void run(CommandSender sender, String[] args) {
		if (pl.getConfig().getBoolean("clearchat.timeout") == true) {
			if (!sender.isOp()) {
				int cooldownTime = pl.getConfig().getInt("clearchat.timeoutInSeconds");

				if (Store.cooldown.containsKey(sender.getName())) {
					long secondsLeft = ((Store.cooldown.get(sender.getName()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
					if (secondsLeft >= 1) {
						sender.sendMessage("§cOops! §4You can't broadcast for another " + secondsLeft + " seconds!");
						return;
					}
				}
			}
		}

		if (pl.getConfig().getBoolean("clearchat.timeout") == true) {
			if (!sender.isOp()) {
				Store.cooldown.put(sender.getName(), System.currentTimeMillis());
			}
		}
		
		for(int i=0; i < 100; i ++)
		{
		Bukkit.broadcastMessage("");
		}
		String msg = pl.getConfig().getString("clearchat.clearMessage");
		Bukkit.broadcastMessage(Util.colour(pl.getConfig().getString("clearchat.format").replaceAll("%message%", msg)));

		for (Player p : Bukkit.getOnlinePlayers()) {
			Util.sendActionBar(p, Util.colour(pl.getConfig().getString("clearchat.format").replaceAll("%message%", msg)));
		}
	}
}