//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class CustomControlFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ARTIFACTALTFLOW = 987;

	private int CustomControlRefId = 0;
	public CustomControlFramework(int CustomControlRefId) {
		this.CustomControlRefId = CustomControlRefId;		
	}

	public int getCustomControlRefId() {
		return CustomControlRefId;
	}

	public static CustomControlFramework get(int CustomControlRefId) {
		return new CustomControlFramework(CustomControlRefId);
	}

}
