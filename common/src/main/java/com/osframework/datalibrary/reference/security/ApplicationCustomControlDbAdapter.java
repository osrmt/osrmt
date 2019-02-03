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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.osframework.datalibrary.common.AccessDataTypeException;
import com.osframework.datalibrary.common.BaseAdapter;
import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.DataUpdateException;
import com.osframework.datalibrary.common.Db;
import com.osframework.datalibrary.common.DbConnection;
import com.osframework.datalibrary.common.Null;
import com.osframework.datalibrary.common.SQLResult;
import com.osframework.datalibrary.common.UpdateResult;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.exceptions.NullArgumentException;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.group.RecordTypeFramework;
import com.osframework.modellibrary.reference.group.TableNameFramework;
import com.osframework.modellibrary.reference.security.ApplicationCustomControlList;
import com.osframework.modellibrary.reference.security.ApplicationCustomControlModel;

/**
null
*/
public class ApplicationCustomControlDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public ApplicationCustomControlDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateApplicationCustomControl(ApplicationCustomControlModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateApplicationCustomControl(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateApplicationCustomControl(ApplicationCustomControlModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		ApplicationCustomControlModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getApplicationCustomControl(m.getApplicationCustomControlId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setApplicationCustomControlId(original.getApplicationCustomControlId());
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
	
	private UpdateResult save(ApplicationCustomControlModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getApplicationCustomControlId()==0) {
				m.setApplicationCustomControlId(Db.getNextSequence(TableNameFramework.APPLICATIONCUSTOMCONTROL, conn));
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
			return new UpdateResult(nbrRows,m.getApplicationCustomControlId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportApplicationCustomControl(ApplicationCustomControlModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteApplicationCustomControl(ApplicationCustomControlModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapApplicationCustomControl(ResultSet rset, ApplicationCustomControlModel m) throws SQLException {
		if (columnExists(rset, "application_custom_control_id")) {
			m.setApplicationCustomControlId(rset.getInt("application_custom_control_id"));
			if (rset.wasNull()) m.setApplicationCustomControlId(0);
		}
		if (columnExists(rset, "custom_control_ref_id")) {
			m.setCustomControlRefId(rset.getInt("custom_control_ref_id"));
			if (rset.wasNull()) m.setCustomControlRefId(0);
		}
		if (columnExists(rset, "class_name")) {
			m.setClassName(rset.getString("class_name"));
			if (rset.wasNull()) m.setClassName(null);
		}
		if (columnExists(rset, "populate_script")) {
			m.setPopulateScript(rset.getString("populate_script"));
			if (rset.wasNull()) m.setPopulateScript(null);
		}
		if (columnExists(rset, "html_script")) {
			m.setHtmlScript(rset.getString("html_script"));
			if (rset.wasNull()) m.setHtmlScript(null);
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
	public ApplicationCustomControlList  getApplicationCustomControl(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getApplicationCustomControl(sql, conn);
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
	public ApplicationCustomControlList  getApplicationCustomControl(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ApplicationCustomControlList list = new ApplicationCustomControlList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ApplicationCustomControlModel m = new ApplicationCustomControlModel();
				mapApplicationCustomControl(rset, m);
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
	public ApplicationCustomControlList  getApplicationCustomControl(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getApplicationCustomControl(sql, params, conn);
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
	public ApplicationCustomControlList  getApplicationCustomControl(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ApplicationCustomControlList list = new ApplicationCustomControlList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ApplicationCustomControlModel m = new ApplicationCustomControlModel();
				mapApplicationCustomControl(rset, m);
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
	public ApplicationCustomControlModel getApplicationCustomControl(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getApplicationCustomControl(id, conn);
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
	public ApplicationCustomControlModel getApplicationCustomControl(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			ApplicationCustomControlModel m = new ApplicationCustomControlModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapApplicationCustomControl(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "ApplicationCustomControlModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public ApplicationCustomControlList exportApplicationCustomControl() throws DataAccessException {
		int rows = Db.countTable("application_custom_control");
		Debug.LogInfo(this, rows + " found in application_custom_control");
		String sql = "select * from application_custom_control where record_type_ref_id in (?, ?)";
		ApplicationCustomControlList list = this.getApplicationCustomControl(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " application_custom_control rows exported");
		return list;
	}

	public int importApplicationCustomControl(ApplicationCustomControlList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			ApplicationCustomControlModel m = (ApplicationCustomControlModel) e1.nextElement();
			rows += ImportApplicationCustomControl(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select application_custom_control_id, custom_control_ref_id, class_name, populate_script, "
			+ "html_script, create_dt, create_user_id, update_dt, "
			+ "update_user_id, update_count, active_ind, system_assigned_version_nbr, "
			+ "record_type_ref_id"
			+ " from application_custom_control "
			+ " where application_custom_control_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into application_custom_control (application_custom_control_id, custom_control_ref_id, class_name, populate_script, "
			+ "html_script, create_dt, create_user_id, update_dt, "
			+ "update_user_id, update_count, active_ind, system_assigned_version_nbr, "
			+ "record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(ApplicationCustomControlModel m) {
		Vector v = new Vector(13);
		v.add(new Integer(m.getApplicationCustomControlId()));
		v.add(new Integer(m.getCustomControlRefId()));
		if (m.getClassName() != null) v.add(m.getClassName());
		else v.add(new Null(new String()));
		if (m.getPopulateScript() != null) v.add(m.getPopulateScript());
		else v.add(new Null(new String()));
		if (m.getHtmlScript() != null) v.add(m.getHtmlScript());
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
		return "update application_custom_control set  custom_control_ref_id = ?, class_name = ?, populate_script = ?, "
			+ "html_script = ?, create_dt = ?, create_user_id = ?, update_dt = ?, "
			+ "update_user_id = ?, update_count = ?, active_ind = ?, system_assigned_version_nbr = ?, "
			+ "record_type_ref_id = ? "
			+ " where application_custom_control_id = ?";				 
	}
	
	private Vector getUpdateParameters(ApplicationCustomControlModel m) {
		Vector v = new Vector(13);
		v.add(new Integer(m.getCustomControlRefId()));
		if (m.getClassName() != null) v.add(m.getClassName());
		else v.add(new Null(new String()));
		if (m.getPopulateScript() != null) v.add(m.getPopulateScript());
		else v.add(new Null(new String()));
		if (m.getHtmlScript() != null) v.add(m.getHtmlScript());
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
		v.add(new Integer(m.getApplicationCustomControlId()));
		return v;
	}

	private Vector getDeleteParameters(ApplicationCustomControlModel m) {
		Vector v = new Vector(13);
		v.add(new Integer(m.getApplicationCustomControlId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from application_custom_control where application_custom_control_id = ?";				 
	}

}
