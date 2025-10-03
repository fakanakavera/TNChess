package com.tnchess.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class Items {

	private Items() {}

	public static ItemStack whitePiece(String name) {
		return named(Material.WHITE_WOOL, ChatColor.WHITE + name);
	}

	public static ItemStack blackPiece(String name) {
		return named(Material.BLACK_WOOL, ChatColor.GRAY + name);
	}

	public static ItemStack quitButton() {
		return named(Material.BARRIER, ChatColor.RED + "Quit Game");
	}

	private static ItemStack named(Material material, String name) {
		ItemStack stack = new ItemStack(material);
		ItemMeta meta = stack.getItemMeta();
		if (meta != null) {
			meta.setDisplayName(name);
			stack.setItemMeta(meta);
		}
		return stack;
	}
}


