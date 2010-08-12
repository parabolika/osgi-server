package com.parabolika.server.net.netty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.common.GenericServiceTracker;

public class NettyNetworkClientHandler extends SimpleChannelHandler {
	private static final Logger logger = LoggerFactory.getLogger(NettyNetworkClientHandler.class);

	private final NettyNetworkService networkService;
	private final NettyNetworkClient client;
	private final GenericServiceTracker<EventAdmin> eventAdminTracker;

	private Map<String, Object> defaultEventData;
	private ChannelHandlerContext context = null;

	public NettyNetworkClientHandler(NettyNetworkService networkService, GenericServiceTracker<EventAdmin> eventAdminTracker) {
		this.networkService = networkService;
		this.client = new NettyNetworkClient();
		this.eventAdminTracker = eventAdminTracker;

		defaultEventData = new HashMap<String, Object>();
		defaultEventData.put("client", client);
		defaultEventData = Collections.unmodifiableMap(defaultEventData);

		networkService.getClients().add(client);
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelOpen(ctx, e);

		context = ctx;
		client.setChannel(context.getChannel());

		Event event = new Event("com/parabolika/server/net/CHANNEL_OPEN", defaultEventData);
		eventAdminTracker.getService().postEvent(event);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		super.messageReceived(ctx, e);

		Map<String, Object> eventData = new HashMap<String, Object>(defaultEventData);
		eventData.put("packet", e.getMessage());
		Event event = new Event("com/parabolika/server/net/PACKET_ARRIVE", eventData);
		eventAdminTracker.getService().postEvent(event);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelClosed(ctx, e);

		networkService.getClients().remove(client);

		Event event = new Event("com/parabolika/server/net/CHANNEL_CLOSE", defaultEventData);
		eventAdminTracker.getService().postEvent(event);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.error("An error has occured!", e.getCause());
	}
}
