package com.parabolika.server.packet.translate.parse;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.WalkPacket;
import com.parabolika.server.packet.translate.IPacketParser;
import com.parabolika.server.packet.translate.annotate.ParsesPackets;
import com.parabolika.server.packet.translate.io.RSInputStream;

@ParsesPackets({ 98, 164, 248 })
public class WalkPacketParser implements IPacketParser {
	@Override
	public IPacketRepresentation parse(Packet packet) {
		RSInputStream input = new RSInputStream(packet.getData());

		int size = packet.getLength();
		if(packet.getId() == 248) {
			size -= 14;
		}

		int steps = (size - 5) / 2;
		if(steps < 0) steps = 0;
		int[][] path = new int[steps][2];

		int firstX = input.readLEShortA();
		for(int i = 0; i < steps; i++) {
			path[i][0] = (byte) input.read();
			path[i][1] = (byte) input.read();
		}
		int firstY = input.readLEShort();
		boolean runSteps = input.readByteC() == 1;

		return new WalkPacket(steps, runSteps, path, firstX, firstY);
	}
}
