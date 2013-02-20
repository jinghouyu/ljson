package org.jinghouyu.json.deserializer;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.Deserializer;
import org.jinghouyu.json.core.JsonArray;

public class JsonArrayDeserializer implements Deserializer<JsonArray> {

	public JsonArray deserialize(Object value, TypeDescriptor type) {
		if(value instanceof JsonArray) {
			return (JsonArray) value;
		}
		return JsonArray.fromArray(value);
	}
}
