
package com.Banjo226.commands.inventory.sub;

import java.util.Arrays;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Wool;

import com.Banjo226.commands.Permissions;
import com.Banjo226.commands.exception.ConsoleSenderException;
import com.Banjo226.commands.inventory.InvCommand;
import com.Banjo226.util.Util;
import com.Banjo226.util.files.PlayerData;

public class Open extends InvCommand implements Listener {
	Player targ = null;
	PlayerData pd;

	public Open() {
		super("open", "Open another players inventory", "[player]", Arrays.asList("openinv", "invopen"), Permissions.OPENINV);
	}

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) throw new ConsoleSenderException(getName());

		pd = new PlayerData(((Player) sender).getUniqueId());

		if (args.length == 0) {
			Util.invalidArgCount(sender, "Open Inventory", "Open the inventory of another individual.", "/inv open [player]");
		} else {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				Util.offline(sender, "Open Inventory", args[0]);
			} else {
				Player player = (Player) sender;
				openInventory(player, target);
				pd.set("invopentarget", target.getName());
			}
		}
	}

	private void openInventory(Player player, Player target) {
		Util.playSound(player);

		modifyInventory(target.getInventory(), player, target);
	}

	private void modifyInventory(PlayerInventory inven, Player player, Player target) {
		Inventory inv = Bukkit.createInventory(null, 45, "§cPlayer Inventory");

		inv.setContents(inven.getContents());
		inv.setItem(36, inven.getHelmet());
		inv.setItem(37, inven.getChestplate());
		inv.setItem(38, inven.getLeggings());
		inv.setItem(39, inven.getBoots());

		ItemStack health = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta hmeta = (SkullMeta) health.getItemMeta();
		hmeta.setOwner(target.getName());
		hmeta.setDisplayName("§c" + target.getDisplayName() + "§c's health: §4" + Math.round(target.getHealth()));
		health.setItemMeta(hmeta);

		ItemStack food = new ItemStack(Material.PORK, 1);
		ItemMeta fmeta = food.getItemMeta();
		fmeta.setDisplayName("§c" + target.getDisplayName() + "§c's food: §4" + target.getFoodLevel());
		food.setItemMeta(fmeta);

		ItemStack gm = new ItemStack(Material.ARMOR_STAND, 1);
		ItemMeta gmeta = gm.getItemMeta();
		gmeta.setDisplayName("§c" + target.getDisplayName() + "§c's gamemode: §4" + target.getGameMode().toString());
		gm.setItemMeta(gmeta);

		if (player.hasPermission(Permissions.GAMEMODE_OTHERS)) {
			ItemStack tc = new Wool(DyeColor.LIME).toItemStack(1);
			ItemMeta tcmeta = tc.getItemMeta();
			tcmeta.setDisplayName("§6Set gamemode to creative");
			tc.setItemMeta(tcmeta);

			ItemStack ts = new Wool(DyeColor.RED).toItemStack(1);
			ItemMeta tsmeta = ts.getItemMeta();
			tsmeta.setDisplayName("§6Set gamemode to survival");
			ts.setItemMeta(tsmeta);

			inv.setItem(40, ts);
			inv.setItem(41, tc);
		}

		inv.setItem(42, health);
		inv.setItem(43, food);
		inv.setItem(44, gm);

		player.openInventory(inv);
	}

	@EventHandler
	public void onClickEvent(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		pd = new PlayerData(player.getUniqueId(), false);

		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Player Inventory")) return;
		if (!e.getWhoClicked().hasPermission(Permissions.OPENINV_MODIFY)) e.setCancelled(true);
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) return;

		if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§6Set gamemode to creative")) {
			targ = Bukkit.getPlayer((String) pd.get("invopentarget"));
			if (targ == null) {
				player.closeInventory();
				player.sendMessage("§cOpen Inventory: §4The player has just left the server, please try again.");
				pd.set("invopentarget", null);
				return;
			}

			e.setCancelled(true);

			targ.setGameMode(GameMode.CREATIVE);
			player.closeInventory();
			player.sendMessage("§6Open Inventory: §eSet " + targ.getDisplayName() + "§e's gamemode to Creative");

			pd.set("invopentarget", null);
		}

		if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§6Set gamemode to survival")) {
			targ = Bukkit.getPlayer((String) pd.get("invopentarget"));
			if (targ == null) {
				player.closeInventory();
				player.sendMessage("§cOpen Inventory: §4The player has just left the server, please try again.");
				pd.set("invopentarget", null);
				return;
			}

			e.setCancelled(true);

			targ.setGameMode(GameMode.SURVIVAL);
			player.closeInventory();
			player.sendMessage("§6Open Inventory: §eSet " + targ.getDisplayName() + "§e's gamemode Survival");

			pd.set("invopentarget", null);
		}
	}
}