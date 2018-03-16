package com.specmate.connectors.api;

public class ConnectorUtil {

	public static String toId(String text) {
		return text.replaceAll("[^a-zA-Z0-9\\-_]*", "");
	}
}
