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

*/package com.osrmt.datalibrary.issue;

import java.sql.*;
import java.util.*;

import com.osrmt.modellibrary.issue.*;
import com.osrmt.modellibrary.reference.group.IssueTypeGroup;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ServiceCall;

/**
null
*/
public class IssueDataAdapter extends IssueDbAdapter{ 

	public IssueDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	

	/**  
	 *  Get issues for an artifact
	 */ 
	public IssueList getIssuesByArtifact(int artifactId, IssueTypeGroup issueType) throws DataAccessException, Exception {
		try {
			String sql = "select i.* from issue i where i.artifact_id = ? and i.issue_type_ref_id = ? and i.active_ind = 1";
			return getIssue(sql, Db.getParameter(artifactId, issueType.getIssueTypeRefId()));
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	/**  
	 *  Get issues for an assigned user
	 */ 
	public IssueList getIssuesBySubmitUser(int userId, IssueTypeGroup issueType) throws DataAccessException, Exception {
		try {
			String sql = "select i.* from issue i where i.submitted_user_id = ? and i.issue_type_ref_id = ? and i.active_ind = 1";
			return getIssue(sql, Db.getParameter(userId, issueType.getIssueTypeRefId()));
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	/**  
	 *  Get issues for the submitting user
	 */ 
	public IssueList getIssuesByAssignedGroup(int userId, IssueTypeGroup issueType) throws DataAccessException, Exception {
		try {
			String sql = "select i.* from issue i, application_user_group grp where grp.user_id = ? " +
				" and grp.user_group_ref_id = i.assigned_user_group_ref_id" +
				"  and i.issue_type_ref_id = ? and i.active_ind = 1";
			return getIssue(sql, Db.getParameter(userId, issueType.getIssueTypeRefId()));
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	/**  
	 *  Get issues for an artifact
	 */ 
	public IssueList getIssues(IssueCriteria criteria) throws DataAccessException, Exception {
		try {
			String sql = "select * from issue ";
			sql += criteria.getSqlWhereClause(true);
			return getIssue(sql, criteria.getParameters());
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	public int getNextIssueNbr(int issueTypeRefId, DbConnection conn) throws DataAccessException {
		String sql = "select max(issue_nbr) from issue "
			+ " where issue_type_ref_id = ?";
		Object max = Db.getSingleValue(sql, Db.getParameter(issueTypeRefId), conn);
		if (max == null) {
			return 1;
		} else {
			return ((Integer)max).intValue()+1;
		}
	}}