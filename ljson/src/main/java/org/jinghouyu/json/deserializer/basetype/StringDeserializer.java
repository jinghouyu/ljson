package org.jinghouyu.json.deserializer.basetype;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.Deserializer;

public class StringDeserializer implements Deserializer<String> {

	public String deserialize(Object value, TypeDescriptor type) {
		return value.toString();
	}
}
