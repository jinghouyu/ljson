package org.jinghouyu.json.core.config;

import java.util.HashMap;
import java.util.Map;

import org.jinghouyu.json.core.Deserializer;

public class JsonDeserializeConfig {

	private Map<Class<?>, Deserializer<?>> deserializerMap = new HashMap<Class<?>, Deserializer<?>>();
	private Map<String, Deserializer<?>> proDeserializerMap = new HashMap<String, Deserializer<?>>();  
	
	public <T> void setDeserializer(Class<T> clazz, Deserializer<T> deserializer) {
		deserializerMap.put(clazz, deserializer);
	}
	
	public Deserializer<?> getDeserializer(Class<?> clazz) {
		return deserializerMap.get(clazz);
	}
	
	public void setDeserializer(String pros, Deserializer<?> serializer) {
		proDeserializerMap.put(pros, serializer);
	}
	
	public Deserializer<?> getDeserializer(String pros) {
		return proDeserializerMap.get(pros);
	}
}
