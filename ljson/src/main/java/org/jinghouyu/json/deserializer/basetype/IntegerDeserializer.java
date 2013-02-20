package org.jinghouyu.json.deserializer.basetype;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.Deserializer;
import org.jinghouyu.json.utils.CastUtils;

public class IntegerDeserializer implements Deserializer<Integer> {

	public Integer deserialize(Object value, TypeDescriptor type) {
		return CastUtils.castToInteger(value);
	}
}
