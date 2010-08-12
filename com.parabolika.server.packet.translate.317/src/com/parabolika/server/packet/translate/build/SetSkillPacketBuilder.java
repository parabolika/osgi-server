package com.parabolika.server.packet.translate.build;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.SetSkillPacket;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;
import com.parabolika.server.packet.translate.io.RSOutputStream;

@BuildsPacket(SetSkillPacket.class)
public class SetSkillPacketBuilder implements IPacketBuilder {
	@Override
	public Packet build(IPacketRepresentation packetRep) {
		SetSkillPacket packet = (SetSkillPacket) packetRep;
		RSOutputStream output = new RSOutputStream();

		output.write(packet.skillId);
		output.writeInteger1(packet.experience);
		output.write(packet.level);

		return new Packet(134, output.getData());
	}
}
