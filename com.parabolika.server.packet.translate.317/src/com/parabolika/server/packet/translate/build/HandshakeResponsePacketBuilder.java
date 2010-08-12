package com.parabolika.server.packet.translate.build;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.HandshakeResponsePacket;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;
import com.parabolika.server.packet.translate.io.RSOutputStream;

@BuildsPacket(HandshakeResponsePacket.class)
public class HandshakeResponsePacketBuilder implements IPacketBuilder {
	@Override
	public Packet build(IPacketRepresentation packetRep) {
		HandshakeResponsePacket packet = (HandshakeResponsePacket) packetRep;
		RSOutputStream output = new RSOutputStream();
		for(int i = 0; i < 8; i++) {
			output.write(0);
		}
		output.write(packet.responseCode);
		output.writeLong(packet.serverSessionKey);
		return new Packet(output.getData());
	}
}
