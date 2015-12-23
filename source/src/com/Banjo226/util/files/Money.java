/**
 *  Monet.java
 *  BottomLine
 *
 *  Created by Banjo226 on 2 Nov 2015 at 5:38 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.util.files;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Money {
	static Money econ = new Money();

	public static Money getInstance() {
		return econ;
	}

	private FileConfiguration config;
	private File cfile;

	public void setup(Plugin p) {
		cfile = new File(p.getDataFolder(), "economy.yml");

		if (!cfile.exists()) {
			try {
				cfile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		config = YamlConfiguration.loadConfiguration(cfile);
	}

	public void addBalance(Player p, double amount) {
		setBalance(p, getBalance(p) + amount);
	}

	public void removeBalance(Player p, double amount) {
		setBalance(p, getBalance(p) - amount);
	}

	public void resetBalance(Player p) {
		setBalance(p, 0D);
	}

	public void setBalance(Player p, double amount) {
		config.set("balance." + p.getName(), amount);
		save();
	}

	public double getBalance(CommandSender p) {
		return config.getDouble("balance." + p.getName());
	}

	public void addBalance(CommandSender p, double amount) {
		setBalance(p, getBalance(p) + amount);
	}

	public void removeBalance(CommandSender p, double amount) {
		setBalance(p, getBalance(p) - amount);
	}

	public void resetBalance(CommandSender p) {
		setBalance(p, 0D);
	}

	public void setBalance(CommandSender p, double amount) {
		config.set("balance." + p.getName(), amount);
		save();
	}

	public double getBalance(Player p) {
		return config.getDouble("balance." + p.getName());
	}

	public double getBalance(String offline) {
		return config.getDouble("balance." + offline);
	}

	public void addBalance(String p, double amount) {
		setBalance(p, getBalance(p) + amount);
	}

	public boolean removeBalance(String p, double amount) {
		if (getBalance(p) - amount < 0) return false;

		setBalance(p, getBalance(p) - amount);
		return true;
	}

	public void resetBalance(String p) {
		setBalance(p, 0);
	}

	public void setBalance(String p, double amount) {
		config.set("balance." + p, amount);
		save();
	}

	public ArrayList<String> getValues() {
		Map<String, Object> map = config.getValues(true);
		ArrayList<String> lines = new ArrayList<String>();

		for (Entry<String, Object> e : map.entrySet()) {
			lines.add(e.getValue() + " " + e.getKey());
		}

		return lines;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public boolean hasPlayerData(Player player) {
		if (config.getConfigurationSection("balance." + player.getName()) == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean hasPlayerData(CommandSender sender) {
		if (config.getConfigurationSection("balance." + sender.getName()) == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean hasStringData(String name) {
		if (config.getConfigurationSection("balance." + name) == null) {
			return false;
		} else {
			return true;
		}
	}

	public String getName() {
		return cfile.getName();
	}

	public void save() {
		try {
			config.save(cfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(cfile);
	}
}