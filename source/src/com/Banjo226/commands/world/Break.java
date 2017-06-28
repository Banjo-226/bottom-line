package com.Banjo226.commands.world;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.Banjo226.manager.Cmd;
import com.Banjo226.util.Store;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;

public class Break extends Cmd {

	public Break() {
		super("break", Permissions.BREAK);
	}

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		Player player = (Player) sender;
		Block block = player.getTargetBlock((HashSet<Byte>) null, 100);
		Location bl = block.getLocation();
		ItemStack norm = new ItemStack(block.getTypeId());

		for (int i = 0; i < Store.breakbl.size(); i++) {
			Material mat = Material.matchMaterial(Store.breakbl.get(i));
			if (block.getType().equals(mat) && !sender.hasPermission(Permissions.BREAK_BYPASS)) {
				ItemStack ite = new ItemStack(mat);
				sender.sendMessage("§cBreak: §4The item to remove is blacklisted (" + ite.getType().toString() + ", " + mat.getId() + ")");
				return;
			}
		}

		bl.getBlock().setType(Material.AIR);

		sender.sendMessage("§6Break: §eBreaking block that you are looking at (" + norm.getType().toString() + ", " + norm.getTypeId() + ")");
	}
}
