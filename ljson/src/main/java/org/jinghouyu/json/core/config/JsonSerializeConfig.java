package org.jinghouyu.json.core.config;

import java.util.HashMap;
import java.util.Map;

import org.jinghouyu.json.core.Serializer;

/**
 * JsonSerializeConfig allows user define his own Serializer 
 * @author jinghouyu
 *
 */
public class JsonSerializeConfig {

	private Map<Class<?>, Serializer<?>> serializerMap = new HashMap<Class<?>, Serializer<?>>(); // low priority
	
	private Map<String, Serializer<?>> proSerializerMap = new HashMap<String, Serializer<?>>();  // high priority
	
	public <T> void setSerializer(Class<T> clazz, Serializer<T> serializer) {
		serializerMap.put(clazz, serializer);
	}
	
	public void setSerializer(String pros, Serializer<?> serializer) {
		proSerializerMap.put(pros, serializer);
	}
	
	public Serializer<?> getSerializer(String pros) {
		return proSerializerMap.get(pros);
	}
	
	/**
	 * will fix it to 
	 * get the most suitable Serializer
	 * refer to HierarchicalMap later
	 * @param clazz
	 * @return
	 */
	public Serializer<?> getSerializer(Class<?> clazz) {
		return serializerMap.get(clazz);
	}
}
