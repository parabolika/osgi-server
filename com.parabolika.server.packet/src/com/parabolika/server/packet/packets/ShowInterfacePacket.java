package com.parabolika.server.packet.packets;

public class ShowInterfacePacket implements IPacketRepresentation {
	public final int interfaceId;

	public ShowInterfacePacket(int interfaceId) {
		this.interfaceId = interfaceId;
	}
}
