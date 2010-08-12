package com.parabolika.server.packet.translate.parse;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.ObjectActionPacket;
import com.parabolika.server.packet.translate.IPacketParser;
import com.parabolika.server.packet.translate.annotate.ParsesPackets;
import com.parabolika.server.packet.translate.io.RSInputStream;

@ParsesPackets({ 132, 252 })
public class ObjectActionPacketParser implements IPacketParser {
	@Override
	public IPacketRepresentation parse(Packet packet) {
		RSInputStream input = new RSInputStream(packet.getData());
		if(packet.getId() == 132) {
			return parseFirstOption(input);
		} else if(packet.getId() == 252) {
			return parseSecondOption(input);
		}
		return null;
	}

	private ObjectActionPacket parseFirstOption(RSInputStream input) {
		int x = input.readLEShortA() & 0xFFFF;
		int id = input.readShort() & 0xFFFF;
		int y = input.readShortA() & 0xFFFF;
		return new ObjectActionPacket(1, id, x, y);
	}

	private ObjectActionPacket parseSecondOption(RSInputStream input) {
		int id = input.readLEShortA() & 0xFFFF;
		int y = input.readLEShort() & 0xFFFF;
		int x = input.readShortA() & 0xFFFF;
		return new ObjectActionPacket(2, id, x, y);
	}
}
