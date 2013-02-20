package org.jinghouyu.json.beans;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

public class TypeDescriptor {

	private Type type;
	
	public TypeDescriptor(Type type) {
		this.type = type;
	}
	
	private Class<?> getTypeClass(Type type) {
		if(type instanceof Class) {
			return (Class<?>) type;
		}
		if(type instanceof ParameterizedType) {  //if it's generic type
			Type subType = ((ParameterizedType) type).getRawType();
			return getTypeClass(subType);
		}
		if(type instanceof WildcardType) {
			Type[] subTypes = ((WildcardType) type).getUpperBounds();
			if(subTypes == null || subTypes.length == 0) {
				return Object.class;
			}
			return getTypeClass(subTypes[0]);
		}
		return null;
	}
	
	public TypeDescriptor getValidInnerGeneric(int index) {
		if(type instanceof Class) return new TypeDescriptor(Object.class);
		if(!(type instanceof ParameterizedType)) return null;
		Type[] generics = ((ParameterizedType) type).getActualTypeArguments();
		if(generics == null || generics.length -1 < index) return null;
		Type generic = generics[index];
		return new TypeDescriptor(generic);
	}

	public Class<?> getTypeClass() {
		return getTypeClass(type);
	}
	
}
