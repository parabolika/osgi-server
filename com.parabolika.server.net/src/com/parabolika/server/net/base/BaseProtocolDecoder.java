package com.parabolika.server.net.base;

import java.nio.ByteBuffer;

import net.burtleburtle.bob.rand.ISAACAlgorithm;

import com.parabolika.server.common.Packet;
import com.parabolika.server.net.util.ProtocolConstants;

public class BaseProtocolDecoder {
	private ISAACAlgorithm cipher = null;

	public Packet decode(ByteBuffer buffer) {
		if(buffer.limit() == 0) return null;

		int packetId = buffer.get() & 0xFF;

		// cipher will be null if the keys haven't yet been exchanged
		if(cipher != null) {
			packetId = packetId - cipher.nextInt() & 0xFF;
		}

		int packetLength = getPacketLength(packetId, buffer);
		if(buffer.remaining() < packetLength) {
			return null;
		}
		byte[] packetData = new byte[packetLength];
		buffer.get(packetData);

		return new Packet(packetId, packetLength, packetData);
	}

	private int getPacketLength(int packetId, ByteBuffer buffer) {
		int possibleLength = ProtocolConstants.INCOMING_PACKET_LENGTHS[packetId];
		if(possibleLength == ProtocolConstants.PACKET_SIZE_BYTE) {
			return buffer.get() & 0xFF;
		} else if(possibleLength == ProtocolConstants.PACKET_SIZE_SHORT) {
			return buffer.getShort();
		}
		return possibleLength;
	}

	public void setCipher(ISAACAlgorithm cipher) {
		this.cipher = cipher;
	}
}
