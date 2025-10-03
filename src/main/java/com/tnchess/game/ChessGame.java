package com.tnchess.game;

import com.tnchess.gui.ChessBoardGUI;
import org.bukkit.entity.Player;
import com.github.bhlangonijr.chesslib.Piece;

public class ChessGame {

	private final Player player;
	private final ChessEngine engine;
	private final ChessBoardGUI gui;

	private int selectedFile = -1;
	private int selectedRank = -1;

	public ChessGame(Player player) {
		this.player = player;
		this.engine = new ChessEngine();
		this.gui = new ChessBoardGUI(player, engine);
	}

	public void open() {
		gui.open();
	}

	public void close() {
		gui.restore();
		player.closeInventory();
	}

	public void handleBoardClick(int file, int rank) {
		if (!isWithinBoard(file, rank)) return;
		Piece piece = engine.getPieceAt(file, rank);
		if (noSelection()) {
			if (piece != Piece.NONE && piece.getPieceSide() == engine.getSideToMove()) {
				selectedFile = file;
				selectedRank = rank;
			}
			return;
		}
		// second click: try to move
		if (file == selectedFile && rank == selectedRank) {
			clearSelection();
			return;
		}
		if (engine.isLegalMove(selectedFile, selectedRank, file, rank)) {
			engine.makeMove(selectedFile, selectedRank, file, rank);
			guiRepaint();
		}
		clearSelection();
	}

	private void guiRepaint() {
		gui.repaintAll();
	}

	private boolean noSelection() {
		return selectedFile < 0 || selectedRank < 0;
	}

	private void clearSelection() {
		selectedFile = -1;
		selectedRank = -1;
	}

	private boolean isWithinBoard(int file, int rank) {
		return file >= 0 && file < 8 && rank >= 0 && rank < 8;
	}
}


