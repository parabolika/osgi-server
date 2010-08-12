package com.parabolika.server.packet.packets;

@Called("set_skill")
public class SetSkillPacket implements IPacketRepresentation {
	public final int skillId;
	public final int experience, level;

	public SetSkillPacket(int skillId, int experience, int level) {
		this.skillId = skillId;
		this.experience = experience;
		this.level = level;
	}
}
