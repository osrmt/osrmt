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

*/package com.osrmt.datalibrary.reqmanager;

import java.sql.*;
import java.util.*;

import com.osrmt.modellibrary.reqmanager.*;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.common.ServiceCall;

/**
Artfiact History stores the image of a artifact prior to modification - along with the user/date who modified the record.  Additionally all baselines are stored.
*/
public class ArtifactHistoryDataAdapter extends ArtifactHistoryDbAdapter{ 

	public ArtifactHistoryDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	

	public ArtifactHistoryList getHistory(int artifactId) throws DataAccessException {
		String sql = "select * from artifact_history " 
			+ " where artifact_id = ? order by history_dt desc";
		return getArtifactHistory(sql, Db.getParameter(artifactId));
	}
}