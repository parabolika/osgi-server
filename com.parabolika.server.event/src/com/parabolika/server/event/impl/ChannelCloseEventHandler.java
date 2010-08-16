package com.parabolika.server.event.impl;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.model.Player;
import com.parabolika.server.model.World;
import com.parabolika.server.net.INetworkClient;

public class ChannelCloseEventHandler implements EventHandler {
	private static final Logger logger = LoggerFactory.getLogger(ChannelCloseEventHandler.class);

	@Override
	public void handleEvent(Event event) {
		INetworkClient client = (INetworkClient) event.getProperty("client");
		logger.info("Connection closed with {}", client.getUUID());

		Player player = World.getWorld().getPlayer(client.getUUID());
		World.getWorld().removePlayer(player);
	}
}
