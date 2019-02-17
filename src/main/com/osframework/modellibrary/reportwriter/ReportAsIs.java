package com.osframework.modellibrary.reportwriter;

public class ReportAsIs extends ReportElement {

	private String xml;
	
	public ReportAsIs(String xml) {
		this.xml = xml;
	}
	
	@Override
	public String toString() {
		return xml;
	}
}
