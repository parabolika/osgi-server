package com.parabolika.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Collects and stores information regarding the connected players.
 */
public class World {
	private static final World WORLD = new World();

	private final List<Player> players = new ArrayList<Player>();

	public Player getPlayer(UUID uuid) {
		for(Player p : players) {
			if(p.getUUID().equals(uuid)) {
				return p;
			}
		}
		return null;
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public void removePlayer(Player player) {
		players.remove(player);
	}

	/**
	 * Determines the next player index available.  This is not always
	 * sequential.  For instance, if there are 100 players, indices 0-99, and
	 * player 50 leaves, the 49th index is now available.
	 */
	public int getNextAvailableIndex() {
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i) == null) {
				return i;
			}
		}
		return players.size();
	}

	public static World getWorld() {
		return WORLD;
	}
}
