/**
 *  ChatEventManager.java
 *  BottomLine
 *
 *  Created by Banjo226 on 28 November at 1:44 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.manager;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.Banjo226.events.chat.BottomLineChat;
import com.Banjo226.events.chat.FormatChatListener;
import com.Banjo226.events.chat.ManipulateChatListener;
import com.Banjo226.events.chat.SpamChecker;

public class ChatEventManager extends BottomLineChat {

	private ArrayList<BottomLineChat> chat = new ArrayList<>();

	public ChatEventManager() {
		chat.add(new ManipulateChatListener());
		chat.add(new FormatChatListener());
		chat.add(new SpamChecker());
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		for (BottomLineChat c : chat) {
			c.onPlayerChat(e);
		}
	}
}