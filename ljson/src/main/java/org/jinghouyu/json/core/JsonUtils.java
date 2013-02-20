package org.jinghouyu.json.core;

import java.util.HashSet;
import java.util.Set;

public class JsonUtils {

	static String getNextString(TextScanner scanner, char... delimiters) {
		StringBuilder key = new StringBuilder();
		boolean hasTran = false;
		for(;;) {
			char current = scanner.current();
			if(current == '\\' && !hasTran) {
				hasTran = true;
			} else {
				if(hasTran) {
					appendTran(key, scanner);
				} else if(contains(delimiters, current)) {
					break;
				} else {
					key.append(current);
				}
				hasTran = false;
			}
			scanner.increment();
		}
		return key.toString();
	}
	
	static void appendTran(StringBuilder appender, TextScanner scanner) {
		char current = scanner.current();
		switch(current) {
		case '\\' : appender.append("\\");break;
		case 't' : appender.append("\t");break;
		case 'b' : appender.append("\b");break;
		case 'r' : appender.append("\r");break;
		case 'n' : appender.append("\n");break;
		case 'f' : appender.append("\f");break;
		case '\'' : appender.append("\'");break;
		case '"' : appender.append("\"");break;
		case '0' : appender.append("\0");break;
		case '1' : appender.append("\1");break;
		case '2' : appender.append("\2");break;
		case '3' : appender.append("\3");break;
		case '4' : appender.append("\4");break;
		case '5' : appender.append("\5");break;
		case '6' : appender.append("\6");break;
		case '7' : appender.append("\7");break;
		default : throw new JsonException("invalid escape character " + current + "," + scanner.currentPosition());
		}
	}
	
	private static boolean contains(char[] array, char value) {
		for(int i = 0, n = array.length; i < n; i++) {
			if(value == array[i]) {
				return true;
			}
		}
		return false;
	}
	
	private static Set<Character> needRendered = new HashSet<Character>();
	static {
		needRendered.add('\\');
		needRendered.add('\"');
	}
	
	public static String render(String value) {
		StringBuilder sb = new StringBuilder();
		for(char c : value.toCharArray()) {
			if(needRendered.contains(c)) {
				sb.append('\\');
			}
			sb.append(c);
		}
		return sb.toString();
	}
}
