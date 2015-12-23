package com.Banjo226.commands.teleportation.spawn;

import org.bukkit.command.CommandSender;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.files.Spawns;

import com.Banjo226.commands.Permissions;

public class DelSpawn extends Cmd {
	Spawns s = Spawns.getInstance();

	public DelSpawn() {
		super("delspawn", Permissions.REMOVESPAWN);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		String spawn = (args.length == 0) ? "default" : args[0];

		if (!s.spawnExists(spawn))
			sender.sendMessage("§cWarp: §4The spawn " + spawn + " does not exist!");
		else {
			s.removeSpawn(spawn);
			sender.sendMessage("§6Spawn: §eRemoved the spawn " + spawn + ".");
		}
	}
}