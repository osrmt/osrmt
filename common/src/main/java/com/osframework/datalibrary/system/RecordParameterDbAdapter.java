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
package com.osframework.datalibrary.system;

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
import com.osframework.modellibrary.system.RecordParameterList;
import com.osframework.modellibrary.system.RecordParameterModel;
import com.osframework.modellibrary.system.RecordParameterValueModel;

/**
null
*/
public class RecordParameterDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public RecordParameterDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}
	private RecordParameterValueDataAdapter da1 = new RecordParameterValueDataAdapter(reference, security);


	public UpdateResult UpdateRecordParameterModel(RecordParameterModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateRecordParameterModel(m,call,conn); 
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	public UpdateResult UpdateRecordParameterModel(RecordParameterModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		try { 
			if (m == null) {
				throw new NullArgumentException();
			}
			UpdateResult result = UpdateRecordParameter(m, call, conn);
			int nbrrows = result.getRowsUpdated();

			Enumeration e1 = m.getRecordParameterValueList().elements();
			while (e1.hasMoreElements()) {
				RecordParameterValueModel m1 = (RecordParameterValueModel) e1.nextElement();
				m1.setRecordParameterId(m.getRecordParameterId());
				nbrrows += da1.UpdateRecordParameterValue(m1, call, conn).getRowsUpdated();
			}

			return result;

		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw new DataUpdateException(ex);
		}
	}

	public UpdateResult UpdateRecordParameter(RecordParameterModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateRecordParameter(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateRecordParameter(RecordParameterModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		RecordParameterModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getRecordParameter(m.getRecordParameterId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setRecordParameterId(original.getRecordParameterId());
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
	
	private UpdateResult save(RecordParameterModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getRecordParameterId()==0) {
				m.setRecordParameterId(Db.getNextSequence(TableNameFramework.RECORDPARAMETER, conn));
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
			return new UpdateResult(nbrRows,m.getRecordParameterId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportRecordParameter(RecordParameterModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteRecordParameter(RecordParameterModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapRecordParameter(ResultSet rset, RecordParameterModel m) throws SQLException {
		if (columnExists(rset, "record_parameter_id")) {
			m.setRecordParameterId(rset.getInt("record_parameter_id"));
			if (rset.wasNull()) m.setRecordParameterId(0);
		}
		if (columnExists(rset, "table_id")) {
			m.setTableId(rset.getInt("table_id"));
			if (rset.wasNull()) m.setTableId(0);
		}
		if (columnExists(rset, "table_ref_id")) {
			m.setTableRefId(rset.getInt("table_ref_id"));
			if (rset.wasNull()) m.setTableRefId(0);
		}
		if (columnExists(rset, "parameter_name")) {
			m.setParameterName(rset.getString("parameter_name"));
			if (rset.wasNull()) m.setParameterName(null);
		}
		if (columnExists(rset, "model_column_ref_id")) {
			m.setModelColumnRefId(rset.getInt("model_column_ref_id"));
			if (rset.wasNull()) m.setModelColumnRefId(0);
		}
		if (columnExists(rset, "data_type_ref_id")) {
			m.setDataTypeRefId(rset.getInt("data_type_ref_id"));
			if (rset.wasNull()) m.setDataTypeRefId(0);
		}
		if (columnExists(rset, "parameter_seq")) {
			m.setParameterSeq(rset.getInt("parameter_seq"));
			if (rset.wasNull()) m.setParameterSeq(0);
		}
		if (columnExists(rset, "application_control_id")) {
			m.setApplicationControlId(rset.getInt("application_control_id"));
			if (rset.wasNull()) m.setApplicationControlId(0);
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
	public RecordParameterList  getRecordParameter(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getRecordParameter(sql, conn);
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
	public RecordParameterList  getRecordParameter(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			RecordParameterList list = new RecordParameterList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				RecordParameterModel m = new RecordParameterModel();
				mapRecordParameter(rset, m);
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
	public RecordParameterList  getRecordParameter(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getRecordParameter(sql, params, conn);
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
	public RecordParameterList  getRecordParameter(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			RecordParameterList list = new RecordParameterList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				RecordParameterModel m = new RecordParameterModel();
				mapRecordParameter(rset, m);
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
	public RecordParameterModel getRecordParameter(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getRecordParameter(id, conn);
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
	public RecordParameterModel getRecordParameter(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			RecordParameterModel m = new RecordParameterModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapRecordParameter(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "RecordParameterModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public RecordParameterList exportRecordParameter() throws DataAccessException {
		int rows = Db.countTable("record_parameter");
		Debug.LogInfo(this, rows + " found in record_parameter");
		String sql = "select * from record_parameter where record_type_ref_id in (?, ?)";
		RecordParameterList list = this.getRecordParameter(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " record_parameter rows exported");
		return list;
	}

	public int importRecordParameter(RecordParameterList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			RecordParameterModel m = (RecordParameterModel) e1.nextElement();
			rows += ImportRecordParameter(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select record_parameter_id, table_id, table_ref_id, parameter_name, "
			+ "model_column_ref_id, data_type_ref_id, parameter_seq, application_control_id, "
			+ "create_dt, create_user_id, update_dt, update_user_id, "
			+ "update_count, active_ind, system_assigned_version_nbr, record_type_ref_id"
			+ " from record_parameter "
			+ " where record_parameter_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into record_parameter (record_parameter_id, table_id, table_ref_id, parameter_name, "
			+ "model_column_ref_id, data_type_ref_id, parameter_seq, application_control_id, "
			+ "create_dt, create_user_id, update_dt, update_user_id, "
			+ "update_count, active_ind, system_assigned_version_nbr, record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(RecordParameterModel m) {
		Vector v = new Vector(16);
		v.add(new Integer(m.getRecordParameterId()));
		v.add(new Integer(m.getTableId()));
		v.add(new Integer(m.getTableRefId()));
		if (m.getParameterName() != null) v.add(m.getParameterName());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getModelColumnRefId()));
		v.add(new Integer(m.getDataTypeRefId()));
		v.add(new Integer(m.getParameterSeq()));
		v.add(new Integer(m.getApplicationControlId()));
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
		return "update record_parameter set  table_id = ?, table_ref_id = ?, parameter_name = ?, "
			+ "model_column_ref_id = ?, data_type_ref_id = ?, parameter_seq = ?, application_control_id = ?, "
			+ "create_dt = ?, create_user_id = ?, update_dt = ?, update_user_id = ?, "
			+ "update_count = ?, active_ind = ?, system_assigned_version_nbr = ?, record_type_ref_id = ? "
			+ " where record_parameter_id = ?";				 
	}
	
	private Vector getUpdateParameters(RecordParameterModel m) {
		Vector v = new Vector(16);
		v.add(new Integer(m.getTableId()));
		v.add(new Integer(m.getTableRefId()));
		if (m.getParameterName() != null) v.add(m.getParameterName());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getModelColumnRefId()));
		v.add(new Integer(m.getDataTypeRefId()));
		v.add(new Integer(m.getParameterSeq()));
		v.add(new Integer(m.getApplicationControlId()));
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
		v.add(new Integer(m.getRecordParameterId()));
		return v;
	}

	private Vector getDeleteParameters(RecordParameterModel m) {
		Vector v = new Vector(16);
		v.add(new Integer(m.getRecordParameterId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from record_parameter where record_parameter_id = ?";				 
	}

}
