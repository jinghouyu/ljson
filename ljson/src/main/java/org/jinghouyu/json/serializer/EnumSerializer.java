package org.jinghouyu.json.serializer;

import org.jinghouyu.json.core.JsonContext;
import org.jinghouyu.json.core.Serializer;
import org.jinghouyu.json.core.JsonUtils;

public class EnumSerializer implements Serializer<Enum<?>> {

	public void serialize(JsonContext context, Enum<?> value) {
		String valueStr = value.name();
		context.context('"')
			    .context(JsonUtils.render(valueStr))
			    .context('"');
	}
}
