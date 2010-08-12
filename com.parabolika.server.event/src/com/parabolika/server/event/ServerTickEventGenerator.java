package com.parabolika.server.event;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.parabolika.server.common.GenericServiceTracker;

/**
 * Generates and fires <code>com/parabolika/server/event/SERVER_TICK</code>
 * events to the OSGi container every 600ms (one tick) on a new thread.
 */
public class ServerTickEventGenerator implements Runnable {
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private final GenericServiceTracker<EventAdmin> eventAdminTracker;

	public ServerTickEventGenerator(GenericServiceTracker<EventAdmin> eventAdminTracker) {
		this.eventAdminTracker = eventAdminTracker;
	}

	public void start() {
		executor.scheduleAtFixedRate(this, 0, 600, TimeUnit.MILLISECONDS);
	}

	public void stop() {
		executor.shutdown();
	}

	@Override
	public void run() {
		Event event = new Event("com/parabolika/server/event/SERVER_TICK", Collections.EMPTY_MAP);
		eventAdminTracker.getService().postEvent(event);
	}
}
