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
public class ApplicationViewDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public ApplicationViewDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateApplicationView(ApplicationViewModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateApplicationView(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateApplicationView(ApplicationViewModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		ApplicationViewModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getApplicationView(m.getApplicationViewId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setApplicationViewId(original.getApplicationViewId());
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
	
	private UpdateResult save(ApplicationViewModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getApplicationViewId()==0) {
				m.setApplicationViewId(Db.getNextSequence(TableNameFramework.APPLICATIONVIEW, conn));
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
			return new UpdateResult(nbrRows,m.getApplicationViewId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportApplicationView(ApplicationViewModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteApplicationView(ApplicationViewModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapApplicationView(ResultSet rset, ApplicationViewModel m) throws SQLException {
		if (columnExists(rset, "application_view_id")) {
			m.setApplicationViewId(rset.getInt("application_view_id"));
			if (rset.wasNull()) m.setApplicationViewId(0);
		}
		if (columnExists(rset, "application_ref_id")) {
			m.setApplicationRefId(rset.getInt("application_ref_id"));
			if (rset.wasNull()) m.setApplicationRefId(0);
		}
		if (columnExists(rset, "app_type_ref_id")) {
			m.setAppTypeRefId(rset.getInt("app_type_ref_id"));
			if (rset.wasNull()) m.setAppTypeRefId(0);
		}
		if (columnExists(rset, "view_ref_id")) {
			m.setViewRefId(rset.getInt("view_ref_id"));
			if (rset.wasNull()) m.setViewRefId(0);
		}
		if (columnExists(rset, "form_title")) {
			m.setFormTitle(rset.getString("form_title"));
			if (rset.wasNull()) m.setFormTitle(null);
		}
		if (columnExists(rset, "init_script")) {
			m.setInitScript(rset.getString("init_script"));
			if (rset.wasNull()) m.setInitScript(null);
		}
		if (columnExists(rset, "validation_script")) {
			m.setValidationScript(rset.getString("validation_script"));
			if (rset.wasNull()) m.setValidationScript(null);
		}
		if (columnExists(rset, "form_width")) {
			m.setFormWidth(rset.getInt("form_width"));
			if (rset.wasNull()) m.setFormWidth(0);
		}
		if (columnExists(rset, "form_height")) {
			m.setFormHeight(rset.getInt("form_height"));
			if (rset.wasNull()) m.setFormHeight(0);
		}
		if (columnExists(rset, "form_center_screen_ind")) {
			m.setFormCenterScreenInd(rset.getInt("form_center_screen_ind"));
			if (rset.wasNull()) m.setFormCenterScreenInd(0);
		}
		if (columnExists(rset, "form_x_pos")) {
			m.setFormXPos(rset.getInt("form_x_pos"));
			if (rset.wasNull()) m.setFormXPos(0);
		}
		if (columnExists(rset, "form_y_pos")) {
			m.setFormYPos(rset.getInt("form_y_pos"));
			if (rset.wasNull()) m.setFormYPos(0);
		}
		if (columnExists(rset, "form_control_columns")) {
			m.setFormControlColumns(rset.getInt("form_control_columns"));
			if (rset.wasNull()) m.setFormControlColumns(0);
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
	public ApplicationViewList  getApplicationView(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getApplicationView(sql, conn);
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
	public ApplicationViewList  getApplicationView(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ApplicationViewList list = new ApplicationViewList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ApplicationViewModel m = new ApplicationViewModel();
				mapApplicationView(rset, m);
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
	public ApplicationViewList  getApplicationView(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getApplicationView(sql, params, conn);
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
	public ApplicationViewList  getApplicationView(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ApplicationViewList list = new ApplicationViewList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ApplicationViewModel m = new ApplicationViewModel();
				mapApplicationView(rset, m);
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
	public ApplicationViewModel getApplicationView(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getApplicationView(id, conn);
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
	public ApplicationViewModel getApplicationView(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			ApplicationViewModel m = new ApplicationViewModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapApplicationView(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "ApplicationViewModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public ApplicationViewList exportApplicationView() throws DataAccessException {
		int rows = Db.countTable("application_view");
		Debug.LogInfo(this, rows + " found in application_view");
		String sql = "select * from application_view where record_type_ref_id in (?, ?)";
		ApplicationViewList list = this.getApplicationView(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " application_view rows exported");
		return list;
	}

	public int importApplicationView(ApplicationViewList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			ApplicationViewModel m = (ApplicationViewModel) e1.nextElement();
			rows += ImportApplicationView(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select application_view_id, application_ref_id, app_type_ref_id, view_ref_id, "
			+ "form_title, init_script, validation_script, form_width, "
			+ "form_height, form_center_screen_ind, form_x_pos, form_y_pos, "
			+ "form_control_columns, create_dt, create_user_id, update_dt, "
			+ "update_user_id, update_count, active_ind, system_assigned_version_nbr, "
			+ "record_type_ref_id"
			+ " from application_view "
			+ " where application_view_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into application_view (application_view_id, application_ref_id, app_type_ref_id, view_ref_id, "
			+ "form_title, init_script, validation_script, form_width, "
			+ "form_height, form_center_screen_ind, form_x_pos, form_y_pos, "
			+ "form_control_columns, create_dt, create_user_id, update_dt, "
			+ "update_user_id, update_count, active_ind, system_assigned_version_nbr, "
			+ "record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(ApplicationViewModel m) {
		Vector v = new Vector(21);
		v.add(new Integer(m.getApplicationViewId()));
		v.add(new Integer(m.getApplicationRefId()));
		v.add(new Integer(m.getAppTypeRefId()));
		v.add(new Integer(m.getViewRefId()));
		if (m.getFormTitle() != null) v.add(m.getFormTitle());
		else v.add(new Null(new String()));
		if (m.getInitScript() != null) v.add(m.getInitScript());
		else v.add(new Null(new String()));
		if (m.getValidationScript() != null) v.add(m.getValidationScript());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getFormWidth()));
		v.add(new Integer(m.getFormHeight()));
		v.add(new Integer(m.getFormCenterScreenInd()));
		v.add(new Integer(m.getFormXPos()));
		v.add(new Integer(m.getFormYPos()));
		v.add(new Integer(m.getFormControlColumns()));
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
		return "update application_view set  application_ref_id = ?, app_type_ref_id = ?, view_ref_id = ?, "
			+ "form_title = ?, init_script = ?, validation_script = ?, form_width = ?, "
			+ "form_height = ?, form_center_screen_ind = ?, form_x_pos = ?, form_y_pos = ?, "
			+ "form_control_columns = ?, create_dt = ?, create_user_id = ?, update_dt = ?, "
			+ "update_user_id = ?, update_count = ?, active_ind = ?, system_assigned_version_nbr = ?, "
			+ "record_type_ref_id = ? "
			+ " where application_view_id = ?";				 
	}
	
	private Vector getUpdateParameters(ApplicationViewModel m) {
		Vector v = new Vector(21);
		v.add(new Integer(m.getApplicationRefId()));
		v.add(new Integer(m.getAppTypeRefId()));
		v.add(new Integer(m.getViewRefId()));
		if (m.getFormTitle() != null) v.add(m.getFormTitle());
		else v.add(new Null(new String()));
		if (m.getInitScript() != null) v.add(m.getInitScript());
		else v.add(new Null(new String()));
		if (m.getValidationScript() != null) v.add(m.getValidationScript());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getFormWidth()));
		v.add(new Integer(m.getFormHeight()));
		v.add(new Integer(m.getFormCenterScreenInd()));
		v.add(new Integer(m.getFormXPos()));
		v.add(new Integer(m.getFormYPos()));
		v.add(new Integer(m.getFormControlColumns()));
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
		v.add(new Integer(m.getApplicationViewId()));
		return v;
	}

	private Vector getDeleteParameters(ApplicationViewModel m) {
		Vector v = new Vector(21);
		v.add(new Integer(m.getApplicationViewId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from application_view where application_view_id = ?";				 
	}

}
