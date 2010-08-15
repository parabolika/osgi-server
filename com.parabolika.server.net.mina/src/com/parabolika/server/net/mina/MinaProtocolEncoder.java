package com.parabolika.server.net.mina;

import net.burtleburtle.bob.rand.ISAACAlgorithm;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.parabolika.server.common.Packet;
import com.parabolika.server.net.base.BaseProtocolEncoder;

public class MinaProtocolEncoder extends ProtocolEncoderAdapter {
	private final BaseProtocolEncoder encoder = new BaseProtocolEncoder();

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		encoder.setCipher((ISAACAlgorithm) session.getAttribute("cipher_out")); // XXX: Need better solution

		out.write(IoBuffer.wrap(encoder.encode((Packet) message)));
	}
}
