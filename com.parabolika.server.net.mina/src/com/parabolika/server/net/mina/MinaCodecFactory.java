package com.parabolika.server.net.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MinaCodecFactory implements ProtocolCodecFactory {
	private final MinaProtocolEncoder ENCODER = new MinaProtocolEncoder();
	private final MinaProtocolDecoder DECODER = new MinaProtocolDecoder();

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return DECODER;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return ENCODER;
	}
}
