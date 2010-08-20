package com.parabolika.server.persist.mongo;

import java.util.UUID;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.parabolika.server.model.Location;
import com.parabolika.server.model.Player;
import com.parabolika.server.model.PlayerDetails;
import com.parabolika.server.model.World;
import com.parabolika.server.persist.IPersistor;
import com.parabolika.server.persist.annotate.Persists;

@Persists(Player.class)
public class MongoPlayerPersistor implements IPersistor<Player> {
	private final MongoContext context;

	public MongoPlayerPersistor(MongoContext context) {
		this.context = context;
	}

	@Override
	public void put(Player player) {
		DBCollection playersCollection = context.getDb().getCollection("players");

		BasicDBObject query = new BasicDBObject();
		query.put("name", player.getDetails().getName());

		DBObject doc = null;
		if((doc = playersCollection.findOne(query)) == null) {
			doc = new BasicDBObject();
			doc.put("name", player.getDetails().getName());
		}

		doc.put("password", player.getDetails().getPassword());
		doc.put("rights", player.getDetails().getRights());

		doc.put("x", player.getLocation().getX());
		doc.put("y", player.getLocation().getY());
		doc.put("z", player.getLocation().getZ());

		playersCollection.save(doc);
	}

	@Override
	public Player get(UUID uuid, String key) {
		DBCollection playersCollection = context.getDb().getCollection("players");

		BasicDBObject query = new BasicDBObject();
		query.put("name", key);

		DBObject r = playersCollection.findOne(query);
		PlayerDetails details = new PlayerDetails((String) r.get("name"),
				(String) r.get("password"), (Integer) r.get("rights"));
		Location location = Location.create((Integer) r.get("x"),
				(Integer) r.get("y"), (Integer) r.get("z"));

		Player player = new Player(uuid, details, World.getWorld().getNextAvailableIndex());
		player.setLocation(location);

		return player;
	}
}
