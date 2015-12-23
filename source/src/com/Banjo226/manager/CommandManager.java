/**
 *  CommandManager.java
 *  BottomLine
 *
 *  Created by Banjo226 on 26 Nov 2015 at 10:08 am AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.Banjo226.commands.chat.*;
import com.Banjo226.commands.chat.list.MOTD;
import com.Banjo226.commands.chat.list.Rules;
import com.Banjo226.commands.economy.Balance;
import com.Banjo226.commands.economy.Eco;
import com.Banjo226.commands.economy.Pay;
import com.Banjo226.commands.exception.ConsoleSenderException;
import com.Banjo226.commands.inventory.Inventory;
import com.Banjo226.commands.inventory.item.Item;
import com.Banjo226.commands.inventory.kits.Kits;
import com.Banjo226.commands.inventory.kits.ShowKits;
import com.Banjo226.commands.law.*;
import com.Banjo226.commands.law.ban.Ban;
import com.Banjo226.commands.law.ban.TempBan;
import com.Banjo226.commands.law.ban.Unban;
import com.Banjo226.commands.law.history.History;
import com.Banjo226.commands.packets.Ping;
import com.Banjo226.commands.player.*;
import com.Banjo226.commands.player.damagaeble.Burn;
import com.Banjo226.commands.player.damagaeble.Extinguish;
import com.Banjo226.commands.player.damagaeble.Feed;
import com.Banjo226.commands.player.damagaeble.Heal;
import com.Banjo226.commands.player.gamemode.Gamemode;
import com.Banjo226.commands.player.whois.RealName;
import com.Banjo226.commands.player.whois.Whois;
import com.Banjo226.commands.server.Core;
import com.Banjo226.commands.server.List;
import com.Banjo226.commands.teleportation.*;
import com.Banjo226.commands.teleportation.home.Home;
import com.Banjo226.commands.teleportation.request.Tpa;
import com.Banjo226.commands.teleportation.request.Tpahere;
import com.Banjo226.commands.teleportation.spawn.DelSpawn;
import com.Banjo226.commands.teleportation.spawn.SetSpawn;
import com.Banjo226.commands.teleportation.spawn.Spawn;
import com.Banjo226.commands.teleportation.warp.Warp;
import com.Banjo226.commands.teleportation.warp.WarpUtil;
import com.Banjo226.commands.world.Break;
import com.Banjo226.commands.world.Time;

public class CommandManager implements Listener, CommandExecutor, TabCompleter {

	private Map<Cmd, String> cmds = new HashMap<>();

	public CommandManager() {
		cmds.put(new Rules(), "rules");
		cmds.put(new MOTD(), "motd");
		cmds.put(new Broadcast(), "broadcast");
		cmds.put(new AdminChat(), "amsg");
		cmds.put(new Fly(), "fly");
		cmds.put(new Teleport(), "tp");
		cmds.put(new Tphere(), "tphere");
		cmds.put(new God(), "god");
		cmds.put(new Tpa(), "tpa");
		cmds.put(new Tpahere(), "tpahere");
		cmds.put(new Tppos(), "tppos");
		cmds.put(new Balance(), "balance");
		cmds.put(new Eco(), "eco");
		cmds.put(new Pay(), "pay");
		cmds.put(new Message(), "msg");
		cmds.put(new Tptoggle(), "tptoggle");
		cmds.put(new Reply(), "reply");
		cmds.put(new Break(), "break");
		cmds.put(new AFK(), "afk");
		cmds.put(new Ping(), "ping");
		cmds.put(new Feed(), "feed");
		cmds.put(new Heal(), "heal");
		cmds.put(new History(), "history");
		cmds.put(new Mute(), "mute");
		cmds.put(new Jail(), "jail");
		cmds.put(new JailUtil(), "jutil");
		cmds.put(new Enforcer(), "kick");
		cmds.put(new Freeze(), "freeze");
		cmds.put(new Core(), "core");
		cmds.put(new Ban(), "ban");
		cmds.put(new Unban(), "unban");
		cmds.put(new Warp(), "warp");
		cmds.put(new WarpUtil(), "warputil");
		cmds.put(new Inventory(), "inv");
		cmds.put(new Item(), "item");
		cmds.put(new Nick(), "nick");
		cmds.put(new Strike(), "strike");
		cmds.put(new CommandSpy(), "cmdspy");
		cmds.put(new List(), "list");
		cmds.put(new PowerTool(), "powertool");
		cmds.put(new TempBan(), "tempban");
		cmds.put(new SetSpawn(), "setspawn");
		cmds.put(new Spawn(), "spawn");
		cmds.put(new DelSpawn(), "delspawn");
		cmds.put(new Gamemode(), "gamemode");
		cmds.put(new Home(), "home");
		cmds.put(new RealName(), "realname");
		cmds.put(new Kits(), "kit");
		cmds.put(new ShowKits(), "showkit");
		cmds.put(new ForceTp(), "ftp");
		cmds.put(new Whois(), "whois");
		cmds.put(new Burn(), "burn");
		cmds.put(new Extinguish(), "extinguish");
		cmds.put(new Time(), "time");
		cmds.put(new Hat(), "hat");
	}

	public boolean onCommand(CommandSender sender, Command c, String lbl, String[] args) {
		for (Map.Entry<Cmd, String> entry : cmds.entrySet()) {
			Cmd cmd = entry.getKey();
			String name = entry.getValue();

			if (c.getName().equalsIgnoreCase(name)) {
				if (sender.hasPermission(cmd.getPermission())) {
					try {
						cmd.run(sender, args);
					} catch (ConsoleSenderException e) {
						sender.sendMessage(e.getMessage());
					} catch (Exception e) {
						sender.sendMessage("§cException: §4An exception has occured in the command: /" + name + ".");
						if (sender.isOp() && sender instanceof Player) {
							sender.sendMessage("§cException: §4The stacktrace has been sent to console.");
						}

						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}

	public java.util.List<String> onTabComplete(CommandSender sender, Command c, String lbl, String[] args) {
		for (Map.Entry<Cmd, String> entry : cmds.entrySet()) {
			Cmd cmd = entry.getKey();
			String value = entry.getValue();

			if (c.getName().equalsIgnoreCase(value)) {
				if (sender.hasPermission(cmd.getPermission())) {
					return cmd.onTabComplete(sender, c, lbl, args);
				}
			}
		}
		return null;
	}
}