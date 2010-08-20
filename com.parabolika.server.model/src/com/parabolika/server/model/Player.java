package com.parabolika.server.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

public class Player extends Entity {
	private final List<LocalPlayerListEntry> localPlayers = new ArrayList<LocalPlayerListEntry>();
	private final UpdateFlagSet updateFlags = new UpdateFlagSet();
	private final Queue<ChatMessage> chatQueue = new LinkedList<ChatMessage>();

	private final UUID uuid;
	private final PlayerDetails details;
	private final int index;

	public Player(UUID uuid, PlayerDetails details, int index) {
		this.uuid = uuid;
		this.details = details;
		this.index = index;
	}

	public UpdateFlagSet getUpdateFlags() {
		return updateFlags;
	}

	public List<LocalPlayerListEntry> getLocalPlayerEntries() {
		return localPlayers;
	}

	public Queue<ChatMessage> getChatQueue() {
		return chatQueue;
	}

	public PlayerDetails getDetails() {
		return details;
	}

	public int getIndex() {
		return index;
	}

	public UUID getUUID() {
		return uuid;
	}
}
