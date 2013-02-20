package org.jinghouyu.json.core;

import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

public class TestJsonObject extends TestCase {

	public void testParseArray() {
		String jsonText = readFile("testJson.txt");
		JsonObject jsonObject = JsonObject.fromObject(jsonText);
		System.out.println(jsonObject.getBoolean("age"));
		System.out.println(jsonObject.getInteger("age"));
		System.out.println(jsonObject.getString("friends"));
		jsonObject.put("frields", jsonObject.getString("friends"));
		System.out.println(jsonObject.toString());
		System.out.println(jsonObject.getJsonArray("friends"));
	}
	
	private String readFile(String file) {
		FileReader reader = null;
		char[] c = new char[128];
		int len = 0;
		try {
			StringBuilder sb = new StringBuilder();
			reader = new FileReader(file);
			while((len = reader.read(c)) > 0) {
				sb.append(c, 0, len);
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
