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
package com.osrmt.datalibrary.issue;

import java.sql.*;
import java.util.*;
import com.osframework.framework.exceptions.*;
import com.osframework.framework.logging.*;
import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.framework.*;
import com.osframework.ejb.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.ejb.reference.security.*;
import com.osrmt.modellibrary.issue.*;

/**
null
*/
public class IssueDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public IssueDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateIssue(IssueModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateIssue(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateIssue(IssueModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		IssueModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getIssue(m.getIssueId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setIssueId(original.getIssueId());
				return result;
			} else {
				return new UpdateResult();
			}
		} catch (Exception ex) {
			if (original != null) {
				Debug.LogException(this, ex, original.toString());
			} else {
				Debug.LogException(this, ex);
			}
			throw new DataUpdateException(ex); 
		}
	}
	
	private UpdateResult save(IssueModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getIssueId()==0) {
				m.setIssueId(Db.getNextSequence(TableNameFramework.ISSUE, conn));
				sql = getInsertSql();
				params = getInsertParameters(m);
				result = Db.getAccess().executeUpdate(getInsertSql(), getInsertParameters(m), conn);
				nbrRows = result.getRowsUpdated();
			} else {
				sql = getUpdateSql();
				params = getInsertParameters(m);
				result = Db.getAccess().executeUpdate(getUpdateSql(), getUpdateParameters(m), conn);
				nbrRows = result.getRowsUpdated();
			}
			return new UpdateResult(nbrRows,m.getIssueId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportIssue(IssueModel m) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			sql = getInsertSql();
			params = getInsertParameters(m);
			result = Db.getAccess().executeUpdate(getInsertSql(), getInsertParameters(m));
			nbrRows = result.getRowsUpdated();
			return nbrRows;
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	public int DeleteIssue(IssueModel m, DbConnection conn) throws DataUpdateException {
		SQLResult result = null;
		try {
			int nbrRows = 0;
			result = Db.getAccess().executeUpdate(getDeleteSql(), getDeleteParameters(m), conn);
			nbrRows = result.getRowsUpdated();
			return nbrRows;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw new DataUpdateException(ex);
		}
	}
	/**
	 * Maps the resultset to the model.
	 */
	public void mapIssue(ResultSet rset, IssueModel m) throws SQLException {
		if (columnExists(rset, "issue_id")) {
			m.setIssueId(rset.getInt("issue_id"));
			if (rset.wasNull()) m.setIssueId(0);
		}
		if (columnExists(rset, "issue_type_ref_id")) {
			m.setIssueTypeRefId(rset.getInt("issue_type_ref_id"));
			if (rset.wasNull()) m.setIssueTypeRefId(0);
		}
		if (columnExists(rset, "issue_nbr")) {
			m.setIssueNbr(rset.getInt("issue_nbr"));
			if (rset.wasNull()) m.setIssueNbr(0);
		}
		if (columnExists(rset, "product_ref_id")) {
			m.setProductRefId(rset.getInt("product_ref_id"));
			if (rset.wasNull()) m.setProductRefId(0);
		}
		if (columnExists(rset, "issue_name")) {
			m.setIssueName(rset.getString("issue_name"));
			if (rset.wasNull()) m.setIssueName(null);
		}
		if (columnExists(rset, "issue_occur_dt")) {
			m.setIssueOccurDt(Db.getCalendarDate(rset.getTimestamp("issue_occur_dt")));
			if (rset.wasNull()) m.setIssueOccurDt(null);
		}
		if (columnExists(rset, "issue_open_dt")) {
			m.setIssueOpenDt(Db.getCalendarDate(rset.getTimestamp("issue_open_dt")));
			if (rset.wasNull()) m.setIssueOpenDt(null);
		}
		if (columnExists(rset, "version_ref_id")) {
			m.setVersionRefId(rset.getInt("version_ref_id"));
			if (rset.wasNull()) m.setVersionRefId(0);
		}
		if (columnExists(rset, "artifact_id")) {
			m.setArtifactId(rset.getInt("artifact_id"));
			if (rset.wasNull()) m.setArtifactId(0);
		}
		if (columnExists(rset, "test_log_id")) {
			m.setTestLogId(rset.getInt("test_log_id"));
			if (rset.wasNull()) m.setTestLogId(0);
		}
		if (columnExists(rset, "status_ref_id")) {
			m.setStatusRefId(rset.getInt("status_ref_id"));
			if (rset.wasNull()) m.setStatusRefId(0);
		}
		if (columnExists(rset, "assigned_user_group_ref_id")) {
			m.setAssignedUserGroupRefId(rset.getInt("assigned_user_group_ref_id"));
			if (rset.wasNull()) m.setAssignedUserGroupRefId(0);
		}
		if (columnExists(rset, "assigned_user_group_id")) {
			m.setAssignedUserGroupId(rset.getInt("assigned_user_group_id"));
			if (rset.wasNull()) m.setAssignedUserGroupId(0);
		}
		if (columnExists(rset, "assigned_user_id")) {
			m.setAssignedUserId(rset.getInt("assigned_user_id"));
			if (rset.wasNull()) m.setAssignedUserId(0);
		}
		if (columnExists(rset, "description")) {
			m.setDescription(rset.getString("description"));
			if (rset.wasNull()) m.setDescription(null);
		}
		if (columnExists(rset, "priority_ref_id")) {
			m.setPriorityRefId(rset.getInt("priority_ref_id"));
			if (rset.wasNull()) m.setPriorityRefId(0);
		}
		if (columnExists(rset, "severity_ref_id")) {
			m.setSeverityRefId(rset.getInt("severity_ref_id"));
			if (rset.wasNull()) m.setSeverityRefId(0);
		}
		if (columnExists(rset, "fix_by_dt")) {
			m.setFixByDt(Db.getCalendarDate(rset.getTimestamp("fix_by_dt")));
			if (rset.wasNull()) m.setFixByDt(null);
		}
		if (columnExists(rset, "frequency_ref_id")) {
			m.setFrequencyRefId(rset.getInt("frequency_ref_id"));
			if (rset.wasNull()) m.setFrequencyRefId(0);
		}
		if (columnExists(rset, "submitted_user_id")) {
			m.setSubmittedUserId(rset.getInt("submitted_user_id"));
			if (rset.wasNull()) m.setSubmittedUserId(0);
		}
		if (columnExists(rset, "organization_id")) {
			m.setOrganizationId(rset.getInt("organization_id"));
			if (rset.wasNull()) m.setOrganizationId(0);
		}
		if (columnExists(rset, "organization_environment_id")) {
			m.setOrganizationEnvironmentId(rset.getInt("organization_environment_id"));
			if (rset.wasNull()) m.setOrganizationEnvironmentId(0);
		}
		if (columnExists(rset, "reproduce_ref_id")) {
			m.setReproduceRefId(rset.getInt("reproduce_ref_id"));
			if (rset.wasNull()) m.setReproduceRefId(0);
		}
		if (columnExists(rset, "parent_issue_id")) {
			m.setParentIssueId(rset.getInt("parent_issue_id"));
			if (rset.wasNull()) m.setParentIssueId(0);
		}
		if (columnExists(rset, "effort")) {
			m.setEffort(rset.getDouble("effort"));
			if (rset.wasNull()) m.setEffort(0);
		}
		if (columnExists(rset, "reproduce_directions")) {
			m.setReproduceDirections(rset.getString("reproduce_directions"));
			if (rset.wasNull()) m.setReproduceDirections(null);
		}
		if (columnExists(rset, "last_update")) {
			m.setLastUpdate(rset.getString("last_update"));
			if (rset.wasNull()) m.setLastUpdate(null);
		}
		if (columnExists(rset, "history")) {
			m.setHistory(rset.getString("history"));
			if (rset.wasNull()) m.setHistory(null);
		}
		if (columnExists(rset, "closed_dt")) {
			m.setClosedDt(Db.getCalendarDate(rset.getTimestamp("closed_dt")));
			if (rset.wasNull()) m.setClosedDt(null);
		}
		if (columnExists(rset, "closed_category_ref_id")) {
			m.setClosedCategoryRefId(rset.getInt("closed_category_ref_id"));
			if (rset.wasNull()) m.setClosedCategoryRefId(0);
		}
		if (columnExists(rset, "closed_user_id")) {
			m.setClosedUserId(rset.getInt("closed_user_id"));
			if (rset.wasNull()) m.setClosedUserId(0);
		}
		if (columnExists(rset, "resolved_dt")) {
			m.setResolvedDt(Db.getCalendarDate(rset.getTimestamp("resolved_dt")));
			if (rset.wasNull()) m.setResolvedDt(null);
		}
		if (columnExists(rset, "resolved_user_id")) {
			m.setResolvedUserId(rset.getInt("resolved_user_id"));
			if (rset.wasNull()) m.setResolvedUserId(0);
		}
		if (columnExists(rset, "resolved_category_ref_id")) {
			m.setResolvedCategoryRefId(rset.getInt("resolved_category_ref_id"));
			if (rset.wasNull()) m.setResolvedCategoryRefId(0);
		}
		if (columnExists(rset, "external_alias1")) {
			m.setExternalAlias1(rset.getString("external_alias1"));
			if (rset.wasNull()) m.setExternalAlias1(null);
		}
		if (columnExists(rset, "external_alias2")) {
			m.setExternalAlias2(rset.getString("external_alias2"));
			if (rset.wasNull()) m.setExternalAlias2(null);
		}
		if (columnExists(rset, "external_alias3")) {
			m.setExternalAlias3(rset.getString("external_alias3"));
			if (rset.wasNull()) m.setExternalAlias3(null);
		}
		if (columnExists(rset, "client_os_ref_id")) {
			m.setClientOsRefId(rset.getInt("client_os_ref_id"));
			if (rset.wasNull()) m.setClientOsRefId(0);
		}
		if (columnExists(rset, "server_os_ref_id")) {
			m.setServerOsRefId(rset.getInt("server_os_ref_id"));
			if (rset.wasNull()) m.setServerOsRefId(0);
		}
		if (columnExists(rset, "create_dt")) {
			m.setCreateDt(Db.getCalendarDate(rset.getTimestamp("create_dt")));
			if (rset.wasNull()) m.setCreateDt(null);
		}
		if (columnExists(rset, "create_user_id")) {
			m.setCreateUserId(rset.getInt("create_user_id"));
			if (rset.wasNull()) m.setCreateUserId(0);
		}
		if (columnExists(rset, "update_dt")) {
			m.setUpdateDt(Db.getCalendarDate(rset.getTimestamp("update_dt")));
			if (rset.wasNull()) m.setUpdateDt(null);
		}
		if (columnExists(rset, "update_user_id")) {
			m.setUpdateUserId(rset.getInt("update_user_id"));
			if (rset.wasNull()) m.setUpdateUserId(0);
		}
		if (columnExists(rset, "update_count")) {
			m.setUpdateCount(rset.getInt("update_count"));
			if (rset.wasNull()) m.setUpdateCount(0);
		}
		if (columnExists(rset, "active_ind")) {
			m.setActiveInd(rset.getInt("active_ind"));
			if (rset.wasNull()) m.setActiveInd(0);
		}
		if (columnExists(rset, "system_assigned_version_nbr")) {
			m.setSystemAssignedVersionNbr(rset.getInt("system_assigned_version_nbr"));
			if (rset.wasNull()) m.setSystemAssignedVersionNbr(0);
		}
		if (columnExists(rset, "record_type_ref_id")) {
			m.setRecordTypeRefId(rset.getInt("record_type_ref_id"));
			if (rset.wasNull()) m.setRecordTypeRefId(0);
		}
		m.resetModified();
	}

 /**
  * Execute the SQL and return a list for the result set
  */
	public IssueList  getIssue(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getIssue(sql, conn);
		} catch (Exception ex) {
			throw new DataAccessException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	} /**
  * Execute the SQL and return a list for the result set
  */
	public IssueList  getIssue(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			IssueList list = new IssueList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				IssueModel m = new IssueModel();
				mapIssue(rset, m);
				list.add(m);
			}
			list.setReferenceDisplay(reference, security);
			return list;
		} catch (SQLException se) {
			Debug.LogError(this, Db.getFormattedException(se, sql));
			throw new DataAccessException(se);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

 /**
  * Execute the SQL and return a list for the result set
  */
	public IssueList  getIssue(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getIssue(sql, params, conn);
		} catch (Exception ex) {
			throw new DataAccessException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

 /**
  * Execute the SQL and return a list for the result set
  */
	public IssueList  getIssue(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			IssueList list = new IssueList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				IssueModel m = new IssueModel();
				mapIssue(rset, m);
				list.add(m);
			}
			list.setReferenceDisplay(reference, security);
			return list;
		} catch (SQLException se) {
			Debug.LogError(this, Db.getFormattedException(se, sql, params));
			throw new DataAccessException(se);
		} catch (AccessDataTypeException ae) {
			throw new DataAccessException(ae);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	/**
	 * Returns the model from the database.
	 * Returns an empty model if the id is 0 or not found
	 */
	public IssueModel getIssue(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getIssue(id, conn);
		} catch (Exception ex) {
			throw new DataAccessException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 * Returns the model from the database.
	 * Returns an empty model if the id is 0 or not found
	 */
	public IssueModel getIssue(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			IssueModel m = new IssueModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapIssue(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "IssueModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public IssueList exportIssue() throws DataAccessException {
		int rows = Db.countTable("issue");
		Debug.LogInfo(this, rows + " found in issue");
		String sql = "select * from issue where record_type_ref_id in (?, ?)";
		IssueList list = this.getIssue(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " issue rows exported");
		return list;
	}

	public int importIssue(IssueList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			IssueModel m = (IssueModel) e1.nextElement();
			rows += ImportIssue(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select issue_id, issue_type_ref_id, issue_nbr, product_ref_id, "
			+ "issue_name, issue_occur_dt, issue_open_dt, version_ref_id, "
			+ "artifact_id, test_log_id, status_ref_id, assigned_user_group_ref_id, "
			+ "assigned_user_group_id, assigned_user_id, description, priority_ref_id, "
			+ "severity_ref_id, fix_by_dt, frequency_ref_id, submitted_user_id, "
			+ "organization_id, organization_environment_id, reproduce_ref_id, parent_issue_id, "
			+ "effort, reproduce_directions, last_update, history, "
			+ "closed_dt, closed_category_ref_id, closed_user_id, resolved_dt, "
			+ "resolved_user_id, resolved_category_ref_id, external_alias1, external_alias2, "
			+ "external_alias3, client_os_ref_id, server_os_ref_id, create_dt, "
			+ "create_user_id, update_dt, update_user_id, update_count, "
			+ "active_ind, system_assigned_version_nbr, record_type_ref_id"
			+ " from issue "
			+ " where issue_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into issue (issue_id, issue_type_ref_id, issue_nbr, product_ref_id, "
			+ "issue_name, issue_occur_dt, issue_open_dt, version_ref_id, "
			+ "artifact_id, test_log_id, status_ref_id, assigned_user_group_ref_id, "
			+ "assigned_user_group_id, assigned_user_id, description, priority_ref_id, "
			+ "severity_ref_id, fix_by_dt, frequency_ref_id, submitted_user_id, "
			+ "organization_id, organization_environment_id, reproduce_ref_id, parent_issue_id, "
			+ "effort, reproduce_directions, last_update, history, "
			+ "closed_dt, closed_category_ref_id, closed_user_id, resolved_dt, "
			+ "resolved_user_id, resolved_category_ref_id, external_alias1, external_alias2, "
			+ "external_alias3, client_os_ref_id, server_os_ref_id, create_dt, "
			+ "create_user_id, update_dt, update_user_id, update_count, "
			+ "active_ind, system_assigned_version_nbr, record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(IssueModel m) {
		Vector v = new Vector(47);
		v.add(new Integer(m.getIssueId()));
		v.add(new Integer(m.getIssueTypeRefId()));
		v.add(new Integer(m.getIssueNbr()));
		v.add(new Integer(m.getProductRefId()));
		if (m.getIssueName() != null) v.add(m.getIssueName());
		else v.add(new Null(new String()));
		if (m.getIssueOccurDt() != null) v.add(m.getIssueOccurDt());
		else v.add(new Null(new GregorianCalendar()));
		if (m.getIssueOpenDt() != null) v.add(m.getIssueOpenDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getVersionRefId()));
		v.add(new Integer(m.getArtifactId()));
		v.add(new Integer(m.getTestLogId()));
		v.add(new Integer(m.getStatusRefId()));
		v.add(new Integer(m.getAssignedUserGroupRefId()));
		v.add(new Integer(m.getAssignedUserGroupId()));
		v.add(new Integer(m.getAssignedUserId()));
		if (m.getDescription() != null) v.add(m.getDescription());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getPriorityRefId()));
		v.add(new Integer(m.getSeverityRefId()));
		if (m.getFixByDt() != null) v.add(m.getFixByDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getFrequencyRefId()));
		v.add(new Integer(m.getSubmittedUserId()));
		v.add(new Integer(m.getOrganizationId()));
		v.add(new Integer(m.getOrganizationEnvironmentId()));
		v.add(new Integer(m.getReproduceRefId()));
		v.add(new Integer(m.getParentIssueId()));
		v.add(new Double(m.getEffort()));
		if (m.getReproduceDirections() != null) v.add(m.getReproduceDirections());
		else v.add(new Null(new String()));
		if (m.getLastUpdate() != null) v.add(m.getLastUpdate());
		else v.add(new Null(new String()));
		if (m.getHistory() != null) v.add(m.getHistory());
		else v.add(new Null(new String()));
		if (m.getClosedDt() != null) v.add(m.getClosedDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getClosedCategoryRefId()));
		v.add(new Integer(m.getClosedUserId()));
		if (m.getResolvedDt() != null) v.add(m.getResolvedDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getResolvedUserId()));
		v.add(new Integer(m.getResolvedCategoryRefId()));
		if (m.getExternalAlias1() != null) v.add(m.getExternalAlias1());
		else v.add(new Null(new String()));
		if (m.getExternalAlias2() != null) v.add(m.getExternalAlias2());
		else v.add(new Null(new String()));
		if (m.getExternalAlias3() != null) v.add(m.getExternalAlias3());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getClientOsRefId()));
		v.add(new Integer(m.getServerOsRefId()));
		if (m.getCreateDt() != null) v.add(m.getCreateDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getCreateUserId()));
		if (m.getUpdateDt() != null) v.add(m.getUpdateDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getUpdateUserId()));
		v.add(new Integer(m.getUpdateCount()));
		v.add(new Integer(m.getActiveInd()));
		v.add(new Integer(m.getSystemAssignedVersionNbr()));
		v.add(new Integer(m.getRecordTypeRefId()));
		return v;
	}
	
	private String getUpdateSql() {
		return "update issue set  issue_type_ref_id = ?, issue_nbr = ?, product_ref_id = ?, "
			+ "issue_name = ?, issue_occur_dt = ?, issue_open_dt = ?, version_ref_id = ?, "
			+ "artifact_id = ?, test_log_id = ?, status_ref_id = ?, assigned_user_group_ref_id = ?, "
			+ "assigned_user_group_id = ?, assigned_user_id = ?, description = ?, priority_ref_id = ?, "
			+ "severity_ref_id = ?, fix_by_dt = ?, frequency_ref_id = ?, submitted_user_id = ?, "
			+ "organization_id = ?, organization_environment_id = ?, reproduce_ref_id = ?, parent_issue_id = ?, "
			+ "effort = ?, reproduce_directions = ?, last_update = ?, history = ?, "
			+ "closed_dt = ?, closed_category_ref_id = ?, closed_user_id = ?, resolved_dt = ?, "
			+ "resolved_user_id = ?, resolved_category_ref_id = ?, external_alias1 = ?, external_alias2 = ?, "
			+ "external_alias3 = ?, client_os_ref_id = ?, server_os_ref_id = ?, create_dt = ?, "
			+ "create_user_id = ?, update_dt = ?, update_user_id = ?, update_count = ?, "
			+ "active_ind = ?, system_assigned_version_nbr = ?, record_type_ref_id = ? "
			+ " where issue_id = ?";				 
	}
	
	private Vector getUpdateParameters(IssueModel m) {
		Vector v = new Vector(47);
		v.add(new Integer(m.getIssueTypeRefId()));
		v.add(new Integer(m.getIssueNbr()));
		v.add(new Integer(m.getProductRefId()));
		if (m.getIssueName() != null) v.add(m.getIssueName());
		else v.add(new Null(new String()));
		if (m.getIssueOccurDt() != null) v.add(m.getIssueOccurDt());
		else v.add(new Null(new GregorianCalendar()));
		if (m.getIssueOpenDt() != null) v.add(m.getIssueOpenDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getVersionRefId()));
		v.add(new Integer(m.getArtifactId()));
		v.add(new Integer(m.getTestLogId()));
		v.add(new Integer(m.getStatusRefId()));
		v.add(new Integer(m.getAssignedUserGroupRefId()));
		v.add(new Integer(m.getAssignedUserGroupId()));
		v.add(new Integer(m.getAssignedUserId()));
		if (m.getDescription() != null) v.add(m.getDescription());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getPriorityRefId()));
		v.add(new Integer(m.getSeverityRefId()));
		if (m.getFixByDt() != null) v.add(m.getFixByDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getFrequencyRefId()));
		v.add(new Integer(m.getSubmittedUserId()));
		v.add(new Integer(m.getOrganizationId()));
		v.add(new Integer(m.getOrganizationEnvironmentId()));
		v.add(new Integer(m.getReproduceRefId()));
		v.add(new Integer(m.getParentIssueId()));
		v.add(new Double(m.getEffort()));
		if (m.getReproduceDirections() != null) v.add(m.getReproduceDirections());
		else v.add(new Null(new String()));
		if (m.getLastUpdate() != null) v.add(m.getLastUpdate());
		else v.add(new Null(new String()));
		if (m.getHistory() != null) v.add(m.getHistory());
		else v.add(new Null(new String()));
		if (m.getClosedDt() != null) v.add(m.getClosedDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getClosedCategoryRefId()));
		v.add(new Integer(m.getClosedUserId()));
		if (m.getResolvedDt() != null) v.add(m.getResolvedDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getResolvedUserId()));
		v.add(new Integer(m.getResolvedCategoryRefId()));
		if (m.getExternalAlias1() != null) v.add(m.getExternalAlias1());
		else v.add(new Null(new String()));
		if (m.getExternalAlias2() != null) v.add(m.getExternalAlias2());
		else v.add(new Null(new String()));
		if (m.getExternalAlias3() != null) v.add(m.getExternalAlias3());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getClientOsRefId()));
		v.add(new Integer(m.getServerOsRefId()));
		if (m.getCreateDt() != null) v.add(m.getCreateDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getCreateUserId()));
		if (m.getUpdateDt() != null) v.add(m.getUpdateDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getUpdateUserId()));
		v.add(new Integer(m.getUpdateCount()));
		v.add(new Integer(m.getActiveInd()));
		v.add(new Integer(m.getSystemAssignedVersionNbr()));
		v.add(new Integer(m.getRecordTypeRefId()));
		v.add(new Integer(m.getIssueId()));
		return v;
	}

	private Vector getDeleteParameters(IssueModel m) {
		Vector v = new Vector(47);
		v.add(new Integer(m.getIssueId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from issue where issue_id = ?";				 
	}

}
