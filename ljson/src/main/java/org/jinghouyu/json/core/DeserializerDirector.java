package org.jinghouyu.json.core;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.deserializer.DateDeserializer;
import org.jinghouyu.json.deserializer.EnumDeserializer;
import org.jinghouyu.json.deserializer.JsonArrayDeserializer;
import org.jinghouyu.json.deserializer.JsonObjectDeserializer;
import org.jinghouyu.json.deserializer.basetype.BooleanDeserializer;
import org.jinghouyu.json.deserializer.basetype.ByteDeserializer;
import org.jinghouyu.json.deserializer.basetype.CharDeserializer;
import org.jinghouyu.json.deserializer.basetype.DoubleDeserializer;
import org.jinghouyu.json.deserializer.basetype.FloatDeserializer;
import org.jinghouyu.json.deserializer.basetype.IntegerDeserializer;
import org.jinghouyu.json.deserializer.basetype.LongDeserializer;
import org.jinghouyu.json.deserializer.basetype.ShortDeserializer;
import org.jinghouyu.json.deserializer.basetype.StringDeserializer;
import org.jinghouyu.json.deserializer.collection.ArrayDeserializer;
import org.jinghouyu.json.deserializer.collection.CollectionDeseriablizer;
import org.jinghouyu.json.deserializer.map.MapDeserializer;
import org.jinghouyu.json.utils.TypeUtils;

public class DeserializerDirector {

	private static Map<Class<?>, Deserializer<?>> map = new HashMap<Class<?>, Deserializer<?>>();
	
	static {
		map.put(Integer.class, new IntegerDeserializer());
		map.put(Short.class, new ShortDeserializer());
		map.put(Long.class, new LongDeserializer());
		map.put(Byte.class, new ByteDeserializer());
		map.put(Character.class, new CharDeserializer());
		map.put(Boolean.class, new BooleanDeserializer());
		map.put(Float.class, new FloatDeserializer());
		map.put(Double.class, new DoubleDeserializer());
		map.put(int.class, new IntegerDeserializer());
		map.put(short.class, new ShortDeserializer());
		map.put(long.class, new LongDeserializer());
		map.put(byte.class, new ByteDeserializer());
		map.put(char.class, new CharDeserializer());
		map.put(boolean.class, new BooleanDeserializer());
		map.put(float.class, new FloatDeserializer());
		map.put(double.class, new DoubleDeserializer());
		map.put(String.class, new StringDeserializer());
		map.put(Collection.class, new CollectionDeseriablizer());
		map.put(List.class, new CollectionDeseriablizer());
		map.put(AbstractCollection.class, new CollectionDeseriablizer());
		map.put(AbstractList.class, new CollectionDeseriablizer());
		map.put(ArrayList.class, new CollectionDeseriablizer());
		map.put(LinkedList.class, new CollectionDeseriablizer());
		map.put(Set.class, new CollectionDeseriablizer());
		map.put(SortedSet.class, new CollectionDeseriablizer());
		map.put(NavigableSet.class, new CollectionDeseriablizer());
		map.put(AbstractSet.class, new CollectionDeseriablizer());
		map.put(HashSet.class, new CollectionDeseriablizer());
		map.put(TreeSet.class, new CollectionDeseriablizer());
		map.put(Map.class, new MapDeserializer());
		map.put(NavigableMap.class,  new MapDeserializer());
		map.put(SortedMap.class,  new MapDeserializer());
		map.put(AbstractMap.class,  new MapDeserializer());
		map.put(TreeMap.class,  new MapDeserializer());
		map.put(HashMap.class,  new MapDeserializer());
		map.put(JsonObject.class, new JsonObjectDeserializer());
		map.put(JsonArray.class, new JsonArrayDeserializer());
		
		map.put(java.util.Date.class, new DateDeserializer());
		map.put(java.sql.Date.class, new DateDeserializer());
		map.put(java.sql.Timestamp.class, new DateDeserializer());
	}
	
	private static Deserializer<?> getDeserializer(Class<?> clazz) {
		return map.get(clazz);
	}
	
	private static final ArrayDeserializer arrayDeserializer = new ArrayDeserializer();
	private static final EnumDeserializer enumDeserializer = new EnumDeserializer();
	private static final JavaBeanDeserializer javabeanDeserializer = new JavaBeanDeserializer();
	
	public static Object invoke(Object value, TypeDescriptor typeDescriptor) {
		if(value == null) return value;
		Class<?> targetClazz = typeDescriptor.getTypeClass();
		if(targetClazz == null) return value;
		if(targetClazz.equals(Object.class)) return value;
		//		if(TypeUtils.isSuperClass(targetClazz, value.getClass())) return value;
		Deserializer<?> deserializer = getDeserializer(value, typeDescriptor);
		if(deserializer != null) {
			return deserializer.deserialize(value, typeDescriptor);
		} else {
			throw new JsonException("cannot transform value to " + targetClazz.getName());
		}
	}
	
	private static Deserializer<?> getDeserializer(Object value, TypeDescriptor typeDescriptor) {
		Class<?> targetClazz = typeDescriptor.getTypeClass();
		String pros = JsonDeserializerConfigHolder.getPros();
		Deserializer<?> deserializer = JsonDeserializerConfigHolder.get(pros);
		if(deserializer != null) return deserializer;
		deserializer = JsonDeserializerConfigHolder.get(targetClazz);
		if(deserializer != null) return deserializer;
		if(targetClazz.isEnum()) return enumDeserializer;
		if(targetClazz.isArray()) return arrayDeserializer;
		deserializer = getDeserializer(targetClazz);
		if(deserializer != null)  return deserializer;
		if(TypeUtils.isJavaBean(targetClazz))  return javabeanDeserializer;
		return null;
	}
}
