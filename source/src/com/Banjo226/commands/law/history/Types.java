package com.Banjo226.commands.law.history;

public enum Types {

	/* Main types */ MUTE("Mutes"), JAIL("Jails"),
	/* Secondary types */ BAN("Bans"), TEMPBAN("TempBan"),
	/* Other types */ KICK("Kicks"), FREEZE("Freezes");

	private final String text;

	private Types(final String text) {
		this.text = text;
	}

	public String toString() {
		return text.toLowerCase();
	}
}