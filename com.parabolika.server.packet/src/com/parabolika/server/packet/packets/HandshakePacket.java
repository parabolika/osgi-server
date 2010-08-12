package com.parabolika.server.packet.packets;

@Called("handshake")
public class HandshakePacket implements IPacketRepresentation {
	public final int nameHash;

	public HandshakePacket(int nameHash) {
		this.nameHash = nameHash;
	}
}
