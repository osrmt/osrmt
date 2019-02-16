package com.osframework.modellibrary.common;

import java.util.*;

public class Comparison {
	
	public static boolean areEqual(int field1, int field2) {
		return field1 == field2;
	}
	
	public static boolean areEqual(double field1, double field2) {
		return field1 == field2;
	}

	public static boolean areEqual(String field1, String field2) {
		if (field1 == null && field2 == null) {
			return true;
		} else if (field1 == null) {
			return false;
		} else if (field2 == null) {
			return false;
		} else {
			return field1.compareTo(field2)==0;
		}
	}

	public static boolean areEqual(Calendar field1, Calendar field2) {
		if (field1 == null && field2 == null) {
			return true;
		} else if (field1 == null) {
			return false;
		} else if (field2 == null) {
			return false;
		} else {
			return field1.getTimeInMillis() == field2.getTimeInMillis();
		}
	}

	public static int compare(int i, int j) {
		if (i==j) {
			return 0;
		} else if (i<j) {
			return -1;
		} else {
			return 1;
		}
	}
}
