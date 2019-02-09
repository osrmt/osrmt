package com.osframework.datalibrary.reference.common;

import java.sql.*;

import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.reference.common.*;

public class ReferenceTreeDataAdapter extends ReferenceTreeDbAdapter{ 

	public ReferenceTreeDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	
	
	/**
	 * Returns all of the reference parient-child relationships
	 * @return valid empty reference tree if not found
	 */
	public ReferenceTreeList getAllReference() throws DataAccessException {
		//TODO Note that you need the unique id to generate a proper
		// list for lists using hashtables
		String sql = "select * from reference_tree";
		return getReferenceTree(sql);
	}
	
	/**
	 * Returns all of the reference parient-child relationships
	 * @return valid empty reference tree if not found
	 */
	public ReferenceTreeList getReferenceChildren(int parentId, int tableRefId) throws DataAccessException {
		//TODO Note that you need the unique id to generate a proper
		// list for lists using hashtables
		String sql = "select * from reference_tree where parent_table_key_id = ? and parent_table_ref_id = ?";
		return getReferenceTree(sql, Db.getParameter(parentId, tableRefId));
	}
	
	/**
	 * Returns all of the reference parient-child relationships
	 * @return valid empty reference tree if not found
	 */
	public ReferenceTreeList getReferenceChildren(int parentId, int tableRefId, int relationshipRefId, int childTableRefId) throws DataAccessException {
		//TODO Note that you need the unique id to generate a proper
		// list for lists using hashtables
		String sql = "select * from reference_tree where parent_table_key_id = ? and parent_table_ref_id = ?"
			+ " and relationship_ref_id = ? and table_ref_id = ?";
		return getReferenceTree(sql, Db.getParameter(parentId, tableRefId, relationshipRefId, childTableRefId));
	}
	
}