package com.parabolika.server.common;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class GenericServiceTracker<T> extends ServiceTracker {
	public GenericServiceTracker(BundleContext context, Filter filter, ServiceTrackerCustomizer customizer) {
		super(context, filter, customizer);
	}

	public GenericServiceTracker(BundleContext context, ServiceReference reference, ServiceTrackerCustomizer customizer) {
		super(context, reference, customizer);
	}

	public GenericServiceTracker(BundleContext context, String clazz, ServiceTrackerCustomizer customizer) {
		super(context, clazz, customizer);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getService() {
		return (T) super.getService();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getService(ServiceReference reference) {
		return (T) super.getService(reference);
	}
}
