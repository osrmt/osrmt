//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ComponentTypeGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int REQUIREMENTDETAIL = 988;
	public static final int USECASEALTFLOW = 989;
	public static final int USECASEMAINFLOW = 981;

	private int ComponentTypeRefId = 0;
	public ComponentTypeGroup(int ComponentTypeRefId) {
		this.ComponentTypeRefId = ComponentTypeRefId;		
	}

	public int getComponentTypeRefId() {
		return ComponentTypeRefId;
	}

	public static ComponentTypeGroup get(int ComponentTypeRefId) {
		return new ComponentTypeGroup(ComponentTypeRefId);
	}

}
