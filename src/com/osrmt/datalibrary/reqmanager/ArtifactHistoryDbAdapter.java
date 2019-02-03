/*
    Artfiact History stores the image of a artifact prior to modification - along with the user/date who modified the record.  Additionally all baselines are stored.

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
Artfiact History stores the image of a artifact prior to modification - along with the user/date who modified the record.  Additionally all baselines are stored.
*/
public class ArtifactHistoryDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public ArtifactHistoryDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateArtifactHistory(ArtifactHistoryModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateArtifactHistory(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateArtifactHistory(ArtifactHistoryModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		ArtifactHistoryModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getArtifactHistory(m.getArtifactHistoryId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setArtifactHistoryId(original.getArtifactHistoryId());
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
	
	private UpdateResult save(ArtifactHistoryModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getArtifactHistoryId()==0) {
				m.setArtifactHistoryId(Db.getNextSequence(TableNameFramework.ARTIFACTHISTORY, conn));
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
			return new UpdateResult(nbrRows,m.getArtifactHistoryId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportArtifactHistory(ArtifactHistoryModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteArtifactHistory(ArtifactHistoryModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapArtifactHistory(ResultSet rset, ArtifactHistoryModel m) throws SQLException {
		if (columnExists(rset, "artifact_history_id")) {
			m.setArtifactHistoryId(rset.getInt("artifact_history_id"));
			if (rset.wasNull()) m.setArtifactHistoryId(0);
		}
		if (columnExists(rset, "artifact_id")) {
			m.setArtifactId(rset.getInt("artifact_id"));
			if (rset.wasNull()) m.setArtifactId(0);
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
		if (columnExists(rset, "product_ref_id")) {
			m.setProductRefId(rset.getInt("product_ref_id"));
			if (rset.wasNull()) m.setProductRefId(0);
		}
		if (columnExists(rset, "version_ref_id")) {
			m.setVersionRefId(rset.getInt("version_ref_id"));
			if (rset.wasNull()) m.setVersionRefId(0);
		}
		if (columnExists(rset, "artifact_ref_id")) {
			m.setArtifactRefId(rset.getInt("artifact_ref_id"));
			if (rset.wasNull()) m.setArtifactRefId(0);
		}
		if (columnExists(rset, "artifact_nbr")) {
			m.setArtifactNbr(rset.getInt("artifact_nbr"));
			if (rset.wasNull()) m.setArtifactNbr(0);
		}
		if (columnExists(rset, "artifact_seq")) {
			m.setArtifactSeq(rset.getInt("artifact_seq"));
			if (rset.wasNull()) m.setArtifactSeq(0);
		}
		if (columnExists(rset, "artifact_level_ref_id")) {
			m.setArtifactLevelRefId(rset.getInt("artifact_level_ref_id"));
			if (rset.wasNull()) m.setArtifactLevelRefId(0);
		}
		if (columnExists(rset, "component_type_ref_id")) {
			m.setComponentTypeRefId(rset.getInt("component_type_ref_id"));
			if (rset.wasNull()) m.setComponentTypeRefId(0);
		}
		if (columnExists(rset, "last_updated_baseline_id")) {
			m.setLastUpdatedBaselineId(rset.getInt("last_updated_baseline_id"));
			if (rset.wasNull()) m.setLastUpdatedBaselineId(0);
		}
		if (columnExists(rset, "artifact_name")) {
			m.setArtifactName(rset.getString("artifact_name"));
			if (rset.wasNull()) m.setArtifactName(null);
		}
		if (columnExists(rset, "description")) {
			m.setDescription(rset.getString("description"));
			if (rset.wasNull()) m.setDescription(null);
		}
		if (columnExists(rset, "status_ref_id")) {
			m.setStatusRefId(rset.getInt("status_ref_id"));
			if (rset.wasNull()) m.setStatusRefId(0);
		}
		if (columnExists(rset, "priority_ref_id")) {
			m.setPriorityRefId(rset.getInt("priority_ref_id"));
			if (rset.wasNull()) m.setPriorityRefId(0);
		}
		if (columnExists(rset, "complexity_ref_id")) {
			m.setComplexityRefId(rset.getInt("complexity_ref_id"));
			if (rset.wasNull()) m.setComplexityRefId(0);
		}
		if (columnExists(rset, "author_ref_id")) {
			m.setAuthorRefId(rset.getInt("author_ref_id"));
			if (rset.wasNull()) m.setAuthorRefId(0);
		}
		if (columnExists(rset, "assigned_ref_id")) {
			m.setAssignedRefId(rset.getInt("assigned_ref_id"));
			if (rset.wasNull()) m.setAssignedRefId(0);
		}
		if (columnExists(rset, "category_ref_id")) {
			m.setCategoryRefId(rset.getInt("category_ref_id"));
			if (rset.wasNull()) m.setCategoryRefId(0);
		}
		if (columnExists(rset, "assigned_user_id")) {
			m.setAssignedUserId(rset.getInt("assigned_user_id"));
			if (rset.wasNull()) m.setAssignedUserId(0);
		}
		if (columnExists(rset, "assigned_user_group_id")) {
			m.setAssignedUserGroupId(rset.getInt("assigned_user_group_id"));
			if (rset.wasNull()) m.setAssignedUserGroupId(0);
		}
		if (columnExists(rset, "effort")) {
			m.setEffort(rset.getDouble("effort"));
			if (rset.wasNull()) m.setEffort(0);
		}
		if (columnExists(rset, "rationale")) {
			m.setRationale(rset.getString("rationale"));
			if (rset.wasNull()) m.setRationale(null);
		}
		if (columnExists(rset, "origin")) {
			m.setOrigin(rset.getString("origin"));
			if (rset.wasNull()) m.setOrigin(null);
		}
		if (columnExists(rset, "goal")) {
			m.setGoal(rset.getString("goal"));
			if (rset.wasNull()) m.setGoal(null);
		}
		if (columnExists(rset, "context")) {
			m.setContext(rset.getString("context"));
			if (rset.wasNull()) m.setContext(null);
		}
		if (columnExists(rset, "precondition")) {
			m.setPrecondition(rset.getString("precondition"));
			if (rset.wasNull()) m.setPrecondition(null);
		}
		if (columnExists(rset, "postcondition")) {
			m.setPostcondition(rset.getString("postcondition"));
			if (rset.wasNull()) m.setPostcondition(null);
		}
		if (columnExists(rset, "summary")) {
			m.setSummary(rset.getString("summary"));
			if (rset.wasNull()) m.setSummary(null);
		}
		if (columnExists(rset, "external_references")) {
			m.setExternalReferences(rset.getString("external_references"));
			if (rset.wasNull()) m.setExternalReferences(null);
		}
		if (columnExists(rset, "planned_version_ref_id")) {
			m.setPlannedVersionRefId(rset.getInt("planned_version_ref_id"));
			if (rset.wasNull()) m.setPlannedVersionRefId(0);
		}
		if (columnExists(rset, "last_modified_version_ref_id")) {
			m.setLastModifiedVersionRefId(rset.getInt("last_modified_version_ref_id"));
			if (rset.wasNull()) m.setLastModifiedVersionRefId(0);
		}
		if (columnExists(rset, "removed_ind")) {
			m.setRemovedInd(rset.getInt("removed_ind"));
			if (rset.wasNull()) m.setRemovedInd(0);
		}
		if (columnExists(rset, "module_ref_id")) {
			m.setModuleRefId(rset.getInt("module_ref_id"));
			if (rset.wasNull()) m.setModuleRefId(0);
		}
		if (columnExists(rset, "origin_category_ref_id")) {
			m.setOriginCategoryRefId(rset.getInt("origin_category_ref_id"));
			if (rset.wasNull()) m.setOriginCategoryRefId(0);
		}
		if (columnExists(rset, "change_reason")) {
			m.setChangeReason(rset.getString("change_reason"));
			if (rset.wasNull()) m.setChangeReason(null);
		}
		if (columnExists(rset, "change_made")) {
			m.setChangeMade(rset.getString("change_made"));
			if (rset.wasNull()) m.setChangeMade(null);
		}
		if (columnExists(rset, "report_sequence")) {
			m.setReportSequence(rset.getInt("report_sequence"));
			if (rset.wasNull()) m.setReportSequence(0);
		}
		if (columnExists(rset, "report_outline")) {
			m.setReportOutline(rset.getString("report_outline"));
			if (rset.wasNull()) m.setReportOutline(null);
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
		if (columnExists(rset, "custom_text1")) {
			m.setCustomText1(rset.getString("custom_text1"));
			if (rset.wasNull()) m.setCustomText1(null);
		}
		if (columnExists(rset, "custom_text2")) {
			m.setCustomText2(rset.getString("custom_text2"));
			if (rset.wasNull()) m.setCustomText2(null);
		}
		if (columnExists(rset, "custom_text3")) {
			m.setCustomText3(rset.getString("custom_text3"));
			if (rset.wasNull()) m.setCustomText3(null);
		}
		if (columnExists(rset, "custom_text4")) {
			m.setCustomText4(rset.getString("custom_text4"));
			if (rset.wasNull()) m.setCustomText4(null);
		}
		if (columnExists(rset, "custom1_ref_id")) {
			m.setCustom1RefId(rset.getInt("custom1_ref_id"));
			if (rset.wasNull()) m.setCustom1RefId(0);
		}
		if (columnExists(rset, "custom2_ref_id")) {
			m.setCustom2RefId(rset.getInt("custom2_ref_id"));
			if (rset.wasNull()) m.setCustom2RefId(0);
		}
		if (columnExists(rset, "custom3_ref_id")) {
			m.setCustom3RefId(rset.getInt("custom3_ref_id"));
			if (rset.wasNull()) m.setCustom3RefId(0);
		}
		if (columnExists(rset, "custom4_ref_id")) {
			m.setCustom4RefId(rset.getInt("custom4_ref_id"));
			if (rset.wasNull()) m.setCustom4RefId(0);
		}
		if (columnExists(rset, "custom_date1")) {
			m.setCustomDate1(Db.getCalendarDate(rset.getTimestamp("custom_date1")));
			if (rset.wasNull()) m.setCustomDate1(null);
		}
		if (columnExists(rset, "custom_date2")) {
			m.setCustomDate2(Db.getCalendarDate(rset.getTimestamp("custom_date2")));
			if (rset.wasNull()) m.setCustomDate2(null);
		}
		if (columnExists(rset, "custom_memo1")) {
			m.setCustomMemo1(rset.getString("custom_memo1"));
			if (rset.wasNull()) m.setCustomMemo1(null);
		}
		if (columnExists(rset, "custom_memo2")) {
			m.setCustomMemo2(rset.getString("custom_memo2"));
			if (rset.wasNull()) m.setCustomMemo2(null);
		}
		if (columnExists(rset, "custom_int1")) {
			m.setCustomInt1(rset.getInt("custom_int1"));
			if (rset.wasNull()) m.setCustomInt1(0);
		}
		if (columnExists(rset, "custom_int2")) {
			m.setCustomInt2(rset.getInt("custom_int2"));
			if (rset.wasNull()) m.setCustomInt2(0);
		}
		if (columnExists(rset, "custom_double1")) {
			m.setCustomDouble1(rset.getDouble("custom_double1"));
			if (rset.wasNull()) m.setCustomDouble1(0);
		}
		m.resetModified();
	}

 /**
  * Execute the SQL and return a list for the result set
  */
	public ArtifactHistoryList  getArtifactHistory(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getArtifactHistory(sql, conn);
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
	public ArtifactHistoryList  getArtifactHistory(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ArtifactHistoryList list = new ArtifactHistoryList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ArtifactHistoryModel m = new ArtifactHistoryModel();
				mapArtifactHistory(rset, m);
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
	public ArtifactHistoryList  getArtifactHistory(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getArtifactHistory(sql, params, conn);
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
	public ArtifactHistoryList  getArtifactHistory(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ArtifactHistoryList list = new ArtifactHistoryList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ArtifactHistoryModel m = new ArtifactHistoryModel();
				mapArtifactHistory(rset, m);
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
	public ArtifactHistoryModel getArtifactHistory(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getArtifactHistory(id, conn);
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
	public ArtifactHistoryModel getArtifactHistory(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			ArtifactHistoryModel m = new ArtifactHistoryModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapArtifactHistory(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "ArtifactHistoryModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public ArtifactHistoryList exportArtifactHistory() throws DataAccessException {
		int rows = Db.countTable("artifact_history");
		Debug.LogInfo(this, rows + " found in artifact_history");
		String sql = "select * from artifact_history where record_type_ref_id in (?, ?)";
		ArtifactHistoryList list = this.getArtifactHistory(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " artifact_history rows exported");
		return list;
	}

	public int importArtifactHistory(ArtifactHistoryList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			ArtifactHistoryModel m = (ArtifactHistoryModel) e1.nextElement();
			rows += ImportArtifactHistory(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select artifact_history_id, artifact_id, history_dt, history_user_id, "
			+ "baseline_id, product_ref_id, version_ref_id, artifact_ref_id, "
			+ "artifact_nbr, artifact_seq, artifact_level_ref_id, component_type_ref_id, "
			+ "last_updated_baseline_id, artifact_name, description, status_ref_id, "
			+ "priority_ref_id, complexity_ref_id, author_ref_id, assigned_ref_id, "
			+ "category_ref_id, assigned_user_id, assigned_user_group_id, effort, "
			+ "rationale, origin, goal, context, "
			+ "precondition, postcondition, summary, external_references, "
			+ "planned_version_ref_id, last_modified_version_ref_id, removed_ind, module_ref_id, "
			+ "origin_category_ref_id, change_reason, change_made, report_sequence, "
			+ "report_outline, create_dt, create_user_id, update_dt, "
			+ "update_user_id, update_count, active_ind, system_assigned_version_nbr, "
			+ "record_type_ref_id, custom_text1, custom_text2, custom_text3, "
			+ "custom_text4, custom1_ref_id, custom2_ref_id, custom3_ref_id, "
			+ "custom4_ref_id, custom_date1, custom_date2, custom_memo1, "
			+ "custom_memo2, custom_int1, custom_int2, custom_double1"
			+ " from artifact_history "
			+ " where artifact_history_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into artifact_history (artifact_history_id, artifact_id, history_dt, history_user_id, "
			+ "baseline_id, product_ref_id, version_ref_id, artifact_ref_id, "
			+ "artifact_nbr, artifact_seq, artifact_level_ref_id, component_type_ref_id, "
			+ "last_updated_baseline_id, artifact_name, description, status_ref_id, "
			+ "priority_ref_id, complexity_ref_id, author_ref_id, assigned_ref_id, "
			+ "category_ref_id, assigned_user_id, assigned_user_group_id, effort, "
			+ "rationale, origin, goal, context, "
			+ "precondition, postcondition, summary, external_references, "
			+ "planned_version_ref_id, last_modified_version_ref_id, removed_ind, module_ref_id, "
			+ "origin_category_ref_id, change_reason, change_made, report_sequence, "
			+ "report_outline, create_dt, create_user_id, update_dt, "
			+ "update_user_id, update_count, active_ind, system_assigned_version_nbr, "
			+ "record_type_ref_id, custom_text1, custom_text2, custom_text3, "
			+ "custom_text4, custom1_ref_id, custom2_ref_id, custom3_ref_id, "
			+ "custom4_ref_id, custom_date1, custom_date2, custom_memo1, "
			+ "custom_memo2, custom_int1, custom_int2, custom_double1)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(ArtifactHistoryModel m) {
		Vector v = new Vector(64);
		v.add(new Integer(m.getArtifactHistoryId()));
		v.add(new Integer(m.getArtifactId()));
		if (m.getHistoryDt() != null) v.add(m.getHistoryDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getHistoryUserId()));
		v.add(new Integer(m.getBaselineId()));
		v.add(new Integer(m.getProductRefId()));
		v.add(new Integer(m.getVersionRefId()));
		v.add(new Integer(m.getArtifactRefId()));
		v.add(new Integer(m.getArtifactNbr()));
		v.add(new Integer(m.getArtifactSeq()));
		v.add(new Integer(m.getArtifactLevelRefId()));
		v.add(new Integer(m.getComponentTypeRefId()));
		v.add(new Integer(m.getLastUpdatedBaselineId()));
		if (m.getArtifactName() != null) v.add(m.getArtifactName());
		else v.add(new Null(new String()));
		if (m.getDescription() != null) v.add(m.getDescription());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getStatusRefId()));
		v.add(new Integer(m.getPriorityRefId()));
		v.add(new Integer(m.getComplexityRefId()));
		v.add(new Integer(m.getAuthorRefId()));
		v.add(new Integer(m.getAssignedRefId()));
		v.add(new Integer(m.getCategoryRefId()));
		v.add(new Integer(m.getAssignedUserId()));
		v.add(new Integer(m.getAssignedUserGroupId()));
		v.add(new Double(m.getEffort()));
		if (m.getRationale() != null) v.add(m.getRationale());
		else v.add(new Null(new String()));
		if (m.getOrigin() != null) v.add(m.getOrigin());
		else v.add(new Null(new String()));
		if (m.getGoal() != null) v.add(m.getGoal());
		else v.add(new Null(new String()));
		if (m.getContext() != null) v.add(m.getContext());
		else v.add(new Null(new String()));
		if (m.getPrecondition() != null) v.add(m.getPrecondition());
		else v.add(new Null(new String()));
		if (m.getPostcondition() != null) v.add(m.getPostcondition());
		else v.add(new Null(new String()));
		if (m.getSummary() != null) v.add(m.getSummary());
		else v.add(new Null(new String()));
		if (m.getExternalReferences() != null) v.add(m.getExternalReferences());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getPlannedVersionRefId()));
		v.add(new Integer(m.getLastModifiedVersionRefId()));
		v.add(new Integer(m.getRemovedInd()));
		v.add(new Integer(m.getModuleRefId()));
		v.add(new Integer(m.getOriginCategoryRefId()));
		if (m.getChangeReason() != null) v.add(m.getChangeReason());
		else v.add(new Null(new String()));
		if (m.getChangeMade() != null) v.add(m.getChangeMade());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getReportSequence()));
		if (m.getReportOutline() != null) v.add(m.getReportOutline());
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
		if (m.getCustomText1() != null) v.add(m.getCustomText1());
		else v.add(new Null(new String()));
		if (m.getCustomText2() != null) v.add(m.getCustomText2());
		else v.add(new Null(new String()));
		if (m.getCustomText3() != null) v.add(m.getCustomText3());
		else v.add(new Null(new String()));
		if (m.getCustomText4() != null) v.add(m.getCustomText4());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getCustom1RefId()));
		v.add(new Integer(m.getCustom2RefId()));
		v.add(new Integer(m.getCustom3RefId()));
		v.add(new Integer(m.getCustom4RefId()));
		if (m.getCustomDate1() != null) v.add(m.getCustomDate1());
		else v.add(new Null(new GregorianCalendar()));
		if (m.getCustomDate2() != null) v.add(m.getCustomDate2());
		else v.add(new Null(new GregorianCalendar()));
		if (m.getCustomMemo1() != null) v.add(m.getCustomMemo1());
		else v.add(new Null(new String()));
		if (m.getCustomMemo2() != null) v.add(m.getCustomMemo2());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getCustomInt1()));
		v.add(new Integer(m.getCustomInt2()));
		v.add(new Double(m.getCustomDouble1()));
		return v;
	}
	
	private String getUpdateSql() {
		return "update artifact_history set  artifact_id = ?, history_dt = ?, history_user_id = ?, "
			+ "baseline_id = ?, product_ref_id = ?, version_ref_id = ?, artifact_ref_id = ?, "
			+ "artifact_nbr = ?, artifact_seq = ?, artifact_level_ref_id = ?, component_type_ref_id = ?, "
			+ "last_updated_baseline_id = ?, artifact_name = ?, description = ?, status_ref_id = ?, "
			+ "priority_ref_id = ?, complexity_ref_id = ?, author_ref_id = ?, assigned_ref_id = ?, "
			+ "category_ref_id = ?, assigned_user_id = ?, assigned_user_group_id = ?, effort = ?, "
			+ "rationale = ?, origin = ?, goal = ?, context = ?, "
			+ "precondition = ?, postcondition = ?, summary = ?, external_references = ?, "
			+ "planned_version_ref_id = ?, last_modified_version_ref_id = ?, removed_ind = ?, module_ref_id = ?, "
			+ "origin_category_ref_id = ?, change_reason = ?, change_made = ?, report_sequence = ?, "
			+ "report_outline = ?, create_dt = ?, create_user_id = ?, update_dt = ?, "
			+ "update_user_id = ?, update_count = ?, active_ind = ?, system_assigned_version_nbr = ?, "
			+ "record_type_ref_id = ?, custom_text1 = ?, custom_text2 = ?, custom_text3 = ?, "
			+ "custom_text4 = ?, custom1_ref_id = ?, custom2_ref_id = ?, custom3_ref_id = ?, "
			+ "custom4_ref_id = ?, custom_date1 = ?, custom_date2 = ?, custom_memo1 = ?, "
			+ "custom_memo2 = ?, custom_int1 = ?, custom_int2 = ?, custom_double1 = ? "
			+ " where artifact_history_id = ?";				 
	}
	
	private Vector getUpdateParameters(ArtifactHistoryModel m) {
		Vector v = new Vector(64);
		v.add(new Integer(m.getArtifactId()));
		if (m.getHistoryDt() != null) v.add(m.getHistoryDt());
		else v.add(new Null(new GregorianCalendar()));
		v.add(new Integer(m.getHistoryUserId()));
		v.add(new Integer(m.getBaselineId()));
		v.add(new Integer(m.getProductRefId()));
		v.add(new Integer(m.getVersionRefId()));
		v.add(new Integer(m.getArtifactRefId()));
		v.add(new Integer(m.getArtifactNbr()));
		v.add(new Integer(m.getArtifactSeq()));
		v.add(new Integer(m.getArtifactLevelRefId()));
		v.add(new Integer(m.getComponentTypeRefId()));
		v.add(new Integer(m.getLastUpdatedBaselineId()));
		if (m.getArtifactName() != null) v.add(m.getArtifactName());
		else v.add(new Null(new String()));
		if (m.getDescription() != null) v.add(m.getDescription());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getStatusRefId()));
		v.add(new Integer(m.getPriorityRefId()));
		v.add(new Integer(m.getComplexityRefId()));
		v.add(new Integer(m.getAuthorRefId()));
		v.add(new Integer(m.getAssignedRefId()));
		v.add(new Integer(m.getCategoryRefId()));
		v.add(new Integer(m.getAssignedUserId()));
		v.add(new Integer(m.getAssignedUserGroupId()));
		v.add(new Double(m.getEffort()));
		if (m.getRationale() != null) v.add(m.getRationale());
		else v.add(new Null(new String()));
		if (m.getOrigin() != null) v.add(m.getOrigin());
		else v.add(new Null(new String()));
		if (m.getGoal() != null) v.add(m.getGoal());
		else v.add(new Null(new String()));
		if (m.getContext() != null) v.add(m.getContext());
		else v.add(new Null(new String()));
		if (m.getPrecondition() != null) v.add(m.getPrecondition());
		else v.add(new Null(new String()));
		if (m.getPostcondition() != null) v.add(m.getPostcondition());
		else v.add(new Null(new String()));
		if (m.getSummary() != null) v.add(m.getSummary());
		else v.add(new Null(new String()));
		if (m.getExternalReferences() != null) v.add(m.getExternalReferences());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getPlannedVersionRefId()));
		v.add(new Integer(m.getLastModifiedVersionRefId()));
		v.add(new Integer(m.getRemovedInd()));
		v.add(new Integer(m.getModuleRefId()));
		v.add(new Integer(m.getOriginCategoryRefId()));
		if (m.getChangeReason() != null) v.add(m.getChangeReason());
		else v.add(new Null(new String()));
		if (m.getChangeMade() != null) v.add(m.getChangeMade());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getReportSequence()));
		if (m.getReportOutline() != null) v.add(m.getReportOutline());
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
		if (m.getCustomText1() != null) v.add(m.getCustomText1());
		else v.add(new Null(new String()));
		if (m.getCustomText2() != null) v.add(m.getCustomText2());
		else v.add(new Null(new String()));
		if (m.getCustomText3() != null) v.add(m.getCustomText3());
		else v.add(new Null(new String()));
		if (m.getCustomText4() != null) v.add(m.getCustomText4());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getCustom1RefId()));
		v.add(new Integer(m.getCustom2RefId()));
		v.add(new Integer(m.getCustom3RefId()));
		v.add(new Integer(m.getCustom4RefId()));
		if (m.getCustomDate1() != null) v.add(m.getCustomDate1());
		else v.add(new Null(new GregorianCalendar()));
		if (m.getCustomDate2() != null) v.add(m.getCustomDate2());
		else v.add(new Null(new GregorianCalendar()));
		if (m.getCustomMemo1() != null) v.add(m.getCustomMemo1());
		else v.add(new Null(new String()));
		if (m.getCustomMemo2() != null) v.add(m.getCustomMemo2());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getCustomInt1()));
		v.add(new Integer(m.getCustomInt2()));
		v.add(new Double(m.getCustomDouble1()));
		v.add(new Integer(m.getArtifactHistoryId()));
		return v;
	}

	private Vector getDeleteParameters(ArtifactHistoryModel m) {
		Vector v = new Vector(64);
		v.add(new Integer(m.getArtifactHistoryId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from artifact_history where artifact_history_id = ?";				 
	}

}
