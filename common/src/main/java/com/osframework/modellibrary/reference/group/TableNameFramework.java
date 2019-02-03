//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class TableNameFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int APPCONTROLTEMPLATE = 5409;
	public static final int APPCONTROLUSERDEFINED = 454;
	public static final int APPLICATIONCONTROL = 453;
	public static final int APPLICATIONCUSTOMCONTROL = 483;
	public static final int APPLICATIONSECURITY = 455;
	public static final int APPLICATIONSETTING = 456;
	public static final int APPLICATIONUSER = 21;
	public static final int APPLICATIONUSERGROUP = 1407;
	public static final int APPLICATIONVIEW = 1133;
	public static final int ARTIFACT = 32;
	public static final int ARTIFACTDOCUMENT = 1630;
	public static final int ARTIFACTHISTORY = 457;
	public static final int BASELINE = 458;
	public static final int ISSUE = 1337;
	public static final int ISSUELOG = 1338;
	public static final int RECORDEXTENSION = 459;
	public static final int RECORDEXTENSIONCOLUMN = 460;
	public static final int RECORDFILE = 461;
	public static final int RECORDFILEHISTORY = 5549;
	public static final int RECORDPARAMETER = 462;
	public static final int RECORDPARAMETERVALUE = 485;
	public static final int REFERENCE = 22;
	public static final int REFERENCEGROUP = 463;
	public static final int REFERENCEHISTORY = 5551;
	public static final int REFERENCETREE = 464;
	public static final int REPORT = 277;
	public static final int REQUIREMENTTREE = 465;
	public static final int REQUIREMENTTREEHISTORY = 5550;

	private int TableNameRefId = 0;
	public TableNameFramework(int TableNameRefId) {
		this.TableNameRefId = TableNameRefId;		
	}

	public int getTableNameRefId() {
		return TableNameRefId;
	}

	public static TableNameFramework get(int TableNameRefId) {
		return new TableNameFramework(TableNameRefId);
	}

}
