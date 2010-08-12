package com.parabolika.server.event.impl;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.common.EventBuilder;
import com.parabolika.server.event.IGameEventManager;
import com.parabolika.server.net.INetworkClient;
import com.parabolika.server.packet.packets.Called;
import com.parabolika.server.packet.packets.IPacketRepresentation;

public class GameEventManager implements IGameEventManager {
	private static final Logger logger = LoggerFactory.getLogger(GameEventManager.class);

	private EventAdmin eventAdmin = null;

	@Override
	public void sendEvent(String eventName, INetworkClient client,IPacketRepresentation packetRep) {
		// eventName can be null if this is a Packet event, and packetRep can be
		// null if this is a server tick event.
		if(eventName == null) {
			if(packetRep.getClass().isAnnotationPresent(Called.class)) {
				eventName = packetRep.getClass().getAnnotation(Called.class).value();
			} else {
				// All IPacketRepresentation implementations should have this
				logger.error("Class {} is missing Callable annotation.", packetRep.getClass().getName());
			}
		}

		Event globalEvent = new EventBuilder("com/parabolika/server/event/GAME_EVENT")
					.withOption("eventName", eventName)
					.withOption("packet", packetRep)
					.withOption("client", client).build();
		eventAdmin.postEvent(globalEvent);
	}

	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}
}