package com.tnchess.game;

import java.util.UUID;

public class Lobby {

	public enum State { WAITING, FULL }

	private final UUID id;
	private final UUID host;
	private UUID guest;
	private State state = State.WAITING;
	private String name;

	public Lobby(UUID host, String name) {
		this.id = UUID.randomUUID();
		this.host = host;
		this.name = name;
	}

	public UUID getId() { return id; }
	public UUID getHost() { return host; }
	public UUID getGuest() { return guest; }
	public State getState() { return state; }
	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public boolean join(UUID player) {
		if (state != State.WAITING) return false;
		if (player.equals(host)) return false;
		this.guest = player;
		this.state = State.FULL;
		return true;
	}
}
