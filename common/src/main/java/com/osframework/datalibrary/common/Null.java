package com.osframework.datalibrary.common;

/**
 * Used to store a null object in a collection
 */
public class Null {
	public Object type = null;
	public Null(Object o) {
		this.type = o;
	}
	public String toString() {
		return "null";
	}
}
