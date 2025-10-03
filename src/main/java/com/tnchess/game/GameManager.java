package com.tnchess.game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class GameManager {

	private final Map<UUID, ChessGame> activeGames = new HashMap<>();

	public ChessGame startTestGame(Player player) {
		ChessGame existing = activeGames.remove(player.getUniqueId());
		if (existing != null) {
			existing.close();
		}
		ChessGame game = new ChessGame(player);
		activeGames.put(player.getUniqueId(), game);
		game.open();
		return game;
	}

	public ChessGame getGame(Player player) {
		return activeGames.get(player.getUniqueId());
	}

	public void endGame(Player player) {
		ChessGame game = activeGames.remove(player.getUniqueId());
		if (game != null) {
			game.close();
		}
	}
}


