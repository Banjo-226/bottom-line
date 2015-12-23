/**
 *  VaultConnector.java
 *  BottomLine
 *
 *  Created by Banjo226 on 3 Nov 2015 at 7:03 pm AEST
 *  Copyright © 2015 Banjo226. All rights reserved.
 */

package com.Banjo226;

import java.util.List;

import org.bukkit.OfflinePlayer;

import com.Banjo226.util.files.Money;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class VaultConnector implements Economy {
	Money econ = Money.getInstance();

	public EconomyResponse bankBalance(String arg0) {
		return null;
	}

	public EconomyResponse bankDeposit(String arg0, double arg1) {
		return null;
	}

	public EconomyResponse bankHas(String arg0, double arg1) {
		return null;
	}

	public EconomyResponse bankWithdraw(String arg0, double arg1) {
		return null;
	}

	public EconomyResponse createBank(String arg0, String arg1) {
		return null;
	}

	public boolean createPlayerAccount(String name) {
		econ.setBalance(name, 0);
		return true;
	}

	public boolean createPlayerAccount(String name, String world) {
		return createPlayerAccount(name);
	}

	public String currencyNamePlural() {
		return "dollars";
	}

	public String currencyNameSingular() {
		return "dollar";
	}

	public EconomyResponse deleteBank(String arg0) {
		return null;
	}

	public EconomyResponse depositPlayer(String name, double amnt) {
		econ.addBalance(name, amnt);
		return new EconomyResponse(amnt, econ.getBalance(name), ResponseType.SUCCESS, "");
	}

	public EconomyResponse depositPlayer(String name, String world, double amnt) {
		return depositPlayer(name, amnt);
	}

	public String format(double amnt) {
		return BottomLine.getInstance().getConfig().getString("economy.money-symbol") + String.valueOf(amnt);
	}

	public int fractionalDigits() {
		return 2;
	}

	public double getBalance(String name) {
		return econ.getBalance(name);
	}

	public double getBalance(String name, String world) {
		return getBalance(name);
	}

	public List<String> getBanks() {
		return null;
	}

	public String getName() {
		return "Bottom Line";
	}

	public boolean has(String player, double amnt) {
		return econ.getBalance(player) >= amnt;
	}

	public boolean has(String player, String world, double amnt) {
		return has(player, amnt);
	}

	public boolean hasAccount(String name) {
		return econ.hasStringData(name);
	}

	public boolean hasAccount(String arg0, String arg1) {
		return false;
	}

	public boolean hasBankSupport() {
		return false;
	}

	public EconomyResponse isBankMember(String arg0, String arg1) {
		return null;
	}

	public EconomyResponse isBankOwner(String arg0, String arg1) {
		return null;
	}

	public boolean isEnabled() {
		return BottomLine.getInstance().isEnabled();
	}

	public EconomyResponse withdrawPlayer(String player, double amnt) {
		return new EconomyResponse(amnt, econ.getBalance(player) - amnt, econ.removeBalance(player, amnt) ? ResponseType.SUCCESS : ResponseType.FAILURE, "Insufficient funds.");
	}

	public EconomyResponse withdrawPlayer(String player, String world, double amnt) {
		return withdrawPlayer(player, amnt);
	}

	public EconomyResponse withdrawPlayer(OfflinePlayer name, String world, double amnt) {
		return withdrawPlayer(name.getName(), amnt);
	}

	public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean createPlayerAccount(OfflinePlayer name) {
		return createPlayerAccount(name.getName());
	}

	public boolean createPlayerAccount(OfflinePlayer name, String world) {
		return createPlayerAccount(name);
	}

	public EconomyResponse depositPlayer(OfflinePlayer name, double amnt) {
		return depositPlayer(name.getName(), amnt);
	}

	public EconomyResponse depositPlayer(OfflinePlayer name, String world, double amnt) {
		return depositPlayer(name, amnt);
	}

	public double getBalance(OfflinePlayer name) {
		return econ.getBalance(name.getName());
	}

	public double getBalance(OfflinePlayer name, String world) {
		return getBalance(name);
	}

	public boolean has(OfflinePlayer arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean has(OfflinePlayer arg0, String arg1, double arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasAccount(OfflinePlayer name) {
		return econ.hasStringData(name.getName());
	}

	public boolean hasAccount(OfflinePlayer name, String world) {
		return hasAccount(name);
	}

	public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public EconomyResponse withdrawPlayer(OfflinePlayer name, double amnt) {
		return withdrawPlayer(name.getName(), amnt);
	}
}