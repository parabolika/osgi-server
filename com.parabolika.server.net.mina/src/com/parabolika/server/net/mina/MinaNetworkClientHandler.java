package com.parabolika.server.net.mina;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.parabolika.server.common.GenericServiceTracker;

public class MinaNetworkClientHandler extends IoHandlerAdapter {
	private final MinaNetworkService networkService;
	private final GenericServiceTracker<EventAdmin> eventAdminTracker;
	private final MinaNetworkClient client;
	
	private Map<String, Object> defaultEventData;

	public MinaNetworkClientHandler(MinaNetworkService networkService,
			GenericServiceTracker<EventAdmin> eventAdminTracker) {
		this.networkService = networkService;
		this.client = new MinaNetworkClient();
		this.eventAdminTracker = eventAdminTracker;

		defaultEventData = new HashMap<String, Object>();
		defaultEventData.put("client", client);
		defaultEventData = Collections.unmodifiableMap(defaultEventData);

		networkService.getClients().add(client);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);

		client.setSession(session);

		Event event = new Event("com/parabolika/server/net/CHANNEL_OPEN", defaultEventData);
		eventAdminTracker.getService().postEvent(event);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);

		Map<String, Object> eventData = new HashMap<String, Object>(defaultEventData);
		eventData.put("packet", message);
		Event event = new Event("com/parabolika/server/net/PACKET_ARRIVE", eventData);
		eventAdminTracker.getService().postEvent(event);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);

		networkService.getClients().remove(client);

		Event event = new Event("com/parabolika/server/net/CHANNEL_CLOSE", defaultEventData);
		eventAdminTracker.getService().postEvent(event);
	}
}
