package org.jinghouyu.json.serializer;

import java.util.Iterator;

import org.jinghouyu.json.core.JsonContext;
import org.jinghouyu.json.core.ObjectSerializer;
import org.jinghouyu.json.core.Serializer;

public class IterableSerializer implements Serializer<Iterable> {

	@Override
	public void serialize(JsonContext context, Iterable value) {
		int i = 0;
		context.context('[');
		Iterator it = value.iterator();
		while(it.hasNext()) {
			ObjectSerializer.getInstance().serialize(context, it.next());
			if(it.hasNext()) {
				context.context(',');
			}
		}
		context.context(']');
	}
}
