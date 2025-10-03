package com.tnchess.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import com.tnchess.gui.holders.ChessBoardHolder;
import com.tnchess.TNChessPlugin;
import org.bukkit.entity.Player;

public class ChessCloseListener implements Listener {

	private final TNChessPlugin plugin;

	public ChessCloseListener(TNChessPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (e.getInventory().getHolder() instanceof ChessBoardHolder) {
			Player player = (Player) e.getPlayer();
			plugin.getGameManager().endGame(player);
		}
	}
}


