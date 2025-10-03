package com.tnchess.game;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class ChessEngine {

	private final Board board;

	public ChessEngine() {
		this.board = new Board(); // starts at initial position
	}

	public Side getSideToMove() {
		return board.getSideToMove();
	}

	public Piece getPieceAt(int file, int rank) {
		Square sq = squareOf(file, rank);
		return board.getPiece(sq);
	}

	public boolean isLegalMove(int fromFile, int fromRank, int toFile, int toRank) {
		Square from = squareOf(fromFile, fromRank);
		Square to = squareOf(toFile, toRank);
		return findMatchingMove(from, to) != null;
	}

	public boolean makeMove(int fromFile, int fromRank, int toFile, int toRank) {
		Square from = squareOf(fromFile, fromRank);
		Square to = squareOf(toFile, toRank);
		Move chosen = findMatchingMove(from, to);
		if (chosen == null) {
			return false;
		}
		board.doMove(chosen);
		return true;
	}

    public java.util.List<Piece> getPromotionOptions(int fromFile, int fromRank, int toFile, int toRank) {
        Square from = squareOf(fromFile, fromRank);
        Square to = squareOf(toFile, toRank);
        java.util.List<Move> legal;
        try {
            legal = MoveGenerator.generateLegalMoves(board);
        } catch (Exception e) {
            return java.util.List.of();
        }
        java.util.Set<Piece> pieces = new java.util.LinkedHashSet<>();
        for (Move mv : legal) {
            if (mv.getFrom().equals(from) && mv.getTo().equals(to)) {
                try {
                    Piece promo = mv.getPromotion();
                    if (promo != null && promo != Piece.NONE) {
                        pieces.add(promo);
                    }
                } catch (Throwable ignore) {
                    // Library without promotion getter; no options then
                }
            }
        }
        return new java.util.ArrayList<>(pieces);
    }

    public boolean makePromotion(int fromFile, int fromRank, int toFile, int toRank, Piece promotionPiece) {
        Square from = squareOf(fromFile, fromRank);
        Square to = squareOf(toFile, toRank);
        java.util.List<Move> legal;
        try {
            legal = MoveGenerator.generateLegalMoves(board);
        } catch (Exception e) {
            return false;
        }
        for (Move mv : legal) {
            if (mv.getFrom().equals(from) && mv.getTo().equals(to)) {
                try {
                    Piece promo = mv.getPromotion();
                    if (promo != null && promo.equals(promotionPiece)) {
                        board.doMove(mv);
                        return true;
                    }
                } catch (Throwable ignore) {
                    // If getPromotion is unavailable, fall back to normal move (shouldn't happen here)
                }
            }
        }
        return false;
    }

    public boolean isMated() {
        try {
            return board.isMated();
        } catch (Throwable t) {
            // Fallback: no legal moves and in check is treated as mated.
            java.util.List<Move> legal;
            try { legal = MoveGenerator.generateLegalMoves(board); } catch (Exception e) { return false; }
            if (!legal.isEmpty()) return false;
            try { return board.isKingAttacked(); } catch (Throwable t2) { return false; }
        }
    }

    // getSideToMove is already defined above

	public Set<long[]> getLegalDestinations(int fromFile, int fromRank) {
		Square from = squareOf(fromFile, fromRank);
		List<Move> legal;
		try {
			legal = MoveGenerator.generateLegalMoves(board);
		} catch (Exception e) {
			return Set.of();
		}
		Set<long[]> result = new HashSet<>();
		for (Move mv : legal) {
			if (mv.getFrom().equals(from)) {
				Square to = mv.getTo();
				int file = to.getFile().ordinal();
				int rank = to.getRank().ordinal();
				result.add(new long[] { file, rank });
			}
		}
		return result;
	}

	private static Square squareOf(int file, int rank) {
		// file 0..7 -> A..H, rank 0..7 -> 1..8 (top to bottom)
		int chessRank = rank + 1; // gui 0 -> rank 1 (white at top)
		char fileChar = (char) ('A' + file);
		String name = ("" + fileChar) + chessRank;
		return Square.fromValue(name);
	}

	private Move findMatchingMove(Square from, Square to) {
		List<Move> legal;
		try {
			legal = MoveGenerator.generateLegalMoves(board);
		} catch (Exception e) {
			return null;
		}
		Move fallback = null;
		for (Move mv : legal) {
			if (mv.getFrom().equals(from) && mv.getTo().equals(to)) {
				// Prefer queen promotion when multiple promotion choices exist
				try {
					// getPromotion may be null or Piece.NONE when not a promotion
					var promo = mv.getPromotion();
					if (promo != null && (promo == Piece.WHITE_QUEEN || promo == Piece.BLACK_QUEEN)) {
						return mv;
					}
				} catch (Throwable ignore) {
					// If the library version lacks getPromotion(), just pick the first match
					return mv;
				}
				if (fallback == null) fallback = mv;
			}
		}
		return fallback;
	}
}


