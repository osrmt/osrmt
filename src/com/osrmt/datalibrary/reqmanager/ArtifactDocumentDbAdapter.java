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
public class ArtifactDocumentDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public ArtifactDocumentDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateArtifactDocument(ArtifactDocumentModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateArtifactDocument(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateArtifactDocument(ArtifactDocumentModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		ArtifactDocumentModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getArtifactDocument(m.getArtifactDocumentId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setArtifactDocumentId(original.getArtifactDocumentId());
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
	
	private UpdateResult save(ArtifactDocumentModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getArtifactDocumentId()==0) {
				m.setArtifactDocumentId(Db.getNextSequence(TableNameFramework.ARTIFACTDOCUMENT, conn));
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
			return new UpdateResult(nbrRows,m.getArtifactDocumentId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportArtifactDocument(ArtifactDocumentModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteArtifactDocument(ArtifactDocumentModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapArtifactDocument(ResultSet rset, ArtifactDocumentModel m) throws SQLException {
		if (columnExists(rset, "artifact_document_id")) {
			m.setArtifactDocumentId(rset.getInt("artifact_document_id"));
			if (rset.wasNull()) m.setArtifactDocumentId(0);
		}
		if (columnExists(rset, "artifact_id")) {
			m.setArtifactId(rset.getInt("artifact_id"));
			if (rset.wasNull()) m.setArtifactId(0);
		}
		if (columnExists(rset, "line_seq")) {
			m.setLineSeq(rset.getInt("line_seq"));
			if (rset.wasNull()) m.setLineSeq(0);
		}
		if (columnExists(rset, "line_text")) {
			m.setLineText(rset.getString("line_text"));
			if (rset.wasNull()) m.setLineText(null);
		}
		if (columnExists(rset, "image_record_file_id")) {
			m.setImageRecordFileId(rset.getInt("image_record_file_id"));
			if (rset.wasNull()) m.setImageRecordFileId(0);
		}
		if (columnExists(rset, "style_ref_id")) {
			m.setStyleRefId(rset.getInt("style_ref_id"));
			if (rset.wasNull()) m.setStyleRefId(0);
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
	public ArtifactDocumentList  getArtifactDocument(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getArtifactDocument(sql, conn);
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
	public ArtifactDocumentList  getArtifactDocument(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ArtifactDocumentList list = new ArtifactDocumentList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ArtifactDocumentModel m = new ArtifactDocumentModel();
				mapArtifactDocument(rset, m);
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
	public ArtifactDocumentList  getArtifactDocument(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getArtifactDocument(sql, params, conn);
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
	public ArtifactDocumentList  getArtifactDocument(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ArtifactDocumentList list = new ArtifactDocumentList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ArtifactDocumentModel m = new ArtifactDocumentModel();
				mapArtifactDocument(rset, m);
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
	public ArtifactDocumentModel getArtifactDocument(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getArtifactDocument(id, conn);
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
	public ArtifactDocumentModel getArtifactDocument(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			ArtifactDocumentModel m = new ArtifactDocumentModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapArtifactDocument(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "ArtifactDocumentModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public ArtifactDocumentList exportArtifactDocument() throws DataAccessException {
		int rows = Db.countTable("artifact_document");
		Debug.LogInfo(this, rows + " found in artifact_document");
		String sql = "select * from artifact_document where record_type_ref_id in (?, ?)";
		ArtifactDocumentList list = this.getArtifactDocument(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " artifact_document rows exported");
		return list;
	}

	public int importArtifactDocument(ArtifactDocumentList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			ArtifactDocumentModel m = (ArtifactDocumentModel) e1.nextElement();
			rows += ImportArtifactDocument(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select artifact_document_id, artifact_id, line_seq, line_text, "
			+ "image_record_file_id, style_ref_id, create_dt, create_user_id, "
			+ "update_dt, update_user_id, update_count, active_ind, "
			+ "system_assigned_version_nbr, record_type_ref_id"
			+ " from artifact_document "
			+ " where artifact_document_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into artifact_document (artifact_document_id, artifact_id, line_seq, line_text, "
			+ "image_record_file_id, style_ref_id, create_dt, create_user_id, "
			+ "update_dt, update_user_id, update_count, active_ind, "
			+ "system_assigned_version_nbr, record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(ArtifactDocumentModel m) {
		Vector v = new Vector(14);
		v.add(new Integer(m.getArtifactDocumentId()));
		v.add(new Integer(m.getArtifactId()));
		v.add(new Integer(m.getLineSeq()));
		if (m.getLineText() != null) v.add(m.getLineText());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getImageRecordFileId()));
		v.add(new Integer(m.getStyleRefId()));
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
		return "update artifact_document set  artifact_id = ?, line_seq = ?, line_text = ?, "
			+ "image_record_file_id = ?, style_ref_id = ?, create_dt = ?, create_user_id = ?, "
			+ "update_dt = ?, update_user_id = ?, update_count = ?, active_ind = ?, "
			+ "system_assigned_version_nbr = ?, record_type_ref_id = ? "
			+ " where artifact_document_id = ?";				 
	}
	
	private Vector getUpdateParameters(ArtifactDocumentModel m) {
		Vector v = new Vector(14);
		v.add(new Integer(m.getArtifactId()));
		v.add(new Integer(m.getLineSeq()));
		if (m.getLineText() != null) v.add(m.getLineText());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getImageRecordFileId()));
		v.add(new Integer(m.getStyleRefId()));
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
		v.add(new Integer(m.getArtifactDocumentId()));
		return v;
	}

	private Vector getDeleteParameters(ArtifactDocumentModel m) {
		Vector v = new Vector(14);
		v.add(new Integer(m.getArtifactDocumentId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from artifact_document where artifact_document_id = ?";				 
	}

}
