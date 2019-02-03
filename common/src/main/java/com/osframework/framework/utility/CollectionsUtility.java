package com.osframework.framework.utility;

import java.util.Enumeration;
import java.util.Vector;

public class CollectionsUtility {

	/**
	 * Gets the string representation of all vector elements
	 * and returns with linefeed in between
	 * 
	 * @param v
	 * @return
	 */
	public static String getVectorStringContents(Vector v) {
		StringBuffer sb = new StringBuffer(1024);
		Enumeration e1 = v.elements();
		while (e1.hasMoreElements()) {
			sb.append(e1.nextElement().toString() + "\r\n");
		}
		return sb.toString();
	}
}
