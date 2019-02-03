//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ActionFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ADDARTIFACT = 10;

	private int ActionRefId = 0;
	public ActionFramework(int ActionRefId) {
		this.ActionRefId = ActionRefId;		
	}

	public int getActionRefId() {
		return ActionRefId;
	}

	public static ActionFramework get(int ActionRefId) {
		return new ActionFramework(ActionRefId);
	}

}
