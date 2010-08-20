package com.parabolika.server.net.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.osgi.service.event.EventAdmin;

import com.parabolika.server.common.GenericServiceTracker;
import com.parabolika.server.net.base.BaseNetworkService;

public class MinaNetworkService extends BaseNetworkService {
	private final GenericServiceTracker<EventAdmin> eventAdminTracker;

	private IoAcceptor acceptor;

	public MinaNetworkService(GenericServiceTracker<EventAdmin> eventAdminTracker) {
		this.eventAdminTracker = eventAdminTracker;
	}

	public void start() {
		acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MinaCodecFactory()));
		acceptor.setHandler(new MinaMultiHandler(this, eventAdminTracker));

		try {
			acceptor.bind(new InetSocketAddress(43594));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		acceptor.unbind();
	}
}
