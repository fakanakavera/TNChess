package com.tnchess.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class Items {

	private Items() {}

    // White (light birch variants)
    public static ItemStack whiteKing() { return named(Material.BIRCH_DOOR, ChatColor.WHITE + "♔ White King"); }
    public static ItemStack whiteQueen() { return named(Material.BIRCH_FENCE, ChatColor.WHITE + "♕ White Queen"); }
    public static ItemStack whiteRook() { return named(Material.BIRCH_LOG, ChatColor.WHITE + "♖ White Rook"); }
    public static ItemStack whiteBishop() { return named(Material.BIRCH_STAIRS, ChatColor.WHITE + "♗ White Bishop"); }
    public static ItemStack whiteKnight() { return named(Material.BIRCH_TRAPDOOR, ChatColor.WHITE + "♘ White Knight"); }
    public static ItemStack whitePawn() { return named(Material.BIRCH_PLANKS, ChatColor.WHITE + "♙ White Pawn"); }

    // Black (dark oak variants)
    public static ItemStack blackKing() { return named(Material.DARK_OAK_DOOR, ChatColor.GRAY + "♚ Black King"); }
    public static ItemStack blackQueen() { return named(Material.DARK_OAK_FENCE, ChatColor.GRAY + "♛ Black Queen"); }
    public static ItemStack blackRook() { return named(Material.DARK_OAK_LOG, ChatColor.GRAY + "♜ Black Rook"); }
    public static ItemStack blackBishop() { return named(Material.DARK_OAK_STAIRS, ChatColor.GRAY + "♝ Black Bishop"); }
    public static ItemStack blackKnight() { return named(Material.DARK_OAK_TRAPDOOR, ChatColor.GRAY + "♞ Black Knight"); }
    public static ItemStack blackPawn() { return named(Material.DARK_OAK_PLANKS, ChatColor.GRAY + "♟ Black Pawn"); }

	public static ItemStack quitButton() {
		return named(Material.BARRIER, ChatColor.RED + "Quit Game");
	}

    public static ItemStack disabledSlot() {
        return named(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + "");
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


