package org.jinghouyu.json.serializer;

import org.jinghouyu.json.core.JsonContext;
import org.jinghouyu.json.core.Serializer;

public class NumberSerializer implements Serializer<Number> {

	public void serialize(JsonContext context, Number value) {
		context.context(value);
	}
}
