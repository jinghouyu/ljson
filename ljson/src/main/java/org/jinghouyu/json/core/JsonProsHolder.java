package org.jinghouyu.json.core;

import java.util.LinkedList;

public class JsonProsHolder {

	private static ThreadLocal<DotString> threadLocal = new ThreadLocal<DotString>();
	
	static void add(String s) {
		DotString dotString = threadLocal.get();
		if(dotString == null) {
			dotString = new DotString();
			threadLocal.set(dotString);
		}
		dotString.add(s);
	}
	
	static String get() {
		DotString dotString = threadLocal.get();
		if(dotString == null) return null;
		return dotString.get();
	}
	
	static void back() {
		DotString dotString = threadLocal.get();
		if(dotString == null) return;
		dotString.removeLast();
	}
	
	static void remove() {
		threadLocal.remove();
	}
	
	private static class DotString {
		private String current = "";
		private LinkedList<String> list = new LinkedList<String>();
		public void add(String s) {
			if(list.size() != 0) {
				current += '.';
			}
			current+= s;
			list.add(s);
		}
		public String get() {
			return "".equals(current) ? null : current;
		}
		public void removeLast() {
			String lastStr = list.pollLast();
			if(list.size() == 0) {
				current = "";
			} else {
				current = current.substring(0, current.length() - lastStr.length() - 1);
			}
		}
	}
	
	public static void main(String[] args) {
		JsonProsHolder.add("1");
		JsonProsHolder.add("2");
		JsonProsHolder.add("3");
		JsonProsHolder.add("4");
		System.out.println(JsonProsHolder.get());
		JsonProsHolder.add("5.0");
		JsonProsHolder.add("6");
		System.out.println(JsonProsHolder.get());
		JsonProsHolder.back();
		JsonProsHolder.back();
//		JsonProsHolder.back();
//		JsonProsHolder.back();
//		JsonProsHolder.back();
//		JsonProsHolder.back();
		System.out.println(JsonProsHolder.get());
	}
}
