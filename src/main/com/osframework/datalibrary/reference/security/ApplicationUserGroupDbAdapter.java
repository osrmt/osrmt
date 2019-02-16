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
public class ApplicationUserGroupDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public ApplicationUserGroupDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateApplicationUserGroup(ApplicationUserGroupModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateApplicationUserGroup(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateApplicationUserGroup(ApplicationUserGroupModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		ApplicationUserGroupModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getApplicationUserGroup(m.getApplicationUserGroupId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setApplicationUserGroupId(original.getApplicationUserGroupId());
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
	
	private UpdateResult save(ApplicationUserGroupModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getApplicationUserGroupId()==0) {
				m.setApplicationUserGroupId(Db.getNextSequence(TableNameFramework.APPLICATIONUSERGROUP, conn));
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
			return new UpdateResult(nbrRows,m.getApplicationUserGroupId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportApplicationUserGroup(ApplicationUserGroupModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteApplicationUserGroup(ApplicationUserGroupModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapApplicationUserGroup(ResultSet rset, ApplicationUserGroupModel m) throws SQLException {
		if (columnExists(rset, "application_user_group_id")) {
			m.setApplicationUserGroupId(rset.getInt("application_user_group_id"));
			if (rset.wasNull()) m.setApplicationUserGroupId(0);
		}
		if (columnExists(rset, "user_id")) {
			m.setUserId(rset.getInt("user_id"));
			if (rset.wasNull()) m.setUserId(0);
		}
		if (columnExists(rset, "user_group_ref_id")) {
			m.setUserGroupRefId(rset.getInt("user_group_ref_id"));
			if (rset.wasNull()) m.setUserGroupRefId(0);
		}
		if (columnExists(rset, "email")) {
			m.setEmail(rset.getString("email"));
			if (rset.wasNull()) m.setEmail(null);
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
	public ApplicationUserGroupList  getApplicationUserGroup(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getApplicationUserGroup(sql, conn);
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
	public ApplicationUserGroupList  getApplicationUserGroup(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ApplicationUserGroupList list = new ApplicationUserGroupList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ApplicationUserGroupModel m = new ApplicationUserGroupModel();
				mapApplicationUserGroup(rset, m);
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
	public ApplicationUserGroupList  getApplicationUserGroup(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getApplicationUserGroup(sql, params, conn);
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
	public ApplicationUserGroupList  getApplicationUserGroup(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ApplicationUserGroupList list = new ApplicationUserGroupList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ApplicationUserGroupModel m = new ApplicationUserGroupModel();
				mapApplicationUserGroup(rset, m);
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
	public ApplicationUserGroupModel getApplicationUserGroup(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getApplicationUserGroup(id, conn);
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
	public ApplicationUserGroupModel getApplicationUserGroup(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			ApplicationUserGroupModel m = new ApplicationUserGroupModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapApplicationUserGroup(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "ApplicationUserGroupModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public ApplicationUserGroupList exportApplicationUserGroup() throws DataAccessException {
		int rows = Db.countTable("application_user_group");
		Debug.LogInfo(this, rows + " found in application_user_group");
		String sql = "select * from application_user_group where record_type_ref_id in (?, ?)";
		ApplicationUserGroupList list = this.getApplicationUserGroup(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " application_user_group rows exported");
		return list;
	}

	public int importApplicationUserGroup(ApplicationUserGroupList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			ApplicationUserGroupModel m = (ApplicationUserGroupModel) e1.nextElement();
			rows += ImportApplicationUserGroup(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select application_user_group_id, user_id, user_group_ref_id, email, "
			+ "create_dt, create_user_id, update_dt, update_user_id, "
			+ "update_count, active_ind, system_assigned_version_nbr, record_type_ref_id"
			+ " from application_user_group "
			+ " where application_user_group_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into application_user_group (application_user_group_id, user_id, user_group_ref_id, email, "
			+ "create_dt, create_user_id, update_dt, update_user_id, "
			+ "update_count, active_ind, system_assigned_version_nbr, record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(ApplicationUserGroupModel m) {
		Vector v = new Vector(12);
		v.add(new Integer(m.getApplicationUserGroupId()));
		v.add(new Integer(m.getUserId()));
		v.add(new Integer(m.getUserGroupRefId()));
		if (m.getEmail() != null) v.add(m.getEmail());
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
		return "update application_user_group set  user_id = ?, user_group_ref_id = ?, email = ?, "
			+ "create_dt = ?, create_user_id = ?, update_dt = ?, update_user_id = ?, "
			+ "update_count = ?, active_ind = ?, system_assigned_version_nbr = ?, record_type_ref_id = ? "
			+ " where application_user_group_id = ?";				 
	}
	
	private Vector getUpdateParameters(ApplicationUserGroupModel m) {
		Vector v = new Vector(12);
		v.add(new Integer(m.getUserId()));
		v.add(new Integer(m.getUserGroupRefId()));
		if (m.getEmail() != null) v.add(m.getEmail());
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
		v.add(new Integer(m.getApplicationUserGroupId()));
		return v;
	}

	private Vector getDeleteParameters(ApplicationUserGroupModel m) {
		Vector v = new Vector(12);
		v.add(new Integer(m.getApplicationUserGroupId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from application_user_group where application_user_group_id = ?";				 
	}

}
