package com.parabolika.server.net.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.osgi.service.event.EventAdmin;

import com.parabolika.server.common.GenericServiceTracker;
import com.parabolika.server.net.base.BaseNetworkService;

public class NettyNetworkService extends BaseNetworkService {
	private final ServerBootstrap bootstrap;

	private Channel channel;

	public NettyNetworkService(GenericServiceTracker<EventAdmin> eventAdminTracker) {
		this.bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new NettyPipelineFactory(this, eventAdminTracker));
	}

	public void start() {
		channel = bootstrap.bind(new InetSocketAddress(43594));
	}

	public void stop() {
		channel.close();
	}
}
