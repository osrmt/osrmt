/**
 * 
 */
package com.osframework.framework.utility;

/**
 * Report Writing helper
 *
 */
public class RW {
	
	/**
	 * Return an empty string or string representation
	 * 
	 * @param o
	 * @return
	 */
	public static String chk(Object o) {
		if (o == null) {
			return "";
		} else {
			return o.toString();
		}
	}
	
	/**
	 * Add string representation of value to stringbuffer
	 * wrapped in a tag 
	 * 
	 * @param sb
	 * @param tag
	 * @param value
	 */
	public static void add(StringBuffer sb, String tag, Object value) {
		sb.append("<");
		sb.append(tag);
		sb.append(">");
		sb.append("<![CDATA[");
		sb.append(chk(value));
		sb.append("]]>");
		sb.append("</");
		sb.append(tag);
		sb.append(">");
	}
}
