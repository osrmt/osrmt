package com.osframework.modellibrary.reportwriter;

import java.util.*;

public class ReportAttributeList extends Vector {
	
	public ReportAttributeList() {
		
	}
	
	public ReportAttributeList (String name, String value) {
		add(new ReportAttribute(name, value));
	}
	
	public ReportAttributeList(String name, String value, String name2, String value2) {
		add(new ReportAttribute(name, value));
		add(new ReportAttribute(name2, value2));
	}
	
	public void addAttribute(String name, String value) {
		add(new ReportAttribute(name, value));
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer(64);
		Enumeration e1 = elements();
		while (e1.hasMoreElements()) {
			sb.append(e1.nextElement());
		}
		return sb.toString();
	}

}
