package com.parabolika.server.packet.packets;

import java.util.Date;

@Called("ping")
public class PingPacket implements IPacketRepresentation {
	public final Date createdAt;

	public PingPacket() {
		this.createdAt = new Date();
	}
}
