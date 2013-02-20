package org.jinghouyu.json.core;

public class JsonArrayParser {

	static JsonArray parse(TextScanner scanner) {
		char current = scanner.current();
		if(current != '[') {
			throw new JsonException("syntax error, should be '[' " + scanner.currentPosition());
		}
		scanner.increment();
		return parseJsonArray(scanner);
	}
	
	private static JsonArray parseJsonArray(TextScanner scanner) {
		char current = scanner.current();
		JsonArray array = new JsonArray();
		while(current != ']') {
			scanner.skipWhiteSpace();
			Object item = JsonParser.parse(scanner);
			array.add(item);
			scanner.skipWhiteSpace();
			current = scanner.current();
			if(current != ',' && current != ']') {
				throw new JsonException("syntax error, should be ',' or ']' " + scanner.currentPosition());
			}
			scanner.increment();
		}
		return array;
	}
}
