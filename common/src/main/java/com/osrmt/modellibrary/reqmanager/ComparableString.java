package com.osrmt.modellibrary.reqmanager;


public class ComparableString implements Comparable {
	
	private String s;
	
	public ComparableString(String s) {
		this.s =s;
	}

	public int compareTo(Object arg0) {
		return 0;
	}
	
	public String toString() {
		return s;
	}	
}

