/**
 *  Warps.java
 *  BottomLine
 *
 *  Created by Banjo226 on 15 Nov 2015 at 5:11 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.util.files;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.Banjo226.BottomLine;

public class Warps {
	static Warps instance = new Warps();

	public static Warps getInstance() {
		return instance;
	}

	FileConfiguration conf;
	File file;

	public List<String> warps;

	public void setup(Plugin pl) {
		file = new File(pl.getDataFolder(), "warps.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		conf = YamlConfiguration.loadConfiguration(file);
		warps = conf.getStringList("warplist");
		conf.set("warplist", warps);
	}

	public FileConfiguration getConfig() {
		return conf;
	}

	public String getName() {
		return file.getName();
	}

	public void setWarp(String warp, World world, double x, double y, double z, float yaw, float pitch) {
		conf.set("warps." + warp + ".world", world.getName());
		conf.set("warps." + warp + ".x", x);
		conf.set("warps." + warp + ".y", y);
		conf.set("warps." + warp + ".z", z);
		conf.set("warps." + warp + ".yaw", yaw);
		conf.set("warps." + warp + ".pitch", pitch);

		warps.add(warp);
		conf.set("warplist", warps);
		saveConfig();
	}

	public Location getWarp(String warp) {
		World w = Bukkit.getWorld(conf.getString("warps." + warp + ".world"));
		double x = conf.getDouble("warps." + warp + ".x");
		double y = conf.getDouble("warps." + warp + ".y");
		double z = conf.getDouble("warps." + warp + ".z");
		float yaw = (float) conf.getDouble("warps." + warp + ".yaw");
		float pitch = (float) conf.getDouble("warps." + warp + ".pitch");

		return new Location(w, x, y, z, yaw, pitch);
	}

	public void removeWarp(String warp) {
		conf.set("warps." + warp + ".world", null);
		conf.set("warps." + warp + ".x", null);
		conf.set("warps." + warp + ".y", null);
		conf.set("warps." + warp + ".z", null);
		conf.set("warps." + warp + ".yaw", null);
		conf.set("warps." + warp + ".pitch", null);

		warps.remove(warp);
		conf.set("warplist", warp);
		saveConfig();
	}

	public boolean warpExists(String warp) {
		if (conf.getConfigurationSection("warps." + warp) == null) {
			return false;
		} else {
			return true;
		}
	}

	public void saveConfig() {
		try {
			conf.save(file);
		} catch (IOException e) {
			BottomLine.warning("Could not save conf file!");
		}
	}

	public void reloadConfig() {
		conf = YamlConfiguration.loadConfiguration(file);
	}
}