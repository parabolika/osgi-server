package com.parabolika.server.packet.translate.build;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.MessagePacket;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;
import com.parabolika.server.packet.translate.io.RSOutputStream;

@BuildsPacket(MessagePacket.class)
public class MessagePacketBuilder implements IPacketBuilder {
	@Override
	public Packet build(IPacketRepresentation packetRep) {
		MessagePacket packet = (MessagePacket) packetRep;
		RSOutputStream output = new RSOutputStream();
		output.writeString(packet.message);

		return new Packet(253, output.getData());
	}
}
