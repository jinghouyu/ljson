package org.jinghouyu.json.core;

public interface Serializer<T> {

	void serialize(JsonContext context, T value);
}
