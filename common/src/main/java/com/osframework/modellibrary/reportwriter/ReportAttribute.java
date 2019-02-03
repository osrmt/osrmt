package com.osframework.modellibrary.reportwriter;

/**
 * Represents an XML Attribute
 * e.g. printOrder="Vertical"
 * 
 *
 */
public class ReportAttribute {
	
	private String name;
	private String value = "";
	private boolean visible = true;

	public ReportAttribute () {
		
	}
	
	public ReportAttribute (String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String toString() {
		if (name != null && isVisible()) {
			return " " + name + "=\"" + value + "\"";
		} else {
			return "";
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
