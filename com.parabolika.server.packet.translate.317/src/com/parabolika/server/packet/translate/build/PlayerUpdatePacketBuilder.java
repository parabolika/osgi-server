package com.parabolika.server.packet.translate.build;

import java.util.EnumSet;

import com.parabolika.server.common.Packet;
import com.parabolika.server.model.ChatMessage;
import com.parabolika.server.model.LocalPlayerListEntry;
import com.parabolika.server.model.LocalPlayerListEntry.LocalPlayerStatus;
import com.parabolika.server.model.Player;
import com.parabolika.server.model.UpdateFlagSet;
import com.parabolika.server.model.UpdateFlagSet.UpdateType;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets.PlayerUpdatePacket;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;
import com.parabolika.server.packet.translate.io.RSOutputStream;
import com.parabolika.server.packet.translate.util.TranslationHelpers;

@BuildsPacket(PlayerUpdatePacket.class)
public class PlayerUpdatePacketBuilder implements IPacketBuilder {
	private enum PlayerStatus { CURRENT_PLAYER, NEW_PLAYER };

	@Override
	public Packet build(IPacketRepresentation packetRep) {
		Player player = ((PlayerUpdatePacket) packetRep).player;

		RSOutputStream output = new RSOutputStream();
		RSOutputStream updateBlock = new RSOutputStream();

		updateThisPlayerMovement(output, player);
		if(player.getUpdateFlags().isUpdateRequired()) {
			updatePlayer(updateBlock, player, PlayerStatus.CURRENT_PLAYER, true);
		}

		int playerCount = 0;
		for(LocalPlayerListEntry entry : player.getLocalPlayerEntries()) {
			if(entry.getStatus() != LocalPlayerStatus.BEING_ADDED) {
				playerCount++;
			}
		}

		output.writeBits(8, playerCount);
		for(LocalPlayerListEntry entry : player.getLocalPlayerEntries()) {
			Player updatePlayer = entry.getPlayer();
			if(entry.getStatus() == LocalPlayerStatus.NO_CHANGE) {
				updatePlayerMovement(output, updatePlayer);
				if(updatePlayer.getUpdateFlags().isUpdateRequired()) {
					updatePlayer(updateBlock, updatePlayer, PlayerStatus.CURRENT_PLAYER, false);
				}
			} else if(entry.getStatus() == LocalPlayerStatus.BEING_REMOVED) {
				output.writeBits(1, 1);
				output.writeBits(2, 3);
			} else if(entry.getStatus() == LocalPlayerStatus.BEING_ADDED) {
				addNewPlayer(output, player, updatePlayer);
				updatePlayer(updateBlock, updatePlayer, PlayerStatus.NEW_PLAYER, false);
			}
		}

		if(updateBlock.getLength() > 0) {
			output.writeBits(11, 2047);
			output.writeBytes(updateBlock.getData());
		}

		return new Packet(81, output.getData());
	}

	private void addNewPlayer(RSOutputStream output, Player ourPlayer, Player otherPlayer) {
		output.writeBits(11, otherPlayer.getIndex());

		output.writeBits(1, 1);
		output.writeBits(1, 1);

		int yPos = otherPlayer.getLocation().getY() - ourPlayer.getLocation().getY();
		int xPos = otherPlayer.getLocation().getX() - ourPlayer.getLocation().getX();

		output.writeBits(5, yPos); // XXX UPDATE: Multiple people on top of each other disappear
		output.writeBits(5, xPos);
	}

	private void updateThisPlayerMovement(RSOutputStream output, Player player) {
		if(player.isTeleporting() || player.isMapRegionChanging()) {
			output.writeBits(1, 1);
			output.writeBits(2, 3);
			output.writeBits(2, 0); // z
			output.writeBits(1, player.isTeleporting() ? 1 : 0);
			output.writeBits(1, isUpdateRequired(player.getUpdateFlags()));

			output.writeBits(7, player.getLastKnownRegion().getLocalY());
			output.writeBits(7, player.getLastKnownRegion().getLocalX());
		} else {
			if(player.getSprites().getPrimarySprite() == -1) {
				// no movement
				if(player.getUpdateFlags().isUpdateRequired()) {
					output.writeBits(1, 1);
					output.writeBits(2, 0);
				} else {
					output.writeBits(1, 0);
				}
			} else {
				if(player.getSprites().getSecondarySprite() == -1) {
					// walk
					output.writeBits(1, 1);
					output.writeBits(2, 1);
					output.writeBits(3, player.getSprites().getPrimarySprite());
					output.writeBits(1, isUpdateRequired(player.getUpdateFlags()));
				} else {
					// run
					output.writeBits(1, 1);
					output.writeBits(2, 2);
					output.writeBits(3, player.getSprites().getPrimarySprite());
					output.writeBits(3, player.getSprites().getSecondarySprite());
					output.writeBits(1, isUpdateRequired(player.getUpdateFlags()));
				}
			}
		}
	}

	private void updatePlayerMovement(RSOutputStream output, Player player) {
		if(player.getSprites().getPrimarySprite() == -1) {
			// no movement
			if(player.getUpdateFlags().isUpdateRequired()) {
				output.writeBits(1, 1);
				output.writeBits(2, 0);
			} else {
				output.writeBits(1, 0);
			}
		} else if(player.getSprites().getSecondarySprite() == -1) {
			// walk
			output.writeBits(1, 1);
			output.writeBits(2, 1);
			output.writeBits(3, player.getSprites().getPrimarySprite());
			output.writeBits(1, isUpdateRequired(player.getUpdateFlags()));
		} else {
			// run
			output.writeBits(1, 1);
			output.writeBits(2, 2);
			output.writeBits(3, player.getSprites().getPrimarySprite());
			output.writeBits(3, player.getSprites().getSecondarySprite());
			output.writeBits(1, isUpdateRequired(player.getUpdateFlags()));
		}
	}

	private void updatePlayer(RSOutputStream output, Player player,
			PlayerStatus status, boolean ourPlayer) {
		EnumSet<UpdateType> flagSet = player.getUpdateFlags().getFlags();
		RSOutputStream updateBlock = new RSOutputStream();

		int mask = 0;
		if(flagSet.contains(UpdateType.CHAT) && !player.getChatQueue().isEmpty() && !ourPlayer) {
			mask |= 0x80;
			appendChatMessageUpdate(updateBlock, player, player.getChatQueue().peek());
		}
		if(flagSet.contains(UpdateType.APPEARANCE) || status == PlayerStatus.NEW_PLAYER) {
			mask |= 0x10;
			appendPlayerAppearanceUpdate(updateBlock, player);
		}

		if(mask >= 0x100) {
			mask |= 0x40;
			output.write(mask & 0xFF);
			output.write(mask >> 8);
		} else {
			output.write(mask);
		}

		output.writeBytes(updateBlock.getData());
	}

	private void appendPlayerAppearanceUpdate(RSOutputStream output, Player player) {
		RSOutputStream props = new RSOutputStream();
		props.write(0); // gender
		props.write(0); // skull icon

		props.writeShort(0x200 + 1149); // helmet
		props.writeShort(0x200 + 1478); // amulet
		props.writeShort(0x200 + 1052); // cape
		props.writeShort(0x200 + 7158); // weapon
		props.writeShort(0x200 + 3140); // chest
		props.writeShort(0x200 + 1187); // shield
		props.writeShort(0x200 + 3140); // plate
		// props.writeShort(0x200 + 10); // chest arms
		props.writeShort(0x200 + 4087); // legs
		props.writeShort(0x200 + 1149); // helmet
		props.writeShort(0x200 + 1580); // gloves
		props.writeShort(0x200 + 88); // boots
		props.write(0); // beard

		props.write(0); // colors
		props.write(0);
		props.write(0);
		props.write(0);
		props.write(0);

		props.writeShort(0x328); // stand
		props.writeShort(0x337); // stand turn
		props.writeShort(0x333); // walk
		props.writeShort(0x334); // turn 180
		props.writeShort(0x335); // turn 90 cw
		props.writeShort(0x336); // turn 90 ccw
		props.writeShort(0x338); // run

		props.writeLong(TranslationHelpers.nameToLong(player.getDetails().getName())); // name
		props.write(100); // combat level
		props.writeShort(0); // skill level instead of combat level

		output.writeByteC(props.getLength());
		output.writeBytes(props.getData());
	}

	private void appendChatMessageUpdate(RSOutputStream output, Player player, ChatMessage chat) {
		output.writeLEShort((chat.getColor() & 0xFF) << 8 | chat.getEffects() & 0xFF);
		output.write(player.getDetails().getRights());

		byte[] packed = new byte[chat.getMessage().length()];
		TranslationHelpers.textPack(packed, chat.getMessage());
		output.writeByteC(packed.length);
		for(int i = packed.length - 1; i >= 0; i--) {
			output.write(packed[i]);
		}
	}

	private static int isUpdateRequired(UpdateFlagSet flags) {
		return flags.isUpdateRequired() ? 1 : 0;
	}
}
