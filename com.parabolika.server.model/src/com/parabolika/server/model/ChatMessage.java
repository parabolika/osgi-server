package com.parabolika.server.model;

/**
 * Represents a chat message that may be stored in a player's queue.  Messages
 * are only dispatched once per tick in the player's update packet, so a queue
 * must be built up containing backlogged messages.
 */
public class ChatMessage {
	private final String message;
	private final int effects, color;

	public ChatMessage(String message, int effects, int color) {
		this.message = message;
		this.effects = effects;
		this.color = color;
	}

	public String getMessage() {
		return message;
	}

	public int getEffects() {
		return effects;
	}

	public int getColor() {
		return color;
	}
}
