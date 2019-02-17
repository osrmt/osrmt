//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ControlTemplateFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ALTFLOW = 5435;

	private int ControlTemplateRefId = 0;
	public ControlTemplateFramework(int ControlTemplateRefId) {
		this.ControlTemplateRefId = ControlTemplateRefId;		
	}

	public int getControlTemplateRefId() {
		return ControlTemplateRefId;
	}

	public static ControlTemplateFramework get(int ControlTemplateRefId) {
		return new ControlTemplateFramework(ControlTemplateRefId);
	}

}
