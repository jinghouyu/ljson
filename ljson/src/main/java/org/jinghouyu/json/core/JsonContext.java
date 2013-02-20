package org.jinghouyu.json.core;

/**
 * @author jinghouyu
 *
 */
public class JsonContext {

	private StringBuilder context = new StringBuilder();
	
	public JsonContext() {}
	
	public JsonContext contextNull() {
		context.append("null");
		return this;
	}
	
	public JsonContext context(String string) {
		context.append(string);
		return this;
	}
	
	public JsonContext context(char c) {
		context.append(c);
		return this;
	}
	
	public JsonContext context(Number number) {
		context.append(number);
		return this;
	}
	
	public JsonContext context(char[] chars, int start, int end) {
		context.append(chars, start, end);
		return this;
	}
	
	public String toString() {
		return context.toString();
	}
}
