package com.parabolika.server.event.impl;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.event.IGameEventManager;
import com.parabolika.server.net.INetworkClient;
import com.parabolika.server.packet.packets.IPacketRepresentation;

public class PacketParseEventHandler implements EventHandler {
	private static final Logger logger = LoggerFactory.getLogger(PacketParseEventHandler.class);

	private IGameEventManager gameEventManager = null;

	@Override
	public void handleEvent(Event event) {
		INetworkClient client = (INetworkClient) event.getProperty("client");
		IPacketRepresentation packetRep = (IPacketRepresentation) event.getProperty("packet");
		logger.debug("Packet parsed with {}", client.getUUID());

		gameEventManager.sendEvent(null, client, packetRep);
	}

	public void setGameEventManager(IGameEventManager gameEventManager) {
		this.gameEventManager = gameEventManager;
	}
}
