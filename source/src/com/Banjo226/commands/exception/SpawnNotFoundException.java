package com.Banjo226.commands.exception;

public class SpawnNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public SpawnNotFoundException(String warp) {
		super("§cSpawn: §4Could not find spawn with name: " + warp);
	}
}