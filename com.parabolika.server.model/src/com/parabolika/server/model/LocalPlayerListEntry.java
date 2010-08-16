package com.parabolika.server.model;

/**
 * An entry in a player's list of local players containing some information
 * about their status relative to the owner's. For instance, an instance holds
 * state on whether the player is just being added to or removed from the list
 * (<code>BEING_ADDED</code>, <code>BEING_REMOVED</code>), meaning extra
 * information will have to be sent in the update packet, or if no changes were
 * made (<code>STEADY</code>).
 */
public class LocalPlayerListEntry {
	public enum LocalPlayerStatus { BEING_ADDED, STEADY, BEING_REMOVED };

	private final Player player;
	private LocalPlayerStatus status;

	public LocalPlayerListEntry(Player player, LocalPlayerStatus status) {
		this.player = player;
		this.status = status;
	}

	public Player getPlayer() {
		return player;
	}

	public LocalPlayerStatus getStatus() {
		return status;
	}

	public void setStatus(LocalPlayerStatus status) {
		this.status = status;
	}
}
