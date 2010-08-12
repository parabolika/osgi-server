package com.parabolika.server.packet.translate.build;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.CameraResetPacket;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;

@BuildsPacket(CameraResetPacket.class)
public class CameraResetPacketBuilder implements IPacketBuilder {
	@Override
	public Packet build(IPacketRepresentation packetRep) {
		return new Packet(107, new byte[0]);
	}
}
