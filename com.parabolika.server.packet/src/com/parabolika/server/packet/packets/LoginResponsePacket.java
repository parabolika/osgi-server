package com.parabolika.server.packet.packets;

@Called("login_response")
public class LoginResponsePacket implements IPacketRepresentation {
	public final int responseCode;
	public final int playerStatus;
	public final int flagged;

	public LoginResponsePacket(int responseCode, int playerStatus, int flagged) {
		this.responseCode = responseCode;
		this.playerStatus = playerStatus;
		this.flagged = flagged;
	}
}
