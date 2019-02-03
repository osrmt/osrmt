//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.datalibrary.reference.common;

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.reference.common.ReferenceGroupList;

public class ReferenceGroupDataAdapter extends ReferenceGroupDbAdapter{ 
	
	public ReferenceGroupDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	
	
	public ReferenceGroupList getReferenceGroups() throws DataAccessException {
		String sql = "select * from reference_group"
			 + " where active_ind = 1"
			 + " order by reference_group";
		return this.getReferenceGroup(sql);
	}


}