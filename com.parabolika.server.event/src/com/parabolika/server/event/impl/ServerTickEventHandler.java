package com.parabolika.server.event.impl;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.parabolika.server.event.IGameEventManager;
import com.parabolika.server.model.World;
import com.parabolika.server.net.INetworkClient;
import com.parabolika.server.net.INetworkService;
import com.parabolika.server.packet.packets._DummyPacket;

public class ServerTickEventHandler implements EventHandler {
	private INetworkService networkService = null;
	private IGameEventManager gameEventManager = null;

	@Override
	public void handleEvent(Event event) {
		sendTickEvent("pre_player_tick");
		sendTickEvent("player_tick");
		sendTickEvent("post_player_tick");
	}

	public void setNetworkService(INetworkService networkService) {
		this.networkService = networkService;
	}

	public void setGameEventManager(IGameEventManager gameEventManager) {
		this.gameEventManager = gameEventManager;
	}

	private void sendTickEvent(String name) {
		// XXX: Thread-safe
		for(INetworkClient client : networkService.getClients()) {
			// Can be null if the client is not completely logged in yet
			if(World.getWorld().getPlayer(client.getUUID()) != null) {
				gameEventManager.sendEvent(name, client, new _DummyPacket());
			}
		}
	}
}
