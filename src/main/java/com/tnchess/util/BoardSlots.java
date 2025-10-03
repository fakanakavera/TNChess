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
		// Place pawns (rank 6) on the top of player inventory (18..25),
		// and the back rank (rank 7) on the bottom row (27..34)
		return (guiRank == 6 ? 18 : 27) + guiFile; // guiRank 6->18..25, 7->27..34
	}
}


