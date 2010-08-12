package com.parabolika.server.packet.translate.parse;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.HandshakePacket;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.translate.IPacketParser;
import com.parabolika.server.packet.translate.annotate.ParsesPackets;
import com.parabolika.server.packet.translate.io.RSInputStream;

@ParsesPackets(14)
public class HandshakePacketParser implements IPacketParser {
	@Override
	public IPacketRepresentation parse(Packet packet) {
		RSInputStream input = new RSInputStream(packet.getData());
		int nameHash = input.readUnsignedByte();

		return new HandshakePacket(nameHash);
	}
}
