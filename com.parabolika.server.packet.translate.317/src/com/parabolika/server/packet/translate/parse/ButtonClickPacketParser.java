package com.parabolika.server.packet.translate.parse;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.ButtonClickPacket;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.translate.IPacketParser;
import com.parabolika.server.packet.translate.annotate.ParsesPackets;
import com.parabolika.server.packet.translate.io.RSInputStream;

@ParsesPackets(185)
public class ButtonClickPacketParser implements IPacketParser {
	@Override
	public IPacketRepresentation parse(Packet packet) {
		RSInputStream input = new RSInputStream(packet.getData());
		int buttonId = input.readShort();
		return new ButtonClickPacket(buttonId);
	}
}
