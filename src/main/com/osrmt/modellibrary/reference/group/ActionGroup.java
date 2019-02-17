//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ActionGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ADDARTIFACT = 10;
	public static final int CHANGECONTROLCLOSE = 1455;
	public static final int CHANGECONTROLEDIT = 1456;
	public static final int CHANGECONTROLFIND = 1457;
	public static final int CHANGECONTROLNEW = 1452;
	public static final int CHANGECONTROLPRINT = 1453;
	public static final int CHANGECONTROLREFRESH = 1454;
	public static final int CREATEBASELINE = 9;
	public static final int MODIFYARTIFACT = 11;
	public static final int REQMGRADMINPOSITIONS = 1011;
	public static final int REQMGRADMINPROJECTS = 5339;
	public static final int REQMGRADMINREF = 365;
	public static final int REQMGRADMINUSERGROUPS = 1561;
	public static final int REQMGRADMINUSERS = 290;
	public static final int REQMGRCHANGEPASSWORD = 471;
	public static final int REQMGREDITARTIFACT = 255;
	public static final int REQMGREDITCOPY = 256;
	public static final int REQMGREDITCUT = 257;
	public static final int REQMGREDITDELETE = 258;
	public static final int REQMGREDITFIND = 259;
	public static final int REQMGREDITPASTE = 260;
	public static final int REQMGREDITREPLACE = 261;
	public static final int REQMGREXPORTBRANCH = 5465;
	public static final int REQMGRFILECLOSE = 249;
	public static final int REQMGRFILEEXIT = 251;
	public static final int REQMGRFILEEXPORT = 910;
	public static final int REQMGRFILEIMPORT = 997;
	public static final int REQMGRFILENEW = 247;
	public static final int REQMGRFILEOPEN = 248;
	public static final int REQMGRFILEPRINT = 253;
	public static final int REQMGRFILERECENT = 250;
	public static final int REQMGRFILEREFRESH = 254;
	public static final int REQMGRHELPABOUT = 1103;
	public static final int REQMGRHELPOSRMT = 266;
	public static final int REQMGRHELPUPDATES = 267;
	public static final int REQMGRMOVEDOWN = 1579;
	public static final int REQMGRMOVEUP = 1578;
	public static final int REQMGRNEWPRODUCT = 284;
	public static final int REQMGRSYSTEMBASELINE = 1137;
	public static final int REQMGRSYSTEMDEBUG = 995;
	public static final int REQMGRSYSTEMEXPORT = 478;
	public static final int REQMGRSYSTEMFORMS = 287;
	public static final int REQMGRSYSTEMIMPORT = 480;
	public static final int REQMGRSYSTEMLOG = 468;
	public static final int REQMGRSYSTEMNEWARTIFACT = 1637;
	public static final int REQMGRSYSTEMNEWARTIFACTFIELD = 5363;
	public static final int REQMGRSYSTEMOPTIONS = 1585;
	public static final int REQMGRSYSTEMREF = 364;
	public static final int REQMGRTOOLSCUSTOMIZE = 262;
	public static final int REQMGRTOOLSFILTER = 903;
	public static final int REQMGRTOOLSIMPACT = 1183;
	public static final int REQMGRTOOLSOPTIONS = 263;
	public static final int REQMGRTOOLSREPORTOUTLINE = 5352;
	public static final int REQMGRTOOLSREPORTS = 276;
	public static final int REQMGRTOOLSSPELLING = 264;
	public static final int REQMGRTOOLSTRACE = 265;
	public static final int REQMGRTOOLSTRACEBRANCH = 1693;
	public static final int TRACEEDITCRITERIA = 1522;
	public static final int TRACEEDITUNTRACE = 1540;
	public static final int TRACEFILECLOSE = 1529;
	public static final int TRACEFILEOPENPRODUCT = 1634;
	public static final int TRACEFILEPRINT = 1530;
	public static final int TRACEFILEREFRESH = 1528;
	public static final int TRACELEFTTORIGHT = 1531;
	public static final int TRACERIGHTTOLEFT = 1575;
	public static final int USERADMINPASSWORD = 416;
	public static final int USERADMINSECURITY = 440;
	public static final int VIEWARTIFACT = 12;
	public static final int VIEWCHANGECONTROL = 1428;
	public static final int VIEWTRACEABILITY = 1429;

	private int ActionRefId = 0;
	public ActionGroup(int ActionRefId) {
		this.ActionRefId = ActionRefId;		
	}

	public int getActionRefId() {
		return ActionRefId;
	}

	public static ActionGroup get(int ActionRefId) {
		return new ActionGroup(ActionRefId);
	}

}
