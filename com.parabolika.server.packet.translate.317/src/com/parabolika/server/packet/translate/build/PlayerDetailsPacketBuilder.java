package com.parabolika.server.packet.translate.build;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.PlayerDetailsPacket;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;
import com.parabolika.server.packet.translate.io.RSOutputStream;

@BuildsPacket(PlayerDetailsPacket.class)
public class PlayerDetailsPacketBuilder implements IPacketBuilder {
	@Override
	public Packet build(IPacketRepresentation packetRep) {
		PlayerDetailsPacket packet = (PlayerDetailsPacket) packetRep;
		RSOutputStream output = new RSOutputStream();

		output.writeByteA(packet.isMember ? 1 : 0);
		output.writeLEShortA(packet.playerIndex);

		return new Packet(249, output.getData());
	}
}
