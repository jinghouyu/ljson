package org.jinghouyu.json.deserializer.collection;

import java.util.Arrays;
import java.util.Iterator;

import org.jinghouyu.json.core.JsonArray;
import org.jinghouyu.json.core.JsonException;

public class CollectionUtils {

	public static Iterator<?> getValueIterator(Object value) {
		if(value instanceof String) {
			return JsonArray.fromArray((String) value).iterator(); 
		}
		if(value instanceof Iterable) {
			return ((Iterable<?>) value).iterator();
		}
		Class<?> clazz = value.getClass();
		if(clazz.isArray()) {
			return Arrays.asList((Object[]) value).iterator();
		}
		throw new JsonException(value.getClass() + " cannot transform to Collection ");
	}
}
