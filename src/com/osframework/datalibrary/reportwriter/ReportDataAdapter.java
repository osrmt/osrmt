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

*/package com.osframework.datalibrary.reportwriter;

import java.sql.*;
import java.util.*;

import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reportwriter.*;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.logging.Debug;

/**
null
*/
public class ReportDataAdapter extends ReportDbAdapter{ 
	

	public ReportDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	

	public ReportList getReports(int reportRefId) throws DataAccessException {
		String sql = "select * from report where "
			+ " report_ref_id = ? "
			+ " and active_ind = 1";
		return getReport(sql, Db.getParameter(reportRefId));
	}

	public ReportList getReports() throws DataAccessException {
		String sql = "select * from report where "
			+ " active_ind = 1";
		return getReport(sql);
	}

	/**
	 * Permanently delete a report
	 * 
	 * @param reportId
	 * @return
	 * @throws Exception
	 */
	public void deleteReport(int reportId) throws Exception {
		String sql = "delete from report where "
			+ " report_id = ? "; 
		Vector params = new Vector(1);
		params.add(new Integer(reportId));
		Db.getAccess().executeUpdate(sql, params);
		sql = "delete from record_file where "
			+ " table_id = ? "
			+ " and table_ref_id = ?";
		params = new Vector(2);
		params.add(new Integer(reportId));
		params.add(new Integer(com.osframework.modellibrary.reference.group.TableNameFramework.REPORT));
		Db.getAccess().executeUpdate(sql, params);
	}
	
	public boolean hasUpdateSince(DbCalendar sinceDate) throws Exception {
		String sql = "select count(*) as count from artifact where update_dt > ?";
		Vector params = new Vector(1);
		params.add(sinceDate);
		
		SQLResult result = null;
		int count = 0;
		try {
			result = Db.getAccess().executeQuery(sql, params);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				count = rset.getInt("count");
			}
		} catch (SQLException se) {
			Debug.LogError(this, Db.getFormattedException(se, sql));
			throw new DataAccessException(se);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
			return count > 0;
		}
	}

	public Map<Integer, Boolean> qualifyingArtifacts(String sql) throws Exception {		
		SQLResult result = null;
		Map<Integer, Boolean> map = new Hashtable<Integer, Boolean>(4096);
		int count = 0;
		try {
			result = Db.getAccess().executeQuery(sql);
			ResultSet rset = result.getRset();
			while (rset.next()) {
				int artifactId = rset.getInt("artifact_id");
				map.put(artifactId, new Boolean(true));
			}
		} catch (SQLException se) {
			Debug.LogError(this, Db.getFormattedException(se, sql));
			throw new DataAccessException(se);
		} finally {
			try { if (result!=null) result.closeStatements();} catch (Exception ex) {}
		}
		return map;
	}

}