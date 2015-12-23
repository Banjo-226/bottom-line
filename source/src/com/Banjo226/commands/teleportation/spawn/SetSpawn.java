package com.Banjo226.commands.teleportation.spawn;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.files.Spawns;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class SetSpawn extends Cmd {
	Spawns s = Spawns.getInstance();

	public SetSpawn() {
		super("setspawn", Permissions.SETSPAWN);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		Player player = (Player) sender;
		String spawn = (args.length == 0) ? "default" : args[0];
		s.setSpawn(spawn, player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
		sender.sendMessage("§6Spawn: §eSet the spawn §o'" + spawn + "' §eat your current location!");
	}
}