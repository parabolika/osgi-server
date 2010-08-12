package com.parabolika.server.packet.packets;

@Called("message")
public class MessagePacket implements IPacketRepresentation {
	public final String message;

	public MessagePacket(String message) {
		this.message = message;
	}
}
