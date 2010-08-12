package com.parabolika.server.net.base;

import java.util.UUID;

import com.parabolika.server.net.INetworkClient;

public abstract class BaseNetworkClient implements INetworkClient {
	private final UUID uuid = UUID.randomUUID();

	@Override
	public UUID getUUID() {
		return uuid;
	}
}
