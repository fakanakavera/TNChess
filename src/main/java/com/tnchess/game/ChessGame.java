package com.tnchess.game;

import com.tnchess.TNChessPlugin;
import com.tnchess.gui.ChessBoardGUI;
import org.bukkit.entity.Player;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;

public class ChessGame {

	private final Player player;
    private final TNChessPlugin plugin;
	private final ChessEngine engine;
	private final ChessBoardGUI gui;

    private int selectedFile = -1;
    private int selectedRank = -1;

    private boolean promotionPending = false;
    private int promFromFile, promFromRank, promToFile, promToRank;

    public ChessGame(TNChessPlugin plugin, Player player) {
        this.player = player;
        this.plugin = plugin;
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
        if (promotionPending) return; // wait for promotion selection
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
            // Check for promotion options
            java.util.List<Piece> options = engine.getPromotionOptions(selectedFile, selectedRank, file, rank);
            if (!options.isEmpty()) {
                beginPromotion(selectedFile, selectedRank, file, rank);
            } else {
                engine.makeMove(selectedFile, selectedRank, file, rank);
                guiRepaint();
                finishIfMated();
            }
        }
		clearSelection();
	}

    public boolean isPromotionPending() {
        return promotionPending;
    }

    public void handlePromotionClick(int guiRank) {
        if (!promotionPending) return;
        // Map rows 1..4 to Q, R, B, N
        Piece choice;
        Side side = engine.getSideToMove();
        switch (guiRank) {
            case 1: choice = (side == Side.WHITE ? Piece.WHITE_QUEEN : Piece.BLACK_QUEEN); break;
            case 2: choice = (side == Side.WHITE ? Piece.WHITE_ROOK : Piece.BLACK_ROOK); break;
            case 3: choice = (side == Side.WHITE ? Piece.WHITE_BISHOP : Piece.BLACK_BISHOP); break;
            case 4: choice = (side == Side.WHITE ? Piece.WHITE_KNIGHT : Piece.BLACK_KNIGHT); break;
            default: return;
        }
        boolean ok = engine.makePromotion(promFromFile, promFromRank, promToFile, promToRank, choice);
        promotionPending = false;
        gui.clearPromotionChoices();
        if (ok) {
            guiRepaint();
            finishIfMated();
        }
    }

    private void beginPromotion(int fromFile, int fromRank, int toFile, int toRank) {
        this.promotionPending = true;
        this.promFromFile = fromFile;
        this.promFromRank = fromRank;
        this.promToFile = toFile;
        this.promToRank = toRank;
        gui.showPromotionChoices(engine.getSideToMove());
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

    private void finishIfMated() {
        if (engine.isMated()) {
            Side toMove = engine.getSideToMove();
            String winner = (toMove == Side.WHITE ? "Black" : "White");
            player.sendMessage("Checkmate! " + winner + " wins.");
            plugin.getGameManager().endGame(player);
        }
    }
}


