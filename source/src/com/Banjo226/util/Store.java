/**
 *  Store.java
 *  BottomLine
 *
 *  Created by Banjo226 on 31 Oct 2015 at 5:02 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.event.Listener;

import com.Banjo226.BottomLine;
import com.Banjo226.util.files.Data;

import com.Banjo226.commands.teleportation.request.Request;
import com.Banjo226.commands.teleportation.request.TpahereRequest;

public class Store implements Listener {

	public static List<String> atoggle = Data.getInstance().getConfig().getStringList("adminchat.toggle");
	public static List<String> god = Data.getInstance().getConfig().getStringList("god.toggle");
	public static List<String> tptoggle = Data.getInstance().getConfig().getStringList("tp.toggle");
	public static List<String> cmdspy = Data.getInstance().getConfig().getStringList("spy.cmd.toggle");

	public static HashMap<String, Long> cooldown = new HashMap<>();
	public static HashMap<String, Long> clearchatcooldown = new HashMap<>();
	public static HashMap<String, Long> tpacooldown = new HashMap<>();
	public static HashMap<String, String> reply = new HashMap<>();
	public static HashMap<String, String> spam = new HashMap<>();

	public static HashMap<String, Integer> clickCount = new HashMap<>();
	public static HashMap<String, Double> clickRate = new HashMap<>();

	public static ArrayList<Request> tpa = new ArrayList<>();
	public static ArrayList<TpahereRequest> tpahere = new ArrayList<>();
	public static ArrayList<String> away = new ArrayList<>();
	public static ArrayList<String> muted = new ArrayList<>();
	public static ArrayList<String> jailed = new ArrayList<>();
	public static ArrayList<String> freeze = new ArrayList<>();

//	public static List<String> rules = BottomLine.getInstance().getConfig().getStringList("rules");
//	public static List<String> motd = BottomLine.getInstance().getConfig().getStringList("motd.message");
	
	public static List<String> breakbl = BottomLine.getInstance().getConfig().getStringList("block.blocks");
	public static List<String> newbiesKit = BottomLine.getInstance().getConfig().getStringList("newbies.kit.items");
	public static List<String> weatherChange = BottomLine.getInstance().getConfig().getStringList("weatherchange.worlds");
	public static List<String> muteBlocked = BottomLine.getInstance().getConfig().getStringList("law.mute.commands");
	public static List<String> jailBlocked = BottomLine.getInstance().getConfig().getStringList("law.jail.commands");
	public static List<String> swearlist = BottomLine.getInstance().getConfig().getStringList("law.block.list");
	public static List<String> kickBanned = BottomLine.getInstance().getConfig().getStringList("law.ban.tryToJoinKick");
	public static List<String> cmdBlacklist = BottomLine.getInstance().getConfig().getStringList("cmdspy.blacklist");
	public static List<String> tempBanMessage = BottomLine.getInstance().getConfig().getStringList("law.ban.tempBanJoinKick");
	public static List<String> disabledHats = BottomLine.getInstance().getConfig().getStringList("disabled-hats");

}