/**
 *  Permissions.java
 *  BottomLine
 *
 *  Created by Banjo226 on 31 Oct 2015 at 7:37 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.commands;

import org.bukkit.permissions.Permission;

public class Permissions {
	// This class is to handle the permissions.
	// Command permissions
	public static final Permission RULES = new Permission("bottomline.rules");
	public static final Permission BROADCAST = new Permission("bottomline.broadcast");
	public static final Permission MOTD = new Permission("bottomline.motd");
	public static final Permission ADMINCHAT = new Permission("bottomline.adminchat");
	public static final Permission FLY = new Permission("bottonline.fly");
	public static final Permission TELEPORT = new Permission("bottomline.teleport");
	public static final Permission GOD = new Permission("bottomline.god");
	public static final Permission TPA = new Permission("bottomline.tpa");
	public static final Permission TPAHERE = new Permission("bottomline.tpahere");
	public static final Permission TPPOS = new Permission("bottomline.tppos");
	public static final Permission BALANCE = new Permission("bottomline.balance");
	public static final Permission PAY = new Permission("bottomline.pay");
	public static final Permission MSG = new Permission("bottomline.message");
	public static final Permission BREAK = new Permission("bottomline.break");
	public static final Permission AFK = new Permission("bottomline.afk");
	public static final Permission PING = new Permission("bottomline.ping");
	public static final Permission FEED = new Permission("bottomline.feed");
	public static final Permission HEAL = new Permission("bottomline.heal");
	public static final Permission HISTORY = new Permission("bottomline.history");
	public static final Permission MUTE = new Permission("bottomline.mute");
	public static final Permission JAIL = new Permission("bottomline.jail");
	public static final Permission KICK = new Permission("bottomline.kick");
	public static final Permission FREEZE = new Permission("bottomline.freeze");
	public static final Permission CORE = new Permission("bottomline.core");
	public static final Permission BAN = new Permission("bottomline.ban");
	public static final Permission UNBAN = new Permission("bottomline.ban.unban");
	public static final Permission WARP = new Permission("bottomline.warp");
	public static final Permission INVENTORY = new Permission("bottomline.inventory");
	public static final Permission ITEM = new Permission("bottomline.item");
	public static final Permission BALTOP = new Permission("bottomline.balance.top");
	public static final Permission NICKNAME = new Permission("bottomline.nick");
	public static final Permission STRIKE = new Permission("bottomline.strike");
	public static final Permission CMDSPY = new Permission("bottomline.cmdspy");
	public static final Permission LIST = new Permission("bottomline.list");
	public static final Permission POWERTOOL = new Permission("bottomline.powertool");
	public static final Permission TEMPBAN = new Permission("bottomline.ban.temp");
	public static final Permission SPAWN = new Permission("bottomline.spawn");
	public static final Permission SETSPAWN = new Permission("bottomline.spawn.set");
	public static final Permission REMOVESPAWN = new Permission("bottomline.spawn.remove");
	public static final Permission GAMEMODE = new Permission("bottomline.gamemode");
	public static final Permission HOME = new Permission("bottomline.home");
	public static final Permission REALNAME = new Permission("bottomline.realname");
	public static final Permission KIT = new Permission("bottomline.kit");
	public static final Permission FORCETP = new Permission("bottomline.teleport.force");
	public static final Permission BURN = new Permission("bottomline.burn");
	public static final Permission EXTINGUISH = new Permission("bottomline.extinguish");
	public static final Permission TIME = new Permission("bottomline.time");
	public static final Permission HAT = new Permission("bottomline.hat");
	public static final Permission CLEARCHAT = new Permission("bottomline.clearchat");

	// Others-sub permissions [--> toggle for other people]
	public static final Permission FLY_OTHERS = new Permission("bottomline.fly.others");
	public static final Permission TELEPORT_OTHERS = new Permission("bottomline.teleport.others");
	public static final Permission TPHERE = new Permission("bottomline.teleport.here");
	public static final Permission GOD_OTHERS = new Permission("bottomline.god.others");
	public static final Permission TPPOS_OTHERS = new Permission("bottomline.tppos.others");
	public static final Permission BALANCE_OTHERS = new Permission("bottomline.balance.others");
	public static final Permission ECONOMY_OTHERS = new Permission("bottomline.balance.modify.others");
	public static final Permission TELEPORT_TOGGLE_OTHERS = new Permission("bottomline.teleport.toggle.others");
	public static final Permission PING_OTHERS = new Permission("bottomline.ping.others");
	public static final Permission AFK_OTHERS = new Permission("bottomline.afk.others");
	public static final Permission FEED_OTHERS = new Permission("bottomline.feed.others");
	public static final Permission HEAL_OTHERS = new Permission("bottomline.heal.others");
	public static final Permission CLEAR_OTHERS = new Permission("bottomline.inventory.clear.others");
	public static final Permission NICKNAME_OTHERS = new Permission("bottomline.nick.others");
	public static final Permission CMDSPY_OTHERS = new Permission("bottomline.cmdspy.others");
	public static final Permission GAMEMODE_OTHERS = new Permission("bottomline.gamemode.others");
	public static final Permission WHOIS = new Permission("bottomline.whois");

	// Misc sub permissions [--> like chat toggles etc.]
	public static final Permission ADMINCHAT_TOGGLE = new Permission("bottomline.adminchat.toggle");
	public static final Permission ECONOMY = new Permission("bottomline.balance.modify");
	public static final Permission MSG_COLOUR = new Permission("bottomline.message.colour");
	public static final Permission MSG_FORMAT = new Permission("bottomline.message.format");
	public static final Permission MSG_MAGIC = new Permission("bottomline.message.magic");
	public static final Permission TELEPORT_HERE = new Permission("bottomline.teleport.here");
	public static final Permission TELEPORT_TOGGLE = new Permission("bottomline.teleport.toggle");
	public static final Permission REPLY = new Permission("bottomline.message.reply");
	public static final Permission JAIL_MODIFY = new Permission("bottomline.jail.modify");
	public static final Permission JAIL_OTHER = new Permission("bottomline.jail.util");
	public static final Permission RELOADCONFIG = new Permission("bottomline.core.reload");
	public static final Permission STATUS = new Permission("bottomline.core.status");
	public static final Permission WARP_LIST = new Permission("bottomline.warp.list");
	public static final Permission WARP_UTIL = new Permission("bottomline.warp.util");
	public static final Permission WARP_REMOVE = new Permission("bottomline.warp.remove");
	public static final Permission WARP_CREATE = new Permission("bottomline.warp.create");
	public static final Permission CREATEOVERWRITE = new Permission("bottomline.warp.create.overwrite");
	public static final Permission OPENINV = new Permission("bottomline.inventory.open");
	public static final Permission OPENINV_MODIFY = new Permission("bottomline.inventory.open.modify");
	public static final Permission HASITEM = new Permission("bottomline.inventory.hasitem");
	public static final Permission CLEAR = new Permission("bottomline.inventory.clear");
	public static final Permission SURVIVAL = new Permission("bottomline.gamemode.survival");
	public static final Permission CREATIVE = new Permission("bottomline.gamemode.creative");
	public static final Permission ADVENTURE = new Permission("bottomline.gamemode.adventure");
	public static final Permission COSTOVERRIDE = new Permission("bottomline.kit.costoverride");
	public static final Permission ALLKITS = new Permission("bottomline.kit.*");
	public static final Permission SHOWKITS = new Permission("bottomline.kit.showkit");

	// Event permissions
	public static final Permission GRAMMAR_OVERRIDE = new Permission("bottomline.chat.gramamr");
	public static final Permission EXCEPTION = new Permission("bottomline.chat.exception");
	public static final Permission CHATCOLOURS = new Permission("bottomline.chat.colour");
	public static final Permission CHATFORMAT = new Permission("bottomline.chat.format");
	public static final Permission CHATMAGIC = new Permission("bottomline.chat.magic");
	public static final Permission PLUGINS = new Permission("bottomline.plugins");
	public static final Permission VERSION = new Permission("bottomline.version");
	public static final Permission CPS_EXCEPTION = new Permission("bottomline.cpsexception");

	// Sign permissions
	public static final Permission MODIFY_FEED_SIGN = new Permission("bottomline.signs.modify.feed");
	public static final Permission MODIFY_HEAL_SIGN = new Permission("bottomline.signs.modify.heal");
	public static final Permission SIGNCOLOURS = new Permission("bottomline.signs.colour");
	public static final Permission SIGNFORMAT = new Permission("bottomline.signs.format");
	public static final Permission SIGNMAGIC = new Permission("bottomline.signs.magic");
	public static final Permission MODIFY_KIT_SIGN = new Permission("bottomline.signs.modify.kit");
}