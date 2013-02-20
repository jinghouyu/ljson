package org.jinghouyu.json.serializer;

import java.util.Date;

import org.jinghouyu.json.core.JsonContext;
import org.jinghouyu.json.core.Serializer;

public class DateSerializer implements Serializer<Date> {

	@Override
	public void serialize(JsonContext context, Date value) {
		context.context(value.getTime());
	}
}
