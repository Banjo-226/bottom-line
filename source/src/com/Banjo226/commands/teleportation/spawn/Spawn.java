package com.Banjo226.commands.teleportation.spawn;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.files.PlayerData;
import com.Banjo226.util.files.Spawns;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;
import com.Banjo226.commands.exception.SpawnNotFoundException;

public class Spawn extends Cmd {
	Spawns s = Spawns.getInstance();

	public Spawn() {
		super("spawn", Permissions.SPAWN);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		String spawn = (args.length == 0) ? "default" : args[0];
		Player player = (Player) sender;

		if (!s.spawnExists(spawn)) {
			try {
				throw new SpawnNotFoundException(spawn);
			} catch (SpawnNotFoundException e) {
				sender.sendMessage(e.getMessage());
			}
		} else {
			PlayerData pd = new PlayerData(player.getUniqueId());
			pd.setBackLocation(player.getLocation());
			
			player.teleport(s.getSpawn(spawn));
			sender.sendMessage("§6Spawn: §eTeleporting...");
		}
	}
}