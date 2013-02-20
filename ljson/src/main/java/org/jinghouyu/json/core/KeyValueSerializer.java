package org.jinghouyu.json.core;

import java.util.List;
import java.util.Map.Entry;


public abstract class KeyValueSerializer<T> implements Serializer<T> {

	public final void serialize(JsonContext context, T keyValuePairs) {
		List<KeyValue<String, Object>> items = listItem(keyValuePairs);
		if(items == null) {
			context.context("{}");
			return;
		}
		context.context('{');
		int i = 0;
		for(KeyValue<String, Object> item : items) {
			String key = item.getKey();
			Object value = item.getValue();
			context.context('"');
			context.context(JsonUtils.render(key.toString()));
			context.context('"');
			context.context(':');
			JsonSerializeConfigHolder.addPro(key);
			ObjectSerializer.getInstance().serialize(context, value);
			if(i++ != items.size() - 1) {
				context.context(",");
			}
			JsonSerializeConfigHolder.backPro();
		}
		context.context('}');
	}

	protected abstract List<KeyValue<String, Object>> listItem(T value);
	
	protected static class KeyValue<K, V> implements Entry<K, V> {
		private K k;
		private V v;
		public KeyValue(K k, V v) {
			this.k = k;
			this.v = v;
		}
		public K getKey() {
			return k;
		}
		public V getValue() {
			return v;
		}
		public V setValue(V value) {
			return v = value;
		}
	}
}
