package com.tnchess.game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import com.tnchess.TNChessPlugin;

public class GameManager {

    private final Map<UUID, ChessGame> activeGames = new HashMap<>();
    private final Map<UUID, ChessGame> gamesByLobby = new HashMap<>();
    private final TNChessPlugin plugin;

	public GameManager(TNChessPlugin plugin) {
		this.plugin = plugin;
	}

    public ChessGame startTestGame(Player player) {
        ChessGame existing = activeGames.remove(player.getUniqueId());
        if (existing != null) {
            existing.close();
        }
        ChessGame game = new ChessGame(plugin, player);
        activeGames.put(player.getUniqueId(), game);
        game.open();
        return game;
    }

    public ChessGame startWaitingGame(Player host, Lobby lobby) {
        // end any prior game for host
        ChessGame existing = activeGames.remove(host.getUniqueId());
        if (existing != null) {
            existing.close();
        }
        ChessGame game = new ChessGame(plugin, host);
        gamesByLobby.put(lobby.getId(), game);
        activeGames.put(host.getUniqueId(), game);
        game.open();
        return game;
    }

    public void startGuest(Player guest, Lobby lobby) {
        ChessGame game = gamesByLobby.get(lobby.getId());
        if (game == null) return;
        activeGames.put(guest.getUniqueId(), game);
        game.attachGuest(guest);
    }

    public ChessGame getGame(Player player) {
        return activeGames.get(player.getUniqueId());
    }

    public void endGame(Player player) {
        ChessGame game = activeGames.remove(player.getUniqueId());
        if (game != null) {
            // remove both players and any lobby mapping
            removeMappingsForGame(game);
            game.close();
        }
    }

    public void endGame(ChessGame game) {
        if (game == null) return;
        removeMappingsForGame(game);
        game.close();
    }

    private void removeMappingsForGame(ChessGame game) {
        // Remove any player mappings pointing to this game
        activeGames.entrySet().removeIf(e -> e.getValue() == game);
        // Remove lobby mapping if present
        gamesByLobby.entrySet().removeIf(e -> e.getValue() == game);
    }
}


