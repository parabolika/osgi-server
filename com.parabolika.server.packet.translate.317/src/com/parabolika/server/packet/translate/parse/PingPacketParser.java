package com.parabolika.server.packet.translate.parse;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.PingPacket;
import com.parabolika.server.packet.translate.IPacketParser;
import com.parabolika.server.packet.translate.annotate.ParsesPackets;

@ParsesPackets(0)
public class PingPacketParser implements IPacketParser {
	@Override
	public IPacketRepresentation parse(Packet packet) {
		return new PingPacket();
	}
}
