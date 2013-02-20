package org.jinghouyu.json.core;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jinghouyu.json.beans.ClassDescriptor;
import org.jinghouyu.json.beans.FieldDescriptor;
import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.deserializer.map.MapDeserializer;
import org.jinghouyu.json.utils.TypeUtils;

public class JavaBeanDeserializer implements Deserializer<Object> {

	private static MapDeserializer mapDeserializer = new MapDeserializer();
	
	@Override
	public Object deserialize(Object value, TypeDescriptor targetType) {
		Class<?> targetClass = targetType.getTypeClass();
		Class<?> valueClass = value.getClass();
		if(TypeUtils.isSuperClass(targetClass, valueClass)) {
			return value;
		}
		ClassDescriptor classDescriptor = ClassDescriptor.getClassDescriptor(targetClass);
		Map map = (Map)mapDeserializer.deserialize(value, new TypeDescriptor(Map.class));
		Set<Entry> entrys = map.entrySet();
		Object result = classDescriptor.newInstance();
		for(Entry entry : entrys) {
			String proName = entry.getKey().toString();
			Object proValue = entry.getValue();
			FieldDescriptor fieldDescriptor = classDescriptor.getFieldDescriptor(proName);
			TypeDescriptor typeDescriptor = fieldDescriptor.getGenericTypeForWrite();
			if(typeDescriptor != null) {
				JsonDeserializerConfigHolder.addPro(proName);
				proValue = DeserializerDirector.invoke(proValue, fieldDescriptor.getGenericTypeForWrite());
				fieldDescriptor.write(result, proValue);
				JsonDeserializerConfigHolder.backPro();
			}
		}
		return result;
	}
}
