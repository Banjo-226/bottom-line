/**
 *  BottomLine.java
 *  BottomLine
 *
 *  Created by Banjo226 on 31 Oct 2015 at 4:45 pm AEST
 *  Copyright © 2015-2017 Banjo226. All rights reserved.
 */

package com.Banjo226;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.Banjo226.commands.PermissionMessages;
import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.chat.AdminChat;
import com.Banjo226.commands.chat.CommandSpy;
import com.Banjo226.commands.chat.list.MOTD;
import com.Banjo226.commands.inventory.item.Item;
import com.Banjo226.commands.inventory.sub.Open;
import com.Banjo226.commands.law.Freeze;
import com.Banjo226.commands.law.Jail;
import com.Banjo226.commands.law.JailUtil;
import com.Banjo226.commands.law.Mute;
import com.Banjo226.commands.law.ban.BanJoinListener;
import com.Banjo226.commands.player.AFK;
import com.Banjo226.commands.player.God;
import com.Banjo226.commands.player.PowerTool;
import com.Banjo226.commands.player.gamemode.QuickGM;
import com.Banjo226.commands.replace.Plugins;
import com.Banjo226.commands.replace.Version;
import com.Banjo226.commands.world.time.FreezeTimer;
import com.Banjo226.commands.world.time.QuickTime;
import com.Banjo226.commands.world.weather.QuickWeather;
import com.Banjo226.events.ConfigListener;
import com.Banjo226.events.DeathListener;
import com.Banjo226.events.RespawnListener;
import com.Banjo226.events.WeatherListener;
import com.Banjo226.events.block.ClicksPerSecond;
import com.Banjo226.events.block.ClicksPerSecondListener;
import com.Banjo226.events.joining.JoinLeaveListener;
import com.Banjo226.events.joining.NewPlayerListener;
import com.Banjo226.events.signs.FeedSign;
import com.Banjo226.events.signs.FormatSigns;
import com.Banjo226.events.signs.HealSign;
import com.Banjo226.events.signs.KitSign;
import com.Banjo226.manager.ChatEventManager;
import com.Banjo226.manager.CommandManager;
import com.Banjo226.util.Metrics;
import com.Banjo226.util.Store;
import com.Banjo226.util.TicksPerSecond;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.*;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

/**
 * <code>BottomLine.java</code> </br>
 * <code>BottomLine</code> </br>
 * </br>
 * Created by Banjo226 on 31 Oct 2015 at 4:45 pm AEST </br>
 * Copyright © 2015-2017 Banjo226. All rights reserved.
 * 
 * @see {@link https://github.com/Banjo-226/bottom-line/wiki}
 * @author Banjo226
 * @version 1.2.9
 */

public class BottomLine extends JavaPlugin implements Listener {
	private static BottomLine bl;

	public PluginDescriptionFile pdf;
	public String vers;
	public String servervs;
	public File file;
	public FileConfiguration conf;

	static Chat chat = null;
	static Permission perm = null;

	public static boolean update = false;

	Data d = Data.getInstance();
	Money e = Money.getInstance();
	Jails j = Jails.getInstance();
	Warps w = Warps.getInstance();
	Spawns s = Spawns.getInstance();
	TextFiles txt = TextFiles.getInstance();

	public void onEnable() {
		bl = this;
		pdf = this.getDescription();
		vers = pdf.getVersion();
		servervs = Bukkit.getServer().getClass().getPackage().getName();
		servervs = servervs.substring(servervs.lastIndexOf(".") + 1);
		file = new File(this.getDataFolder(), "config.yml");

		registerEvents(this, new MOTD(), new JoinLeaveListener(), new AdminChat(), new God(), new AFK(), new ConfigListener(), new WeatherListener(), new Mute(), new Jail(), new JailUtil(), new NewPlayerListener(), new Plugins(), new Version(), new Freeze(),
				new BanJoinListener(), new Open(), new Item(), new ClicksPerSecondListener(), new CommandSpy(), new PowerTool(), new DeathListener(), new ChatEventManager(), new FeedSign(), new HealSign(), new RespawnListener(), new QuickGM(), new FormatSigns(),
				new KitSign(), new QuickTime(), new QuickWeather());
		setup();

		if (servervs.startsWith("1.9")) {
			debug("It appears that you are using 1.9, some features may not work, or may be slower to load.");
		}

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TicksPerSecond(), 100L, 1L); // updates the tps
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ClicksPerSecond(), 0L, 20L); // Updating the rates for CPS
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new FreezeTimer(), 0L, getConfig().getLong("time-freeze-seconds") * 20L); // to enable
																																		// time freeze

		debug("Enabled Bottom Line (" + pdf.getDescription() + ") v" + vers + ".");

		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
			public void run() {
				if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
					try {
						setupChat();
					} catch (Exception e) {
						debug("No chat dependency found!");
					}

					try {
						setupPermissions();
					} catch (Exception e) {
						debug("No permissions dependency found!");
					}

					try {
						Updater u = new Updater(15348, bl);
						u.start();
					} catch (Exception e) {
						error("Could not check for update (no connection?)");
					}
				}
			}
		}, 0, 432020);
	}

	public void onDisable() {
		bl = null;
		Store.spam.clear();
		Store.reply.clear();
	}

	protected void registerEvents(Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
			Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}

	protected void setup() {
		commands();
		config();
		new Store();
		new Util();
		new Permissions();

		if (getConfig().getBoolean("metrics") == true) {
			Metrics m = new Metrics(this);
		}

		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
			debug("Enabled Vault connection!");
			Bukkit.getServicesManager().register(Economy.class, new VaultConnector(), this, ServicePriority.Highest);
		} else {
			error("Could not find vault! This plugin can no longer find Vault and may throw errors into console. It is highly recommended you download vault.");
		}
	}

	protected boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		chat = rsp.getProvider();
		debug("Found chat plugin: " + rsp.getProvider().getName());
		return chat != null;
	}

	protected boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perm = rsp.getProvider();
		debug("Found permissions plugin: " + rsp.getProvider().getName());
		return perm != null;
	}

	protected void commands() {
		// Our command manager instance
		CommandManager cmd = new CommandManager();

		// A loop that loops through everything in the plugin.yml file, if its a command, we'll register it.
		for (Entry<String, Map<String, Object>> entry : pdf.getCommands().entrySet()) {
			String command = entry.getKey();
			this.getCommand(command).setExecutor(cmd);
			this.getCommand(command).setPermissionMessage(PermissionMessages.CMD.toString());
			this.getCommand(command).setTabCompleter(cmd);
		}

		// Checks if the commands have been registered for the law systems
		if (Bukkit.getPluginManager().getPlugin("LiteBans") != null) {
			String[] punishments = { "ban", "unban", "tempban", "kick" };

			for (String s : punishments) {
				if (getCommand(s).isRegistered()) {
					this.getCommand(s).setExecutor(null);
				}
			}
		}
	}

	protected void config() {
		if (!file.exists()) {
			warning("Creating new configuration file for Bottom Line");
		}

		conf = YamlConfiguration.loadConfiguration(file);

		if (file.exists()) {
			saveDefaultConfig();

			if (Updater.compareVersions(vers, getConfig().getString("version")) == 1) {
				File configFolder = new File(this.getDataFolder() + File.separator + "archives");
				if (!configFolder.exists()) {
					configFolder.mkdirs();
				}

				File oldConfig = new File(configFolder.getPath(), "config " + getConfig().getString("version") + ".txt");

				try {
					if (oldConfig.getName().equals("config " + vers + ".txt")) {
						configFolder.delete();
						oldConfig.delete();
					} else {
						if (!configFolder.exists()) {
							configFolder.mkdirs();
						}
						oldConfig.createNewFile();

						warning("Crearted new configuration file, renamed old file to " + oldConfig.getName());
					}
				} catch (IOException e) {
					e.printStackTrace();
					error("Failed to create a copy of the old config file while generating new one!");
				}

				file.renameTo(oldConfig);
			}
		}

		saveDefaultConfig();
		d.setup(this);
		e.setup(this);
		j.setup(this);
		w.setup(this);
		s.setup(this);
		txt.setup(this);

		debug("Enabled configuration!");
	}

	public static void debug(String msg) {
		System.out.println("[BottomLine] " + msg);
	}

	public static void warning(String msg) {
		debug("[!] " + msg);
	}

	public static void error(String msg) {
		System.err.println("[BottomLine] [!] [EXCEPTION] " + msg);
	}

	public static BottomLine getInstance() {
		return bl;
	}

	public Chat getChat() {
		return chat;
	}

	public Permission getPerms() {
		return perm;
	}
}
