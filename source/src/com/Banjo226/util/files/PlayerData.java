/**
 *  PlayerData.java
 *  BottomLine
 *
 *  Created by Banjo226 on 8 Nov 2015 at 12:23 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.util.files;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Util;

import com.Banjo226.commands.law.history.Types;

public class PlayerData {

	UUID uuid;
	public File file;
	public static File datafolder;
	FileConfiguration conf;
	DecimalFormat form = new DecimalFormat("#.00");

	public List<String> mutes;
	public List<String> jails;
	public List<String> bans;
	public List<String> kicks;
	public List<String> freezes;
	public int historySize;

	public PlayerData(UUID name) {
		this(name, true);
	}

	public PlayerData(UUID uuid, boolean create) {
		this.uuid = uuid;

		datafolder = new File(BottomLine.getInstance().getDataFolder() + File.separator + "userdata");
		if (!datafolder.exists()) {
			datafolder.mkdirs();
		}

		file = new File(datafolder.getPath() + File.separator + uuid.toString() + ".yml");
		if (!dataExists(file) && create == true) {
			createData();
		}

		conf = YamlConfiguration.loadConfiguration(file);
		mutes = conf.getStringList("history.mutes");
		jails = conf.getStringList("history.jails");
		kicks = conf.getStringList("history.kicks");
		bans = conf.getStringList("history.bans");
		freezes = conf.getStringList("history.freezes");

		historySize = (mutes.size() + jails.size()) + (kicks.size() + bans.size() + freezes.size());
	}

	/** @deprecated */
	public void createData() {
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean dataExists(UUID uuid) {
		File file = new File(datafolder.getPath() + File.separator + uuid.toString() + ".yml");
		return dataExists(file);
	}

	public boolean dataExists(File file) {
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public void setDisplayName(String name) {
		conf.set("displayname", name);
		saveConfig();
	}

	public void setDefaultName(String name) {
		conf.set("name", name);
		saveConfig();
	}

	public void setUUID(UUID uuid) {
		conf.set("uuid", uuid.toString());
		saveConfig();
	}

	public void setIPAddress(Player player) {
		conf.set("ip", player.getAddress().toString().replace("/", "").substring(0).split(":")[0]);
		saveConfig();
	}

	public void setOpStatus(boolean op) {
		conf.set("op", Boolean.valueOf(op));

		saveConfig();
	}

	public void setLocation(Location loc) {
		conf.set("location.x", form.format(loc.getX()));
		conf.set("location.y", form.format(loc.getY()));
		conf.set("location.z", form.format(loc.getZ()));
		conf.set("location.yaw", form.format(loc.getYaw()));
		conf.set("location.pitch", form.format(loc.getPitch()));
		conf.set("location.world", loc.getWorld().getName());

		saveConfig();
	}
	
	public void setBackLocation(Location loc) {
		conf.set("location.back.x", form.format(loc.getX()));
		conf.set("location.back.y", form.format(loc.getY()));
		conf.set("location.back.z", form.format(loc.getZ()));
		conf.set("location.back.yaw", form.format(loc.getYaw()));
		conf.set("location.back.pitch", form.format(loc.getPitch()));
		conf.set("location.back.world", loc.getWorld().getName());

		saveConfig();
	}

	public void setLastTimeConnected(SimpleDateFormat sdf) {
		Date date = new Date();

		conf.set("lastLeft", "§c" + sdf.format(date));
		saveConfig();
	}

	public void setLastTimeConnected(String custTime) {
		conf.set("lastLeft", custTime);
		saveConfig();
	}

	public void addHistory(Types category, String message, String punisher, String time, String timestamp) {
		if (category.equals(Types.MUTE)) {
			mutes.add("§8[§e" + time + "§8] §6" + message + "§e, Punisher: §6" + punisher + "§e, for §6" + timestamp);
			conf.set("history." + category.toString(), mutes);
		} else if (category.equals(Types.JAIL)) {
			jails.add("§8[§e" + time + "§8] §6" + message + "§e, Punisher: §6" + punisher + "§e, for §6" + timestamp);
			conf.set("history." + category.toString(), jails);
		} else if (category.equals(Types.KICK)) {
			kicks.add("§8[§e" + time + "§8] §6" + message + "§e, Punisher: §6" + punisher);
			conf.set("history." + category.toString(), kicks);
		} else if (category.equals(Types.BAN)) {
			bans.add("§8[§e" + time + "§8] §6" + message + "§e, Punisher: §6" + punisher);
			conf.set("history." + category.toString(), bans);
		} else if (category.equals(Types.FREEZE)) {
			freezes.add("§8[§e" + time + "§8] §6" + message + "§e, Punisher: §6" + punisher + "§e, for §6" + timestamp);
			conf.set("history." + category.toString(), freezes);
		} else if (category.equals(Types.TEMPBAN)) {
			bans.add("§8[§e" + time + "§8] §6" + message + "§e, Punisher: §6" + punisher + "§e, for §6" + timestamp);
			conf.set("history.bans", bans);
		}

		saveConfig();
	}

	public void setNick(String nick) {
		conf.set("nickname", nick);
		saveConfig();
	}

	@SuppressWarnings("deprecation")
	public void setBanned(Player target, boolean b, String reason, PlayerData pd) {
		target.setBanned(b);
		target.kickPlayer(Util.colour(BottomLine.getInstance().getConfig().getString("law.ban.kickMessage").replaceAll("%sender%", pd.getDisplayName()).replaceAll("%message%", reason)));

		conf.set("banned.banned", Boolean.valueOf(b));
		conf.set("banned.reason", reason);
		conf.set("banned.sender", pd.getDisplayName());
		saveConfig();
	}

	@SuppressWarnings("deprecation")
	public void setOfflineBanned(OfflinePlayer target, boolean b, String reason, PlayerData pd) {
		target.setBanned(b);

		if (b == true) {
			conf.set("banned.banned", Boolean.valueOf(b));
			conf.set("banned.reason", reason);
			conf.set("banned.sender", pd.getDisplayName());
		} else {
			conf.set("banned", null);
			conf.set("banned.banned", null);
			conf.set("banned.reason", null);
			conf.set("banned.sender", null);
		}

		saveConfig();
	}

	@SuppressWarnings("deprecation")
	public void setTempBanned(Player target, boolean b, String reason, PlayerData pd, long kicktime, long end) {
		target.setBanned(b);

		if (b == true) {
			target.kickPlayer(Util.colour(BottomLine.getInstance().getConfig().getString("law.ban.tempBanKick").replaceAll("%sender%", pd.getDisplayName()).replaceAll("%message%", reason).replaceAll("%time%", Util.timeFromMs(end - System.currentTimeMillis()))));

			conf.set("banned.temp.banned", Boolean.valueOf(b));
			conf.set("banned.temp.reason", reason);
			conf.set("banned.temp.end", end);
			conf.set("banned.temp.sender", pd.getDisplayName());
		} else {
			conf.set("banned.temp", null);
		}

		saveConfig();
	}

	@SuppressWarnings("deprecation")
	public void setOfflineTempBanned(OfflinePlayer target, boolean b, String reason, PlayerData pd, long now, long end) {
		target.setBanned(b);

		if (b == true) {
			conf.set("banned.temp.banned", Boolean.valueOf(b));
			conf.set("banned.temp.reason", reason);
			conf.set("banned.temp.end", end);
			conf.set("banned.temp.sender", pd.getDisplayName());
		} else {
			conf.set("banned.temp", null);
		}

		saveConfig();
	}

	public void updateBanTime(long now) {
		conf.set("banned.temp.now", now);
		saveConfig();
	}

	public void setPowertool(int id, String name) {
		conf.set("powertool." + id, name);
		saveConfig();
	}

	public void removePowertools() {
		conf.set("powertool", null);
		saveConfig();
	}

	public void set(String loc, Object value) {
		conf.set(loc, value);
		saveConfig();
	}

	public Object get(String loc) {
		return conf.get(loc);
	}

	public void clearHistory() {
		mutes.clear();
		jails.clear();
		kicks.clear();
		bans.clear();
		freezes.clear();

		conf.set("history.mutes", mutes);
		conf.set("history.jails", jails);
		conf.set("history.kicks", kicks);
		conf.set("history.bans", bans);
		conf.set("history.freezes", freezes);

		saveConfig();
	}

	public void setHome(Player player) {
		conf.set("home.x", player.getLocation().getX());
		conf.set("home.y", player.getLocation().getY());
		conf.set("home.z", player.getLocation().getZ());
		conf.set("home.yaw", player.getLocation().getYaw());
		conf.set("home.pitch", player.getLocation().getPitch());
		conf.set("home.world", player.getWorld().getName());
		saveConfig();
	}

	public void delHome() {
		conf.set("home", null);
		saveConfig();
	}

	public String getDisplayName() {
		return Util.colour(conf.getString("displayname"));
	}

	public String getUUID() {
		return conf.getString("uuid");
	}

	public String getIP() {
		return conf.getString("ip");
	}

	public Boolean getOpStatus() {
		return conf.getBoolean("op");
	}

	public String getX() {
		return conf.getString("location.x");
	}

	public String getY() {
		return conf.getString("location.y");
	}

	public String getZ() {
		return conf.getString("location.z");
	}

	public String getYaw() {
		return conf.getString("location.yaw");
	}

	public String getPitch() {
		return conf.getString("location.pitch");
	}

	public World getWorld() {
		return Bukkit.getWorld(conf.getString("location.world"));
	}

	public Location getLocation() {
		return new Location(getWorld(), Double.parseDouble(getX()), Double.parseDouble(getY()), Double.parseDouble(getZ()), Float.parseFloat(getYaw()), Float.parseFloat(getYaw()));
	}
	
	public Location getBackLocation() {
		World world = Bukkit.getWorld(conf.getString("location.back.world"));
		String x = conf.getString("location.back.x");
		String y = conf.getString("location.back.y");
		String z = conf.getString("location.back.z");
		String yaw = conf.getString("location.back.yaw");
		String pitch = conf.getString("location.back.pitch");
		
		return new Location(world, Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), Float.parseFloat(yaw), Float.parseFloat(pitch));
	}

	public String getLastTimeConnected() {
		return conf.getString("lastLeft");
	}

	public int getOldNames() {
		return conf.getStringList("history.oldnames").size();
	}

	public String getDefaultName() {
		return conf.getString("name");
	}

	public Boolean isBanned() {
		return conf.getBoolean("banned.banned");
	}

	public Boolean isTempBanned() {
		return conf.getBoolean("banned.temp.banned");
	}

	public String getBanPunisher() {
		if (isTempBanned()) return conf.getString("banned.temp.sender");

		return conf.getString("banned.sender");
	}

	public String getBanMessage() {
		if (isTempBanned()) return conf.getString("banned.temp.reason");

		return conf.getString("banned.reason");
	}

	public String getNick() {
		try {
			return Util.colour(conf.getString("nickname"));
		} catch (NullPointerException e) {
			return getDefaultName();
		}
	}

	public String getCommandOnPowertool(int id) throws NullPointerException {
		return conf.getString("powertool." + id);
	}

	public int getItemId(int id) throws NullPointerException {
		return (conf.contains("powertool." + id)) ? conf.getInt("powertool.") + id : 0;
	}

	@Deprecated
	public int getItemIdi(int id) throws NullPointerException {
		return conf.getInt("powertool.") + id;
	}

	public long getTimeUtilUnban() {
		return (conf.getLong("banned.temp.end") / 1000) - (System.currentTimeMillis() / 1000);
	}

	public Location getHome() {
		World w = Bukkit.getWorld(conf.getString("home.world"));
		double x, y, z;
		float yaw, pitch;

		x = conf.getDouble("home.x");
		y = conf.getDouble("home.y");
		z = conf.getDouble("home.z");
		yaw = (float) conf.getDouble("home.yaw");
		pitch = (float) conf.getDouble("home.pitch");
		return new Location(w, x, y, z, yaw, pitch);
	}

	public static File[] getFiles() {
		return datafolder.listFiles();
	}

	public FileConfiguration getConfig() {
		return conf;
	}

	public void saveConfig() {
		try {
			conf.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}