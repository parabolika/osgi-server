package com.parabolika.server.packet.translate.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.IPacketService;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.IPacketParser;

public class PacketTranslationService implements IPacketService {
	private static final Logger logger = LoggerFactory.getLogger(PacketTranslationService.class);

	@Override
	public IPacketRepresentation parse(Packet packet) {
		IPacketParser parser = PacketTranslationServiceLoader.getParsers().get(packet.getId());
		if(parser != null) {
			return parser.parse(packet);
		} else {
			logger.warn("Unhandled packet {}", packet);
		}
		return null;
	}

	@Override
	public Packet build(IPacketRepresentation packetRep) {
		IPacketBuilder builder = PacketTranslationServiceLoader.getBuilders().get(packetRep.getClass());
		if(builder != null) {
			return builder.build(packetRep);
		} else {
			logger.warn("Unable to build packet from {}", packetRep);
		}
		return null;
	}
}
