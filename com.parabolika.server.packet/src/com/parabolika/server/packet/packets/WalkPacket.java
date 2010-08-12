package com.parabolika.server.packet.packets;

@Called("walk")
public class WalkPacket implements IPacketRepresentation {
	public final int steps;
	public final boolean runSteps;
	public final int[][] path;
	public final int firstX, firstY;

	public WalkPacket(int steps, boolean runSteps, int[][] path, int firstX, int firstY) {
		this.steps = steps;
		this.runSteps = runSteps;
		this.path = path;
		this.firstX = firstX;
		this.firstY = firstY;
	}
}
