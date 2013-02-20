package org.jinghouyu.json.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jinghouyu.json.core.JsonException;

public class ClassDescriptor {

	private Class<?> clazz;
	private PropertyDescriptor[] propertyDescriptors;
	private Map<String, PropertyDescriptor> pdMap;
	private Map<String, FieldDescriptor> fdMap = new HashMap<String, FieldDescriptor>();
	
	private static final Map<Class<?>, ClassDescriptor> map = new HashMap<Class<?>, ClassDescriptor>();
	
	private ClassDescriptor(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public Object newInstance() {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			if(clazz.isArray()) {
				throw new JsonException("target class is a array " + clazz.getName());
			}
			if(clazz.isPrimitive()) {
				throw new JsonException("target class is primitive " + clazz.getName());
			}
			if(clazz.isAnnotation()) {
				throw new JsonException("target class is annotation " + clazz.getName());
			}
			if(clazz.isInterface()) {
				throw new JsonException("target class is an interface " + clazz.getName());
			}
			throw new JsonException("may be has no suitable constructor");
		} catch (IllegalAccessException e) {
			throw new JsonException("has no access to " + clazz.getName());
		}
	}
	
	public static ClassDescriptor getClassDescriptor(Class<?> clazz) {
		if(map.get(clazz) == null) {
			synchronized(ClassDescriptor.class) {
				if(map.get(clazz) == null) {
					ClassDescriptor cd = new ClassDescriptor(clazz);
					map.put(clazz, cd);
				}
			}
		}
		return map.get(clazz);
	}
	
	public Class<?> getType(String field) {
		//TODO 暂时用默认方式，今后不限于JavaBean标准
		PropertyDescriptor pd = getPropertyDescriptor(field);
		if(pd == null) return null;
		return pd.getPropertyType();
	}
	
	public boolean write(Object o, String field, Object value) {
		//TODO 暂时用默认方式，今后不限于JavaBean标准
		PropertyDescriptor pd = getPropertyDescriptor(field);
		if(pd == null) return false;
		Method method = pd.getWriteMethod();
		if(method == null) return false;
		try {
			method.invoke(o, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public PropertyDescriptor[] getShouldJsonPropertyDescriptors() {
		return getPropertyDescriptors();
	}
	
	public PropertyDescriptor[] getPropertyDescriptors() {
		if(propertyDescriptors == null) {
			synchronized(this) {
				if(propertyDescriptors == null) {
					propertyDescriptors = getPropertyDescriptors(clazz);
				}
			}
		}
		return propertyDescriptors;
	}
	
	public PropertyDescriptor getPropertyDescriptor(String name) {
		if(pdMap == null) {
			synchronized(this) {
				if(pdMap == null) {
					pdMap = new HashMap<String, PropertyDescriptor>();
					for(PropertyDescriptor pd : getPropertyDescriptors()) {
						pdMap.put(pd.getName(), pd);
					}
				}
			}
		}
		return pdMap.get(name);
	}
	
	public FieldDescriptor getFieldDescriptor(String fieldName) {
		if(!fdMap.containsKey(fieldName)) {
			synchronized(this) {
				if(!fdMap.containsKey(fieldName)) {
					FieldDescriptor fd = new FieldDescriptor(this, fieldName);
					fdMap.put(fieldName, fd);
				}
			}
		}
		return fdMap.get(fieldName);
	}
	
	private static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
		try {
			List<PropertyDescriptor> pds = new ArrayList<PropertyDescriptor>();
			for(PropertyDescriptor pd : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
				if(!"class".equals(pd.getName())) {
					pds.add(pd);
				}
			}
			return pds.toArray(new PropertyDescriptor[]{});
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Class<?> getOriginClass() {
		return this.clazz;
	}
}
