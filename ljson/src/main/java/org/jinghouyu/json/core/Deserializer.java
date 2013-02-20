package org.jinghouyu.json.core;

import org.jinghouyu.json.beans.TypeDescriptor;


public interface Deserializer<T> {

	T deserialize(Object value, TypeDescriptor targetType);
}