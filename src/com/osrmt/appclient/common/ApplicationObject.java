package com.osrmt.appclient.common;

import java.io.Serializable;
import java.util.Enumeration;

import com.osframework.appclient.services.Application;
import com.osframework.appclient.services.BaseApplicationObject;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.tree.*;
import com.osrmt.appclient.changecontrol.ChangeControlViewController;
import com.osrmt.appclient.reqmanager.RequirementManagerController;
import com.osrmt.modellibrary.reqmanager.ArtifactList;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;
import com.osrmt.modellibrary.issue.*;
import com.osrmt.appclient.traceability.TraceabilityViewController;

public class ApplicationObject extends BaseApplicationObject {
	
	private static final long serialVersionUID = 1L;

	private int productRefId;
	private String productRefDisplay;
	private static RequirementManagerController requirementManagerController;
	private static ChangeControlViewController changeControlViewController;
	private static TraceabilityViewController traceViewController;

	public int getProductRefId() {
		return productRefId;
	}

	public String getProductRefDisplay() {
		return productRefDisplay;
	}

	public void setProductRefId(int productRefId, String productRefDisplay) {
		this.productRefId = productRefId;
	}
	
	public static int getApplicationProductRefId() {
		if (Application.getObject() != null) {
			ApplicationObject ao = (ApplicationObject) Application.getObject();
			return ao.getProductRefId();			
		} else {
			return 0;
		}
	}
	
	public static UIScrollTree getRequirementTree() {
		return getRequirementManagerController().getReqTree();
	}
	
	public static ArtifactList getArtifactList() {
		if (getRequirementManagerController().getTableList() != null
				&& getRequirementManagerController().getTableList().getResultList() != null) {
			return (ArtifactList) getRequirementManagerController().getTableList().getResultList();
		} else {
			return null;
		}
	}

	public static IssueList getIssueList() {
		if (getChangeControlViewController().getTableList() != null
				&& getChangeControlViewController().getTableList().getResultList() != null) {
			return (IssueList) getChangeControlViewController().getTableList().getResultList();
		} else {
			return null;
		}
	}

	public static RequirementManagerController getRequirementManagerController() {
		return requirementManagerController;
	}

	public static void setRequirementManagerController(
			RequirementManagerController rmc) {
		ApplicationObject.requirementManagerController = rmc;
	}
	
	
		
	public static ChangeControlViewController getChangeControlViewController() {
		return changeControlViewController;
	}

	public static void setChangeControlViewController(
			ChangeControlViewController changeControlViewController) {
		ApplicationObject.changeControlViewController = changeControlViewController;
	}

	public static int maxSearchDescriptionLength() {
		return 80;
	}
	
	public static void trimDescription(ArtifactList artifactList) {
		Enumeration e1 = artifactList.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			String trimmed = GUI.substring(am.getDescription(), ApplicationObject.maxSearchDescriptionLength());
			if (trimmed != null && am.getDescription() != null
					&& trimmed.length() < am.getDescription().length()) {
				trimmed = trimmed + "...";
			}
				
			am.setDescription(trimmed);
		}
	}

	public static TraceabilityViewController getTraceViewController() {
		return traceViewController;
	}

	public static void setTraceViewController(
			TraceabilityViewController traceViewController) {
		ApplicationObject.traceViewController = traceViewController;
	}
	
	
}
