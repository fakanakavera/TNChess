package com.tnchess.gui;

import com.tnchess.gui.holders.ChessBoardHolder;
import com.tnchess.util.BoardSlots;
import com.tnchess.util.Items;
import com.tnchess.util.InventorySnapshot;
import com.tnchess.game.ChessEngine;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class ChessBoardGUI {

	private final Player player;
	private final Inventory inventory;
	private final InventorySnapshot snapshot;
	private final ChessEngine engine;

	public ChessBoardGUI(Player player, ChessEngine engine) {
		this.player = player;
		this.engine = engine;
		this.snapshot = InventorySnapshot.snapshotRows(player);
		this.inventory = Bukkit.createInventory(new ChessBoardHolder(), 54, "TNChess");
		paintQuitColumn();
		repaintAll();
	}

	public void open() {
		player.openInventory(inventory);
	}

	public void restore() {
		snapshot.restore(player);
	}

	private void paintQuitColumn() {
		ItemStack quit = Items.quitButton();
		for (int row = 0; row < 6; row++) {
			inventory.setItem(row * 9 + 8, quit);
		}
	}

	public void repaintAll() {
		for (int rank = 0; rank < 8; rank++) {
			for (int file = 0; file < 8; file++) {
				ItemStack stack = pieceItemAt(file, rank);
				if (BoardSlots.isTopInventoryRow(rank)) {
					inventory.setItem(BoardSlots.toTopInventorySlot(rank, file), stack);
				} else {
					player.getInventory().setItem(BoardSlots.toPlayerInventorySlot(rank, file), stack);
				}
			}
		}
	}

	private ItemStack pieceItemAt(int file, int rank) {
		switch (engine.getPieceAt(file, rank)) {
			case WHITE_KING: return Items.whitePiece("♔ White King");
			case WHITE_QUEEN: return Items.whitePiece("♕ White Queen");
			case WHITE_ROOK: return Items.whitePiece("♖ White Rook");
			case WHITE_BISHOP: return Items.whitePiece("♗ White Bishop");
			case WHITE_KNIGHT: return Items.whitePiece("♘ White Knight");
			case WHITE_PAWN: return Items.whitePiece("♙ White Pawn");
			case BLACK_KING: return Items.blackPiece("♚ Black King");
			case BLACK_QUEEN: return Items.blackPiece("♛ Black Queen");
			case BLACK_ROOK: return Items.blackPiece("♜ Black Rook");
			case BLACK_BISHOP: return Items.blackPiece("♝ Black Bishop");
			case BLACK_KNIGHT: return Items.blackPiece("♞ Black Knight");
			case BLACK_PAWN: return Items.blackPiece("♟ Black Pawn");
			default: return new ItemStack(Material.AIR);
		}
	}
}


