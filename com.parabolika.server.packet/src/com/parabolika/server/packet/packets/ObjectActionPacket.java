package com.parabolika.server.packet.packets;

@Called("object_action")
public class ObjectActionPacket implements IPacketRepresentation {
	public final int menuIndex;
	public final int objectId, objectX, objectY;

	public ObjectActionPacket(int menuIndex, int objectId, int objectX, int objectY) {
		this.menuIndex = menuIndex;
		this.objectId = objectId;
		this.objectX = objectX;
		this.objectY = objectY;
	}
}
