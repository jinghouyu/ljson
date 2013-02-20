package org.jinghouyu.json.deserializer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.Deserializer;
import org.jinghouyu.json.core.JsonException;
import org.jinghouyu.json.core.JsonObject;
import org.jinghouyu.json.utils.CastUtils;
import org.jinghouyu.json.utils.TypeUtils;

public class DateDeserializer implements Deserializer<Date> {

	private static final String[] patterns = new String[] {
		"yyyy-MM-dd HH:mm:ss SSS",
		"yyyyMMddHHmmssSSS",
		"yyyy/MM/dd HH:mm:ss SSS"
	};
	
	@Override
	public Date deserialize(Object value, TypeDescriptor targetType) {
		Class<?> targetClass = targetType.getTypeClass();
		Class<?> valueClass = value.getClass();
		if(TypeUtils.isSuperClass(targetClass, valueClass)) {
			return (Date) value;
		}
		if(TypeUtils.isSuperClass(targetClass, java.util.Date.class)) {
			return new java.util.Date(getTime(value));
		} else if(TypeUtils.isSuperClass(targetClass, java.sql.Date.class)) {
			return new java.sql.Date(getTime(value));
		} else if(TypeUtils.isSuperClass(targetClass, java.sql.Timestamp.class)) {
			return new java.sql.Timestamp(getTime(value));
		}
		throw new JsonException(value + " cannot transform to Date");
	}
	
	private Long getTime(Object value) {
		if(value instanceof Date) {
			return ((Date) value).getTime();
		}
		if(value instanceof CharSequence) {
			String valueStr = ((CharSequence) value).toString();
			try {
				return CastUtils.castToLong(valueStr);
			} catch(Exception e) {
			}
			for(String pattern : patterns) {
				DateFormat dateFormat = new SimpleDateFormat(pattern);
				try {
					return dateFormat.parse(valueStr).getTime();
				} catch (ParseException e) {
				}
			}
		}
		if(TypeUtils.isBaseType(value.getClass())) {
			return CastUtils.castToLong(value);
		}
		JsonObject jsonObject = JsonObject.fromObject(value);
		Long time = jsonObject.getLong("time");
		if(time != null) {
			return time;
		}
		throw new JsonException(value + " cannot transform to Date");
	}
}
