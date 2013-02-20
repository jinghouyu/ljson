package org.jinghouyu.json.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.jinghouyu.json.beans.TypeDescriptor;
import org.jinghouyu.json.core.config.JsonDeserializeConfig;
import org.jinghouyu.json.to.Novel;
import org.jinghouyu.json.to.Person;
import org.jinghouyu.json.to.Sex;

public class TestJsonDeserialize extends TestCase{

	private Person person = null;
	
	public void setUp() {
		person = new Person();
		person.setName("liujingyu");
		person.setAge(25);
		person.setSex(Sex.MALE);
		person.setBirthday(new Date(1360162545000L));
		List<Novel> novels = new ArrayList<Novel>();
		Novel novel = new Novel();
		novel.setAuthorName("Sim Marting");
		novel.setPublishDate(new Date());
		novel.setWordSize(1000);
		novel.setName("how to become stongers");
		novels.add(novel);
		person.setNovels(novels);
		Map<String, List<? extends Novel>> cateNovels = new HashMap<String, List<? extends Novel>>();
		String[] cates = new String[]{"武侠", "修真", "玄幻", "网游"};
		for(int i = 0 ; i < cates.length ; i++) {
			List<Novel> list = new ArrayList<Novel>();
			for(int j = 0; j < 2; j++) {
				Novel novel_1 = new Novel();
				novel_1.setAuthorName("《" + cates[i] +"_" + "小说_" + j + "》");
				list.add(novel_1);
			}
			cateNovels.put(cates[i], list);
		}
		person.setCateNovels(cateNovels);
	}
	
	public void testJsonObject() {
		Person person1 = JsonObject.fromObject(person).cast(Person.class);
		assertEquals(JsonObject.fromObject(person).toJsonString(), JsonObject.fromObject(person1).toJsonString());
		String jsonStr = JsonObject.fromObject(person).toJsonString();
		System.out.println(jsonStr);
		JsonObject object = JsonObject.fromObject(jsonStr);
		Person person2 = object.cast(Person.class);
		assertEquals("how to become stongers", person2.getNovels().get(0).getName());
		assertEquals("《修真_小说_0》", person2.getCateNovels().get("修真").get(0).getAuthorName());
	}
	
	public void testConfig() {
		String jsonStr = JsonObject.fromObject(person).toJsonString();
		JsonObject jsonObject = JsonObject.fromObject(jsonStr);
		JsonDeserializeConfig config = new JsonDeserializeConfig();
		final String expectedDateStr = "2012-01-02";
		config.setDeserializer(Date.class, new Deserializer<Date>() {
			@Override
			public Date deserialize(Object value, TypeDescriptor targetType) {
				try {
					return new SimpleDateFormat("yyyy-MM-dd").parse(expectedDateStr);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		});
		Person person = jsonObject.cast(Person.class, config);
		String dateStr1 = new SimpleDateFormat("yyyy-MM-dd").format(person.getBirthday());
		System.out.println(person.getNovels().get(0).getClass());
		String dateStr2 = new SimpleDateFormat("yyyy-MM-dd").format(person.getNovels().get(0).getPublishDate());
		assertEquals(expectedDateStr, dateStr1);
		assertEquals(expectedDateStr, dateStr2);
	}
}
