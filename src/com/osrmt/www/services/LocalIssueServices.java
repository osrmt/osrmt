package com.osrmt.www.services;
import java.rmi.RemoteException;
import java.util.*;

import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.UpdateResult;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.tree.*;
import com.osrmt.www.services.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.services.*;
import com.osrmt.modellibrary.reference.group.IssueTypeGroup;
import com.osrmt.modellibrary.reference.group.RelationGroup;
import com.osrmt.modellibrary.issue.*;

import javax.servlet.http.*;
import org.apache.struts.*;
import org.apache.struts.action.*;

public class LocalIssueServices extends BaseService {

	public LocalIssueServices() {
		super();
	}

	public static IssueModel getIssue(Integer issueId, ServiceCall serviceCall) {
		authenticateContainer();
		IssueModel issue = IssueServices.getIssue(issueId.intValue(), serviceCall);
		return issue;
	}
	
	public static IssueList getIssuesByArtifact(int artifactId, IssueTypeGroup issueType, ServiceCall call) { 
		authenticateContainer();
		IssueList issues = IssueServices.getIssuesByArtifact(artifactId, issueType, call);
		return issues;
	}
	
	public static UpdateResult updateIssue(IssueModel issue, ServiceCall serviceCall) {
		authenticateContainer();
		return IssueServices.UpdateIssue(issue, IssueTypeGroup.get(IssueTypeGroup.FEEDBACK), serviceCall);
	}
	
	
	private static void setApplicationObject(int productRefId, ServiceCall call) {
		ApplicationObject ao = new ApplicationObject();
		ao.setProductRefId(productRefId, LocalReferenceServices.getDisplay(productRefId));
		call.setApplication(ao);
	}
	
	public static IssueList getIssuesBySubmitUser(int userId, IssueTypeGroup issueType, ServiceCall call) { 
		authenticateContainer();
		IssueList issues = IssueServices.getIssuesBySubmitUser(userId, issueType, call);
		return issues;
	}
	

}

