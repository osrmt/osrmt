package com.osframework.modellibrary.reportwriter;


public class ReportGraphicElement extends ReportElement {

	public ReportGraphicElement() {
		setName("textElement");
		ReportAttributeList list = new ReportAttributeList();
        list.addAttribute("stretchType","NoStretch");
        list.addAttribute("pen","None");
		setAttributes(list);

	}

}
