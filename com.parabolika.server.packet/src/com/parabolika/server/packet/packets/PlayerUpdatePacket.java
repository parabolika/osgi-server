package com.parabolika.server.packet.packets;

import com.parabolika.server.model.Player;

@Called("player_update")
public class PlayerUpdatePacket implements IPacketRepresentation {
	public final Player player;

	public PlayerUpdatePacket(Player player) {
		this.player = player;
	}
}
