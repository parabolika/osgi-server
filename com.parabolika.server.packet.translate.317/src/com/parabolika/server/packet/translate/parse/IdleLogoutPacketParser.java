package com.parabolika.server.packet.translate.parse;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.IdleLogoutPacket;
import com.parabolika.server.packet.translate.IPacketParser;
import com.parabolika.server.packet.translate.annotate.ParsesPackets;

@ParsesPackets(202)
public class IdleLogoutPacketParser implements IPacketParser {
	@Override
	public IPacketRepresentation parse(Packet packet) {
		return new IdleLogoutPacket();
	}
}
