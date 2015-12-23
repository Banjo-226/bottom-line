/**
 *  Data.java
 *  BottomLine
 *
 *  Created by Banjo226 on 31 Oct 2015 at 5:10 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.util.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.Banjo226.BottomLine;

public class Data {
	static Data instance = new Data();

	public static Data getInstance() {
		return instance;
	}

	FileConfiguration data;
	File dfile;

	public void setup(Plugin pl) {
		dfile = new File(pl.getDataFolder(), "data.yml");

		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		data = YamlConfiguration.loadConfiguration(dfile);
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