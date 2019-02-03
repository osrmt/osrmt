//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ApplicationFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int APPLICATIONCONTROLFORM = 384;

	private int ApplicationRefId = 0;
	public ApplicationFramework(int ApplicationRefId) {
		this.ApplicationRefId = ApplicationRefId;		
	}

	public int getApplicationRefId() {
		return ApplicationRefId;
	}

	public static ApplicationFramework get(int ApplicationRefId) {
		return new ApplicationFramework(ApplicationRefId);
	}

}
