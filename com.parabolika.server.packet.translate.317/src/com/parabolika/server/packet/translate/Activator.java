package com.parabolika.server.packet.translate;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.packet.IPacketService;
import com.parabolika.server.packet.translate.impl.PacketTranslationService;

public class Activator implements BundleActivator {
	private static final Logger logger = LoggerFactory.getLogger(Activator.class);

	private ServiceRegistration packetServiceReg = null;

	@Override
	public void start(BundleContext context) throws Exception {
		Dictionary<String, Object> packetServiceParams = new Hashtable<String, Object>();
		packetServiceParams.put("version", 317);

		IPacketService packetService = new PacketTranslationService();
		packetServiceReg = context.registerService(IPacketService.class.getName(), packetService, packetServiceParams);

		logger.info("Packet translation service started");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		packetServiceReg.unregister();
	}
}
