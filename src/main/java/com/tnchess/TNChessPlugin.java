package com.tnchess;

import org.bukkit.plugin.java.JavaPlugin;
import com.tnchess.commands.ChessTestCommand;
import com.tnchess.listeners.ChessInventoryListener;
import com.tnchess.listeners.ChessCloseListener;
import com.tnchess.listeners.QuitListener;
import com.tnchess.game.GameManager;
import com.tnchess.game.LobbyManager;
import com.tnchess.gui.LobbyGUI;
import com.tnchess.listeners.LobbyInventoryListener;

public class TNChessPlugin extends JavaPlugin {

	private GameManager gameManager;
	private LobbyManager lobbyManager;

	@Override
	public void onEnable() {
		getLogger().info("TNChess enabled");
		this.gameManager = new GameManager(this);
		this.lobbyManager = new LobbyManager();
		getCommand("chess").setExecutor(new ChessTestCommand(this));
		getServer().getPluginManager().registerEvents(new ChessInventoryListener(this), this);
		getServer().getPluginManager().registerEvents(new ChessCloseListener(this), this);
		getServer().getPluginManager().registerEvents(new QuitListener(this), this);
		getServer().getPluginManager().registerEvents(new LobbyInventoryListener(this), this);
	}

	@Override
	public void onDisable() {
		getLogger().info("TNChess disabled");
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public LobbyManager getLobbyManager() {
		return lobbyManager;
	}
}


