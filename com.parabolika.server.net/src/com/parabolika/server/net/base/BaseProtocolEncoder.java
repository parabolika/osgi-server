package com.parabolika.server.net.base;

import java.nio.ByteBuffer;

import net.burtleburtle.bob.rand.ISAACAlgorithm;

import com.parabolika.server.common.Packet;
import com.parabolika.server.net.util.ProtocolConstants;

public class BaseProtocolEncoder {
	private ISAACAlgorithm cipher = null;

	public ByteBuffer encode(Packet packet) {
		// Add three to account for possible id and length bytes
		ByteBuffer buffer = ByteBuffer.allocate(packet.getLength() + 3);
		if(!packet.isHeadless()) {
			byte packetId = (byte) (cipher == null ? packet.getId() : packet.getId() + cipher.nextInt());
			buffer.put(packetId);

			if(ProtocolConstants.OUTGOING_PACKET_SIZES[packet.getId()] == ProtocolConstants.PACKET_SIZE_BYTE) {
				buffer.put((byte) packet.getLength());
			} else if(ProtocolConstants.OUTGOING_PACKET_SIZES[packet.getId()] == ProtocolConstants.PACKET_SIZE_SHORT) {
				buffer.putShort((short) packet.getLength());
			}
		}

		buffer.put(packet.getData(), 0, packet.getLength());
		buffer.flip();
		return buffer;
	}

	public void setCipher(ISAACAlgorithm cipher) {
		this.cipher = cipher;
	}
}
