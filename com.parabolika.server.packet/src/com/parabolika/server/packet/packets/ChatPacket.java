package com.parabolika.server.packet.packets;

@Called("chat")
public class ChatPacket implements IPacketRepresentation {
	public final String message;
	public final int effects, color;

	public ChatPacket(String message, int effects, int color) {
		this.message = message;
		this.effects = effects;
		this.color = color;
	}
}
