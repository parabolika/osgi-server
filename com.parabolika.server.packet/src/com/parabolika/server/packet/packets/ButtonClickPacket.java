package com.parabolika.server.packet.packets;

@Called("button_click")
public class ButtonClickPacket implements IPacketRepresentation {
	public int buttonId;

	public ButtonClickPacket(int buttonId) {
		this.buttonId = buttonId;
	}
}
