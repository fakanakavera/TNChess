package com.tnchess.gui;

import com.tnchess.gui.holders.LobbyHolder;
import com.tnchess.util.Items;
import com.tnchess.game.Lobby;
import com.tnchess.game.LobbyManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LobbyGUI {

	private final Player player;
	private final LobbyManager lobbyManager;
	private final Inventory inventory;

	public LobbyGUI(Player player, LobbyManager lobbyManager) {
		this.player = player;
		this.lobbyManager = lobbyManager;
		this.inventory = Bukkit.createInventory(new LobbyHolder(), 54, "TNChess Lobbies");
		repaint();
	}

	public void open() {
		player.openInventory(inventory);
	}

	public void repaint() {
		inventory.clear();
		List<Lobby> open = lobbyManager.listOpenLobbies();
		int idx = 0;
		for (Lobby lobby : open) {
			if (idx >= 45) break;
			ItemStack icon = new ItemStack(Material.WHITE_BANNER);
			ItemMeta meta = icon.getItemMeta();
			if (meta != null) {
				meta.setDisplayName("Lobby: " + lobby.getName());
				icon.setItemMeta(meta);
			}
			inventory.setItem(idx++, icon);
		}
		// Create lobby button on bottom row center
		inventory.setItem(49, Items.simple(Material.EMERALD, "Create Lobby"));
	}
}


