package com.parabolika.server.persist.mongo;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.parabolika.server.persist.Persistors;

public class Activator implements BundleActivator {
	private MongoContext mongoContext = null;

	@Override
	public void start(BundleContext context) throws Exception {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("runescape");

		mongoContext = new MongoContext(mongo, db);
		Persistors.add(new MongoPlayerPersistor(mongoContext));
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		mongoContext.getMongo().close();
	}
}
