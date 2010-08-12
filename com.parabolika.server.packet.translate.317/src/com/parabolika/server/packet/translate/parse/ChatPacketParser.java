package com.parabolika.server.packet.translate.parse;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.ChatPacket;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.translate.IPacketParser;
import com.parabolika.server.packet.translate.annotate.ParsesPackets;
import com.parabolika.server.packet.translate.io.RSInputStream;
import com.parabolika.server.packet.translate.util.TranslationHelpers;

@ParsesPackets(4)
public class ChatPacketParser implements IPacketParser {
	@Override
	public IPacketRepresentation parse(Packet packet) {
		RSInputStream input = new RSInputStream(packet.getData());
		int effects = input.readByteA() & 0xFF;
		int color = input.readByteA() & 0xFF;
		int messageSize = packet.getLength() - 2;

		byte[] rawMessage = new byte[messageSize];
		input.read(rawMessage, 0, messageSize);

		byte[] message = new byte[messageSize];
		for(int i = 0; i < message.length; i++) {
			message[i] = (byte) (rawMessage[messageSize - i - 1] - 128);
		}
		String unpacked = TranslationHelpers.textUnpack(message, messageSize);

		return new ChatPacket(unpacked, effects, color);
	}
}
