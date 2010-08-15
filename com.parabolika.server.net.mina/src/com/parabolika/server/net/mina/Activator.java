package com.parabolika.server.net.mina;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.common.GenericServiceTracker;
import com.parabolika.server.net.INetworkService;

public class Activator implements BundleActivator {
	private static final Logger logger = LoggerFactory.getLogger(Activator.class);

	private GenericServiceTracker<EventAdmin> eventAdminTracker = null;
	private MinaNetworkService networkService = null;
	private ServiceRegistration networkServiceReg = null;

	@Override
	public void start(BundleContext context) throws Exception {
		eventAdminTracker = new GenericServiceTracker<EventAdmin>(context, EventAdmin.class.getName(), null);
		eventAdminTracker.open();

		networkService = new MinaNetworkService(eventAdminTracker);
		networkServiceReg = context.registerService(INetworkService.class.getName(), networkService, null);
		networkService.start();

		logger.info("Network service started");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		networkService.stop();
		networkServiceReg.unregister();
		eventAdminTracker.close();
	}
}
