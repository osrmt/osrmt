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
package com.osframework.datalibrary.reportwriter;

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
import com.osframework.modellibrary.reportwriter.*;

/**
null
*/
public class ReportDbAdapter extends BaseAdapter {
	

	private IReferenceMap reference;
	private ISecurity security;

	public ReportDbAdapter(IReferenceMap reference, ISecurity security) {
		this.reference = reference;
		this.security = security;
	}


	public UpdateResult UpdateReport(ReportModel m, ServiceCall call) throws DataUpdateException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return UpdateReport(m, call, conn);
		} catch (Exception ex) {
			throw new DataUpdateException(ex);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public UpdateResult UpdateReport(ReportModel m, ServiceCall call, DbConnection conn) throws DataUpdateException {
		ReportModel original = null;
		try {
			if (m == null) {
				throw new NullArgumentException();
			}
			if (m.isNew()) {
				m.setCreateUserId(call.getUserId());
				m.setCreateDt(new GregorianCalendar());
			}
			if (m.hasModified()) {
				original = getReport(m.getReportId(), conn);
				m.setUpdateDt(new GregorianCalendar());
				m.setUpdateUserId(call.getUserId());
				m.setUpdateCount(original.getUpdateCount()+1);
				m.setSystemAssignedVersionNbr(1);
				m.copyModifiedTo(original);
				UpdateResult result = save(original, conn);
				m.setReportId(original.getReportId());
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
	
	private UpdateResult save(ReportModel m, DbConnection conn) throws SQLException, AccessDataTypeException {
		int nbrRows = 0;
		String sql = "";
		SQLResult result = null;
		Vector params = null;
		try {
			if (m.getReportId()==0) {
				m.setReportId(Db.getNextSequence(TableNameFramework.REPORT, conn));
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
			return new UpdateResult(nbrRows,m.getReportId());
		} catch (SQLException ex) {
			Debug.LogError(ex.getMessage(), Db.getFormattedException(ex, sql, params));
			throw ex;
		}
	}
		
	protected int ImportReport(ReportModel m) throws SQLException, AccessDataTypeException {
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
		
	public int DeleteReport(ReportModel m, DbConnection conn) throws DataUpdateException {
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
	public void mapReport(ResultSet rset, ReportModel m) throws SQLException {
		if (columnExists(rset, "report_id")) {
			m.setReportId(rset.getInt("report_id"));
			if (rset.wasNull()) m.setReportId(0);
		}
		if (columnExists(rset, "report_ref_id")) {
			m.setReportRefId(rset.getInt("report_ref_id"));
			if (rset.wasNull()) m.setReportRefId(0);
		}
		if (columnExists(rset, "report_sql")) {
			m.setReportSql(rset.getString("report_sql"));
			if (rset.wasNull()) m.setReportSql(null);
		}
		if (columnExists(rset, "parameter_view_ref_id")) {
			m.setParameterViewRefId(rset.getInt("parameter_view_ref_id"));
			if (rset.wasNull()) m.setParameterViewRefId(0);
		}
		if (columnExists(rset, "sql_parameter_script")) {
			m.setSqlParameterScript(rset.getString("sql_parameter_script"));
			if (rset.wasNull()) m.setSqlParameterScript(null);
		}
		if (columnExists(rset, "xml_report_ind")) {
			m.setXmlReportInd(rset.getInt("xml_report_ind"));
			if (rset.wasNull()) m.setXmlReportInd(0);
		}
		if (columnExists(rset, "xml_select_script")) {
			m.setXmlSelectScript(rset.getString("xml_select_script"));
			if (rset.wasNull()) m.setXmlSelectScript(null);
		}
		if (columnExists(rset, "xml_sort_script")) {
			m.setXmlSortScript(rset.getString("xml_sort_script"));
			if (rset.wasNull()) m.setXmlSortScript(null);
		}
		if (columnExists(rset, "xml_field_script")) {
			m.setXmlFieldScript(rset.getString("xml_field_script"));
			if (rset.wasNull()) m.setXmlFieldScript(null);
		}
		if (columnExists(rset, "xml_image_qual")) {
			m.setXmlImageQual(rset.getString("xml_image_qual"));
			if (rset.wasNull()) m.setXmlImageQual(null);
		}
		if (columnExists(rset, "xml_xpath")) {
			m.setXmlXpath(rset.getString("xml_xpath"));
			if (rset.wasNull()) m.setXmlXpath(null);
		}
		if (columnExists(rset, "report_outline_script")) {
			m.setReportOutlineScript(rset.getString("report_outline_script"));
			if (rset.wasNull()) m.setReportOutlineScript(null);
		}
		if (columnExists(rset, "report_outline_sql")) {
			m.setReportOutlineSql(rset.getString("report_outline_sql"));
			if (rset.wasNull()) m.setReportOutlineSql(null);
		}
		if (columnExists(rset, "outline_last_run_dt")) {
			m.setOutlineLastRunDt(Db.getCalendarDate(rset.getTimestamp("outline_last_run_dt")));
			if (rset.wasNull()) m.setOutlineLastRunDt(null);
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
	public ReportList  getReport(String sql) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getReport(sql, conn);
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
	public ReportList  getReport(String sql, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ReportList list = new ReportList();
			result = Db.getAccess().executeQuery(sql, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ReportModel m = new ReportModel();
				mapReport(rset, m);
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
	public ReportList  getReport(String sql, Vector params) throws DataAccessException {
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
		return getReport(sql, params, conn);
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
	public ReportList  getReport(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			ReportList list = new ReportList();
			result = Db.getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				ReportModel m = new ReportModel();
				mapReport(rset, m);
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
	public ReportModel getReport(int id) throws DataAccessException { 
		DbConnection conn = null;
		try {
			conn = Db.getConnection();
			return getReport(id, conn);
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
	public ReportModel getReport(int id, DbConnection conn) throws DataAccessException { 
		SQLResult result = null;
		try {
			ReportModel m = new ReportModel();
			// TODO This fails if you actually wanted to get the zero row
			if (id > 0) {
				result = Db.getAccess().executeQuery(getSelectSql(), Db.getParameter(id), conn);
				ResultSet rset = result.getRset();
				if (rset.next()) {
					mapReport(rset, m);
					m.resetModified();
				}
			}
			m.setReferenceDisplay(reference, security);
			return m;
		} catch (Exception ex) {
			Debug.LogException(this, ex, "ReportModel" + id);
			throw new DataAccessException(ex);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
	}

	public ReportList exportReport() throws DataAccessException {
		int rows = Db.countTable("report");
		Debug.LogInfo(this, rows + " found in report");
		String sql = "select * from report where record_type_ref_id in (?, ?)";
		ReportList list = this.getReport(sql, Db.getParameter(RecordTypeFramework.SYSTEMREFERENCE, RecordTypeFramework.USERREFERENCE)); 
		Debug.LogInfo(this,list.size()  + " report rows exported");
		return list;
	}

	public int importReport(ReportList list) throws Exception {
		Enumeration e1 = list.elements();
		int rows = 0;
		while (e1.hasMoreElements()) {
			ReportModel m = (ReportModel) e1.nextElement();
			rows += ImportReport(m);
		}
		Debug.LogInfo(this, rows + " reference_group rows imported");
		return rows;
	}

	private String getSelectSql() {
		return "select report_id, report_ref_id, report_sql, parameter_view_ref_id, "
			+ "sql_parameter_script, xml_report_ind, xml_select_script, xml_sort_script, "
			+ "xml_field_script, xml_image_qual, xml_xpath, report_outline_script, "
			+ "report_outline_sql, outline_last_run_dt, create_dt, create_user_id, "
			+ "update_dt, update_user_id, update_count, active_ind, "
			+ "system_assigned_version_nbr, record_type_ref_id"
			+ " from report "
			+ " where report_id = ?";				 
	}
	
	private String getInsertSql() {
		return "insert into report (report_id, report_ref_id, report_sql, parameter_view_ref_id, "
			+ "sql_parameter_script, xml_report_ind, xml_select_script, xml_sort_script, "
			+ "xml_field_script, xml_image_qual, xml_xpath, report_outline_script, "
			+ "report_outline_sql, outline_last_run_dt, create_dt, create_user_id, "
			+ "update_dt, update_user_id, update_count, active_ind, "
			+ "system_assigned_version_nbr, record_type_ref_id)"
			+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";				 
	}
	
	private Vector getInsertParameters(ReportModel m) {
		Vector v = new Vector(22);
		v.add(new Integer(m.getReportId()));
		v.add(new Integer(m.getReportRefId()));
		if (m.getReportSql() != null) v.add(m.getReportSql());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getParameterViewRefId()));
		if (m.getSqlParameterScript() != null) v.add(m.getSqlParameterScript());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getXmlReportInd()));
		if (m.getXmlSelectScript() != null) v.add(m.getXmlSelectScript());
		else v.add(new Null(new String()));
		if (m.getXmlSortScript() != null) v.add(m.getXmlSortScript());
		else v.add(new Null(new String()));
		if (m.getXmlFieldScript() != null) v.add(m.getXmlFieldScript());
		else v.add(new Null(new String()));
		if (m.getXmlImageQual() != null) v.add(m.getXmlImageQual());
		else v.add(new Null(new String()));
		if (m.getXmlXpath() != null) v.add(m.getXmlXpath());
		else v.add(new Null(new String()));
		if (m.getReportOutlineScript() != null) v.add(m.getReportOutlineScript());
		else v.add(new Null(new String()));
		if (m.getReportOutlineSql() != null) v.add(m.getReportOutlineSql());
		else v.add(new Null(new String()));
		if (m.getOutlineLastRunDt() != null) v.add(m.getOutlineLastRunDt());
		else v.add(new Null(new GregorianCalendar()));
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
		return "update report set  report_ref_id = ?, report_sql = ?, parameter_view_ref_id = ?, "
			+ "sql_parameter_script = ?, xml_report_ind = ?, xml_select_script = ?, xml_sort_script = ?, "
			+ "xml_field_script = ?, xml_image_qual = ?, xml_xpath = ?, report_outline_script = ?, "
			+ "report_outline_sql = ?, outline_last_run_dt = ?, create_dt = ?, create_user_id = ?, "
			+ "update_dt = ?, update_user_id = ?, update_count = ?, active_ind = ?, "
			+ "system_assigned_version_nbr = ?, record_type_ref_id = ? "
			+ " where report_id = ?";				 
	}
	
	private Vector getUpdateParameters(ReportModel m) {
		Vector v = new Vector(22);
		v.add(new Integer(m.getReportRefId()));
		if (m.getReportSql() != null) v.add(m.getReportSql());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getParameterViewRefId()));
		if (m.getSqlParameterScript() != null) v.add(m.getSqlParameterScript());
		else v.add(new Null(new String()));
		v.add(new Integer(m.getXmlReportInd()));
		if (m.getXmlSelectScript() != null) v.add(m.getXmlSelectScript());
		else v.add(new Null(new String()));
		if (m.getXmlSortScript() != null) v.add(m.getXmlSortScript());
		else v.add(new Null(new String()));
		if (m.getXmlFieldScript() != null) v.add(m.getXmlFieldScript());
		else v.add(new Null(new String()));
		if (m.getXmlImageQual() != null) v.add(m.getXmlImageQual());
		else v.add(new Null(new String()));
		if (m.getXmlXpath() != null) v.add(m.getXmlXpath());
		else v.add(new Null(new String()));
		if (m.getReportOutlineScript() != null) v.add(m.getReportOutlineScript());
		else v.add(new Null(new String()));
		if (m.getReportOutlineSql() != null) v.add(m.getReportOutlineSql());
		else v.add(new Null(new String()));
		if (m.getOutlineLastRunDt() != null) v.add(m.getOutlineLastRunDt());
		else v.add(new Null(new GregorianCalendar()));
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
		v.add(new Integer(m.getReportId()));
		return v;
	}

	private Vector getDeleteParameters(ReportModel m) {
		Vector v = new Vector(22);
		v.add(new Integer(m.getReportId()));
		return v;
	}

	private String getDeleteSql() {
		return "delete from report where report_id = ?";				 
	}

}
