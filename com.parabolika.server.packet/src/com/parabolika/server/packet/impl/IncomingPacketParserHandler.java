package com.parabolika.server.packet.impl;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

import com.parabolika.server.common.EventBuilder;
import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.IPacketService;
import com.parabolika.server.packet.packets.IPacketRepresentation;

/**
 * An event listener responsible for parsing incoming <code>Packet</code>s
 * from the <code>com/parabolika/server/net/PACKET_ARRIVE</code> event. It
 * then fires a <code>com/parabolika/server/packet/PACKET_PARSE</code> event,
 * which can be used to listen for parsed packets by higher-level services.
 */
public class IncomingPacketParserHandler implements EventHandler {
	private EventAdmin eventAdmin = null;
	private IPacketService packetService = null;

	@Override
	public void handleEvent(Event event) {
		Packet packet = (Packet) event.getProperty("packet");
		IPacketRepresentation packetRep = packetService.parse(packet);

		if(packetRep != null) { // null if we couldn't find a handler
			EventBuilder parseEventBuilder = new EventBuilder("com/parabolika/server/packet/PACKET_PARSE")
						.withOption("packet", packetRep);
			for(String propertyName : event.getPropertyNames()) {
				if(parseEventBuilder.getOptions().get(propertyName) == null) {
					parseEventBuilder.withOption(propertyName, event.getProperty(propertyName));
				}
			}
			eventAdmin.postEvent(parseEventBuilder.build());
		}
	}

	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	public void setPacketService(IPacketService packetService) {
		this.packetService = packetService;
	}
}