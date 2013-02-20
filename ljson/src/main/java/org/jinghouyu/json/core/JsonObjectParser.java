package org.jinghouyu.json.core;

/**
 * parse the JsonObject string which includes character "{" and "}"
 * and skip the jsonStr after parse;
 * @author jinghouyu
 *
 */
public class JsonObjectParser {

	static JsonObject parse(TextScanner scanner) {
		char current = scanner.current();
		if(current != '{') {
			throw new JsonException("syntax error , should be '{' " + scanner.currentPosition());
		}
		scanner.increment();
		return parseJson(scanner);
	}
	
	private static JsonObject parseJson(TextScanner scanner) {
		JsonObject object = new JsonObject();
		char current = scanner.current();
		while(current != '}') {
			String key = getKey(scanner);
			if(key.equals("")) {
				throw new JsonException("syntax error, key is empty " + scanner.currentPosition());
			}
			scanner.increment();
			scanner.skipWhiteSpace();
			current = scanner.current();
			if(current != ':') {
				throw new JsonException("syntax error, should be ':' " + scanner.currentPosition());
			}
			scanner.increment();
			Object value = JsonParser.parse(scanner);
			object.put(key, value);
			scanner.skipWhiteSpace();
			current = scanner.current();
			if(current != ',' && current != '}') {
				throw new JsonException("syntax error, shoud be ',' or '}' " + scanner.currentPosition());
			}
			scanner.increment();
		}
		return object;
	}
	
	static String getKey(TextScanner scanner) {
		scanner.skipWhiteSpace();
		char current = scanner.current();
		if(current == '"') {
			scanner.increment();
			String key = JsonUtils.getNextString(scanner, '"');
			current = scanner.current();
			if(current != '"') {
				throw new JsonException("syntax error, should be '\"' " + scanner.currentPosition());
			}
			return key;
		}
		if(current == '\'') {
			scanner.increment();
			String key = JsonUtils.getNextString(scanner, '\'');
			current = scanner.current();
			if(current != '\'') {
				throw new JsonException("syntax error, shold be ''' " + scanner.currentPosition());
			}
			return key;
		}
		return JsonUtils.getNextString(scanner, ' ',  ':',  '\'', '"', '}');
	}
}
