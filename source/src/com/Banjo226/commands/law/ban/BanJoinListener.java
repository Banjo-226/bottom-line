package com.Banjo226.commands.law.ban;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Store;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class BanJoinListener implements Listener {
	BottomLine pl = BottomLine.getInstance();
	PlayerData pd;

	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e) {
		Player player = e.getPlayer();

		pd = new PlayerData(player.getUniqueId());
		if (pd.isBanned() && player.isBanned()) {
			String message = "";
			if (e.getResult() == Result.KICK_BANNED) {
				for (int i = 0; i < Store.kickBanned.size(); i++) {
					message += Util.colour(Store.kickBanned.get(i)) + "\n";
				}
				e.setKickMessage(message.replaceAll("%sender%", pd.getBanPunisher()).replaceAll("%message%", pd.getBanMessage()));
			}
		}

		if (pd.isTempBanned() && player.isBanned()) {
			String message = "";
			pd.updateBanTime(System.currentTimeMillis());

			if (pd.getTimeUtilUnban() == 0 || pd.getTimeUtilUnban() < 0) {
				pd.setTempBanned(player, false, null, null, 0, 0);
				e.allow();
			}

			if (e.getResult() == Result.KICK_BANNED) {
				for (int i = 0; i < Store.tempBanMessage.size(); i++) {
					message += Util.colour(Store.tempBanMessage.get(i)) + "\n";
				}

				e.setKickMessage(message.replaceAll("%sender%", pd.getBanPunisher()).replaceAll("%message%", pd.getBanMessage()).replaceAll("%time%", Util.timeFromSeconds(pd.getTimeUtilUnban())));
			}
		}
	}
}