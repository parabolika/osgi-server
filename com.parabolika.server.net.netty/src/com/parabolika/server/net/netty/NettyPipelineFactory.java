package com.parabolika.server.net.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.osgi.service.event.EventAdmin;

import com.parabolika.server.common.GenericServiceTracker;

public class NettyPipelineFactory implements ChannelPipelineFactory {
	private final GenericServiceTracker<EventAdmin> eventAdminTracker;
	private final NettyNetworkService service;

	public NettyPipelineFactory(NettyNetworkService service, GenericServiceTracker<EventAdmin> eventAdminTracker) {
		this.service = service;
		this.eventAdminTracker = eventAdminTracker;
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("encoder", new NettyProtocolEncoder());
		pipeline.addLast("decoder", new NettyProtocolDecoder());
		pipeline.addLast("handler", new NettyNetworkClientHandler(service, eventAdminTracker));

		return pipeline;
	}
}
