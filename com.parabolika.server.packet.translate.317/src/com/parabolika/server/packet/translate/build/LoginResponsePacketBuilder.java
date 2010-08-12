package com.parabolika.server.packet.translate.build;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.LoginResponsePacket;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;
import com.parabolika.server.packet.translate.io.RSOutputStream;

@BuildsPacket(LoginResponsePacket.class)
public class LoginResponsePacketBuilder implements IPacketBuilder {
	@Override
	public Packet build(IPacketRepresentation packetRep) {
		LoginResponsePacket packet = (LoginResponsePacket) packetRep;
		RSOutputStream output = new RSOutputStream();

		output.write(packet.responseCode);
		output.write(packet.playerStatus);
		output.write(packet.flagged);

		return new Packet(output.getData());
	}
}
