package org.jinghouyu.json.deserializer;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.Deserializer;
import org.jinghouyu.json.core.JsonObject;

public class JsonObjectDeserializer implements Deserializer<JsonObject> {

	public JsonObject deserialize(Object value, TypeDescriptor type) {
		if(value instanceof JsonObject) {
			return (JsonObject) value;
		}
		return JsonObject.fromObject(value);
	}
	
//	public Object deserialize(JsonObject value, Class<?> targetClass) {
//		if(String.class.equals(targetClass)) {
//			return value.toJsonString();
//		}
//		if(TypeUtils.isJavaBean(targetClass)) {
//			return JsonObjectToBean(value, targetClass);
//		}
//		throw new JsonException("cannot transform JsonObject to " + targetClass.getName());
//	}
//	
//	private Object JsonObjectToBean(JsonObject jsonObject, Class<?> targetClass) {
//		ClassDescriptor classDes = ClassDescriptor.getClassDescriptor(targetClass);
//		Object result = classDes.newInstance();
//		for(Entry<String, Object> entry : jsonObject.entrySet()) {
//			String key = entry.getKey();
//			Object value = entry.getValue();
//			Class<?> type = classDes.getType(key);
//			if(type == null) continue;
//			if(value == null || type.equals(value.getClass())) {
//				classDes.write(result, key, value);
//			} else {
//				classDes.write(result, key, ObjectDeserializer.getInstance().deserialize(value, type));
//			}
//		}
//		return result;
//	}
}
