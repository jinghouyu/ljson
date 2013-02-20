package org.jinghouyu.json.deserializer.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.Deserializer;
import org.jinghouyu.json.core.DeserializerDirector;
import org.jinghouyu.json.core.JsonException;
import org.jinghouyu.json.utils.TypeUtils;

@SuppressWarnings("rawtypes")
public class CollectionDeseriablizer implements Deserializer<Collection> {

	@SuppressWarnings("unchecked")
	@Override
	public Collection deserialize(Object value, TypeDescriptor targetType) {
		Class<?> targetClass = targetType.getTypeClass();
		Collection collection = null;
		if(TypeUtils.isSuperClass(targetClass, ArrayList.class)) {
			collection = new ArrayList<Object>();
		} else if(TypeUtils.isSuperClass(targetClass, LinkedList.class)){
			collection = new LinkedList<Object>();
		} else if(TypeUtils.isSuperClass(targetClass, HashSet.class)) {
			collection = new HashSet<Object>();
		} else if(TypeUtils.isSuperClass(targetClass, TreeSet.class)) {
			collection = new TreeSet<Object>();
		}
		//concurrent
		
		if(collection == null) {
			throw new JsonException("unsupported Type Transformation " + targetClass.getName());
		}
		
		Iterator<?> it = CollectionUtils.getValueIterator(value);
		
		while(it.hasNext()) {
			Object next = it.next();
			Object finalValue = DeserializerDirector.invoke(next, targetType.getValidInnerGeneric(0));
			collection.add(finalValue);
		}
		return collection;
	}
	
}
