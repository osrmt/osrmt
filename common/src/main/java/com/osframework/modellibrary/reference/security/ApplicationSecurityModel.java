//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.security;

import com.osframework.modellibrary.common.ResultColumnList;


/**
null
*/
public class ApplicationSecurityModel extends ApplicationSecurityDataModel implements Comparable {

	private static final long serialVersionUID = 1L;
	public ApplicationSecurityModel() {

	}
	
	public static ResultColumnList getResultColumnList() {
		ResultColumnList columns = ApplicationSecurityDataModel.getResultColumnList();
		columns.addColumn("applicationRefId", Integer.class);
		columns.addColumn("applicationRefDisplay", String.class);
		columns.addColumn("appTypeRefId", Integer.class);
		columns.addColumn("appTypeRefDisplay", String.class);
		columns.addColumn("viewRefId", Integer.class);
		columns.addColumn("viewRefDisplay", String.class);
		return columns;
	}
	
	public int compareTo(Object arg0) {
		return 0;
	}

	/** Reference application id  */
	private int applicationRefId = 0;

	private String applicationRefDisplay="";

	/** Foreign key of reference to application specific subtype eg. FeatureForm of ArtifactForm  */
	private int appTypeRefId = 0;

	private String appTypeRefDisplay="";

	/** Security id  */
	private int viewRefId = 0;

	private String viewRefDisplay="";
	
	
	
	public String getApplicationRefDisplay() {
		return applicationRefDisplay;
	}


	public void setApplicationRefDisplay(String applicationRefDisplay) {
		this.applicationRefDisplay = applicationRefDisplay;
	}


	public int getApplicationRefId() {
		return applicationRefId;
	}


	public void setApplicationRefId(int applicationRefId) {
		this.applicationRefId = applicationRefId;
	}


	public String getAppTypeRefDisplay() {
		return appTypeRefDisplay;
	}


	public void setAppTypeRefDisplay(String appTypeRefDisplay) {
		this.appTypeRefDisplay = appTypeRefDisplay;
	}


	public int getAppTypeRefId() {
		return appTypeRefId;
	}


	public void setAppTypeRefId(int appTypeRefId) {
		this.appTypeRefId = appTypeRefId;
	}


	public String getViewRefDisplay() {
		return viewRefDisplay;
	}


	public void setViewRefDisplay(String viewRefDisplay) {
		this.viewRefDisplay = viewRefDisplay;
	}


	public int getViewRefId() {
		return viewRefId;
	}


	public void setViewRefId(int viewRefId) {
		this.viewRefId = viewRefId;
	}
	
	//TODO this will break if you add a column to application security table
	public Object getDataAt(int i) {
		switch (i) {
		case 17: return new Integer(getApplicationRefId());
		case 18: return getApplicationRefDisplay();
		case 19: return new Integer(getAppTypeRefId());
		case 20: return getAppTypeRefDisplay();
		case 21: return new Integer(getViewRefId());
		case 22: return getViewRefDisplay();
		}
		return super.getDataAt(i);
	}
	
	
}