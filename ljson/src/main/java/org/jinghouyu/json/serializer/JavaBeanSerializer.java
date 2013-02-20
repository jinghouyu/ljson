package org.jinghouyu.json.serializer;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.jinghouyu.json.beans.ClassDescriptor;
import org.jinghouyu.json.core.KeyValueSerializer;

public class JavaBeanSerializer extends KeyValueSerializer<Object> {

	@Override
	protected List<KeyValue<String, Object>> listItem(
			Object bean) {
		Class<?> clazz = bean.getClass();
		ClassDescriptor classDes = ClassDescriptor.getClassDescriptor(clazz);
		PropertyDescriptor[] properties = classDes.getPropertyDescriptors();
		List<KeyValue<String, Object>> result = new ArrayList<KeyValue<String, Object>>(properties.length);
		for(PropertyDescriptor p : properties) {
			String key = p.getName();
			Object value = classDes.getFieldDescriptor(key).read(bean);
			result.add(new KeyValue<String, Object>(key, value));
		}
		return result;
	}

}
