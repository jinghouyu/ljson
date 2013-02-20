package org.jinghouyu.json.core;

import org.jinghouyu.json.core.config.JsonSerializeConfig;

public abstract class Json {

	public final String toJsonString()  {
		return toJsonString(this, JsonSerializeConfigHolder.get());
	}
	 
	public final String toJsonString(JsonSerializeConfig config) {
		return toJsonString(this, config);
	}
	
	public static final String toJsonString(Object object) {
		return toJsonString(object,  JsonSerializeConfigHolder.get());
	}
	
	public static final String toJsonString(Object object, JsonSerializeConfig config) {
		try {
			JsonSerializeConfigHolder.set(config);
			JsonContext context = new JsonContext();
			ObjectSerializer.getInstance().serialize(context, object);
			return context.toString();
		} finally {
			JsonSerializeConfigHolder.remove();
		}
	}
}
