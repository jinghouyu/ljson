package org.jinghouyu.json.deserializer.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.Deserializer;
import org.jinghouyu.json.core.DeserializerDirector;
import org.jinghouyu.json.core.JsonException;
import org.jinghouyu.json.core.JsonObject;
import org.jinghouyu.json.utils.TypeUtils;

@SuppressWarnings("rawtypes")
public class MapDeserializer implements Deserializer<Map> {

	@Override
	public Map deserialize(Object keyPairs, TypeDescriptor targetType) {
		Class<?> targetClass = targetType.getTypeClass();
		Map map = null;
		if(TypeUtils.isSuperClass(targetClass, HashMap.class)) {
			map = new HashMap();
		} else if(TypeUtils.isSuperClass(targetClass, TreeMap.class)) {
			map = new TreeMap();
		}
		//concurrent
		
		if(map == null) {
			throw new JsonException("unsupported Type Transformation " + targetClass.getName());
		}
		for(Entry entry : entry(keyPairs)) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			key = DeserializerDirector.invoke(key, targetType.getValidInnerGeneric(0));
			value = DeserializerDirector.invoke(value, targetType.getValidInnerGeneric(1));
			map.put(key, value);
		}
		return map;
	}
	
	private Set<Entry> entry(Object value) {
		if(value instanceof Map) {
			return ((Map) value).entrySet();
		} else {
			Set<Entry> entrySet = new TreeSet<Entry>();
			for(Entry<Object, Object> entry : JsonObject.fromObject(value).entrySet()) {
				entrySet.add(new JsonEntry(entry.getKey(), entry.getValue()));
			}
			return entrySet;
		}
	}
	
	private class JsonEntry<K, V> implements Entry<K, V> {
		private K k;
		private V v;
		public JsonEntry(K k, V v) {
			this.k = k;
			this.v = v;
		}
		public JsonEntry(Entry<K, V> entry) {
			this(entry.getKey(), entry.getValue());
		}
		public K getKey() {
			return k;
		}
		public V getValue() {
			return v;
		}
		@Override
		public V setValue(V value) {
			return v = value;
		}
	}
}
