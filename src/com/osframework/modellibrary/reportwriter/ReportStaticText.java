package com.osframework.modellibrary.reportwriter;

public class ReportStaticText extends ReportElement {

	public ReportStaticText() {
		initialize();
	}
	
	public ReportStaticText(ReportPosition reportElement, ReportTextElement text, ReportText display) {
		initialize();
		children.addReportElement(reportElement);
		children.addReportElement(text);
		children.addReportElement(display);
	}
	
	public void initialize() {
		setName("staticText");
		setAttributes(getTextFieldAttributes());
	}
	
	public ReportAttributeList getTextFieldAttributes() {
		ReportAttributeList list = new ReportAttributeList();
		return list;
	}
}
