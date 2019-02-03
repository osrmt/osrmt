package com.osframework.modellibrary.reportwriter;

import com.osframework.framework.logging.Debug;

public class ReportField extends ReportElement {

	public final static int VARCHAR = 0;
	public final static int DATETIME = 1;
	public final static int INTEGER = 2;
	public final static int DOUBLE = 3;
	private int dataType = 0;
	private String fieldName;
	
	public ReportField(String fieldName, int javaDataType) {
		setName("field");
		this.dataType = javaDataType;
		this.fieldName = fieldName;
		setAttributes(new ReportAttributeList(
				"name",fieldName,"class",getType(javaDataType)));
		this.children.add(new ReportElement("fieldDescription","<![CDATA[" + fieldName + "]]>"));
	}
	
	public static String getType(int type) {
		switch (type) {
		case VARCHAR: return "java.lang.String";
		case DATETIME: return "java.sql.Timestamp";
		case INTEGER: return "java.lang.Integer";
		case DOUBLE: return "java.lang.Double";
		}
		Debug.LogWarning("ReportField", "type " + type + " not found");
		return "java.lang.String";
	}

	public String getDataType() {
		return getType(dataType);
	}

	public String getFieldName() {
		return fieldName;
	}


}
