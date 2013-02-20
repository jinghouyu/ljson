package org.jinghouyu.json.beans;

import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.jinghouyu.json.to.GoodPerson;
import org.jinghouyu.json.to.Person;

public class FieldDescriptorTest extends TestCase {

	public void testGetField_1() {
		ClassDescriptor cd = ClassDescriptor.getClassDescriptor(GoodPerson.class);
		FieldDescriptor fd = cd.getFieldDescriptor("dname");
		Field field = fd.getField();
		assertEquals(field.getDeclaringClass(), Person.class);
	}
	
	public void testGetField_2() {
		ClassDescriptor cd = ClassDescriptor.getClassDescriptor(Person.class);
		FieldDescriptor fd = cd.getFieldDescriptor("fname");
		Field field = fd.getField();
		assertEquals(field.getDeclaringClass(), Person.class);
	}
}
