package com.osframework.modellibrary.reportwriter;

public class ReportTextFieldExpression extends ReportElement {

	public ReportTextFieldExpression(ReportField textField) {
		initialize(textField);
	}
	
	public void initialize(ReportField field) {
		setName("textFieldExpression");
		setAttributes(new ReportAttributeList("class",field.getDataType()));
		setContent("<![CDATA[$F{" + field.getFieldName() + "}]]>"); 
	}

}
