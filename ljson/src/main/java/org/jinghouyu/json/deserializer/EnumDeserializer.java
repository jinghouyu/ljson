package org.jinghouyu.json.deserializer;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.Deserializer;

@SuppressWarnings("rawtypes")
public class EnumDeserializer implements Deserializer<Enum> {

	@SuppressWarnings("unchecked")
	@Override
	public Enum deserialize(Object value, TypeDescriptor targetType) {
		return Enum.valueOf((Class<Enum>)targetType.getTypeClass(), value.toString());
	}

}
