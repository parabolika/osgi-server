package com.parabolika.server.packet.translate.parse;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.LoginPacket;
import com.parabolika.server.packet.translate.IPacketParser;
import com.parabolika.server.packet.translate.annotate.ParsesPackets;
import com.parabolika.server.packet.translate.io.RSInputStream;

@ParsesPackets({ 16, 18 })
public class LoginPacketParser implements IPacketParser {
	@Override
	public IPacketRepresentation parse(Packet packet) {
		RSInputStream input = new RSInputStream(packet.getData());
		int magic = input.readUnsignedByte();
		int clientVersion = input.readUnsignedShort();
		boolean highDetail = input.read() == 1;

		int[] fileChecksums = new int[9];
		for(int i = 0; i < fileChecksums.length; i++) {
			fileChecksums[i] = input.readInteger();
		}

		int blockLength = input.readUnsignedByte();
		int blockOpcode = input.readUnsignedByte();

		int[] sessionKeys = new int[4];
		for(int i = 0; i < sessionKeys.length; i++) {
			sessionKeys[i] = input.readInteger();
		}

		int userId = input.readInteger();
		String username = input.readString();
		String password = input.readString();

		return new LoginPacket(magic, clientVersion, highDetail, fileChecksums, blockLength,
				blockOpcode, sessionKeys, userId, username, password);
	}
}
