package com.parabolika.server.packet.translate.build;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.SetInterfacePacket;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;
import com.parabolika.server.packet.translate.io.RSOutputStream;

@BuildsPacket(SetInterfacePacket.class)
public class SetInterfacePacketBuilder implements IPacketBuilder {
	@Override
	public Packet build(IPacketRepresentation packetRep) {
		SetInterfacePacket packet = (SetInterfacePacket) packetRep;
		RSOutputStream output = new RSOutputStream();

		output.writeShort(packet.interfaceId);
		output.writeByteA(packet.sidebarId);

		return new Packet(71, output.getData());
	}
}
