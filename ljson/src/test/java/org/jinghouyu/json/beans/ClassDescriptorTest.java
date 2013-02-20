package org.jinghouyu.json.beans;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

import junit.framework.TestCase;

import org.jinghouyu.json.to.Person;

public class ClassDescriptorTest  extends TestCase {

	public void testGeneric() {
		ClassDescriptor cd = ClassDescriptor.getClassDescriptor(Person.class);
		Class<?> clazz = cd.getType("novels");
		GenericDeclaration gd = (GenericDeclaration) clazz;
		Method method = cd.getPropertyDescriptor("novels").getReadMethod();
		System.out.println(method.getGenericReturnType());
		for(Type type : ((ParameterizedType)  method.getGenericReturnType()).getActualTypeArguments()) {
			System.out.println(type);
			System.out.println(((WildcardType) type).getLowerBounds().length);
		}
	}
}
