/**
 *  Jails.java
 *  BottomLine
 *
 *  Created by Banjo226 on 8 Nov 2015 at 7:26 pm AEST
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

public class Jails {
	static Jails instance = new Jails();

	public static Jails getInstance() {
		return instance;
	}

	FileConfiguration data;
	File dfile;
	List<String> jails;

	public void setup(Plugin pl) {
		dfile = new File(pl.getDataFolder(), "jails.yml");

		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		data = YamlConfiguration.loadConfiguration(dfile);

		jails = data.getStringList("existing");
	}

	public Location getLocation(String jailName) {
		World w = Bukkit.getWorld(data.getString(jailName + ".world"));
		double x = data.getDouble(jailName + ".x");
		double y = data.getDouble(jailName + ".y");
		double z = data.getDouble(jailName + ".z");
		float yaw = (float) data.getDouble(jailName + ".yaw");
		float pitch = (float) data.getDouble(jailName + ".pitch");

		return new Location(w, x, y, z, yaw, pitch);
	}

	public void setLocation(String jailName, World w, double x, double y, double z, float yaw, float pitch) {
		data.set(jailName + ".world", w.getName());
		data.set(jailName + ".x", x);
		data.set(jailName + ".y", y);
		data.set(jailName + ".z", z);
		data.set(jailName + ".yaw", yaw);
		data.set(jailName + ".pitch", pitch);

		jails.add(jailName);
		data.set("existing", jails);

		saveConfig();
	}

	public void delJail(String jailName) {
		data.set(jailName + ".world", null);
		data.set(jailName + ".x", null);
		data.set(jailName + ".y", null);
		data.set(jailName + ".z", null);
		data.set(jailName + ".yaw", null);
		data.set(jailName + ".pitch", null);
		data.set(jailName, null);

		jails.remove(jailName);
		data.set("existing", jails);

		saveConfig();
	}

	public List<String> getJails() {
		return jails;
	}

	public FileConfiguration getConfig() {
		return data;
	}

	public String getName() {
		return dfile.getName();
	}

	public void saveConfig() {
		try {
			data.save(dfile);
		} catch (IOException e) {
			BottomLine.warning("Could not save data file!");
		}
	}

	public void reloadConfig() {
		data = YamlConfiguration.loadConfiguration(dfile);
	}
}