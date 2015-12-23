/**
 *  TextFiles.java
 *  BottomLine
 *
 *  Created by Banjo226 on 23 Dec 2015 at 12:42:40 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.util.files;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.Banjo226.BottomLine;

public class TextFiles {

	BottomLine pl;
	static TextFiles txt = new TextFiles();

	public File motd;
	public File rules;

	public void setup(Plugin pl) {
		this.pl = (BottomLine) pl;

		motd = new File(pl.getDataFolder(), "motd.txt");
		rules = new File(pl.getDataFolder(), "rules.txt");

		try {
			if (!motd.exists()) motd.createNewFile();
			if (!rules.exists()) rules.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (pl.getConfig().getStringList("motd.message") != null || pl.getConfig().getStringList("motd.message").size() > 0) {
			BufferedWriter bw = null;
			String content = "";
			for (String l : pl.getConfig().getStringList("motd.message")) {
				String n = System.getProperty("line.separator");
				content += l + n;
			}

			try {
				bw = new BufferedWriter(new FileWriter(motd));

				bw.write(content);
				bw.flush();

				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			pl.getConfig().set("motd.message", null);
		}

		if (motd.length() == 0) {
			BufferedWriter bw = null;

			try {
				bw = new BufferedWriter(new FileWriter(motd));

				String n = System.getProperty("line.separator");
				String content = "&8&m---------------------" + n + "&4Welcome back, &c%display%" + n + "&4Currently &c[&7%players%&4/&7%max%&c] &4online people" + n + "&c%localtime% &4is the current time" + n + "&4You have &c$%balance% &4in your balance" + n
						+ "&4Type &c/rules &4for the rulebook of the server!" + n + "&8&m---------------------";

				bw.write(content);
				bw.flush();

				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (pl.getConfig().getStringList("rules") != null || pl.getConfig().getStringList("rules").size() > 0) {
			BufferedWriter bw = null;
			String content = "";
			for (String l : pl.getConfig().getStringList("rules")) {
				String n = System.getProperty("line.separator");
				content += l + n;
			}

			try {
				bw = new BufferedWriter(new FileWriter(rules));

				bw.write(content);
				bw.flush();

				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			pl.getConfig().set("rules", null);
		}

		if (rules.length() == 0) {
			BufferedWriter bw = null;

			try {
				bw = new BufferedWriter(new FileWriter(rules));

				String n = System.getProperty("line.separator");
				String content = "&c[A] &4Be nice" + n + "&c[B] &4Use manners" + n + "&c[C] &4Have fun";

				bw.write(content);
				bw.flush();

				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static TextFiles getInstance() {
		return txt;
	}

	public List<String> getMotd() {
		List<String> motd = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(this.motd))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				motd.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return motd;
	}

	public List<String> getRules() {
		List<String> rules = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(this.rules))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				rules.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rules;
	}
}