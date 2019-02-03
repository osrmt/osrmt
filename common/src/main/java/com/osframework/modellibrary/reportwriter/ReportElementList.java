package com.osframework.modellibrary.reportwriter;

import java.util.Enumeration;
import java.util.Vector;

public class ReportElementList extends Vector {

	public void addReportElement(ReportElement r) {
		add(r);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Enumeration e1 = this.elements();
		while (e1.hasMoreElements()) {
			ReportElement r = (ReportElement) e1.nextElement();
			sb.append(r.toString());
		}
		return sb.toString();
	}
}
