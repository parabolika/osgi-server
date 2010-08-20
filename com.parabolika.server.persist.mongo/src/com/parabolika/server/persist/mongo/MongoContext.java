package com.parabolika.server.persist.mongo;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class MongoContext {
	private final Mongo mongo;
	private final DB db;

	public MongoContext(Mongo mongo, DB db) {
		this.mongo = mongo;
		this.db = db;
	}

	public Mongo getMongo() {
		return mongo;
	}

	public DB getDb() {
		return db;
	}
}
