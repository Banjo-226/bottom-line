package com.Banjo226.commands.law;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.Jails;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class JailUtil extends Cmd {
	Jails j = Jails.getInstance();

	public JailUtil() {
		super("jailutil", Permissions.JAIL);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		if (sender.hasPermission(Permissions.JAIL)) {
			Player player = (Player) sender;

			if (args.length == 0) {
				Util.invalidArgCount(sender, "Jail", "Set, change, and list jails.", "/jutil set <name>", "/jutil del <name>", "/jutil list");
				return;
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("set") && sender.hasPermission(Permissions.JAIL_MODIFY)) {
				sender.sendMessage("§6Jail: §eSetting default jail.");
				j.setLocation("jail", player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
			} else if (args.length == 2 && args[0].equalsIgnoreCase("set") && sender.hasPermission(Permissions.JAIL_MODIFY)) {
				sender.sendMessage("§6Jail: §eSetting custom jail with the name of '" + args[1] + "'.");
				j.setLocation(args[1], player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
			} else if (args.length == 1 && (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del")) && sender.hasPermission(Permissions.JAIL_MODIFY)) {
				if (j.getConfig().getConfigurationSection("jail") != null) {
					sender.sendMessage("§6Jail: §eRemoving default jail location.");
					j.delJail("jail");
				} else {
					sender.sendMessage("§cJail: §4The jail selected doesnt exist.");
				}
			} else if (args.length == 2 && (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del")) && sender.hasPermission(Permissions.JAIL_MODIFY)) {
				if (j.getConfig().getConfigurationSection("jail") != null) {
					sender.sendMessage("§6Jail: §eRemoving custom jail with the name of '" + args[1] + "'.");
					j.delJail(args[1]);
				} else {
					sender.sendMessage("§cJail: §4The jail selected doesnt exist.");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
				sender.sendMessage("§6Jail: §eExisting jails on the server:");
				if (j.getJails().size() == 0) {
					sender.sendMessage("§6No jails exist on the server.");
					return;
				}

				for (int i = 0; i < j.getJails().size(); i++) {
					sender.sendMessage("§6- §e" + j.getConfig().getStringList("existing").get(i));
				}
			}

			Util.playSound(player);
		}
	}
}