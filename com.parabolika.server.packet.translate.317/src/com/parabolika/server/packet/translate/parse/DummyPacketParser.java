package com.parabolika.server.packet.translate.parse;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets._DummyPacket;
import com.parabolika.server.packet.translate.IPacketParser;
import com.parabolika.server.packet.translate.annotate.ParsesPackets;

@ParsesPackets({ 3, 77, 121, 210, 241 })
public class DummyPacketParser implements IPacketParser {
	@Override
	public IPacketRepresentation parse(Packet packet) {
		return new _DummyPacket();
	}
}
