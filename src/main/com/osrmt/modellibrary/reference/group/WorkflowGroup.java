//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class WorkflowGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int WorkflowRefId = 0;
	public WorkflowGroup(int WorkflowRefId) {
		this.WorkflowRefId = WorkflowRefId;		
	}

	public int getWorkflowRefId() {
		return WorkflowRefId;
	}

	public static WorkflowGroup get(int WorkflowRefId) {
		return new WorkflowGroup(WorkflowRefId);
	}

}
