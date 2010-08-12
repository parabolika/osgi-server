package com.parabolika.server.event;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.common.GenericServiceTracker;

public class Activator implements BundleActivator {
	private static final Logger logger = LoggerFactory.getLogger(Activator.class);

	private GenericServiceTracker<EventAdmin> eventAdminTracker = null;
	private ServerTickEventGenerator tickEventGenerator = null;

	@Override
	public void start(BundleContext context) throws Exception {
		eventAdminTracker = new GenericServiceTracker<EventAdmin>(context, EventAdmin.class.getName(), null);
		eventAdminTracker.open();

		tickEventGenerator = new ServerTickEventGenerator(eventAdminTracker);
		tickEventGenerator.start();

		logger.info("Event service started");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		tickEventGenerator.stop();
		eventAdminTracker.close();
	}
}
