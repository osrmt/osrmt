//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class WorkflowFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int WorkflowRefId = 0;
	public WorkflowFramework(int WorkflowRefId) {
		this.WorkflowRefId = WorkflowRefId;		
	}

	public int getWorkflowRefId() {
		return WorkflowRefId;
	}

	public static WorkflowFramework get(int WorkflowRefId) {
		return new WorkflowFramework(WorkflowRefId);
	}

}
