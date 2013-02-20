package org.jinghouyu.json.core;

import org.jinghouyu.json.core.config.JsonSerializeConfig;

public class JsonSerializeConfigHolder {

	private static ThreadLocal<JsonSerializeConfig> local = new ThreadLocal<JsonSerializeConfig>();
	
	static void set(JsonSerializeConfig config) {
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
	
	static JsonSerializeConfig get() {
		return local.get();
	}
	
	static Serializer<?> get(Class<?> clazz) {
		JsonSerializeConfig config = get();
		if(config != null) {
			return config.getSerializer(clazz);
		}
		return null;
	}
	
	static Serializer<?> get(String pros) {
		JsonSerializeConfig config = get();
		if(config != null) {
			return config.getSerializer(pros);
		}
		return null;
	}
	
	static void remove() {
		local.remove();
	}
}
