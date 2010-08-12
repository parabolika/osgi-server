package com.parabolika.server.net.base;

import java.util.ArrayList;
import java.util.List;

import com.parabolika.server.net.INetworkClient;
import com.parabolika.server.net.INetworkService;

public abstract class BaseNetworkService implements INetworkService {
	private final List<INetworkClient> clients = new ArrayList<INetworkClient>();

	@Override
	public List<INetworkClient> getClients() {
		return clients;
	}
}
