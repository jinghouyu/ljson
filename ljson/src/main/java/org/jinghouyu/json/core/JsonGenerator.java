package org.jinghouyu.json.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jinghouyu.json.beans.ClassDescriptor;
import org.jinghouyu.json.utils.TypeUtils;

public class JsonGenerator {

	@SuppressWarnings("rawtypes")
	static Json generate(Object o) {
		if(o == null) {
			return null;
		}
		if(o instanceof String) {
			return (Json)JsonParser.parse((String) o);
		}
		Class<?> clazz = o.getClass();
		if (TypeUtils.isIterable(clazz)) {
			return generate((Iterable) o);
		} else if (TypeUtils.isArray(clazz)) {
			return generate((Object[]) o);
		} else if (TypeUtils.isMap(clazz)) {
			return generate((Map<?, ?>) o);
		} else {
			return generateJavaBean(o);
		}
	}
	
	private static JsonArray generate(@SuppressWarnings("rawtypes") Iterable iterable) {
		JsonArray jsonArray = new JsonArray();
		Iterator<?> it = iterable.iterator();
		while(it.hasNext()) {
			jsonArray.add(generate(it.next()));
		}
		return jsonArray;
	}
	
	private static JsonArray generate(Object[] array) {
		JsonArray jsonArray = new JsonArray();
		for(Object item : array) {
			jsonArray.add(generate(item));
		}
		return jsonArray;
	}
	
	private static JsonObject generate(Map<?, ?> map) {
		JsonObject jsonObject = new JsonObject();
		for(Entry<?, ?> entry : map.entrySet()) {
			Object key = entry.getKey();
			Object value  = generate(entry.getValue());
			jsonObject.put(key, value);
		}
		return jsonObject;
	}
	
	private static JsonObject generateJavaBean(Object bean) {
		JsonObject jsonObject = new JsonObject();
		ClassDescriptor cd = ClassDescriptor.getClassDescriptor(bean.getClass());
		for(PropertyDescriptor pd : cd.getShouldJsonPropertyDescriptors()) {
			String key = pd.getName();
			Method method = pd.getReadMethod();
			if(method == null) continue;
			Object value;
			try {
				value = method.invoke(bean);
				jsonObject.put(key, value);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
		return jsonObject;
	}
}
