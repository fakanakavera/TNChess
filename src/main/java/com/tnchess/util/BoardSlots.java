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
		return (guiRank == 6 ? 27 : 18) + guiFile; // guiRank 6->27..34, 7->18..25
	}
}


