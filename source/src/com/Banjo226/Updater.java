/**
 *  Updater.java
 *  BottomLine
 *
 *  Created by Banjo226 on 4 Dec 2015 at 5:32 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.plugin.Plugin;

public class Updater {

	private Plugin plugin;
	private int id;
	private boolean enabled = true;

	public static String newvers;
	public static String oldvers;
	public static int res;

	public Updater(int id, Plugin pl) throws IOException {
		this.plugin = pl;
		this.id = id;

		res = id;
	}

	public void start() throws Exception {
		if (!plugin.isEnabled()) return;
		if (!enabled) return;
		BottomLine.debug("Searching for update...");

		HttpURLConnection c = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
		c.setDoOutput(true);
		c.setRequestMethod("POST");
		c.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + id).getBytes("UTF-8"));

		String current = plugin.getDescription().getVersion();
		String vers = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine().replaceAll("[a-zA-Z ]", "");
		if (compareVersions(vers, current) == 1) {
			BottomLine.debug("Found new version, " + vers + "! (Currently running " + current + ")");
			BottomLine.debug("Download at: http://www.spigotmc.org/resources/bottomline." + id);

			BottomLine.update = true;

			newvers = vers;
			oldvers = plugin.getDescription().getVersion();
		} else {
			BottomLine.debug("No new update found to download!");
		}
	}

	public static int compareVersions(String vers1, String vers2) {
		String[] v1 = vers1.split("\\.");
		String[] v2 = vers2.split("\\.");
		for (int i = 0; i < v1.length && i < v2.length; i++) {
			int i1 = Integer.parseInt(v1[i]);
			int i2 = Integer.parseInt(v2[i]);
			int cmp = Integer.compare(i1, i2);
			if (cmp != 0) return cmp;
		}
		return Integer.compare(v1.length, v2.length);
	}
}