package com.parabolika.server.packet.translate.build;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.ShowInterfacePacket;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;
import com.parabolika.server.packet.translate.io.RSOutputStream;

@BuildsPacket(ShowInterfacePacket.class)
public class ShowInterfacePacketBuilder implements IPacketBuilder {
	@Override
	public Packet build(IPacketRepresentation packetRep) {
		ShowInterfacePacket packet = (ShowInterfacePacket) packetRep;
		RSOutputStream output = new RSOutputStream();
		output.writeShort(packet.interfaceId);

		return new Packet(97, output.getData());
	}
}
