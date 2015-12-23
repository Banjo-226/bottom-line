package com.Banjo226.commands.exception;

import com.Banjo226.manager.Cmd;

public class ConsoleSenderException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConsoleSenderException() {
		super("Console cannot run the command!");
	}

	public ConsoleSenderException(String command) {
		super("Console cannot run the command " + command + "!");
	}

	public ConsoleSenderException(Cmd command) {
		super("Console cannot run the command " + command.getName().toLowerCase() + "!");
	}

	public ConsoleSenderException(String msg, String command) {
		super("Console cannot run the command " + command + "! " + msg);
	}
}