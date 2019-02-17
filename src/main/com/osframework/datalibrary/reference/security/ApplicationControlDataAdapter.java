package com.osframework.datalibrary.reference.security;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.*;

import com.osframework.modellibrary.reference.common.ReferenceDisplay;
import com.osframework.modellibrary.reference.common.ReferenceDisplayList;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.logging.Debug;

/**
null
*/
public class ApplicationControlDataAdapter extends ApplicationControlDbAdapter{ 

	public ApplicationControlDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}
	
	public ApplicationControlList getControlsByApplication(ViewFramework view, ApplicationFramework application) throws DataAccessException {
		String sql = "select a.* from application_control a, reference r, application_view av"
			+ " where av.view_ref_id = ?"
			+ "   and av.application_ref_id = ? "
			+ "   and av.application_view_id = a.application_view_id " 
			+ "   and a.active_ind = 1"
			+ "   and av.app_type_ref_id = r.ref_id "
			+ "   order by r.display_sequence, a.display_sequence";
		Vector params = new Vector(2);
		params.add(new Integer(view.getViewRefId()));
		params.add(new Integer(application.getApplicationRefId()));
		return getApplicationControl(sql, params);
	}

	
	public ApplicationControlList getControlsByApplication(ViewFramework view, int appTypeRefId, ApplicationFramework application) throws DataAccessException {
		String sql = "select ac.* from application_control ac, application_view av"
			+ " where av.view_ref_id = ?"
			+ "   and av.app_type_ref_id = ? "
			+ "   and av.application_ref_id = ? "
			+ "   and av.application_view_id = ac.application_view_id"
			+ "   and ac.active_ind = 1"
			+ "   order by ac.display_sequence";
		Vector params = new Vector(3);
		params.add(new Integer(view.getViewRefId()));
		params.add(new Integer(appTypeRefId));
		params.add(new Integer(application.getApplicationRefId()));
		return getApplicationControl(sql, params);
	}
	
	

	public ApplicationControlList getApplicationControls(ApplicationViewModel view) throws DataAccessException {
		String sql = "select a.* from application_control a"
			+ " where a.active_ind = 1"
			+ " and a.application_view_id = ?";
		return getApplicationControl(sql, Db.getParameter(view.getApplicationViewId()));
	}

	public int deleteApplicationControl(ApplicationViewModel view, DbConnection conn) throws DataUpdateException {
		SQLResult result = null;
		try {
			int nbrRows = 0;
			if (view.getApplicationViewId() > 0) {
				nbrRows = Db.getAccess().executeUpdate("delete from application_control where application_view_id = ?"
						, Db.getParameter(view.getApplicationViewId()), conn).getRowsUpdated();
			}
			return nbrRows;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw new DataUpdateException(ex);
		}
	}

	
	
}