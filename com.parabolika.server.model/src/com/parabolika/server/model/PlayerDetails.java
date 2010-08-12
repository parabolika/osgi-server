package com.parabolika.server.model;

public class PlayerDetails {
	private final String name, password;
	private final int rights;

	public PlayerDetails(String name, String password, int rights) {
		this.name = name;
		this.password = password;
		this.rights = rights;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public int getRights() {
		return rights;
	}
}
