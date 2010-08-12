package com.parabolika.server.packet.packets;

@Called("map_region")
public class MapRegionPacket implements IPacketRepresentation {
	public final int regionX, regionY;

	public MapRegionPacket(int regionX, int regionY) {
		this.regionX = regionX;
		this.regionY = regionY;
	}
}
