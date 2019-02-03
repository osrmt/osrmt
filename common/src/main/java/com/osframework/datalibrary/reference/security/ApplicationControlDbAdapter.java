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
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;

/**
null
*/
public class ApplicationControlDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public ApplicationControlDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateApplicationControl(ApplicationControlModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateApplicationControl(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateApplicationControl(ApplicationControlModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		ApplicationControlModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getApplicationControl(m.getApplicationControlId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setApplicationControlId(original.getApplicationControlId());
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
	
	private UpdateResult save(ApplicationControlModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getApplicationControlId()==0) {
				m.setApplicationControlId(Db.getNextSequence(TableNameFramework.APPLICATIONCONTROL, conn));
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
			return new UpdateResult(nbrRows,m.getApplicationControlId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportApplicationControl(ApplicationControlModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteApplicationControl(ApplicationControlModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapApplicationControl(ResultSet rset, ApplicationControlModel m) throws SQLException {
		if (columnExists(rset, "application_control_id")) {
			m.setApplicationControlId(rset.getInt("application_control_id"));
			if (rset.wasNull()) m.setApplicationControlId(0);
		}
		if (columnExists(rset, "application_view_id")) {
			m.setApplicationViewId(rset.getInt("application_view_id"));
			if (rset.wasNull()) m.setApplicationViewId(0);
		}
		if (columnExists(rset, "display_sequence")) {
			m.setDisplaySequence(rset.getInt("display_sequence"));
			if (rset.wasNull()) m.setDisplaySequence(0);
		}
		if (columnExists(rset, "control_ref_id")) {
			m.setControlRefId(rset.getInt("control_ref_id"));
			if (rset.wasNull()) m.setControlRefId(0);
		}
		if (columnExists(rset, "control_type_ref_id")) {
			m.setControlTypeRefId(rset.getInt("control_type_ref_id"));
			if (rset.wasNull()) m.setControlTypeRefId(0);
		}
		if (columnExists(rset, "control_text")) {
			m.setControlText(rset.getString("control_text"));
			if (rset.wasNull()) m.setControlText(null);
		}
		if (columnExists(rset, "control_description")) {
			m.setControlDescription(rset.getString("control_description"));
			if (rset.wasNull()) m.setControlDescription(null);
		}
		if (columnExists(rset, "model_column_ref_id")) {
			m.setModelColumnRefId(rset.getInt("model_column_ref_id"));
			if (rset.wasNull()) m.setModelColumnRefId(0);
		}
		if (columnExists(rset, "application_custom_control_id")) {
			m.setApplicationCustomControlId(rset.getInt("application_custom_control_id"));
			if (rset.wasNull()) m.setApplicationCustomControlId(0);
		}
		if (columnExists(rset, "app_control_user_defined_id")) {
			m.setAppControlUserDefinedId(rset.getInt("app_control_user_defined_id"));
			if (rset.wasNull()) m.setAppControlUserDefinedId(0);
		}
		if (columnExists(rset, "control_format")) {
			m.setControlFormat(rset.getString("control_format"));
			if (rset.wasNull()) m.setControlFormat(null);
		}
		if (columnExists(rset, "source_ref_id")) {
			m.setSourceRefId(rset.getInt("source_ref_id"));
			if (rset.wasNull()) m.setSourceRefId(0);
		}
		if (columnExists(rset, "default_value")) {
			m.setDefaultValue(rset.getString("default_value"));
			if (rset.wasNull()) m.setDefaultValue(null);
		}
		if (columnExists(rset, "locked_ind")) {
			m.setLockedInd(rset.getInt("locked_ind"));
			if (rset.wasNull()) m.setLockedInd(0);
		}
		if (columnExists(rset, "disabled_ind")) {
			m.setDisabledInd(rset.getInt("disabled_ind"));
			if (rset.wasNull()) m.setDisabledInd(0);
		}
		if (columnExists(rset, "required_ind")) {
			m.setRequiredInd(rset.getInt("required_ind"));
			if (rset.wasNull()) m.setRequiredInd(0);
		}
		if (columnExists(rset, "visible_ind")) {
			m.setVisibleInd(rset.getInt("visible_ind"));
			if (rset.wasNull()) m.setVisibleInd(0);
		}
		if (columnExists(rset, "init_script")) {
			m.setInitScript(rset.getString("init_script"));
			if (rset.wasNull()) m.setInitScript(null);
		}
		if (columnExists(rset, "focus_lost_script")) {
			m.setFocusLostScript(rset.getString("focus_lost_script"));
			if (rset.wasNull()) m.setFocusLostScript(null);
		}
		if (columnExists(rset, "focus_gained_script")) {
			m.setFocusGainedScript(rset.getString("focus_gained_script"));
			if (rset.wasNull()) m.setFocusGainedScript(null);
		}
		if (columnExists(rset, "image_path")) {
			m.setImagePath(rset.getString("image_path"));
			if (rset.wasNull()) m.setImagePath(null);
		}
		if (columnExists(rset, "scrollpane_ind")) {
			m.setScrollpaneInd(rset.getInt("scrollpane_ind"));
			if (rset.wasNull()) m.setScrollpaneInd(0);
		}
		if (columnExists(rset, "grow_height")) {
			m.setGrowHeight(rset.getDouble("grow_height"));
			if (rset.wasNull()) m.setGrowHeight(0);
		}
		if (columnExists(rset, "grow_width")) {
			m.setGrowWidth(rset.getInt("grow_width"));
			if (rset.wasNull()) m.setGrowWidth(0);
		}
		if (columnExists(rset, "unit_width")) {
			m.setUnitWidth(rset.getInt("unit_width"));
			if (rset.wasNull()) m.setUnitWidth(0);
		}
		if (columnExists(rset, "unit_height")) {
			m.setUnitHeight(rset.getInt("unit_height"));
			if (rset.wasNull()) m.setUnitHeight(0);
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
	public ApplicationControlList  getApplicationControl(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getApplicationControl(sql, conn);
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
	public ApplicationControlList  getApplicationControl(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ApplicationControlList list = new ApplicationControlList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ApplicationControlModel m = new ApplicationControlModel();
				mapApplicationControl(rset, m);
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
	public ApplicationControlList  getApplicationControl(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getApplicationControl(sql, params, conn);
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
	public ApplicationControlList  getApplicationControl(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ApplicationControlList list = new ApplicationControlList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ApplicationControlModel m = new ApplicationControlModel();
				mapApplicationControl(rset, m);
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
	public ApplicationControlModel getApplicationControl(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getApplicationControl(id, conn);
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
	public ApplicationControlModel getApplicationControl(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			ApplicationControlModel m = new ApplicationControlModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapApplicationControl(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "ApplicationControlModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public ApplicationControlList exportApplicationControl() throws DataAccessException {
		int rows = Db.countTable("application_control");
		Debug.LogInfo(this, rows + " found in application_control");
		String sql = "select * from application_control where record_type_ref_id in (?, ?)";
		ApplicationControlList list = this.getApplicationControl(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " application_control rows exported");
		return list;
	}

	public int importApplicationControl(ApplicationControlList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			ApplicationControlModel m = (ApplicationControlModel) e1.nextElement();
			rows += ImportApplicationControl(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select application_control_id, application_view_id, display_sequence, control_ref_id, "
			+ "control_type_ref_id, control_text, control_description, model_column_ref_id, "
			+ "application_custom_control_id, app_control_user_defined_id, control_format, source_ref_id, "
			+ "default_value, locked_ind, disabled_ind, required_ind, "
			+ "visible_ind, init_script, focus_lost_script, focus_gained_script, "
			+ "image_path, scrollpane_ind, grow_height, grow_width, "
			+ "unit_width, unit_height, create_dt, create_user_id, "
			+ "update_dt, update_user_id, update_count, active_ind, "
			+ "system_assigned_version_nbr, record_type_ref_id"
			+ " from application_control "
			+ " where application_control_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into application_control (application_control_id, application_view_id, display_sequence, control_ref_id, "
			+ "control_type_ref_id, control_text, control_description, model_column_ref_id, "
			+ "application_custom_control_id, app_control_user_defined_id, control_format, source_ref_id, "
			+ "default_value, locked_ind, disabled_ind, required_ind, "
			+ "visible_ind, init_script, focus_lost_script, focus_gained_script, "
			+ "image_path, scrollpane_ind, grow_height, grow_width, "
			+ "unit_width, unit_height, create_dt, create_user_id, "
			+ "update_dt, update_user_id, update_count, active_ind, "
			+ "system_assigned_version_nbr, record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(ApplicationControlModel m) {
		Vector v = new Vector(34);
		v.add(new Integer(m.getApplicationControlId()));
		v.add(new Integer(m.getApplicationViewId()));
		v.add(new Integer(m.getDisplaySequence()));
		v.add(new Integer(m.getControlRefId()));
		v.add(new Integer(m.getControlTypeRefId()));
		if (m.getControlText() != null) v.add(m.getControlText());
		else v.add(new Null(new String()));
		if (m.getControlDescription() != null) v.add(m.getControlDescription());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getModelColumnRefId()));
		v.add(new Integer(m.getApplicationCustomControlId()));
		v.add(new Integer(m.getAppControlUserDefinedId()));
		if (m.getControlFormat() != null) v.add(m.getControlFormat());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getSourceRefId()));
		if (m.getDefaultValue() != null) v.add(m.getDefaultValue());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getLockedInd()));
		v.add(new Integer(m.getDisabledInd()));
		v.add(new Integer(m.getRequiredInd()));
		v.add(new Integer(m.getVisibleInd()));
		if (m.getInitScript() != null) v.add(m.getInitScript());
		else v.add(new Null(new String()));
		if (m.getFocusLostScript() != null) v.add(m.getFocusLostScript());
		else v.add(new Null(new String()));
		if (m.getFocusGainedScript() != null) v.add(m.getFocusGainedScript());
		else v.add(new Null(new String()));
		if (m.getImagePath() != null) v.add(m.getImagePath());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getScrollpaneInd()));
		v.add(new Double(m.getGrowHeight()));
		v.add(new Integer(m.getGrowWidth()));
		v.add(new Integer(m.getUnitWidth()));
		v.add(new Integer(m.getUnitHeight()));
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
		return "update application_control set  application_view_id = ?, display_sequence = ?, control_ref_id = ?, "
			+ "control_type_ref_id = ?, control_text = ?, control_description = ?, model_column_ref_id = ?, "
			+ "application_custom_control_id = ?, app_control_user_defined_id = ?, control_format = ?, source_ref_id = ?, "
			+ "default_value = ?, locked_ind = ?, disabled_ind = ?, required_ind = ?, "
			+ "visible_ind = ?, init_script = ?, focus_lost_script = ?, focus_gained_script = ?, "
			+ "image_path = ?, scrollpane_ind = ?, grow_height = ?, grow_width = ?, "
			+ "unit_width = ?, unit_height = ?, create_dt = ?, create_user_id = ?, "
			+ "update_dt = ?, update_user_id = ?, update_count = ?, active_ind = ?, "
			+ "system_assigned_version_nbr = ?, record_type_ref_id = ? "
			+ " where application_control_id = ?";				 
	}
	
	private Vector getUpdateParameters(ApplicationControlModel m) {
		Vector v = new Vector(34);
		v.add(new Integer(m.getApplicationViewId()));
		v.add(new Integer(m.getDisplaySequence()));
		v.add(new Integer(m.getControlRefId()));
		v.add(new Integer(m.getControlTypeRefId()));
		if (m.getControlText() != null) v.add(m.getControlText());
		else v.add(new Null(new String()));
		if (m.getControlDescription() != null) v.add(m.getControlDescription());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getModelColumnRefId()));
		v.add(new Integer(m.getApplicationCustomControlId()));
		v.add(new Integer(m.getAppControlUserDefinedId()));
		if (m.getControlFormat() != null) v.add(m.getControlFormat());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getSourceRefId()));
		if (m.getDefaultValue() != null) v.add(m.getDefaultValue());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getLockedInd()));
		v.add(new Integer(m.getDisabledInd()));
		v.add(new Integer(m.getRequiredInd()));
		v.add(new Integer(m.getVisibleInd()));
		if (m.getInitScript() != null) v.add(m.getInitScript());
		else v.add(new Null(new String()));
		if (m.getFocusLostScript() != null) v.add(m.getFocusLostScript());
		else v.add(new Null(new String()));
		if (m.getFocusGainedScript() != null) v.add(m.getFocusGainedScript());
		else v.add(new Null(new String()));
		if (m.getImagePath() != null) v.add(m.getImagePath());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getScrollpaneInd()));
		v.add(new Double(m.getGrowHeight()));
		v.add(new Integer(m.getGrowWidth()));
		v.add(new Integer(m.getUnitWidth()));
		v.add(new Integer(m.getUnitHeight()));
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
		v.add(new Integer(m.getApplicationControlId()));
		return v;
	}

	private Vector getDeleteParameters(ApplicationControlModel m) {
		Vector v = new Vector(34);
		v.add(new Integer(m.getApplicationControlId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from application_control where application_control_id = ?";				 
	}

}
