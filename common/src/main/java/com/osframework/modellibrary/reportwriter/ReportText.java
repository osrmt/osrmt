package com.osframework.modellibrary.reportwriter;

public class ReportText extends ReportElement {

	public ReportText(String text) {
		initialize(text);
	}
	
	public void initialize(String text) {
		setName("text");
		setContent("<![CDATA[" + text + "]]>"); 
	}

}
