package com.parabolika.server.net.mina;

import net.burtleburtle.bob.rand.ISAACAlgorithm;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.parabolika.server.common.Packet;
import com.parabolika.server.net.base.BaseProtocolDecoder;

// XXX: Handle packet fragmentation
public class MinaProtocolDecoder extends ProtocolDecoderAdapter {
	private final BaseProtocolDecoder decoder = new BaseProtocolDecoder();

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		decoder.setCipher((ISAACAlgorithm) session.getAttribute("cipher_in")); // XXX: Need better solution

		Packet packet = decoder.decode(in.buf());
		out.write(packet);
	}
}
