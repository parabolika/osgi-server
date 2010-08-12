package com.parabolika.server.net.netty;

import java.nio.ByteBuffer;

import net.burtleburtle.bob.rand.ISAACAlgorithm;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.parabolika.server.common.Packet;
import com.parabolika.server.net.base.BaseProtocolDecoder;

public class NettyProtocolDecoder extends FrameDecoder {
	private final BaseProtocolDecoder decoder = new BaseProtocolDecoder();

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		ByteBuffer bBuffer = buffer.toByteBuffer();
		int startRem = bBuffer.remaining();
		Packet packet = decoder.decode(bBuffer);
		int bytesRead = startRem - bBuffer.remaining();
		buffer.readerIndex(buffer.readerIndex() + bytesRead);

		return packet;
	}

	public void setCipher(ISAACAlgorithm isaacAlgorithm) {
		decoder.setCipher(isaacAlgorithm);
	}
}
