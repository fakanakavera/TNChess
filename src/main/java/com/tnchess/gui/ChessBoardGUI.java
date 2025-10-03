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
    private boolean showingPromotion;

	public ChessBoardGUI(Player player, ChessEngine engine) {
		this.player = player;
		this.engine = engine;
		this.snapshot = InventorySnapshot.snapshotRows(player);
		this.inventory = Bukkit.createInventory(new ChessBoardHolder(), 54, "TNChess");
		paintQuitColumn();
		paintBottomFiller();
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

	private void paintBottomFiller() {
		ItemStack filler = Items.disabledSlot();
		player.getInventory().setItem(17, filler);
		player.getInventory().setItem(26, filler);
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

	public void showPromotionChoices(com.github.bhlangonijr.chesslib.Side sideToMove) {
		showingPromotion = true;
		// Fill quit column rows 1..4 with Q, R, B, N for current side
		inventory.setItem(1 * 9 + 8, sideToMove == com.github.bhlangonijr.chesslib.Side.WHITE ? Items.whiteQueen() : Items.blackQueen());
		inventory.setItem(2 * 9 + 8, sideToMove == com.github.bhlangonijr.chesslib.Side.WHITE ? Items.whiteRook() : Items.blackRook());
		inventory.setItem(3 * 9 + 8, sideToMove == com.github.bhlangonijr.chesslib.Side.WHITE ? Items.whiteBishop() : Items.blackBishop());
		inventory.setItem(4 * 9 + 8, sideToMove == com.github.bhlangonijr.chesslib.Side.WHITE ? Items.whiteKnight() : Items.blackKnight());
	}

	public void clearPromotionChoices() {
		showingPromotion = false;
		paintQuitColumn();
	}

	private ItemStack pieceItemAt(int file, int rank) {
		switch (engine.getPieceAt(file, rank)) {
			case WHITE_KING: return Items.whiteKing();
			case WHITE_QUEEN: return Items.whiteQueen();
			case WHITE_ROOK: return Items.whiteRook();
			case WHITE_BISHOP: return Items.whiteBishop();
			case WHITE_KNIGHT: return Items.whiteKnight();
			case WHITE_PAWN: return Items.whitePawn();
			case BLACK_KING: return Items.blackKing();
			case BLACK_QUEEN: return Items.blackQueen();
			case BLACK_ROOK: return Items.blackRook();
			case BLACK_BISHOP: return Items.blackBishop();
			case BLACK_KNIGHT: return Items.blackKnight();
			case BLACK_PAWN: return Items.blackPawn();
			default: return new ItemStack(Material.AIR);
		}
	}
}


