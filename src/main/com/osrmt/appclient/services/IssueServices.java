package com.osrmt.appclient.services;

import java.awt.Cursor;
import java.io.File;
import java.rmi.RemoteException;
import java.util.*;


import com.osframework.appclient.ui.tree.*;
import com.osframework.appclient.services.Application;
import com.osframework.appclient.services.BaseService;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.ejb.common.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.ejb.issue.*;
import com.osframework.datalibrary.common.*;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.SerializeUtility;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.ReferenceGroupList;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.security.ApplicationSecurityList;
import com.osframework.modellibrary.reference.security.ApplicationSecurityModel;
import com.osframework.modellibrary.reference.security.ApplicationUserModel;
import com.osframework.modellibrary.reference.security.ApplicationViewModel;
import com.osrmt.modellibrary.issue.*;
import com.osrmt.modellibrary.reference.group.*;

/**
 * ReferenceServices provides helper functionality to return interfaces
 * to the business entity beans.  
 */
public class IssueServices extends BaseService {
	
		
 	
	public static IIssue getIssueRef() throws Exception {
		return IssueUtil.getIssue();
	}
		
	/**  
	 *  Update the issue
	 */ 
	public static UpdateResult UpdateIssue(IssueModel issue, IssueTypeGroup type, ServiceCall call) {
		try { 
			issue.setIssueTypeRefId(type.getIssueTypeRefId());
			return getIssueRef().UpdateIssue(issue, call);
		} catch (Exception e) { 
			Debug.LogException("IssueService", e);
			return new UpdateResult();
		}
	}
 	
	/**  
	 *  Update the issue
	 */ 
	public static UpdateResult UpdateIssue(IssueModel issue, IssueTypeGroup type) {
		return UpdateIssue(issue, type, getServiceCall(0));
	}

	/**  
	 *  Get the issue
	 */ 
	public static IssueModel getIssue(int issueId, ServiceCall call) {
		try { 
			return getIssueRef().getIssue(issueId, call);
		} catch (Exception e) { 
			Debug.LogException("IssueService", e);
			return new IssueModel();
		}
	}
 	
	/**  
	 *  Get the issue
	 */ 
	public static IssueModel getIssue(int issueId) {
		return getIssue(issueId, getServiceCall(0));
	}

	/**  
	 *  Get the issue
	 */ 
	public static IssueList getIssuesByArtifact(int artifactId, IssueTypeGroup issueType, ServiceCall call) { 
		try { 
			return getIssueRef().getIssuesByArtifact(artifactId, issueType, call);
		} catch (Exception e) { 
			Debug.LogException("IssueService", e);
			return new IssueList();
		}
	}
 	
	/**  
	 *  Get the issue
	 */ 
	public static IssueList getIssuesByArtifact(int artifactId, IssueTypeGroup issueType) { 
		return getIssuesByArtifact(artifactId, issueType, getServiceCall());
	}

   
	/**  
	 *  Get the issue
	 */ 
	public static IssueList getIssuesBySubmitUser(int userId, IssueTypeGroup issueType, ServiceCall call) { 
		try { 
			return getIssueRef().getIssuesBySubmitUser(userId, issueType, call);
		} catch (Exception e) { 
			Debug.LogException("IssueService", e);
			return new IssueList();
		}
	}
 	
	/**  
	 *  Get the issue
	 */ 
	public static IssueList getIssuesByAssignedGroup(int userId, IssueTypeGroup issueType) { 
		return getIssuesByAssignedGroup(userId, issueType, getServiceCall());
	}

	/**  
	 *  Get the issue
	 */ 
	public static IssueList getIssuesByAssignedGroup(int userId, IssueTypeGroup issueType, ServiceCall call) { 
		try { 
			return getIssueRef().getIssuesByAssignedGroup(userId, issueType, call);
		} catch (Exception e) { 
			Debug.LogException("IssueService", e);
			return new IssueList();
		}
	}
 	
	/**  
	 *  Get the issue
	 */ 
	public static IssueList getIssuesBySubmitUser(int userId, IssueTypeGroup issueType) { 
		return getIssuesBySubmitUser(userId, issueType, getServiceCall());
	}

	/**  
	 *  Get the issues
	 */ 
	public static IssueList getIssues(IssueCriteria criteria, IssueTypeGroup type) { 
		return getIssues(criteria, type, getServiceCall());
	}

	/**  
	 *  Get the issues
	 */ 
	public static IssueList getIssues(IssueCriteria criteria,IssueTypeGroup type, ServiceCall call) { 
		try {
			criteria.setIssueTypeRefId(type.getIssueTypeRefId());
			return getIssueRef().getIssues(criteria, call);
		} catch (Exception ex) {
			Debug.LogException("IssueService", ex);
			return new IssueList();
		}
	}


}
