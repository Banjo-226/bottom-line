package com.Banjo226.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class PowerTool extends Cmd {

	public PowerTool() {
		super("powertool", Permissions.POWERTOOL);
	}

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());
		Player player = (Player) sender;

		if (args.length == 0) {
			Util.invalidArgCount(sender, "Powertool", "Bind a command with an item.", "/pt [command... args]", "/pt reset <** (all)>");
			return;
		}

		if (args[0].equalsIgnoreCase("reset")) {
			if (args.length == 2 && args[1].startsWith("*")) {
				PlayerData pd = new PlayerData(player.getUniqueId());
				pd.removePowertools();
				sender.sendMessage("§6Powertool: §eCleared all powertools applied to items!");
			} else {
				PlayerData pd = new PlayerData(player.getUniqueId());
				ItemStack item = player.getItemInHand();

				int id = pd.getItemId(item.getTypeId());
				if (id == 0) {
					sender.sendMessage("§cPowertool: §4No powertool on §o" + item.getType().toString().toLowerCase().replaceAll("_", " "));
					return;
				}

				pd.setPowertool(id, null);

				sender.sendMessage("§6Powertool: §eCleared command on powertool §o" + item.getType().toString().toLowerCase().replaceAll("_", " "));
				return;
			}
			return;
		}

		PlayerData pd = new PlayerData(player.getUniqueId());
		ItemStack item = player.getItemInHand();

		if (item.getType() == Material.AIR) {
			sender.sendMessage("§cPowertool: §4Cannot set powertool onto air!");
			return;
		}

		String command = "";
		for (int i = 0; i < args.length; i++) {
			command += args[i] + " ";
		}

		pd.setPowertool(item.getTypeId(), command.trim());
		sender.sendMessage("§6Powertool: §eApplied the command §o" + command.trim() + " §eto the item §o" + item.getType().toString().toLowerCase().replaceAll("_", " "));
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Player player = e.getPlayer();
			PlayerData pd = new PlayerData(player.getUniqueId());

			try {
				int id = pd.getItemId(player.getItemInHand().getTypeId());
				Bukkit.dispatchCommand(player, pd.getCommandOnPowertool(id));
				e.setCancelled(true);
			} catch (Exception ex) {
				return;
			}
		}
	}
}