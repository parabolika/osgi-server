package com.parabolika.server.persist;

public interface IPersistor<A, T, K> {
	public void put(A a);
	public T get(K builder, String key);
}
