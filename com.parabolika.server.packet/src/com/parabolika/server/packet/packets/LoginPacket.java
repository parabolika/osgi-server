package com.parabolika.server.packet.packets;

@Called("login")
public class LoginPacket implements IPacketRepresentation {
	public final int magic;
	public final int clientVersion;
	public final boolean highDetail;
	public final int[] fileChecksums;
	public final int blockLength, blockOpcode;
	public final int[] sessionKeys;
	public final int userId;
	public final String username, password;

	public LoginPacket(int magic, int clientVersion,
			boolean highDetail, int[] fileChecksums, int blockLength,
			int blockOpcode, int[] sessionKeys, int userId, String username,
			String password) {
		this.magic = magic;
		this.clientVersion = clientVersion;
		this.highDetail = highDetail;
		this.fileChecksums = fileChecksums;
		this.blockLength = blockLength;
		this.blockOpcode = blockOpcode;
		this.sessionKeys = sessionKeys;
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
}
