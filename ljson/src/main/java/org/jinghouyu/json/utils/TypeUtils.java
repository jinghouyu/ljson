package org.jinghouyu.json.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TypeUtils {

	private static Set<Class<?>> baseTypes = new HashSet<Class<?>>();
	
	static {
		baseTypes.add(Short.class);
		baseTypes.add(Integer.class);
		baseTypes.add(Long.class);
		baseTypes.add(Byte.class);
		baseTypes.add(Float.class);
		baseTypes.add(Double.class);
		baseTypes.add(Boolean.class);
		baseTypes.add(Character.class);
		baseTypes.add(short.class);
		baseTypes.add(int.class);
		baseTypes.add(long.class);
		baseTypes.add(byte.class);
		baseTypes.add(float.class);
		baseTypes.add(double.class);
		baseTypes.add(boolean.class);
		baseTypes.add(char.class);
		baseTypes.add(String.class);
	}
	
	public static boolean isEnum(Class<?> clazz) {
		return clazz.isEnum();
	}
	
	public static boolean isBaseType(Class<?> clazz) {
		return baseTypes.contains(clazz);
	}
	
	public static boolean isArray(Class<?> clazz) {
		return clazz.isArray();
	}
	
	public static boolean isIterable(Class<?> clazz) {
		return Iterable.class.isAssignableFrom(clazz);
	}
	
	public static boolean isMap(Class<?> clazz) {
		return Map.class.isAssignableFrom(clazz);
	}
	
	public static boolean isJavaBean(Class<?> clazz) {
		return !isBaseType(clazz) && !isArray(clazz) && !isIterable(clazz) && !isMap(clazz) && !isEnum(clazz);
	}
	
	public static boolean isSuperClass(Class<?> superClass, Class<?> clazz) {
		return superClass.isAssignableFrom(clazz);
	}
}