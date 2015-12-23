/**
 *  Util.java
 *  BottomLine
 *
 *  Created by Banjo226 on 31 Oct 2015 at 5:03 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;

public class Util {

	public static String colour(String colour) {
		return ChatColor.translateAlternateColorCodes('&', colour).replaceAll("(&([a-f0-9]))", "§").replaceAll("&l", "§l").replaceAll("&o", "§o").replaceAll("&k", "§k").replaceAll("&n", "§n").replaceAll("&m", "§m").replaceAll("&r", "§r");
	}

	public static String removeColour(String colour) {
		return colour.replaceAll("(§([a-f0-9]))", "").replaceAll("§l", "").replaceAll("§o", "").replaceAll("§k", "").replaceAll("§n", "").replaceAll("§m", "").replaceAll("§r", "");
	}

	public static String parseColours(String colour) {
		return ChatColor.translateAlternateColorCodes('&', colour);
	}

	public static String parseFormat(String format) {
		return format.replaceAll("&l", "§l").replaceAll("&o", "§o").replaceAll("&n", "§n").replaceAll("&m", "§m").replaceAll("&r", "§r");
	}

	public static String parseMagic(String magic) {
		return magic.replaceAll("&k", "§k");
	}

	public static void offline(CommandSender sender, String pre, String args) {
		sender.sendMessage("§c" + pre + ": §4The player §o(" + args + ") §4is currently offline.");
	}

	public static void invalidArgCount(CommandSender sender, String pre, String desc, String... syntax) {
		sender.sendMessage("§c" + pre + ": §4Invalid argument count!");
		sender.sendMessage("§4" + desc + "§4 Syntax:");
		for (String synt : syntax) {
			sender.sendMessage("§c-        §4" + synt);
		}
	}

	public static void invalidTimestamp(CommandSender sender, String pre, String args) {
		sender.sendMessage("§c" + pre + ": §4Invalid timestamp §o(" + args + ")§4! Example: 14m");
	}

	public static void punishOps(CommandSender sender, String pre) {
		sender.sendMessage("§c" + pre + ": §4You cannot try to " + pre.toLowerCase() + " ops!");
	}

	public static void neverJoined(CommandSender sender, String pre, String args) {
		sender.sendMessage("§c" + pre + ": §4The player §o(" + args + ") §4has not joined before!");
	}

	public static void sendActionBar(Player player, String message) {
		try {
			Class<?> cp = Class.forName("org.bukkit.craftbukkit." + BottomLine.getInstance().servervs + ".entity.CraftPlayer");
			Object p = cp.cast(player);
			Object ppoc = null;
			Class<?> packetChat = Class.forName("net.minecraft.server." + BottomLine.getInstance().servervs + ".PacketPlayOutChat");
			Class<?> packet = Class.forName("net.minecraft.server." + BottomLine.getInstance().servervs + ".Packet");
			if (BottomLine.getInstance().servervs.equalsIgnoreCase("v1_8_R1") || !BottomLine.getInstance().servervs.startsWith("v1_8_")) {
				Class<?> serializer = Class.forName("net.minecraft.server." + BottomLine.getInstance().servervs + ".ChatSerializer");
				Class<?> component = Class.forName("net.minecraft.server." + BottomLine.getInstance().servervs + ".IChatBaseComponent");
				Method a = serializer.getDeclaredMethod("a", new Class<?>[] { String.class });
				Object cbc = component.cast(a.invoke(serializer, "{\"text\": \"" + message + "\"}"));
				ppoc = packetChat.getConstructor(new Class<?>[] { component, byte.class }).newInstance(new Object[] { cbc, (byte) 2 });
			} else {
				Class<?> textComponent = Class.forName("net.minecraft.server." + BottomLine.getInstance().servervs + ".ChatComponentText");
				Class<?> baseComponent = Class.forName("net.minecraft.server." + BottomLine.getInstance().servervs + ".IChatBaseComponent");
				Object o = textComponent.getConstructor(new Class<?>[] { String.class }).newInstance(new Object[] { message });
				ppoc = packetChat.getConstructor(new Class<?>[] { baseComponent, byte.class }).newInstance(new Object[] { o, (byte) 2 });
			}
			Method m1 = cp.getDeclaredMethod("getHandle", new Class<?>[] {});
			Object h = m1.invoke(p);
			Field con = h.getClass().getDeclaredField("playerConnection");
			Object pc = con.get(h);
			Method m5 = pc.getClass().getDeclaredMethod("sendPacket", new Class<?>[] { packet });
			m5.invoke(pc, ppoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void playSound(Player player) {
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
	}

	public static String timeFromSeconds(long ms) {
		StringBuilder sb = new StringBuilder(40);

		if (ms / 628992000 > 0) {
			long years = ms / 628992000;
			sb.append(years + (years == 1 ? " year " : " years "));
			ms -= years * 628992000;
		}

		if (ms / 2620800 > 0) {
			long months = ms / 2620800;
			sb.append(months + (months == 1 ? " month " : " months "));
			ms -= months * 2620800;
		}

		if (ms / 604800 > 0) {
			long weeks = ms / 604800;
			sb.append(weeks + (weeks == 1 ? " week " : " weeks "));
			ms -= weeks * 604800;
		}

		if (ms / 86400 > 0) {
			long days = ms / 86400;
			sb.append(days + (days == 1 ? " day " : " days "));
			ms -= days * 86400;
		}

		if (ms / 3600 > 0) {
			long hours = ms / 3600;
			sb.append(hours + (hours == 1 ? " hour " : " hours "));
			ms -= hours * 3600;
		}

		if (ms / 60 > 0) {
			long minutes = ms / 60;
			sb.append(minutes + (minutes == 1 ? " minute " : " minutes "));
			ms -= minutes * 60;
		}

		if (ms > 0) {
			sb.append(ms + " seconds ");
		}

		if (sb.length() > 1) {
			sb.replace(sb.length() - 1, sb.length(), "");
		} else {
			sb = new StringBuilder("null");
		}

		return sb.toString();
	}

	public static String timeFromMs(long ms) {
		ms = (long) Math.ceil(ms / 1000.0);
		StringBuilder sb = new StringBuilder(40);

		if (ms / 31536000 > 0) {
			long years = ms / 31449600;
			sb.append(years + (years == 1 ? " year " : " years "));
			ms -= years * 31449600;
		}

		if (ms / 2620800 > 0) {
			long months = ms / 2620800;
			sb.append(months + (months == 1 ? " month " : " months "));
			ms -= months * 2620800;
		}

		if (ms / 604800 > 0) {
			long weeks = ms / 604800;
			sb.append(weeks + (weeks == 1 ? " week " : " weeks "));
			ms -= weeks * 604800;
		}

		if (ms / 86400 > 0) {
			long days = ms / 86400;
			sb.append(days + (days == 1 ? " day " : " days "));
			ms -= days * 86400;
		}

		if (ms / 3600 > 0) {
			long hours = ms / 3600;
			sb.append(hours + (hours == 1 ? " hour " : " hours "));
			ms -= hours * 3600;
		}

		if (ms / 60 > 0) {
			long minutes = ms / 60;
			sb.append(minutes + (minutes == 1 ? " minute " : " minutes "));
			ms -= minutes * 60;
		}

		if (ms > 0) {
			sb.append(ms + " seconds ");
		}

		if (sb.length() > 1) {
			sb.replace(sb.length() - 1, sb.length(), "");
		} else {
			sb = new StringBuilder("null");
		}
		return sb.toString();
	}

	public static String getWorldTime(World w) {
		long gameTime = w.getTime(), hours = gameTime / 1000 + 6, minutes = (gameTime % 1000) * 60 / 1000;
		String ap = "AM";

		if (hours >= 12) {
			hours -= 12;
			ap = "PM";
		}

		if (hours >= 12) {
			hours -= 12;
			ap = "AM";
		}
		if (hours == 0) hours = 12;

		String mm = "0" + minutes;
		mm = mm.substring(mm.length() - 2, mm.length());

		return hours + ":" + mm + " " + ap;
	}
}
