package org.jinghouyu.json.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.jinghouyu.json.core.config.JsonSerializeConfig;
import org.jinghouyu.json.to.Novel;
import org.jinghouyu.json.to.Person;
import org.jinghouyu.json.to.Sex;

public class TestJsonSerializer extends TestCase {

	public void testConfig() {
		Person person = new Person();
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
		JsonSerializeConfig config = new JsonSerializeConfig();
		config.setSerializer("novels.authorName", new Serializer<String>() {
			@Override
			public void serialize(JsonContext context, String value) {
				context.context("已经被代理了");
			}
		});
		config.setSerializer(Date.class, new Serializer<Date>() {
			@Override
			public void serialize(JsonContext context, Date value) {
				context.context("\"" + value.toString() + "\"");
			}
		});
		System.out.println(JsonObject.fromObject(person).toJsonString(config));
	}
}
