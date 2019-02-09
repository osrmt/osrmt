//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class PriorityGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DESIGNHIGH = 208;
	public static final int DESIGNLOW = 210;
	public static final int DESIGNMEDIUM = 209;
	public static final int FEATUREIMPORTANT = 15;
	public static final int FEATUREMUSTHAVE = 14;
	public static final int FEATURENOTREQUIRED = 16;
	public static final int FEATURETOBEREVIEWED = 110;
	public static final int IMPLEMENTATIONHIGH = 211;
	public static final int IMPLEMENTATIONLOW = 213;
	public static final int IMPLEMENTATIONMEDIUM = 212;
	public static final int REQUIREMENTHIGH = 205;
	public static final int REQUIREMENTLOW = 207;
	public static final int REQUIREMENTMEDIUM = 206;
	public static final int TESTCASEHIGH = 214;
	public static final int TESTCASELOW = 216;
	public static final int TESTCASEMEDIUM = 215;

	private int PriorityRefId = 0;
	public PriorityGroup(int PriorityRefId) {
		this.PriorityRefId = PriorityRefId;		
	}

	public int getPriorityRefId() {
		return PriorityRefId;
	}

	public static PriorityGroup get(int PriorityRefId) {
		return new PriorityGroup(PriorityRefId);
	}

}
