package com.osrmt.appclient.setup;

/**
 * Used to store a null object in a collection
 */
public class DataNull {
	public Object type = null;
	public DataNull(Object o) {
		this.type = o;
	}
	public String toString() {
		return "null";
	}
}