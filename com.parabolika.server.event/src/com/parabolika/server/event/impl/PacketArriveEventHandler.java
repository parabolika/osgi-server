package com.parabolika.server.event.impl;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.common.Packet;
import com.parabolika.server.net.INetworkClient;

public class PacketArriveEventHandler implements EventHandler {
	private static final Logger logger = LoggerFactory.getLogger(PacketArriveEventHandler.class);

	@Override
	public void handleEvent(Event event) {
		INetworkClient client = (INetworkClient) event.getProperty("client");
		Packet packet = (Packet) event.getProperty("packet");
		logger.debug("Message received from {}", client.getUUID() + ": " + packet);
	}
}
