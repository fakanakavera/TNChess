package com.tnchess.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import com.tnchess.TNChessPlugin;
import org.bukkit.entity.Player;

public class QuitListener implements Listener {

	private final TNChessPlugin plugin;

	public QuitListener(TNChessPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		plugin.getGameManager().endGame(player);
	}
}


