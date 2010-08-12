package com.parabolika.server.packet.packets;

@Called("set_interface")
public class SetInterfacePacket implements IPacketRepresentation {
	public final int sidebarId, interfaceId;

	public SetInterfacePacket(int sidebarId, int interfaceId) {
		this.sidebarId = sidebarId;
		this.interfaceId = interfaceId;
	}
}
