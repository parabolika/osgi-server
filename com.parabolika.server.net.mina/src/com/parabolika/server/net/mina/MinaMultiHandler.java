package com.parabolika.server.net.mina;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.osgi.service.event.EventAdmin;

import com.parabolika.server.common.GenericServiceTracker;

/**
 * Workaround because, as far as I can tell, MINA sucks.
 */
public class MinaMultiHandler extends IoHandlerAdapter {
	private final MinaNetworkService networkService;
	private final GenericServiceTracker<EventAdmin> eventAdminTracker;
	private final Map<IoSession, IoHandler> handlers = new HashMap<IoSession, IoHandler>();

	public MinaMultiHandler(MinaNetworkService networkService,
			GenericServiceTracker<EventAdmin> eventAdminTracker) {
		this.networkService = networkService;
		this.eventAdminTracker = eventAdminTracker;
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);

		IoHandler handler = new MinaNetworkClientHandler(networkService, eventAdminTracker);
		handlers.put(session, handler);
		handler.sessionOpened(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);

		IoHandler handler = handlers.get(session);
		handler.messageReceived(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);

		IoHandler handler = handlers.remove(session);
		handler.sessionClosed(session);
	}
}
