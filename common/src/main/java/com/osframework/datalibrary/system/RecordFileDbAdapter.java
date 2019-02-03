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
import com.osframework.modellibrary.system.RecordFileList;
import com.osframework.modellibrary.system.RecordFileModel;

/**
null
*/
public class RecordFileDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public RecordFileDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateRecordFile(RecordFileModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateRecordFile(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateRecordFile(RecordFileModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		RecordFileModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getRecordFile(m.getRecordFileId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setRecordFileId(original.getRecordFileId());
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
	
	private UpdateResult save(RecordFileModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getRecordFileId()==0) {
				m.setRecordFileId(Db.getNextSequence(TableNameFramework.RECORDFILE, conn));
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
			return new UpdateResult(nbrRows,m.getRecordFileId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportRecordFile(RecordFileModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteRecordFile(RecordFileModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapRecordFile(ResultSet rset, RecordFileModel m) throws SQLException {
		if (columnExists(rset, "record_file_id")) {
			m.setRecordFileId(rset.getInt("record_file_id"));
			if (rset.wasNull()) m.setRecordFileId(0);
		}
		if (columnExists(rset, "table_id")) {
			m.setTableId(rset.getInt("table_id"));
			if (rset.wasNull()) m.setTableId(0);
		}
		if (columnExists(rset, "table_ref_id")) {
			m.setTableRefId(rset.getInt("table_ref_id"));
			if (rset.wasNull()) m.setTableRefId(0);
		}
		if (columnExists(rset, "file_type_ref_id")) {
			m.setFileTypeRefId(rset.getInt("file_type_ref_id"));
			if (rset.wasNull()) m.setFileTypeRefId(0);
		}
		if (columnExists(rset, "version")) {
			m.setVersion(rset.getDouble("version"));
			if (rset.wasNull()) m.setVersion(0);
		}
		if (columnExists(rset, "source_file")) {
			m.setSourceFile(rset.getString("source_file"));
			if (rset.wasNull()) m.setSourceFile(null);
		}
		if (columnExists(rset, "storage_file_name")) {
			m.setStorageFileName(rset.getString("storage_file_name"));
			if (rset.wasNull()) m.setStorageFileName(null);
		}
		if (columnExists(rset, "storage_directory")) {
			m.setStorageDirectory(rset.getString("storage_directory"));
			if (rset.wasNull()) m.setStorageDirectory(null);
		}
		if (columnExists(rset, "file_name")) {
			m.setFileName(rset.getString("file_name"));
			if (rset.wasNull()) m.setFileName(null);
		}
		if (columnExists(rset, "active_version_ind")) {
			m.setActiveVersionInd(rset.getInt("active_version_ind"));
			if (rset.wasNull()) m.setActiveVersionInd(0);
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
	public RecordFileList  getRecordFile(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getRecordFile(sql, conn);
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
	public RecordFileList  getRecordFile(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			RecordFileList list = new RecordFileList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				RecordFileModel m = new RecordFileModel();
				mapRecordFile(rset, m);
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
	public RecordFileList  getRecordFile(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getRecordFile(sql, params, conn);
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
	public RecordFileList  getRecordFile(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			RecordFileList list = new RecordFileList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				RecordFileModel m = new RecordFileModel();
				mapRecordFile(rset, m);
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
	public RecordFileModel getRecordFile(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getRecordFile(id, conn);
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
	public RecordFileModel getRecordFile(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			RecordFileModel m = new RecordFileModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapRecordFile(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "RecordFileModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public RecordFileList exportRecordFile() throws DataAccessException {
		int rows = Db.countTable("record_file");
		Debug.LogInfo(this, rows + " found in record_file");
		String sql = "select * from record_file where record_type_ref_id in (?, ?)";
		RecordFileList list = this.getRecordFile(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " record_file rows exported");
		return list;
	}

	public int importRecordFile(RecordFileList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			RecordFileModel m = (RecordFileModel) e1.nextElement();
			rows += ImportRecordFile(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select record_file_id, table_id, table_ref_id, file_type_ref_id, "
			+ "version, source_file, storage_file_name, storage_directory, "
			+ "file_name, active_version_ind, description, create_dt, "
			+ "create_user_id, update_dt, update_user_id, update_count, "
			+ "active_ind, system_assigned_version_nbr, record_type_ref_id"
			+ " from record_file "
			+ " where record_file_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into record_file (record_file_id, table_id, table_ref_id, file_type_ref_id, "
			+ "version, source_file, storage_file_name, storage_directory, "
			+ "file_name, active_version_ind, description, create_dt, "
			+ "create_user_id, update_dt, update_user_id, update_count, "
			+ "active_ind, system_assigned_version_nbr, record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(RecordFileModel m) {
		Vector v = new Vector(19);
		v.add(new Integer(m.getRecordFileId()));
		v.add(new Integer(m.getTableId()));
		v.add(new Integer(m.getTableRefId()));
		v.add(new Integer(m.getFileTypeRefId()));
		v.add(new Double(m.getVersion()));
		if (m.getSourceFile() != null) v.add(m.getSourceFile());
		else v.add(new Null(new String()));
		if (m.getStorageFileName() != null) v.add(m.getStorageFileName());
		else v.add(new Null(new String()));
		if (m.getStorageDirectory() != null) v.add(m.getStorageDirectory());
		else v.add(new Null(new String()));
		if (m.getFileName() != null) v.add(m.getFileName());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getActiveVersionInd()));
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
		return "update record_file set  table_id = ?, table_ref_id = ?, file_type_ref_id = ?, "
			+ "version = ?, source_file = ?, storage_file_name = ?, storage_directory = ?, "
			+ "file_name = ?, active_version_ind = ?, description = ?, create_dt = ?, "
			+ "create_user_id = ?, update_dt = ?, update_user_id = ?, update_count = ?, "
			+ "active_ind = ?, system_assigned_version_nbr = ?, record_type_ref_id = ? "
			+ " where record_file_id = ?";				 
	}
	
	private Vector getUpdateParameters(RecordFileModel m) {
		Vector v = new Vector(19);
		v.add(new Integer(m.getTableId()));
		v.add(new Integer(m.getTableRefId()));
		v.add(new Integer(m.getFileTypeRefId()));
		v.add(new Double(m.getVersion()));
		if (m.getSourceFile() != null) v.add(m.getSourceFile());
		else v.add(new Null(new String()));
		if (m.getStorageFileName() != null) v.add(m.getStorageFileName());
		else v.add(new Null(new String()));
		if (m.getStorageDirectory() != null) v.add(m.getStorageDirectory());
		else v.add(new Null(new String()));
		if (m.getFileName() != null) v.add(m.getFileName());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getActiveVersionInd()));
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
		v.add(new Integer(m.getRecordFileId()));
		return v;
	}

	private Vector getDeleteParameters(RecordFileModel m) {
		Vector v = new Vector(19);
		v.add(new Integer(m.getRecordFileId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from record_file where record_file_id = ?";				 
	}

}
