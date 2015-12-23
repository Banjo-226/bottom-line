/**
 *  TicksPerSecond.java
 *  BottomLine
 *
 *  Created by Banjo226 on 15 Nov 2015 at 11:35 am AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226.util;

public class TicksPerSecond implements Runnable {
	public static int TICK_COUNT = 0;
	public static long[] TICKS = new long[600];
	public static long LAST_TICK = 0L;

	public static double getTPS() {
		return getTPS(100);
	}

	public static double getTPS(int ticks) {
		if (TICK_COUNT < ticks) {
			return 20.0D;
		}

		int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
		long elapsed = System.currentTimeMillis() - TICKS[target];

		return ticks / (elapsed / 1000.0D);
	}

	public static long getElapsed(int tickID) {
		if (TICK_COUNT - tickID >= TICKS.length) {
		}

		long time = TICKS[(tickID % TICKS.length)];
		return System.currentTimeMillis() - time;
	}

	public void run() {
		TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();

		TICK_COUNT += 1;
	}
}