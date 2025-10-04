package com.tnchess.listeners;

import com.tnchess.gui.holders.LobbyHolder;
import com.tnchess.TNChessPlugin;
import com.tnchess.game.Lobby;
import com.tnchess.game.LobbyManager;
import com.tnchess.game.GameManager;
import com.tnchess.game.ChessGame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LobbyInventoryListener implements Listener {

	private final TNChessPlugin plugin;

	public LobbyInventoryListener(TNChessPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Inventory top = e.getView().getTopInventory();
		if (top == null || !(top.getHolder() instanceof LobbyHolder)) return;
		e.setCancelled(true);
		Player player = (Player) e.getWhoClicked();
		LobbyManager lm = plugin.getLobbyManager();
		GameManager gm = plugin.getGameManager();

		int slot = e.getRawSlot();
		if (slot == 49) {
			// create lobby
			Lobby lobby = lm.createLobby(player.getUniqueId(), player.getName() + "'s lobby");
			player.sendMessage("Lobby created. Waiting for player...");
			// Start a game for host in waiting mode
			ChessGame game = gm.startWaitingGame(player, lobby);
			return;
		}
		// Joining lobby by slot index mapping to list order
		List<Lobby> open = lm.listOpenLobbies();
		if (slot >= 0 && slot < open.size()) {
			Lobby chosen = open.get(slot);
			if (chosen.join(player.getUniqueId())) {
				player.sendMessage("Joined lobby. Starting game...");
				gm.startGuest(player, chosen);
			} else {
				player.sendMessage("Failed to join lobby.");
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		// no-op for now
	}
}


