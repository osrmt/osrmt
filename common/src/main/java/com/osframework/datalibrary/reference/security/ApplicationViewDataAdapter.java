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

*/package com.osframework.datalibrary.reference.security;

import java.rmi.RemoteException;
import java.util.Enumeration;

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.DataUpdateException;
import com.osframework.datalibrary.common.Db;
import com.osframework.datalibrary.common.DbConnection;
import com.osframework.datalibrary.common.SQLResult;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.common.ReferenceDisplay;
import com.osframework.modellibrary.reference.common.ReferenceDisplayList;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.ArtifactFramework;
import com.osframework.modellibrary.reference.group.ViewFramework;
import com.osframework.modellibrary.reference.security.ApplicationViewList;
import com.osframework.modellibrary.reference.security.ApplicationViewModel;

/**
null
*/
public class ApplicationViewDataAdapter extends ApplicationViewDbAdapter{ 

	public ApplicationViewDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}

	public ReferenceDisplayList getViews(IReferenceMap reference, int appTypeRefId, ApplicationFramework application) throws DataAccessException, RemoteException {
		String sql = "select distinct r.display_sequence, a.view_ref_id"
			+ " from application_view a, reference r"
			+ " where a.app_type_ref_id = ? "
			+ " and a.application_ref_id = ? "
			+ " and a.view_ref_id = r.ref_id "
			+ "   and a.active_ind = 1"
			+ "   order by r.display_sequence, a.view_ref_id";
		ApplicationViewList acl = getApplicationView(sql, Db.getParameter(appTypeRefId, application.getApplicationRefId()));
		Enumeration e1 = acl.elements();
		ReferenceDisplayList list = new ReferenceDisplayList();
		while (e1.hasMoreElements()) {
			ApplicationViewModel acm = (ApplicationViewModel) e1.nextElement();
			ReferenceDisplay rd = reference.getReferenceDisplay(acm.getViewRefId());
			list.add(rd);
		}
		return list;
	}
	
	public ReferenceDisplayList getAppTypes(IReferenceMap reference, ApplicationFramework application) throws DataAccessException, RemoteException {
		String sql = "select distinct r.display_sequence, a.app_type_ref_id "
			+ " from application_view a, reference r "
			+ " where a.application_ref_id = ? "
			+ " and a.app_type_ref_id = r.ref_id "
			+ "   and a.active_ind = 1"
			+ "   order by r.display_sequence, a.app_type_ref_id";
		ApplicationViewList acl = getApplicationView(sql, Db.getParameter(application.getApplicationRefId()));
		Enumeration e1 = acl.elements();
		ReferenceDisplayList list = new ReferenceDisplayList();
		while (e1.hasMoreElements()) {
			ApplicationViewModel acm = (ApplicationViewModel) e1.nextElement();
			ReferenceDisplay rd = reference.getReferenceDisplay(acm.getAppTypeRefId());
			list.add(rd);
		}
		return list;
	}

	public ApplicationViewList getApplicationViewsByType() throws DataAccessException {
		String sql = "select av.application_ref_id, av.app_type_ref_id, av.view_ref_id, max(av.application_view_id) as application_view_id"
			+ " from application_control a, application_view av"
			+ " where a.active_ind = 1"
			+ " and a.application_view_id = av.application_view_id"
			+ " group by av.application_ref_id, av.app_type_ref_id, av.view_ref_id";
		return getApplicationView(sql);
	}

	public ApplicationViewList getApplicationViews(ArtifactFramework artifact, DbConnection conn) throws DataAccessException {
		String sql = "select a.* from application_view a"
			+ " where a.active_ind = 1"
			+ " and a.app_type_ref_id = ?";
		return getApplicationView(sql, Db.getParameter(artifact.getArtifactRefId()), conn);
	}

	public int deleteApplicationView(ArtifactFramework artifact, DbConnection conn) throws DataUpdateException {
		SQLResult result = null;
		try {
			int nbrRows = 0;
			if (artifact.getArtifactRefId() > 0) {
				nbrRows = Db.getAccess().executeUpdate("delete from application_view where app_type_ref_id = ?"
						, Db.getParameter(artifact.getArtifactRefId()), conn).getRowsUpdated();
			}
			return nbrRows;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw new DataUpdateException(ex);
		}
	}

	public ApplicationViewModel getApplicationView(ViewFramework view, int appTypeRefId, ApplicationFramework application) throws DataAccessException {
		String sql = "select a.* from application_view a"
			+ " where a.active_ind = 1"
			+ " and a.view_ref_id = ?"
			+ " and a.app_type_ref_id = ?"
		    + " and a.application_ref_id = ?";
		ApplicationViewList list = getApplicationView(sql, Db.getParameter(view.getViewRefId(), appTypeRefId, application.getApplicationRefId()));
		if (list.size() > 0) {
			return list.getFirst();
		} else {
			return new ApplicationViewModel();
		}
	}
	

}