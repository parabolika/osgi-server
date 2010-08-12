package com.parabolika.server.model;

import java.util.Map;
import java.util.concurrent.Callable;

// XXX: Get rid of this, and get rid of the lambdas in the Ruby code
public class ActionFuture {
	private final Map<String, Object> context;
	private final Callable<Void> callable;

	public ActionFuture(Map<String, Object> context, Callable<Void> callable) {
		this.context = context;
		this.callable = callable;
	}

	public Callable<Void> getCallable() {
		return callable;
	}

	public Map<String, Object> getContext() {
		return context;
	}
}
