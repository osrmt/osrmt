//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ApplicationGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int APPLICATIONCONTROLFORM = 384;
	public static final int APPLICATIONCUSTOMCONTROLFORM = 1232;
	public static final int APPLICATIONSECURITYUSERFORM = 424;
	public static final int APPLICATIONVIEWFORM = 1574;
	public static final int APPSECURITYUSERCOLUMNS = 442;
	public static final int ARTIFACTFORM = 134;
	public static final int ARTIFACTHISTORYCOLUMNS = 230;
	public static final int ARTIFACTLISTEXPORT = 911;
	public static final int ARTIFACTLISTIMPORT = 1010;
	public static final int ARTIFACTRELATIONSHIPCOLUMNS = 229;
	public static final int ARTIFACTSEARCHRESULTS = 145;
	public static final int ARTIFACTSTEPCOLUMNS = 972;
	public static final int BASELINECOLUMNS = 1160;
	public static final int BASELINEFORM = 1136;
	public static final int CHANGECONTROLFORM = 1434;
	public static final int CHANGECONTROLMENU = 1436;
	public static final int CHANGECONTROLTOOLBAR = 1435;
	public static final int CHANGECONTROLVIEW = 1432;
	public static final int CHANGEIMPACTFORM = 1186;
	public static final int CHANGEPASSWORDFORM = 420;
	public static final int COLUMNSCHANGECONTROLVIEW = 1462;
	public static final int FILTERARTIFACTLIST = 906;
	public static final int FORMHELPABOUT = 1098;
	public static final int HTMLARTIFACTFORM = 1357;
	public static final int HTMLISSUEFORM = 1246;
	public static final int LOGIN = 7;
	public static final int OPENPRODUCT = 204;
	public static final int OSRMTMAINMENU = 167;
	public static final int OSRMTTOOLBAR = 201;
	public static final int POSITIONSECURITYFORM = 1014;
	public static final int RECORDPARAMETER = 282;
	public static final int REFERENCEFORM = 348;
	public static final int REFERENCESEARCHRESULTS = 368;
	public static final int REFGROUPSEARCHRESULTS = 328;
	public static final int REPORTFORM = 5;
	public static final int REPORTLISTCOLUMNS = 280;
	public static final int REPORTPARAMETERFORM = 5558;
	public static final int REQUIREMENTMANAGER = 37;
	public static final int SYSTEMOPTIONFORM = 1586;
	public static final int TRACEABILITYMENU = 1438;
	public static final int TRACEABILITYTOOLBAR = 1437;
	public static final int TRACEABILITYVIEW = 1433;
	public static final int TRACEFORM = 245;
	public static final int TRACETREECRITERIA = 1521;
	public static final int USERADMINMENU = 417;
	public static final int USERFORM = 304;
	public static final int USERGROUPFORM = 1563;
	public static final int USERGROUPPRODUCT = 5335;
	public static final int USERGROUPPROJECTSEARCHRESULTS = 5340;
	public static final int USERGROUPSEARCHRESULTS = 1569;
	public static final int USERSEARCHRESULTS = 305;

	private int ApplicationRefId = 0;
	public ApplicationGroup(int ApplicationRefId) {
		this.ApplicationRefId = ApplicationRefId;		
	}

	public int getApplicationRefId() {
		return ApplicationRefId;
	}

	public static ApplicationGroup get(int ApplicationRefId) {
		return new ApplicationGroup(ApplicationRefId);
	}

}
