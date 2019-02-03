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
package com.osrmt.datalibrary.reqmanager;

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
import com.osrmt.modellibrary.reqmanager.*;

/**
null
*/
public class RequirementTreeHistoryDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public RequirementTreeHistoryDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateRequirementTreeHistory(RequirementTreeHistoryModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateRequirementTreeHistory(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateRequirementTreeHistory(RequirementTreeHistoryModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		RequirementTreeHistoryModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getRequirementTreeHistory(m.getRequirementTreeHistoryId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setRequirementTreeHistoryId(original.getRequirementTreeHistoryId());
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
	
	private UpdateResult save(RequirementTreeHistoryModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getRequirementTreeHistoryId()==0) {
				m.setRequirementTreeHistoryId(Db.getNextSequence(TableNameFramework.REQUIREMENTTREEHISTORY, conn));
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
			return new UpdateResult(nbrRows,m.getRequirementTreeHistoryId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportRequirementTreeHistory(RequirementTreeHistoryModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteRequirementTreeHistory(RequirementTreeHistoryModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapRequirementTreeHistory(ResultSet rset, RequirementTreeHistoryModel m) throws SQLException {
		if (columnExists(rset, "requirement_tree_history_id")) {
			m.setRequirementTreeHistoryId(rset.getInt("requirement_tree_history_id"));
			if (rset.wasNull()) m.setRequirementTreeHistoryId(0);
		}
		if (columnExists(rset, "requirement_tree_id")) {
			m.setRequirementTreeId(rset.getInt("requirement_tree_id"));
			if (rset.wasNull()) m.setRequirementTreeId(0);
		}
		if (columnExists(rset, "history_dt")) {
			m.setHistoryDt(Db.getCalendarDate(rset.getTimestamp("history_dt")));
			if (rset.wasNull()) m.setHistoryDt(null);
		}
		if (columnExists(rset, "history_user_id")) {
			m.setHistoryUserId(rset.getInt("history_user_id"));
			if (rset.wasNull()) m.setHistoryUserId(0);
		}
		if (columnExists(rset, "baseline_id")) {
			m.setBaselineId(rset.getInt("baseline_id"));
			if (rset.wasNull()) m.setBaselineId(0);
		}
		if (columnExists(rset, "child_id")) {
			m.setChildId(rset.getInt("child_id"));
			if (rset.wasNull()) m.setChildId(0);
		}
		if (columnExists(rset, "child_artifact_ref_id")) {
			m.setChildArtifactRefId(rset.getInt("child_artifact_ref_id"));
			if (rset.wasNull()) m.setChildArtifactRefId(0);
		}
		if (columnExists(rset, "relation_ref_id")) {
			m.setRelationRefId(rset.getInt("relation_ref_id"));
			if (rset.wasNull()) m.setRelationRefId(0);
		}
		if (columnExists(rset, "parent_id")) {
			m.setParentId(rset.getInt("parent_id"));
			if (rset.wasNull()) m.setParentId(0);
		}
		if (columnExists(rset, "parent_artifact_ref_id")) {
			m.setParentArtifactRefId(rset.getInt("parent_artifact_ref_id"));
			if (rset.wasNull()) m.setParentArtifactRefId(0);
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
	public RequirementTreeHistoryList  getRequirementTreeHistory(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getRequirementTreeHistory(sql, conn);
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
	public RequirementTreeHistoryList  getRequirementTreeHistory(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			RequirementTreeHistoryList list = new RequirementTreeHistoryList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				RequirementTreeHistoryModel m = new RequirementTreeHistoryModel();
				mapRequirementTreeHistory(rset, m);
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
	public RequirementTreeHistoryList  getRequirementTreeHistory(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getRequirementTreeHistory(sql, params, conn);
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
	public RequirementTreeHistoryList  getRequirementTreeHistory(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			RequirementTreeHistoryList list = new RequirementTreeHistoryList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				RequirementTreeHistoryModel m = new RequirementTreeHistoryModel();
				mapRequirementTreeHistory(rset, m);
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
	public RequirementTreeHistoryModel getRequirementTreeHistory(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getRequirementTreeHistory(id, conn);
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
	public RequirementTreeHistoryModel getRequirementTreeHistory(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			RequirementTreeHistoryModel m = new RequirementTreeHistoryModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapRequirementTreeHistory(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "RequirementTreeHistoryModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public RequirementTreeHistoryList exportRequirementTreeHistory() throws DataAccessException {
		int rows = Db.countTable("requirement_tree_history");
		Debug.LogInfo(this, rows + " found in requirement_tree_history");
		String sql = "select * from requirement_tree_history where record_type_ref_id in (?, ?)";
		RequirementTreeHistoryList list = this.getRequirementTreeHistory(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " requirement_tree_history rows exported");
		return list;
	}

	public int importRequirementTreeHistory(RequirementTreeHistoryList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			RequirementTreeHistoryModel m = (RequirementTreeHistoryModel) e1.nextElement();
			rows += ImportRequirementTreeHistory(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select requirement_tree_history_id, requirement_tree_id, history_dt, history_user_id, "
			+ "baseline_id, child_id, child_artifact_ref_id, relation_ref_id, "
			+ "parent_id, parent_artifact_ref_id, create_dt, create_user_id, "
			+ "update_dt, update_user_id, update_count, active_ind, "
			+ "system_assigned_version_nbr, record_type_ref_id"
			+ " from requirement_tree_history "
			+ " where requirement_tree_history_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into requirement_tree_history (requirement_tree_history_id, requirement_tree_id, history_dt, history_user_id, "
			+ "baseline_id, child_id, child_artifact_ref_id, relation_ref_id, "
			+ "parent_id, parent_artifact_ref_id, create_dt, create_user_id, "
			+ "update_dt, update_user_id, update_count, active_ind, "
			+ "system_assigned_version_nbr, record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(RequirementTreeHistoryModel m) {
		Vector v = new Vector(18);
		v.add(new Integer(m.getRequirementTreeHistoryId()));
		v.add(new Integer(m.getRequirementTreeId()));
		if (m.getHistoryDt() != null) v.add(m.getHistoryDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getHistoryUserId()));
		v.add(new Integer(m.getBaselineId()));
		v.add(new Integer(m.getChildId()));
		v.add(new Integer(m.getChildArtifactRefId()));
		v.add(new Integer(m.getRelationRefId()));
		v.add(new Integer(m.getParentId()));
		v.add(new Integer(m.getParentArtifactRefId()));
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
		return "update requirement_tree_history set  requirement_tree_id = ?, history_dt = ?, history_user_id = ?, "
			+ "baseline_id = ?, child_id = ?, child_artifact_ref_id = ?, relation_ref_id = ?, "
			+ "parent_id = ?, parent_artifact_ref_id = ?, create_dt = ?, create_user_id = ?, "
			+ "update_dt = ?, update_user_id = ?, update_count = ?, active_ind = ?, "
			+ "system_assigned_version_nbr = ?, record_type_ref_id = ? "
			+ " where requirement_tree_history_id = ?";				 
	}
	
	private Vector getUpdateParameters(RequirementTreeHistoryModel m) {
		Vector v = new Vector(18);
		v.add(new Integer(m.getRequirementTreeId()));
		if (m.getHistoryDt() != null) v.add(m.getHistoryDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getHistoryUserId()));
		v.add(new Integer(m.getBaselineId()));
		v.add(new Integer(m.getChildId()));
		v.add(new Integer(m.getChildArtifactRefId()));
		v.add(new Integer(m.getRelationRefId()));
		v.add(new Integer(m.getParentId()));
		v.add(new Integer(m.getParentArtifactRefId()));
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
		v.add(new Integer(m.getRequirementTreeHistoryId()));
		return v;
	}

	private Vector getDeleteParameters(RequirementTreeHistoryModel m) {
		Vector v = new Vector(18);
		v.add(new Integer(m.getRequirementTreeHistoryId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from requirement_tree_history where requirement_tree_history_id = ?";				 
	}

}
