package com.tnchess.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import com.tnchess.gui.holders.ChessBoardHolder;
import com.tnchess.TNChessPlugin;
import com.tnchess.game.ChessGame;
import org.bukkit.inventory.PlayerInventory;

public class ChessInventoryListener implements Listener {

	private final TNChessPlugin plugin;

	public ChessInventoryListener(TNChessPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Inventory top = e.getView().getTopInventory();
		if (top == null || !(top.getHolder() instanceof ChessBoardHolder)) return;
		e.setCancelled(true);
		Player player = (Player) e.getWhoClicked();
		ChessGame game = plugin.getGameManager().getGame(player);
		if (game == null) return;

		int raw = e.getRawSlot();
		int topSize = top.getSize(); // 54
		if (raw < topSize) {
			int file = raw % 9;
			int rank = raw / 9;
			// quit column or promotion selector
			if (file == 8) {
				if (game.isPromotionPending()) {
					// Rows 1..4 carry promotion options
					if (rank >= 1 && rank <= 4) {
						game.handlePromotionClick(rank);
					}
					return;
				}
				plugin.getGameManager().endGame(player);
				return;
			}
			if (file >= 0 && file <= 7 && rank >= 0 && rank <= 5) {
				game.handleBoardClick(file, rank);
			}
			return;
		}

		// bottom inventory clicks
		if (e.getClickedInventory() instanceof PlayerInventory) {
			int slot = e.getSlot(); // slot within player inventory 0..35
			// Use the top two rows of the main inventory section
			if (slot >= 9 && slot <= 16) {
				int file = slot - 9;   // rank 6 pawns
				int rank = 6;
				game.handleBoardClick(file, rank);
			} else if (slot >= 18 && slot <= 25) {
				int file = slot - 18;  // rank 7 back rank
				int rank = 7;
				game.handleBoardClick(file, rank);
			}
		}
	}

	@EventHandler
	public void onDrag(InventoryDragEvent e) {
		Inventory top = e.getView().getTopInventory();
		if (top == null || !(top.getHolder() instanceof ChessBoardHolder)) return;
		e.setCancelled(true);
	}
}


