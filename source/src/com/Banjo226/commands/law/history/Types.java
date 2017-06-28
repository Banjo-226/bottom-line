package com.Banjo226.commands.law.history;

public enum Types {

	/* Main types */ MUTE("Mute"), JAIL("Jail"),
	/* Secondary types */ BAN("Ban"), TEMPBAN("TempBan"),
	/* Other types */ KICK("Kick"), FREEZE("Freeze");

	private final String text;

	private Types(final String text) {
		this.text = text;
	}

	public String toString() {
		return text.toLowerCase();
	}
}
