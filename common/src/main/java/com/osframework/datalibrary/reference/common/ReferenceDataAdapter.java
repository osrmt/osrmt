package com.osframework.datalibrary.reference.common;

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.Db;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.reference.common.ReferenceList;

public class ReferenceDataAdapter extends ReferenceDbAdapter{ 
	
	public ReferenceDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	

	public ReferenceList getAllReference() throws DataAccessException {
		String sql = "select * from reference";
		return getReference(sql);
	}
	
	public ReferenceList getReferenceList(String group, boolean includeInactive) throws DataAccessException {
		String sql;
		if (includeInactive) {
			sql = "select * from reference where reference_group = ?";
		} else {
			sql = "select * from reference where reference_group = ?" +
			" and active_ind = 1";		
		}
		sql = sql + " order by app_type_ref_id, display_sequence, display";
		return getReference(sql, Db.getParameter(group));
	}
	
	public ReferenceList getReferenceDisplayListbyCode(String group, String code, boolean includeInactive) throws DataAccessException {
		String sql = "select ref_id, display from reference where reference_group = ? and display_code = ?";
		if (!includeInactive) {
			sql += " and active_ind = 1";		
		}
		sql = sql + " order by app_type_ref_id, display_sequence, display";
		return getReference(sql, Db.getParameter(group, code));
	}
	
	public ReferenceList getReferenceDisplayListbyDisplay(String group, String display, boolean includeInactive) throws DataAccessException {
		String sql = "select ref_id, display from reference where reference_group = ? and display = ?";
		if (!includeInactive) {
			sql += " and active_ind = 1";		
		}
		sql = sql + " order by app_type_ref_id, display_sequence, display";
		return getReference(sql, Db.getParameter(group, display));
	}
	
	public ReferenceList getProductList(int userId) throws Exception {
		String sql = "select r.ref_id, r.display "
			 + " from reference r, application_user_group aug, reference_tree rt"
			 + " where aug.user_id = ?"
			 + "   and aug.user_group_ref_id = rt.parent_table_key_id"
			 + "   and rt.table_key_id = r.ref_id";
		return getReference(sql, Db.getParameter(userId));
	}

	public ReferenceList getReferenceHistoryList() throws DataAccessException {
		String sql = "select * from reference where not reference_group in (" +
			"'ModelColumn','Controls','CustomControl','Action','SystemMessage','FormButtonText','Application',"
		+ "'ControlTemplate','FormTitle','TableName','ApplicationSetting')";
		return getReference(sql, new java.util.Vector());
	}
	
}