/*
    //usage

    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
//************ UNLESS YOU SET OVERWRITE IND = 0 IN TABLE EJBLIBRARY *********//
package com.osrmt.ejb.issue;

import java.rmi.RemoteException;
import java.util.*;
import javax.ejb.*;
import com.osframework.framework.logging.*;
import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.ejb.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.common.ReferenceMapBean;
import com.osframework.ejb.reference.common.ReferenceMapUtil;
import com.osframework.ejb.system.SystemUtil;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.RecordTypeFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.datalibrary.issue.*;
import com.osrmt.datalibrary.reqmanager.RequirementTreeDataAdapter;
import com.osrmt.ejb.reqmanager.RequirementManagerBean;
import com.osrmt.modellibrary.issue.*;
import com.osrmt.modellibrary.reference.group.IssueTypeGroup;
import com.osrmt.modellibrary.reference.group.TableNameGroup;
import com.osrmt.modellibrary.reqmanager.ArtifactHistoryModel;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;



public class IssueBean extends BaseBean implements EntityBean, IIssue {

	private EntityContext context= null;
	static final long serialVersionUID = 1L;
	private IssueDataAdapter da;
   	private IReferenceMap reference;

   	private static IssueBean issueBean2Tier = null;

	public IssueBean() {
		try {
			reference = ReferenceMapBean.get2TierInstance();
			da = new IssueDataAdapter(reference, security);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	public static IssueBean get2TierInstance() throws Exception {
		if (issueBean2Tier == null) {
			issueBean2Tier = new IssueBean();
		}
		return issueBean2Tier;
	}

	public void setEntityContext(EntityContext context) {
		this.context = context;
	}


	public String ejbFind() {
		return "IssueBean";
}

	public void unsetEntityContext() {
		this.context = null;
}

	public void ejbStore() {
	}

	public void ejbLoad() {
	}
	public String ejbCreate() {
		return "IssueBean";
	}
	public void ejbPostCreate() {
	}
	public String ejbFindByPrimaryKey(String key) {
		return "IssueBean";
	}

	public void ejbRemove() {

	} 

	public void ejbActivate() {

	}

	public void ejbPassivate() {

	}

	public UpdateResult UpdateIssue(final IssueModel m, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		BeanTransaction transaction = new BeanTransaction() {
			protected void execute(ServiceCall call, DbConnection conn) throws Exception {
				setUpdateResult(UpdateIssue(m, call, conn));
			}
		};
		transaction.start(call);
		return transaction.getUpdateResult();
	}

	/**  
	 *  Update the issue
	 */ 
	public UpdateResult UpdateIssue(IssueList list, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		UpdateResult result = new UpdateResult();
		if (list.size() == 0) {
			return result;
		}
		int nextIssueNbr = da.getNextIssueNbr(list.getFirst().getIssueTypeRefId(), conn);
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			IssueModel m = (IssueModel) e1.nextElement();
			if (m.getIssueNbr() == 0) {
				m.setIssueNbr(nextIssueNbr++);
			}
			result.setRowsUpdated(result.getRowsUpdated() + UpdateIssue(m, call, conn).getRowsUpdated());
		}
		// inactivated issues may need handling in the future
		return result;
	}


	/**  
	 *  Update the issue
	 */ 
	public UpdateResult UpdateIssue(IssueModel m, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		m.setRecordTypeRefId(RecordTypeFramework.USERRECORD);
		// need better place to set data type
		if (m.getProductRefId() == 0) {
			m.setProductRefId(getApplicationObject(call).getProductRefId());
		}
		if (m.getIssueNbr() == 0) {
			m.setIssueNbr(da.getNextIssueNbr(m.getIssueTypeRefId(), conn));
		}
		UpdateResult result = da.UpdateIssue(m, call, conn);
		// inactivated issues may need handling in the future
		return result;
	}


	/**  
	 *  Get an issue
	 */ 
	public IssueModel getIssue(int id, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			IssueModel issue = da.getIssue(id);
			issue.setReferenceDisplay(reference, security);
			stopService(call);
			return issue;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	/**  
	 *  Get issues for an artifact
	 */ 
	public IssueList getIssuesByArtifact(int artifactId, IssueTypeGroup issueType, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			IssueList issues = da.getIssuesByArtifact(artifactId, issueType);
			stopService(call);
			return issues;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	/**  
	 *  Get all issues for a user
	 */ 
	public IssueList getIssuesBySubmitUser(int userId, IssueTypeGroup issueType, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			IssueList issues = da.getIssuesBySubmitUser(userId, issueType);
			stopService(call);
			return issues;
		} catch (Exception ex) {

			Debug.LogException(this, ex);

			throw ex;

		}
	}

	private ApplicationObject getApplicationObject(ServiceCall call) {
		if (call.getApplication() != null) {
			try {
				ApplicationObject ao = (ApplicationObject) call.getApplication();
				return ao;
			} catch (Exception ex) {
				Debug.LogException(this, ex);
			}
		}
		return new ApplicationObject();
	}

	/**  
	 *  Get the issues with a search criteria
	 */ 
	public IssueList getIssues(IssueCriteria criteria, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			IssueList issues = da.getIssues(criteria);
			stopService(call);
			return issues;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	/**  
	 *  Get all issues for a user
	 */ 
	public IssueList getIssuesByAssignedGroup(int userId, IssueTypeGroup issueType, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			IssueList issues = da.getIssuesByAssignedGroup(userId, issueType);
			stopService(call);
			return issues;
		} catch (Exception ex) {

			Debug.LogException(this, ex);

			throw ex;

		}
	}


}
