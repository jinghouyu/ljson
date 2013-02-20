package org.jinghouyu.json.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.config.JsonDeserializeConfig;
import org.jinghouyu.json.utils.CastUtils;
import org.jinghouyu.json.utils.TypeUtils;

public final class JsonArray extends Json implements List<Object>  {

	private List<Object> list = new ArrayList<Object>();
	
	public static JsonArray fromArray(Object o) {
		if(!check(o)) {
			throw new JsonException("cannot transform type " + o.getClass() + " to JsonArray");
		}
		if(o instanceof JsonArray) {
			return (JsonArray) o;
		}
		return (JsonArray) JsonGenerator.generate(o);
	}
	
	private static boolean check(Object o) {
		Class<?> clazz = o.getClass();
		if(o instanceof CharSequence) {
			CharSequence charseq = (CharSequence) o;
			TextScanner scanner = new TextScanner(charseq);
			scanner.skipWhiteSpace();
			if(scanner.current() == '[') {
				return true;
			} else {
				throw new JsonException("syntax error, json array must start with [");
			}
		} else if(TypeUtils.isArray(clazz) || TypeUtils.isIterable(clazz)) {
			return true;
		}
		return false;
	}
	
	public <T> List<T> toList(Class<T> clazz) {
		List<T> result = new ArrayList<T>(size());
		for(Object item : this) {
			T itemValue = (T)DeserializerDirector.invoke(item, new TypeDescriptor(clazz));
			result.add(itemValue);
		}
		return result;
	}
	
	public <T> List<T>  toList(Class<T> clazz, JsonDeserializeConfig config) {
		try {
			JsonDeserializerConfigHolder.set(config);
			return toList(clazz);
		} finally {
			JsonDeserializerConfigHolder.remove();
		}
	}
	
	public Integer getInteger(int index) {
		return CastUtils.castToInteger(list.get(index));
	}
	
	public Long getLong(int index) {
		return CastUtils.castToLong(list.get(index));
	}
	
	public Float getFloat(int index) {
		return CastUtils.castToFloat(list.get(index));
	}
	
	public Short getShort(int index) {
		return CastUtils.castToShort(list.get(index));
	}
	
	public Byte getByte(int index) {
		return CastUtils.castToByte(list.get(index));
	}
	
	public Character getChar(int index) {
		return CastUtils.castToChar(list.get(index));
	}
	
	public Double getDouble(int index) {
		return CastUtils.castToDouble(list.get(index));
	}
	
	public Boolean getBoolean(int index) {
		return CastUtils.castToBoolean(list.get(index));
	}
	
	public String getString(int index) {
		Object value = list.get(index);
		if(value == null) return null;
		return value.toString();
	}
	
	public JsonObject getJsonObject(int index) {
		Object value = list.get(index);
		if(value == null) return null;
		if(value instanceof JsonObject) {
			return (JsonObject) value;
		}
		return JsonObject.fromObject(value);
	}
	
	public JsonArray getJsonArray(int index) {
		Object value = list.get(index);
		if(value == null) return null;
		if(value instanceof JsonArray) {
			return (JsonArray) value;
		}
		return JsonArray.fromArray(value);
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public Iterator<Object> iterator() {
		return list.iterator();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public boolean add(Object e) {
		return list.add(e);
	}

	public boolean remove(Object o) {
		return list.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	public boolean addAll(Collection<? extends Object> c) {
		return list.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Object> c) {
		return list.addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	public void clear() {
		list.clear();
	}

	public Object get(int index) {
		return list.get(index);
	}

	public Object set(int index, Object element) {
		return list.set(index, element);
	}

	public void add(int index, Object element) {
		list.add(index, element);
	}

	public Object remove(int index) {
		return list.remove(index);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<Object> listIterator() {
		return list.listIterator();
	}

	public ListIterator<Object> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<Object> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
	
	public String toString() {
		return toJsonString();
	}
}
