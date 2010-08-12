package com.parabolika.server.packet.packets;

@Called("camera_move")
public class CameraMovePacket implements IPacketRepresentation {
	public final int cameraX, cameraY;

	public CameraMovePacket(int cameraX, int cameraY) {
		this.cameraX = cameraX;
		this.cameraY = cameraY;
	}
}
