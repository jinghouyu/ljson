package org.jinghouyu.json.core;

public class JsonException extends RuntimeException {

	private static final long serialVersionUID = -2825827976335429031L;

	public JsonException() {
		super();
	}

	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonException(String message) {
		super(message);
	}

	public JsonException(Throwable cause) {
		super(cause);
	}
}
