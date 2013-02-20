package org.jinghouyu.json.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jinghouyu.json.serializer.DateSerializer;
import org.jinghouyu.json.serializer.EnumSerializer;
import org.jinghouyu.json.serializer.IterableSerializer;
import org.jinghouyu.json.serializer.JavaBeanSerializer;
import org.jinghouyu.json.serializer.MapSerializer;
import org.jinghouyu.json.serializer.NumberSerializer;
import org.jinghouyu.json.serializer.StringSerializer;
import org.jinghouyu.json.utils.TypeUtils;

public class ObjectSerializer implements Serializer<Object> {

	@SuppressWarnings("rawtypes")
	private static Map<Class<?>, Serializer> serialMap = new HashMap<Class<?>, Serializer>();
	
	static {
		serialMap.put(String.class, new StringSerializer());
		serialMap.put(Integer.class, new NumberSerializer());
		serialMap.put(Short.class, new NumberSerializer());
		serialMap.put(Long.class, new NumberSerializer());
		serialMap.put(Float.class, new NumberSerializer());
		serialMap.put(Double.class, new NumberSerializer());
		serialMap.put(Byte.class, new NumberSerializer());
		serialMap.put(JsonObject.class, new MapSerializer());
		serialMap.put(java.util.Date.class, new DateSerializer());
		serialMap.put(java.sql.Date.class, new DateSerializer());
		serialMap.put(java.sql.Timestamp.class, new DateSerializer());
	}
	
	private final static ObjectSerializer objectSerializer = new ObjectSerializer();
	private final static EnumSerializer enumSeriablize = new EnumSerializer();
	private final static MapSerializer mapSerializer = new MapSerializer();
	private final static JavaBeanSerializer javaBeanSerializer = new JavaBeanSerializer();
	private final static IterableSerializer iterableSerializer = new IterableSerializer(); 
	
	private ObjectSerializer() {}
	
	public static ObjectSerializer getInstance() {
		return objectSerializer;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void serialize(JsonContext context, Object value) {
		if(value == null) {
			context.contextNull();
			return;
		}
		Class<?> clazz = value.getClass();
		Serializer serializer = getSerializer(value);
		if(serializer != null) {
			serializer.serialize(context, value);
		} else if(TypeUtils.isIterable(clazz)) {
			iterableSerializer.serialize(context, (Iterable) value);
		} else if(TypeUtils.isArray(clazz)) {
			iterableSerializer.serialize(context, Arrays.asList(value));
		} else if(TypeUtils.isMap(clazz)) {
			mapSerializer.serialize(context, (Map)value);
		} else if(TypeUtils.isJavaBean(clazz)) {
			javaBeanSerializer.serialize(context, value);
		} else if(TypeUtils.isEnum(clazz)) {
			enumSeriablize.serialize(context, (Enum)value);
		} else {
			context.context('"');
			context.context(value.toString());
			context.context('"');
		}
	}
	
	private Serializer<?> getSerializer(Object value) {
		String pros = JsonSerializeConfigHolder.getPros();
		Serializer<?> serializer = JsonSerializeConfigHolder.get(pros);
		if(serializer != null) return serializer;
		Class<?> clazz = value.getClass();
		serializer = JsonSerializeConfigHolder.get(clazz);
		if(serializer != null) return serializer;
		if(clazz.isEnum()) return enumSeriablize;
		return serialMap.get(clazz);
	}
}
