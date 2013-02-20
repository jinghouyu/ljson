package org.jinghouyu.json.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.config.JsonDeserializeConfig;
import org.jinghouyu.json.utils.CastUtils;
import org.jinghouyu.json.utils.TypeUtils;

public final class JsonObject extends Json implements Map<Object, Object> {
	private Map<Object, Object> map = new HashMap<Object, Object>();

	public static JsonObject fromObject(Object o) {
		if(!check(o)) {
			throw new JsonException("cannot transform type " + o.getClass() + " to JsonObject");
		}
		if(o instanceof JsonObject) {
			return (JsonObject) o;
		}
		return (JsonObject)JsonGenerator.generate(o);
	}
	
	public <T> T cast(Class<T> clazz) {
		return (T) DeserializerDirector.invoke(this, new TypeDescriptor(clazz));
	}
	
	public <T> T cast(Class<T> clazz, JsonDeserializeConfig config) {
		try {
			JsonDeserializerConfigHolder.set(config);
			return cast(clazz);
		} finally {
			JsonDeserializerConfigHolder.remove();
		}
	}
	
	private static boolean check(Object o) {
		Class<?> clazz = o.getClass();
		if(o instanceof CharSequence) {
			CharSequence charseq = (CharSequence) o;
			TextScanner scanner = new TextScanner(charseq);
			scanner.skipWhiteSpace();
			if(scanner.current() == '{') {
				return true;
			} else {
				throw new JsonException("syntax error, json object must start with {");
			}
		} else if(TypeUtils.isMap(clazz) || TypeUtils.isJavaBean(clazz)) {
			return true;
		}
		return false;
	}
	
	public Integer getInteger(String key) {
		return CastUtils.castToInteger(get(key));
	}
	
	public Long getLong(String key) {
		return CastUtils.castToLong(get(key));
	}
	
	public Float getFloat(String key) {
		return CastUtils.castToFloat(get(key));
	}
	
	public Short getShort(String key) {
		return CastUtils.castToShort(get(key));
	}
	
	public Byte getByte(String key) {
		return CastUtils.castToByte(get(key));
	}
	
	public Character getChar(String key) {
		return CastUtils.castToChar(get(key));
	}
	
	public Double getDouble(String key) {
		return CastUtils.castToDouble(get(key));
	}
	
	public Boolean getBoolean(String key) {
		return CastUtils.castToBoolean(get(key));
	}
	
	public String getString(String key) {
		Object value = get(key);
		if(value == null) return null;
		return value.toString();
	}
	
	public JsonObject getJsonObject(String key) {
		Object value =get(key);
		if(value == null) return null;
		if(value instanceof JsonObject) {
			return (JsonObject) value;
		}
		return JsonObject.fromObject(value);
	}
	
	public JsonArray getJsonArray(String key) {
		Object value = get(key);
		if(value == null) return null;
		if(value instanceof JsonArray) {
			return (JsonArray) value;
		}
		return JsonArray.fromArray(value);
	}
	
	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Object get(Object key) {
		return map.get(key);
	}

	public Object put(Object key, Object value) {
		return map.put(key, value);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public void putAll(Map<? extends Object, ? extends Object> m) {
		map.putAll(m);
	}

	public void clear() {
		map.clear();
	}

	public Set<Object> keySet() {
		return map.keySet();
	}

	public Collection<Object> values() {
		return map.values();
	}

	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		return map.entrySet();
	}
	
	public String toString() {
		return toJsonString();
	}
}
