package com.parabolika.server.packet.translate;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;

public interface IPacketParser {
	public IPacketRepresentation parse(Packet packet);
}
