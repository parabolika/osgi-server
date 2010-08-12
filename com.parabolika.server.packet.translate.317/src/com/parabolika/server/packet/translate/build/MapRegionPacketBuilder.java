package com.parabolika.server.packet.translate.build;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.MapRegionPacket;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;
import com.parabolika.server.packet.translate.io.RSOutputStream;

@BuildsPacket(MapRegionPacket.class)
public class MapRegionPacketBuilder implements IPacketBuilder {
	@Override
	public Packet build(IPacketRepresentation packetRep) {
		MapRegionPacket packet = (MapRegionPacket) packetRep;
		RSOutputStream output = new RSOutputStream();

		output.writeShortA(packet.regionX + 6);
		output.writeShort(packet.regionY + 6);
		return new Packet(73, output.getData());
	}
}
