package com.Banjo226.commands.teleportation.home;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;
import com.Banjo226.manager.Cmd;
import com.Banjo226.util.files.PlayerData;

public class Home extends Cmd {

	public Home() {
		super("home", Permissions.HOME);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		PlayerData pd = new PlayerData(((Player) sender).getUniqueId());
		Player player = (Player) sender;

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("set")) {
				pd.setHome(player);
				sender.sendMessage("§6Home: §eSet home successfully!");
			} else if (args[0].equalsIgnoreCase("del")) {
				pd.delHome();
				sender.sendMessage("§6Home: §eDeleted home successfully!");
			}
			return;
		}

		if (pd.getConfig().getConfigurationSection("home") == null) {
			sender.sendMessage("§cHome: §4You do not have a home! To set one type /home set in the desired location");
			return;
		}

		pd.setBackLocation(player.getLocation());
		player.teleport(pd.getHome());
		sender.sendMessage("§6Home: §eWelcome home, " + pd.getDisplayName() + "§e!");
	}
}