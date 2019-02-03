package com.osframework.datalibrary.system;

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.Db;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.reference.group.TableNameFramework;
import com.osframework.modellibrary.system.RecordFileList;

/**
null
*/
public class RecordFileDataAdapter extends RecordFileDbAdapter{ 

	public RecordFileDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	

	public RecordFileList getActiveFileList(int tableRefId, int tableId) throws DataAccessException {
		String sql = "select * from record_file where "
			+ " table_ref_id = ? and table_id = ? and active_version_ind = 1"
			+ " and active_ind = 1";
		return getRecordFile(sql, Db.getParameter(tableRefId, tableId));
	}
	
	public long countAllArtifacts(int productRefId) throws Exception {
		String sql = "select count(*) from artifact"
			+ " where active_ind = 1"
			+ " and product_ref_id = ?";
		return  Integer.parseInt("" + Db.getSingleValue(sql, Db.getParameter(productRefId)));
	}
	
	public RecordFileList getAllFiles(int productRefId) throws Exception {
		String sql = "select r.* from record_file r"
			+ " where exists (select a.artifact_id from artifact a"
		    + "   where a.active_ind = 1"
			+ "   and a.product_ref_id = ?"
			+ "   and r.table_id = a.artifact_id"
			+ "   and r.table_ref_id = " + TableNameFramework.ARTIFACT
		    + "   and r.active_ind = 1"
			+ " )";
		
		RecordFileList files = super.getRecordFile(sql, Db.getParameter(productRefId));
		return files;
	}
}