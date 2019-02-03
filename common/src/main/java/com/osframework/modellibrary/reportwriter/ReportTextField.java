package com.osframework.modellibrary.reportwriter;

public class ReportTextField extends ReportElement {

	public ReportTextField() {
		initialize();
	}
	
	public ReportTextField(ReportPosition reportElement, ReportTextElement text, ReportTextFieldExpression expression) {
		initialize();
		children.addReportElement(reportElement);
		children.addReportElement(text);
		children.addReportElement(expression);
	}
	
	public void initialize() {
		setName("textField");
		setAttributes(getTextFieldAttributes());
	}
	
	public ReportAttributeList getTextFieldAttributes() {
		ReportAttributeList list = new ReportAttributeList();
        list.addAttribute("isStretchWithOverflow","true");
        list.addAttribute("isBlankWhenNull","true");
        list.addAttribute("evaluationTime","Now");
        list.addAttribute("hyperlinkType","None");
        list.addAttribute("hyperlinkTarget","Self" );
		return list;
	}
}
