package org.jinghouyu.json.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.jinghouyu.json.core.JsonException;

/**
 * @author jinghouyu
 *
 */
public class FieldDescriptor {

	private ClassDescriptor classDescriptor;
	private String fieldName;
	
	private Field field;
	private PropertyDescriptor propertyDescriptor;
	private boolean hasField;
	
	public FieldDescriptor(ClassDescriptor classDescriptor, String fieldName) {
		this.classDescriptor = classDescriptor;
		this.fieldName = fieldName;
		init();
	}
	
	/**
	 * will -r up searching until the spicified field discovered
	 * @return
	 */
	public Field getField() {
		if(field == null && !hasField) {
			synchronized(this) {
				if(field == null && !hasField) {
					field = searchAscVisibleField(classDescriptor.getOriginClass());
					hasField = true;
				}
			}
		}
		if(field != null) {
			field.setAccessible(true);
		}
		return field;
	}
	
	private Field searchAscVisibleField(Class<?> current) {
		Field field = null;
		while(field == null && !Object.class.equals(current)) {
			try {
				field = current.getDeclaredField(fieldName);
				if(field == null) continue;
				break; // field has been found
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {
			}
			current = current.getSuperclass();
		}
		if(field != null && isExtend(field)) {
			return field;
		}
		return null;
	}
	
	private boolean isExtend(Field field) {
		int mod = field.getModifiers();
		Class<?> sourceClass = classDescriptor.getOriginClass();
		Class<?> fieldClass = field.getDeclaringClass();
		if(sourceClass.equals(fieldClass)) {
			return true;
		}
		if(Modifier.isPrivate(mod)) {
			//if source class is an inner class
			if(sourceClass.getEnclosingClass() != null && sourceClass.getPackage().equals(fieldClass.getPackage())) {
				return true;
			}
			return false;
		}
		if(Modifier.isPublic(mod) || Modifier.isProtected(mod)) {
			return true;
		}
		if(Modifier.isStatic(mod)) {
			return false;
		}
		//default level, are source class and the class field belong to in the same package?
		return sourceClass.getPackage().equals(fieldClass.getPackage());
	}
	
	private void init() {
		propertyDescriptor = classDescriptor.getPropertyDescriptor(fieldName);
	}
	
	public ClassDescriptor getClassDescriptor() {
		return classDescriptor;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}
	
	public boolean write(Object o, Object value) {
		//setter first the field
		if(propertyDescriptor == null || propertyDescriptor.getWriteMethod() == null) {
			Field field = getField();
			if(field == null) {
				return false;
			}
			try {
				field.set(o, value);
			} catch (IllegalArgumentException e) {
				throw new JsonException("argument Missing match : " + getGenericTypeForWrite(), e);
			} catch (IllegalAccessException e) {
				// unreachable branch;
			}
		} else {
			Method method = propertyDescriptor.getWriteMethod();
			if(method == null) {
				return false;
			}
			try {
				method.invoke(o, value);
			} catch (IllegalArgumentException e) {
				throw new JsonException(e);
			} catch (IllegalAccessException e) {
				// unreachable branch;
			} catch (InvocationTargetException e) {
				throw new JsonException(e);
			}
		}
		return true;
	}
	
	public TypeDescriptor getGenericTypeForWrite() {
		//setter first then field
		if(propertyDescriptor == null || propertyDescriptor.getWriteMethod() == null) {
			Field field = getField();
			if(field == null) {
				return null;
			}
			return new TypeDescriptor(field.getGenericType());
		} else {
			Method method = propertyDescriptor.getWriteMethod();
			if(method == null) {
				return null;
			}
			return new TypeDescriptor(method.getGenericParameterTypes()[0]);
		}
	}
	
	public Object read(Object o) {
		//getter first then field
		if(propertyDescriptor == null || propertyDescriptor.getReadMethod() == null) {
			Field field = getField();
			if(field == null) {
				return null;
			}
			try {
				return field.get(o);
			} catch (IllegalArgumentException e) {
				throw new JsonException(e);
			} catch (IllegalAccessException e) {
				// unreachable branch;
			}
		} else {
			Method method = propertyDescriptor.getReadMethod();
			if(method == null) {
				return null;
			}
			try {
				return method.invoke(o);
			} catch (IllegalArgumentException e) {
				throw new JsonException(e);
			} catch (IllegalAccessException e) {
				// unreachable branch;
			} catch (InvocationTargetException e) {
				throw new JsonException(e);
			}
		}
		return null;
	}
}
