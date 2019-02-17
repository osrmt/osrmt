//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ApplicationSettingFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ARTIFACTLISTDISPLAYALLDESCENDENTS = 1597;
	public static final int ARTIFACTLISTIMMEDIATECHILDRENFORSELECTED = 1689;
	public static final int DISPLAY_ARTIFACT_LONG_NAME = 1584;
	public static final int LDAP_INITIAL_CONTEXT_FACTORY = 1200;
	public static final int LDAP_PROVIDER_URL = 1201;
	public static final int LDAP_SECURITY_AUTHENTICATION = 1202;
	public static final int LDAP_SECURITY_PROTOCOL = 1203;
	public static final int LONGDATETIMEFORMAT = 1134;
	public static final int OSRMT_AUTHENTICATION_METHOD = 1204;
	public static final int PRODUCT_USER_GROUP_SECURITY = 5337;
	public static final int REPORT_OUTLINE_SCRIPT = 5353;
	public static final int RESTRICT_PRODUCT_ACCESS = 5348;
	public static final int SHORTDATEFORMAT = 1135;
	public static final int STORAGEBASELINE = 5552;
	public static final int STORAGEDIRECTORY = 1188;
	public static final int USERNAME_FIRST_LAST = 5331;

	private int ApplicationSettingRefId = 0;
	public ApplicationSettingFramework(int ApplicationSettingRefId) {
		this.ApplicationSettingRefId = ApplicationSettingRefId;		
	}

	public int getApplicationSettingRefId() {
		return ApplicationSettingRefId;
	}

	public static ApplicationSettingFramework get(int ApplicationSettingRefId) {
		return new ApplicationSettingFramework(ApplicationSettingRefId);
	}

}
