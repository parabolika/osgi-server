package com.parabolika.server.persist;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.persist.annotate.Persists;

public class Persistors {
	private static final Logger logger = LoggerFactory.getLogger(Persistors.class);
	private static final Map<Class<?>, IPersistor<?>> PERSISTORS =
		new HashMap<Class<?>, IPersistor<?>>();

	public static Map<Class<?>, IPersistor<?>> getPersistors() {
		return PERSISTORS;
	}

	public static IPersistor<?> getPersistor(Class<?> klass) {
		return PERSISTORS.get(klass);
	}

	@SuppressWarnings("unchecked")
	public static void addPersistor(IPersistor<?> persistor) {
		Class<IPersistor<?>> klass = (Class<IPersistor<?>>) persistor.getClass();
		if(klass.isAnnotationPresent(Persists.class)) {
			Class<?> persistable = klass.getAnnotation(Persists.class).value();
			PERSISTORS.put(persistable, persistor);
		} else {
			logger.warn("Class " + klass.getName() + " should be annotated with @Persists");
		}
	}
}
