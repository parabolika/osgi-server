package com.parabolika.server.net.mina;

import net.burtleburtle.bob.rand.ISAACAlgorithm;

import org.apache.mina.core.session.IoSession;

import com.parabolika.server.common.Packet;
import com.parabolika.server.net.base.BaseNetworkClient;

public class MinaNetworkClient extends BaseNetworkClient {
	private IoSession session;

	@Override
	public void write(Packet packet) {
		session.write(packet);
	}

	@Override
	public void setIncomingCipherKeys(int[] keys) {
		session.setAttribute("cipher_in", new ISAACAlgorithm(keys));
	}

	@Override
	public void setOutgoingCipherKeys(int[] keys) {
		session.setAttribute("cipher_out", new ISAACAlgorithm(keys));
	}

	public void setSession(IoSession session) {
		this.session = session;
	}
}
