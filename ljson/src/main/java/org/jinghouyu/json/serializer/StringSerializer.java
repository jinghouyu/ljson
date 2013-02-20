package org.jinghouyu.json.serializer;

import org.jinghouyu.json.core.JsonContext;
import org.jinghouyu.json.core.Serializer;
import org.jinghouyu.json.core.JsonUtils;

public class StringSerializer implements Serializer<String> {

	public void serialize(JsonContext context, String value) {
		context.context('"')
			    .context(JsonUtils.render(value))
			    .context('"');
	}
}
