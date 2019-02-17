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
package com.osframework.datalibrary.reference.security;

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
import com.osframework.modellibrary.reference.security.*;

/**
null
*/
public class ApplicationSecurityDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public ApplicationSecurityDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateApplicationSecurity(ApplicationSecurityModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateApplicationSecurity(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateApplicationSecurity(ApplicationSecurityModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		ApplicationSecurityModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getApplicationSecurity(m.getApplicationSecurityId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setApplicationSecurityId(original.getApplicationSecurityId());
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
	
	private UpdateResult save(ApplicationSecurityModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getApplicationSecurityId()==0) {
				m.setApplicationSecurityId(Db.getNextSequence(TableNameFramework.APPLICATIONSECURITY, conn));
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
			return new UpdateResult(nbrRows,m.getApplicationSecurityId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportApplicationSecurity(ApplicationSecurityModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteApplicationSecurity(ApplicationSecurityModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapApplicationSecurity(ResultSet rset, ApplicationSecurityModel m) throws SQLException {
		if (columnExists(rset, "application_security_id")) {
			m.setApplicationSecurityId(rset.getInt("application_security_id"));
			if (rset.wasNull()) m.setApplicationSecurityId(0);
		}
		if (columnExists(rset, "table_key_id")) {
			m.setTableKeyId(rset.getInt("table_key_id"));
			if (rset.wasNull()) m.setTableKeyId(0);
		}
		if (columnExists(rset, "table_ref_id")) {
			m.setTableRefId(rset.getInt("table_ref_id"));
			if (rset.wasNull()) m.setTableRefId(0);
		}
		if (columnExists(rset, "application_view_id")) {
			m.setApplicationViewId(rset.getInt("application_view_id"));
			if (rset.wasNull()) m.setApplicationViewId(0);
		}
		if (columnExists(rset, "read_only_ind")) {
			m.setReadOnlyInd(rset.getInt("read_only_ind"));
			if (rset.wasNull()) m.setReadOnlyInd(0);
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
	public ApplicationSecurityList  getApplicationSecurity(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getApplicationSecurity(sql, conn);
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
	public ApplicationSecurityList  getApplicationSecurity(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ApplicationSecurityList list = new ApplicationSecurityList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ApplicationSecurityModel m = new ApplicationSecurityModel();
				mapApplicationSecurity(rset, m);
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
	public ApplicationSecurityList  getApplicationSecurity(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getApplicationSecurity(sql, params, conn);
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
	public ApplicationSecurityList  getApplicationSecurity(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ApplicationSecurityList list = new ApplicationSecurityList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ApplicationSecurityModel m = new ApplicationSecurityModel();
				mapApplicationSecurity(rset, m);
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
	public ApplicationSecurityModel getApplicationSecurity(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getApplicationSecurity(id, conn);
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
	public ApplicationSecurityModel getApplicationSecurity(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			ApplicationSecurityModel m = new ApplicationSecurityModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapApplicationSecurity(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "ApplicationSecurityModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public ApplicationSecurityList exportApplicationSecurity() throws DataAccessException {
		int rows = Db.countTable("application_security");
		Debug.LogInfo(this, rows + " found in application_security");
		String sql = "select * from application_security where record_type_ref_id in (?, ?)";
		ApplicationSecurityList list = this.getApplicationSecurity(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " application_security rows exported");
		return list;
	}

	public int importApplicationSecurity(ApplicationSecurityList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel m = (ApplicationSecurityModel) e1.nextElement();
			rows += ImportApplicationSecurity(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select application_security_id, table_key_id, table_ref_id, application_view_id, "
			+ "read_only_ind, create_dt, create_user_id, update_dt, "
			+ "update_user_id, update_count, active_ind, system_assigned_version_nbr, "
			+ "record_type_ref_id"
			+ " from application_security "
			+ " where application_security_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into application_security (application_security_id, table_key_id, table_ref_id, application_view_id, "
			+ "read_only_ind, create_dt, create_user_id, update_dt, "
			+ "update_user_id, update_count, active_ind, system_assigned_version_nbr, "
			+ "record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(ApplicationSecurityModel m) {
		Vector v = new Vector(13);
		v.add(new Integer(m.getApplicationSecurityId()));
		v.add(new Integer(m.getTableKeyId()));
		v.add(new Integer(m.getTableRefId()));
		v.add(new Integer(m.getApplicationViewId()));
		v.add(new Integer(m.getReadOnlyInd()));
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
		return "update application_security set  table_key_id = ?, table_ref_id = ?, application_view_id = ?, "
			+ "read_only_ind = ?, create_dt = ?, create_user_id = ?, update_dt = ?, "
			+ "update_user_id = ?, update_count = ?, active_ind = ?, system_assigned_version_nbr = ?, "
			+ "record_type_ref_id = ? "
			+ " where application_security_id = ?";				 
	}
	
	private Vector getUpdateParameters(ApplicationSecurityModel m) {
		Vector v = new Vector(13);
		v.add(new Integer(m.getTableKeyId()));
		v.add(new Integer(m.getTableRefId()));
		v.add(new Integer(m.getApplicationViewId()));
		v.add(new Integer(m.getReadOnlyInd()));
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
		v.add(new Integer(m.getApplicationSecurityId()));
		return v;
	}

	private Vector getDeleteParameters(ApplicationSecurityModel m) {
		Vector v = new Vector(13);
		v.add(new Integer(m.getApplicationSecurityId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from application_security where application_security_id = ?";				 
	}

}
