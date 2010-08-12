package com.parabolika.server.model;

// XXX: Rewrite this
public class Entity {
	private final WalkingQueue walkingQueue = new WalkingQueue(this);
	private final Sprites sprites = new Sprites();

	private Location location = Location.create(3268, 3420, 0);
	private Location lastKnownRegion = location;

	private boolean mapRegionChanging = false;
	private boolean teleporting = false;
	private Location teleportTarget = null;

	public WalkingQueue getWalkingQueue() {
		return walkingQueue;
	}

	public Sprites getSprites() {
		return sprites;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLastKnownRegion() {
		return lastKnownRegion;
	}

	public void setLastKnownRegion(Location lastKnownRegion) {
		this.lastKnownRegion = lastKnownRegion;
	}

	public boolean isMapRegionChanging() {
		return mapRegionChanging;
	}

	public void setMapRegionChanging(boolean mapRegionChanging) {
		this.mapRegionChanging = mapRegionChanging;
	}

	public boolean isTeleporting() {
		return teleporting;
	}

	public void setTeleporting(boolean teleporting) {
		this.teleporting = teleporting;
	}

	public Location getTeleportTarget() {
		return teleportTarget;
	}

	public void setTeleportTarget(Location teleportTarget) {
		this.teleportTarget = teleportTarget;
	}
}
