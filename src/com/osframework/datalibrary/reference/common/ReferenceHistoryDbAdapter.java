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
package com.osframework.datalibrary.reference.common;

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
import com.osframework.modellibrary.reference.common.*;

/**
null
*/
public class ReferenceHistoryDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public ReferenceHistoryDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateReferenceHistory(ReferenceHistoryModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateReferenceHistory(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateReferenceHistory(ReferenceHistoryModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		ReferenceHistoryModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getReferenceHistory(m.getReferenceHistoryId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setReferenceHistoryId(original.getReferenceHistoryId());
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
	
	private UpdateResult save(ReferenceHistoryModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getReferenceHistoryId()==0) {
				m.setReferenceHistoryId(Db.getNextSequence(TableNameFramework.REFERENCEHISTORY, conn));
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
			return new UpdateResult(nbrRows,m.getReferenceHistoryId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportReferenceHistory(ReferenceHistoryModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteReferenceHistory(ReferenceHistoryModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapReferenceHistory(ResultSet rset, ReferenceHistoryModel m) throws SQLException {
		if (columnExists(rset, "reference_history_id")) {
			m.setReferenceHistoryId(rset.getInt("reference_history_id"));
			if (rset.wasNull()) m.setReferenceHistoryId(0);
		}
		if (columnExists(rset, "ref_id")) {
			m.setRefId(rset.getInt("ref_id"));
			if (rset.wasNull()) m.setRefId(0);
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
		if (columnExists(rset, "reference_group")) {
			m.setReferenceGroup(rset.getString("reference_group"));
			if (rset.wasNull()) m.setReferenceGroup(null);
		}
		if (columnExists(rset, "app_type_ref_id")) {
			m.setAppTypeRefId(rset.getInt("app_type_ref_id"));
			if (rset.wasNull()) m.setAppTypeRefId(0);
		}
		if (columnExists(rset, "display_code")) {
			m.setDisplayCode(rset.getString("display_code"));
			if (rset.wasNull()) m.setDisplayCode(null);
		}
		if (columnExists(rset, "display_sequence")) {
			m.setDisplaySequence(rset.getInt("display_sequence"));
			if (rset.wasNull()) m.setDisplaySequence(0);
		}
		if (columnExists(rset, "display")) {
			m.setDisplay(rset.getString("display"));
			if (rset.wasNull()) m.setDisplay(null);
		}
		if (columnExists(rset, "description")) {
			m.setDescription(rset.getString("description"));
			if (rset.wasNull()) m.setDescription(null);
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
	public ReferenceHistoryList  getReferenceHistory(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getReferenceHistory(sql, conn);
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
	public ReferenceHistoryList  getReferenceHistory(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ReferenceHistoryList list = new ReferenceHistoryList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ReferenceHistoryModel m = new ReferenceHistoryModel();
				mapReferenceHistory(rset, m);
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
	public ReferenceHistoryList  getReferenceHistory(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getReferenceHistory(sql, params, conn);
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
	public ReferenceHistoryList  getReferenceHistory(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ReferenceHistoryList list = new ReferenceHistoryList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ReferenceHistoryModel m = new ReferenceHistoryModel();
				mapReferenceHistory(rset, m);
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
	public ReferenceHistoryModel getReferenceHistory(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getReferenceHistory(id, conn);
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
	public ReferenceHistoryModel getReferenceHistory(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			ReferenceHistoryModel m = new ReferenceHistoryModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapReferenceHistory(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "ReferenceHistoryModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public ReferenceHistoryList exportReferenceHistory() throws DataAccessException {
		int rows = Db.countTable("reference_history");
		Debug.LogInfo(this, rows + " found in reference_history");
		String sql = "select * from reference_history where record_type_ref_id in (?, ?)";
		ReferenceHistoryList list = this.getReferenceHistory(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " reference_history rows exported");
		return list;
	}

	public int importReferenceHistory(ReferenceHistoryList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			ReferenceHistoryModel m = (ReferenceHistoryModel) e1.nextElement();
			rows += ImportReferenceHistory(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select reference_history_id, ref_id, history_dt, history_user_id, "
			+ "baseline_id, reference_group, app_type_ref_id, display_code, "
			+ "display_sequence, display, description, create_dt, "
			+ "create_user_id, update_dt, update_user_id, update_count, "
			+ "active_ind, system_assigned_version_nbr, record_type_ref_id"
			+ " from reference_history "
			+ " where reference_history_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into reference_history (reference_history_id, ref_id, history_dt, history_user_id, "
			+ "baseline_id, reference_group, app_type_ref_id, display_code, "
			+ "display_sequence, display, description, create_dt, "
			+ "create_user_id, update_dt, update_user_id, update_count, "
			+ "active_ind, system_assigned_version_nbr, record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(ReferenceHistoryModel m) {
		Vector v = new Vector(19);
		v.add(new Integer(m.getReferenceHistoryId()));
		v.add(new Integer(m.getRefId()));
		if (m.getHistoryDt() != null) v.add(m.getHistoryDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getHistoryUserId()));
		v.add(new Integer(m.getBaselineId()));
		if (m.getReferenceGroup() != null) v.add(m.getReferenceGroup());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getAppTypeRefId()));
		if (m.getDisplayCode() != null) v.add(m.getDisplayCode());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getDisplaySequence()));
		if (m.getDisplay() != null) v.add(m.getDisplay());
		else v.add(new Null(new String()));
		if (m.getDescription() != null) v.add(m.getDescription());
		else v.add(new Null(new String()));
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
		return "update reference_history set  ref_id = ?, history_dt = ?, history_user_id = ?, "
			+ "baseline_id = ?, reference_group = ?, app_type_ref_id = ?, display_code = ?, "
			+ "display_sequence = ?, display = ?, description = ?, create_dt = ?, "
			+ "create_user_id = ?, update_dt = ?, update_user_id = ?, update_count = ?, "
			+ "active_ind = ?, system_assigned_version_nbr = ?, record_type_ref_id = ? "
			+ " where reference_history_id = ?";				 
	}
	
	private Vector getUpdateParameters(ReferenceHistoryModel m) {
		Vector v = new Vector(19);
		v.add(new Integer(m.getRefId()));
		if (m.getHistoryDt() != null) v.add(m.getHistoryDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getHistoryUserId()));
		v.add(new Integer(m.getBaselineId()));
		if (m.getReferenceGroup() != null) v.add(m.getReferenceGroup());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getAppTypeRefId()));
		if (m.getDisplayCode() != null) v.add(m.getDisplayCode());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getDisplaySequence()));
		if (m.getDisplay() != null) v.add(m.getDisplay());
		else v.add(new Null(new String()));
		if (m.getDescription() != null) v.add(m.getDescription());
		else v.add(new Null(new String()));
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
		v.add(new Integer(m.getReferenceHistoryId()));
		return v;
	}

	private Vector getDeleteParameters(ReferenceHistoryModel m) {
		Vector v = new Vector(19);
		v.add(new Integer(m.getReferenceHistoryId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from reference_history where reference_history_id = ?";				 
	}

}
