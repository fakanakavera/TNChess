package com.tnchess.util;

public final class BoardSlots {

	private BoardSlots() {}

	public static boolean isTopInventoryRow(int guiRank) {
		return guiRank >= 0 && guiRank <= 5;
	}

	public static int toTopInventorySlot(int guiRank, int guiFile) {
		return guiRank * 9 + guiFile; // 0..5 rows, 0..7 cols
	}

	public static int toPlayerInventorySlot(int guiRank, int guiFile) {
		// Map to the top two rows of the player's inventory grid:
		// rank 6 (pawns) -> 9..16 (top row of main inventory)
		// rank 7 (back rank) -> 18..25 (second row of main inventory)
		return (guiRank == 6 ? 9 : 18) + guiFile; // guiRank 6->9..16, 7->18..25
	}
}


