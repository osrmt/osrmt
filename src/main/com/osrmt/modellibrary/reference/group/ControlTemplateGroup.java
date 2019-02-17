//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ControlTemplateGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ALTFLOW = 5435;
	public static final int ARTIFACTNAME = 5411;
	public static final int ASSIGNED = 5423;
	public static final int ASSIGNEDUSERGROUP = 5438;
	public static final int ASSIGNEDUSERID = 5439;
	public static final int ASSIGNEDUSERNAME = 5440;
	public static final int ATTACHMENTS = 5425;
	public static final int AUTHOR = 5424;
	public static final int CATEGORY = 5437;
	public static final int CHANGEMADE = 5467;
	public static final int CHANGEREASON = 5441;
	public static final int COMPLEXITY = 5420;
	public static final int CONTEXT = 5432;
	public static final int CREATED = 5427;
	public static final int CREATEDBY = 5426;
	public static final int CUSTOM1REFID = 5442;
	public static final int CUSTOM2REFID = 5443;
	public static final int CUSTOM3REFID = 5444;
	public static final int CUSTOM4REFID = 5445;
	public static final int CUSTOMDATE1 = 5446;
	public static final int CUSTOMDATE2 = 5447;
	public static final int CUSTOMDOUBLE1 = 5448;
	public static final int CUSTOMINT1 = 5449;
	public static final int CUSTOMINT2 = 5450;
	public static final int CUSTOMMEMO1 = 5451;
	public static final int CUSTOMMEMO2 = 5452;
	public static final int CUSTOMTEXT1 = 5453;
	public static final int CUSTOMTEXT2 = 5454;
	public static final int CUSTOMTEXT3 = 5455;
	public static final int CUSTOMTEXT4 = 5456;
	public static final int DEPENDENTON = 5416;
	public static final int DESCRIPTION = 5414;
	public static final int DETAILS = 5436;
	public static final int EFFORT = 5419;
	public static final int GOAL = 5431;
	public static final int MAINFLOW = 5417;
	public static final int MODIFICATIONHISTORY = 5430;
	public static final int ORIGIN = 5421;
	public static final int POSTCONDITION = 5434;
	public static final int PRECONDITION = 5433;
	public static final int PRIORITY = 5412;
	public static final int RATIONALE = 5422;
	public static final int REPORTOUTLINE = 5457;
	public static final int REPORTSEQUENCE = 5458;
	public static final int REQUIREMENTNBR = 5418;
	public static final int STATUS = 5413;
	public static final int UPDATED = 5429;
	public static final int UPDATEDBY = 5428;
	public static final int VERSION = 5415;

	private int ControlTemplateRefId = 0;
	public ControlTemplateGroup(int ControlTemplateRefId) {
		this.ControlTemplateRefId = ControlTemplateRefId;		
	}

	public int getControlTemplateRefId() {
		return ControlTemplateRefId;
	}

	public static ControlTemplateGroup get(int ControlTemplateRefId) {
		return new ControlTemplateGroup(ControlTemplateRefId);
	}

}
