package org.jinghouyu.json.core;

public class JsonParser {

	static Object parse(CharSequence cs) {
		TextScanner scanner = new TextScanner(cs);
		Object value = parse(scanner);
		scanner.skipWhiteSpace();
		if(scanner.currentPos() < cs.length()) {
			throw new JsonException("syntax error, end error with string [" + scanner + "] " + scanner.currentPosition());
		}
		return value;
	}
	
	static Object parse(TextScanner scanner) {
		scanner.skipWhiteSpace();
		char current = scanner.current();
		if(current == '{') {
			Object jsonObject = JsonObjectParser.parse(scanner);
			return jsonObject;
		} else if(current == '[') {
			Object jsonObject = JsonArrayParser.parse(scanner);
			return jsonObject;
		} else if(current == '"') {
			scanner.increment();
			String result = JsonUtils.getNextString(scanner, '"');
			scanner.increment();
			return result;
		} else if(current == '\'') {
			scanner.increment();
			String result = JsonUtils.getNextString(scanner, '\'');
			scanner.increment();
			return result;
		} else { //Number
			String str = JsonUtils.getNextString(scanner, ',', '}', ':');
			try {
				str = str.trim().toLowerCase();
				if("null".equals(str)) {
					return null;
				}
				Double result = Double.parseDouble(str);
				if(str.contains(".")) {
					return result;
				} else {
					return Long.parseLong(str.trim());
				}
			} catch(NumberFormatException e) {
				throw new JsonException("synta error, invalid value [" + str + "] " + scanner.currentPosition());
			}
		}
	}
}
