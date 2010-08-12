package com.parabolika.server.packet.packets;

@Called("handshake_response")
public class HandshakeResponsePacket implements IPacketRepresentation {
	public final int responseCode;
	public final long serverSessionKey;

	public HandshakeResponsePacket(int responseCode, long serverSessionKey) {
		this.responseCode = responseCode;
		this.serverSessionKey = serverSessionKey;
	}
}
