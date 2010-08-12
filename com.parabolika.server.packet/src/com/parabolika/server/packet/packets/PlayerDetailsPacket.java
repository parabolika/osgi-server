package com.parabolika.server.packet.packets;

@Called("player_details")
public class PlayerDetailsPacket implements IPacketRepresentation {
	public final int playerIndex;
	public final boolean isMember;

	public PlayerDetailsPacket(int playerIndex, boolean isMember) {
		this.playerIndex = playerIndex;
		this.isMember = isMember;
	}
}
