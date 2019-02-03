//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class StatusGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DESIGNASSIGNED = 82;
	public static final int DESIGNCOMPLETED = 83;
	public static final int DESIGNUNASSIGNED = 81;
	public static final int FEATUREAPPROVED = 18;
	public static final int FEATURECOMPLETED = 19;
	public static final int FEATURESUBMITTED = 17;
	public static final int IMPLEMENTATIONASSIGNED = 218;
	public static final int IMPLEMENTATIONCOMPLETED = 220;
	public static final int IMPLEMENTATIONUNASSIGNED = 217;
	public static final int IMPLEMENTATIONUNITTESTED = 219;
	public static final int REQUIREMENTAPPROVED = 104;
	public static final int REQUIREMENTCOMPLETED = 103;
	public static final int REQUIREMENTSUBMITTED = 105;
	public static final int TESTCASEASSIGNED = 222;
	public static final int TESTCASECOMPLETED = 223;
	public static final int TESTCASEUNASSIGNED = 221;

	private int StatusRefId = 0;
	public StatusGroup(int StatusRefId) {
		this.StatusRefId = StatusRefId;		
	}

	public int getStatusRefId() {
		return StatusRefId;
	}

	public static StatusGroup get(int StatusRefId) {
		return new StatusGroup(StatusRefId);
	}

}
