package com.osframework.modellibrary.reportwriter;

public class ReportQueryString extends ReportElement {

	public ReportQueryString(String sql) {
		initialize(sql);
	}
	
	public void initialize(String sql) {
		setName("queryString");
		setContent("<![CDATA[" + sql + "]]>");
	}
}
