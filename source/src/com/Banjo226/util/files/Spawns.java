/**
 *  Spawns.java
 *  BottomLine
 *
 *  Created by Banjo226 on 6 Dec 2015 at 1:57 pm AEST
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

public class Spawns {
	static Spawns instance = new Spawns();

	public static Spawns getInstance() {
		return instance;
	}

	FileConfiguration conf;
	File file;

	public List<String> spawns;

	public void setup(Plugin pl) {
		file = new File(pl.getDataFolder(), "spawn.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		conf = YamlConfiguration.loadConfiguration(file);
		spawns = conf.getStringList("spawns");
		conf.set("spawns", spawns);
	}

	public FileConfiguration getConfig() {
		return conf;
	}

	public String getName() {
		return file.getName();
	}

	public void setSpawn(String spawn, World world, double x, double y, double z, float yaw, float pitch) {
		conf.set(spawn + ".world", world.getName());
		conf.set(spawn + ".x", x);
		conf.set(spawn + ".y", y);
		conf.set(spawn + ".z", z);
		conf.set(spawn + ".yaw", yaw);
		conf.set(spawn + ".pitch", pitch);

		spawns.add(spawn);
		conf.set("spawns", spawns);
		saveConfig();
	}

	public Location getSpawn(String spawn) {
		World w = Bukkit.getWorld(conf.getString(spawn + ".world"));
		double x = conf.getDouble(spawn + ".x");
		double y = conf.getDouble(spawn + ".y");
		double z = conf.getDouble(spawn + ".z");
		float yaw = (float) conf.getDouble(spawn + ".yaw");
		float pitch = (float) conf.getDouble(spawn + ".pitch");

		return new Location(w, x, y, z, yaw, pitch);
	}

	public void removeSpawn(String warp) {
		conf.set(warp + ".world", null);
		conf.set(warp + ".x", null);
		conf.set(warp + ".y", null);
		conf.set(warp + ".z", null);
		conf.set(warp + ".yaw", null);
		conf.set(warp + ".pitch", null);

		spawns.remove(warp);
		conf.set("spawns", warp);
		saveConfig();
	}

	public boolean spawnExists(String warp) {
		if (conf.getConfigurationSection(warp) == null) {
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