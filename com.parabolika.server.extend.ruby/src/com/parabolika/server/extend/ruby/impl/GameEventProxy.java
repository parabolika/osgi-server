package com.parabolika.server.extend.ruby.impl;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.parabolika.server.model.World;
import com.parabolika.server.net.INetworkClient;
import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.packets._DummyPacket;

/**
 * Receives events from the underlying event system and publishes them to the
 * {@link ScriptManager}.  Three possible parameters are attached: <code>
 * client, packet, and player</code>.  If this is a tick event,
 * <code>packet</code> should be an instance of {@link _DummyPacket}.
 */
public class GameEventProxy implements EventHandler {
	private final ScriptManager scriptManager;

	public GameEventProxy(ScriptManager scriptManager) {
		this.scriptManager = scriptManager;
	}

	@Override
	public void handleEvent(Event event) {
		Map<String, Object> params = getParamsMap(event);

		String eventName = (String) event.getProperty("eventName");
		scriptManager.callScripts(eventName, params);
	}

	private static Map<String, Object> getParamsMap(Event event) {
		Map<String, Object> params = new HashMap<String, Object>();

		INetworkClient client = (INetworkClient) event.getProperty("client");
		IPacketRepresentation packet = (IPacketRepresentation) event.getProperty("packet");

		params.put("client", client);
		params.put("packet", packet);
		params.put("player", World.getWorld().getPlayer(client.getUUID()));

		return params;
	}
}
