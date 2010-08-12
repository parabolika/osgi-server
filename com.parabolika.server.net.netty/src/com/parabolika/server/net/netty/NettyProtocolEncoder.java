package com.parabolika.server.net.netty;

import net.burtleburtle.bob.rand.ISAACAlgorithm;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.parabolika.server.common.Packet;
import com.parabolika.server.net.base.BaseProtocolEncoder;

public class NettyProtocolEncoder extends OneToOneEncoder {
	private final BaseProtocolEncoder encoder = new BaseProtocolEncoder();

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		Packet packet = (Packet) msg;
		return ChannelBuffers.wrappedBuffer(encoder.encode(packet));
	}

	public void setCipher(ISAACAlgorithm cipher) {
		encoder.setCipher(cipher);
	}
}
