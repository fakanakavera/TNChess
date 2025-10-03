package com.tnchess.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class InventorySnapshot {

	private final ItemStack[] saved;

	private InventorySnapshot(ItemStack[] saved) {
		this.saved = saved;
	}

	public static InventorySnapshot snapshotRows(Player player) {
		PlayerInventory inv = player.getInventory();
		ItemStack[] saved = new ItemStack[16];
		for (int i = 0; i < 8; i++) {
			saved[i] = inv.getItem(9 + i);  // row 1 of main inventory (top of main)
			saved[8 + i] = inv.getItem(18 + i); // row 2 of main inventory
		}
		return new InventorySnapshot(saved);
	}

	public void restore(Player player) {
		PlayerInventory inv = player.getInventory();
		for (int i = 0; i < 8; i++) {
			inv.setItem(9 + i, saved[i]);
			inv.setItem(18 + i, saved[8 + i]);
		}
	}
}


