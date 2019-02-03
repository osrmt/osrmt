package com.osrmt.apps.swingApp.services;

import com.osframework.appclient.services.BaseService;
import com.osframework.datalibrary.common.UpdateResult;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ServiceCall;
import com.osrmt.modellibrary.issue.IssueCriteria;
import com.osrmt.modellibrary.issue.IssueList;
import com.osrmt.modellibrary.issue.IssueModel;
import com.osrmt.modellibrary.reference.group.IssueTypeGroup;

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
