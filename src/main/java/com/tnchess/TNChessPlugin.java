package com.tnchess;

import org.bukkit.plugin.java.JavaPlugin;
import com.tnchess.commands.ChessTestCommand;
import com.tnchess.listeners.ChessInventoryListener;
import com.tnchess.listeners.ChessCloseListener;
import com.tnchess.listeners.QuitListener;
import com.tnchess.game.GameManager;

public class TNChessPlugin extends JavaPlugin {

	private GameManager gameManager;

	@Override
	public void onEnable() {
		getLogger().info("TNChess enabled");
		this.gameManager = new GameManager(this);
		getCommand("chess").setExecutor(new ChessTestCommand(this));
		getServer().getPluginManager().registerEvents(new ChessInventoryListener(this), this);
		getServer().getPluginManager().registerEvents(new ChessCloseListener(this), this);
		getServer().getPluginManager().registerEvents(new QuitListener(this), this);
	}

	@Override
	public void onDisable() {
		getLogger().info("TNChess disabled");
	}

	public GameManager getGameManager() {
		return gameManager;
	}
}


