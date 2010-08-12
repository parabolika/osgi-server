package com.parabolika.server.event;

import com.parabolika.server.net.INetworkClient;
import com.parabolika.server.packet.packets.IPacketRepresentation;

/**
 * Handles the sending of events related to the game. This should generate a
 * <code>com/parabolika/server/event/GAME_EVENT</code> event when
 * <code>sendEvent</code> is called.
 * <p>
 * Game events differ from normal events in that they relate to the state of
 * player in-game. Whenever a packet is received, a game event is fired. A game
 * event is also fired on every server tick.
 */
public interface IGameEventManager {

	/**
	 * Prepares and sends the event, along with corresponding data, to the
	 * underlying event system.
	 *
	 * @param eventName the name of the event to be sent.  This is either null,
	 * 			if this is a packet event, or one of the following
	 * 			values: <code>pre_player_tick, player_tick,
	 * 			post_player_tick</code>
	 * @param client the associated client for this event
	 * @param packetRep the <code>PacketRepresentation</code> associated with
	 * 			event.  It may be <code>null</code> if this is a tick event.
	 */
	public void sendEvent(String eventName, INetworkClient client, IPacketRepresentation packetRep);
}
