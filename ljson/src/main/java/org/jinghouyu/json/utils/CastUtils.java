package org.jinghouyu.json.utils;

import org.jinghouyu.json.core.JsonException;

public class CastUtils {

//	@SuppressWarnings("unchecked")
//	public static <T> T cast(Object value, Class<T> clazz) {
//		if(value == null) return null;
//		if(Number.class.isAssignableFrom(clazz)) {
//			Number number = (Number) value;
//			if(clazz == int.class || clazz == Integer.class) {
//				Object result = number.intValue();
//				return (T) result;
//			}
//		}
//		return null;
//	}
	
	public static Number castToNumber(Object value) {
		if(value == null) return null;
		if(value instanceof Number) {
			return (Number) value;
		}
		if(value instanceof String) {
			return Double.parseDouble((String) value);
		}
		throw new JsonException(value + " cannot cast to Number");
	}
	
	public static Integer castToInteger(Object value) {
		if(value == null) return null;
		return castToNumber(value).intValue();
	}
	
	public static Long castToLong(Object value) {
		if(value == null) return null;
		return castToNumber(value).longValue();
	}
	
	public static Short castToShort(Object value) {
		if(value == null) return null;
		return castToNumber(value).shortValue();
	}
	
	public static Byte castToByte(Object value) {
		if(value == null) return null;
		return castToNumber(value).byteValue();
	}
	
	public static Float castToFloat(Object value) {
		if(value == null) return null;
		return castToNumber(value).floatValue();
	}
	
	public static Double castToDouble(Object value) {
		if(value == null) return null;
		return castToNumber(value).doubleValue();
	}
	
	public static Boolean castToBoolean(Object value) {
		if(value == null) return null;
		if(value instanceof Boolean) {
			return (Boolean) value;
		}
		try {
			return castToInteger(value) == 1;
		} catch(Exception e) {
			return Boolean.parseBoolean(value.toString());
		}
	}
	
	public static Character castToChar(Object value) {
		if(value == null) return null;
		return (char) (int) castToInteger(value);
	}
}
