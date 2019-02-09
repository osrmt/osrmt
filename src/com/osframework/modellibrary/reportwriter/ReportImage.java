package com.osframework.modellibrary.reportwriter;

public class ReportImage extends ReportElement {

	public ReportImage() {
		initialize();
	}
	
	public ReportImage(ReportPosition reportElement, ReportGraphicElement text, ReportImageExpression expression) {
		initialize();
		children.addReportElement(reportElement);
		children.addReportElement(text);
		children.addReportElement(expression);
	}
	
	public void initialize() {
		setName("image");
		setAttributes(getTextFieldAttributes());
	}
	
	public ReportAttributeList getTextFieldAttributes() {
		ReportAttributeList list = new ReportAttributeList();
        list.addAttribute("scaleImage","RetainShape");
        list.addAttribute("evaluationTime","Now");
		return list;
	}
}
