package com.parabolika.server.model;

import java.util.UUID;

public class PlayerBuilder {
	private UUID uuid = null;
	private int index = -1;

	private String username = null;
	private String password = null;
	private int rights = -1;

	private Location location = Location.create(3268, 3420, 0);

	public PlayerBuilder withUUID(UUID uuid) {
		this.uuid = uuid;
		return this;
	}

	public PlayerBuilder withIndex(int index) {
		this.index = index;
		return this;
	}

	public PlayerBuilder withDetails(String username, String password, int rights) {
		this.username = username;
		this.password = password;
		this.rights = rights;
		return this;
	}

	public PlayerBuilder withLocation(Location location) {
		this.location = location;
		return this;
	}

	public PlayerBuilder merge(PlayerBuilder other) {
		if(other.uuid != null) this.uuid = other.uuid;
		if(other.index != -1) this.index = other.index;

		if(other.username != null) this.username = other.username;
		if(other.password != null) this.password = other.password;
		if(other.rights != -1) this.rights = other.rights;

		if(other.location != null) {
			this.location = other.location;
			System.out.println("new location: " + location);
		}

		return this;
	}

	public Player build() {
		return new Player(uuid, index, username, password, rights, location);
	}
}
