package com.Banjo226.commands;

import com.Banjo226.BottomLine;
import com.Banjo226.util.Util;

public enum PermissionMessages {

	CMD(Util.colour(BottomLine.getInstance().getConfig().getString("noPermissionMessage"))), KIT(Util.colour(BottomLine.getInstance().getConfig().getString("noKitPermission"))), CREATIVE("§cYou cannot change your gamemode to creative!"), SURVIVAL(
			"§cYou cannot change your gamemode to survival!"), ADVENTURE("§cYou cannot change your gamemode to adventure!");

	private final String text;

	private PermissionMessages(final String text) {
		this.text = text;
	}

	public String toString() {
		return text;
	}
}