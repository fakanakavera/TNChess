package com.tnchess.game;

import java.util.*;
import java.util.stream.Collectors;

public class LobbyManager {

	private final Map<UUID, Lobby> lobbiesById = new LinkedHashMap<>();

	public Lobby createLobby(UUID host, String name) {
		Lobby lobby = new Lobby(host, name);
		lobbiesById.put(lobby.getId(), lobby);
		return lobby;
	}

	public boolean removeLobby(UUID id) {
		return lobbiesById.remove(id) != null;
	}

	public List<Lobby> listOpenLobbies() {
		return lobbiesById.values().stream()
				.filter(l -> l.getState() == Lobby.State.WAITING)
				.collect(Collectors.toList());
	}

	public Optional<Lobby> getLobby(UUID id) {
		return Optional.ofNullable(lobbiesById.get(id));
	}
}


