package com.tnchess.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import com.tnchess.TNChessPlugin;
import com.tnchess.game.GameManager;
import com.tnchess.gui.LobbyGUI;

public class ChessTestCommand implements CommandExecutor {

	private final TNChessPlugin plugin;

	public ChessTestCommand(TNChessPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
			return true;
		}
		Player player = (Player) sender;
        new LobbyGUI(player, plugin.getLobbyManager()).open();
		return true;
	}
}


