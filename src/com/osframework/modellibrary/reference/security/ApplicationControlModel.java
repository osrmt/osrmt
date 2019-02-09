//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.security;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.components.*;

/**
null
*/
public class ApplicationControlModel extends ApplicationControlDataModel 
									implements Comparable, IControlDef, IHtmlApplicationControl {

	static final long serialVersionUID = 1L;
	public ApplicationControlModel() {

	}
	
	
	/**
	 * Default sort for ReferenceModels is by the sequence.
	 * @param o
	 * @return
	 */
	public int compareTo(Object o) {
		ApplicationControlModel rm = (ApplicationControlModel) o;
		if (rm == null) {
			return 0;
		} else if (this.getDisplaySequence() == rm.getDisplaySequence()) {
			return 0;
		} else if (this.getDisplaySequence() < rm.getDisplaySequence()) {
			return -1;
		} else {
			return 1;
		}
	}


	public double getGrowthHeight() {
		return super.getGrowHeight();
	}


	public double getGrowthWidth() {
		return super.getGrowWidth();
	}


	public int getHeight() {
		return super.getUnitHeight();
	}


	public int getWidth() {
		return super.getUnitWidth();
	}
	
	public String getLabel() {
		return super.getControlText();
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
	
	private ApplicationCustomControlModel custom = new ApplicationCustomControlModel();
	
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


	public ApplicationCustomControlModel getCustom() {
		return custom;
	}


	public void setCustom(ApplicationCustomControlModel custom) {
		this.custom = custom;
	}


	public String getHtmlScript() {
		return custom.getHtmlScript();
	}

	
}