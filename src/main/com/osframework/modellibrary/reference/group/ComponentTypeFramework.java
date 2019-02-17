//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ComponentTypeFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int REQUIREMENTDETAIL = 988;

	private int ComponentTypeRefId = 0;
	public ComponentTypeFramework(int ComponentTypeRefId) {
		this.ComponentTypeRefId = ComponentTypeRefId;		
	}

	public int getComponentTypeRefId() {
		return ComponentTypeRefId;
	}

	public static ComponentTypeFramework get(int ComponentTypeRefId) {
		return new ComponentTypeFramework(ComponentTypeRefId);
	}

}
