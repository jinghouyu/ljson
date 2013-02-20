package org.jinghouyu.json.serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jinghouyu.json.core.KeyValueSerializer;


public class MapSerializer extends KeyValueSerializer<Map> {

	@Override
	protected List<org.jinghouyu.json.core.KeyValueSerializer.KeyValue<String, Object>> listItem(
			Map keyValuePairs) {
		Set<Entry> set = keyValuePairs.entrySet();
		List<KeyValue<String, Object>> result = new ArrayList<KeyValue<String, Object>>(keyValuePairs.size());
		for(Entry entry : set) {
			String key = entry.getKey() == null ? null : entry.getKey().toString();
			Object value = entry.getValue();
			result.add(new KeyValue<String, Object>(key, value));
		}
		return result;
	}

}