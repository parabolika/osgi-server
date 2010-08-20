package com.parabolika.server.persist;

import java.util.UUID;

public interface IPersistor<T> {
	public void put(T t);
	public T get(UUID uuid, String key);
}
