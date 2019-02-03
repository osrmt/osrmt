package com.osframework.modellibrary.reportwriter;


public class ReportImageExpression extends ReportElement {

	public ReportImageExpression(ReportField field) {
		setName("imageExpression");
		ReportAttributeList list = new ReportAttributeList();
        list.addAttribute("class","java.lang.String");
		setAttributes(list);
		setContent("<![CDATA[$F{" + field.getFieldName() + "}]]>"); 

	}

}
