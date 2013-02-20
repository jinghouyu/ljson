package org.jinghouyu.json.deserializer.collection;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.Deserializer;
import org.jinghouyu.json.core.DeserializerDirector;

public class ArrayDeserializer implements Deserializer<Object[]> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object[] deserialize(Object value, TypeDescriptor targetType) {
		Class<?> valueClass = value.getClass();
		Class<?> targetClass = targetType.getClass();
		if(valueClass.equals(targetClass)) {
			return (Object[]) value;
		}
		Iterator it = CollectionUtils.getValueIterator(value);
		List list = new LinkedList();
		Class<?> targetSubClass = targetClass.getComponentType();
		while(it.hasNext()) {
			Object item = it.next();
			item = DeserializerDirector.invoke(item, new TypeDescriptor(targetSubClass));
			list.add(item);
		}
		return list.toArray((Object[])Array.newInstance(targetSubClass, 0));
	}
}
