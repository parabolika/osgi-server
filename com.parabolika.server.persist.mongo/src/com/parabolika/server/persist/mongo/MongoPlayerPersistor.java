package com.parabolika.server.persist.mongo;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.parabolika.server.model.Player;
import com.parabolika.server.model.PlayerBuilder;
import com.parabolika.server.persist.IPersistor;
import com.parabolika.server.persist.annotate.Persists;

@Persists(PlayerBuilder.class)
public class MongoPlayerPersistor implements IPersistor<Player, PlayerBuilder, PlayerBuilder> {
	private static final Gson GSON = new Gson();
	private final MongoContext context;

	public MongoPlayerPersistor(MongoContext context) {
		this.context = context;
	}

	@Override
	public void put(Player player) {
		DBCollection playersCollection = context.getDb().getCollection("players");

		DBObject query = new BasicDBObject("username", player.getUsername());
		DBObject result = playersCollection.findOne(query);

		String json = GSON.toJson(player);
		DBObject object = (DBObject) JSON.parse(json);

		if(result != null) {
			playersCollection.update(result, object);
		} else {
			playersCollection.save(object);
		}
	}

	@Override
	public PlayerBuilder get(PlayerBuilder builder, String key) {
		DBCollection playersCollection = context.getDb().getCollection("players");

		DBObject query = new BasicDBObject("username", key);
		DBObject result = playersCollection.findOne(query);

		if(result != null) {
			return builder.merge(GSON.fromJson(result.toString(), PlayerBuilder.class));
		}
		return builder;
	}
}
