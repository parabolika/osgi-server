package com.parabolika.server.net.netty;

import net.burtleburtle.bob.rand.ISAACAlgorithm;

import org.jboss.netty.channel.Channel;

import com.parabolika.server.common.Packet;
import com.parabolika.server.net.base.BaseNetworkClient;

public class NettyNetworkClient extends BaseNetworkClient {
	private Channel channel;

	@Override
	public void write(Packet packet) {
		channel.write(packet);
	}

	@Override
	public void setIncomingCipherKeys(int[] keys) {
		NettyProtocolDecoder decoder = channel.getPipeline().get(NettyProtocolDecoder.class);
		decoder.setCipher(new ISAACAlgorithm(keys));
	}

	@Override
	public void setOutgoingCipherKeys(int[] keys) {
		NettyProtocolEncoder encoder = channel.getPipeline().get(NettyProtocolEncoder.class);
		encoder.setCipher(new ISAACAlgorithm(keys));
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
