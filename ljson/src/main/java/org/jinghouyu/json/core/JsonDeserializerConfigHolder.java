package org.jinghouyu.json.core;

import org.jinghouyu.json.core.config.JsonDeserializeConfig;
import org.jinghouyu.json.core.config.JsonSerializeConfig;

public class JsonDeserializerConfigHolder {

private static ThreadLocal<JsonDeserializeConfig> local = new ThreadLocal<JsonDeserializeConfig>();
	
	static void set(JsonDeserializeConfig config) {
		local.set(config);
	}
	
	static void addPro(String pro) {
		JsonProsHolder.add(pro);
	}
	
	static void backPro() {
		JsonProsHolder.back();
	}
	
	static String getPros() {
		return JsonProsHolder.get();
	}
	
	private static JsonDeserializeConfig get() {
		return local.get();
	}
	
	static Deserializer<?> get(Class<?> clazz) {
		JsonDeserializeConfig config = get();
		if(config != null) {
			return config.getDeserializer(clazz);
		}
		return null;
	}
	
	static Deserializer<?> get(String pros) {
		JsonDeserializeConfig config = get();
		if(config != null) {
			return config.getDeserializer(pros);
		}
		return null;
	}
	
	static void remove() {
		local.remove();
	}
}
