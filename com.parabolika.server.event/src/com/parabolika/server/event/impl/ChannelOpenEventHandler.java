package com.parabolika.server.event.impl;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.net.INetworkClient;

public class ChannelOpenEventHandler implements EventHandler {
	private static final Logger logger = LoggerFactory.getLogger(ChannelOpenEventHandler.class);

	@Override
	public void handleEvent(Event event) {
		INetworkClient client = (INetworkClient) event.getProperty("client");
		logger.info("Connection opened with {}", client.getUUID());
	}
}
