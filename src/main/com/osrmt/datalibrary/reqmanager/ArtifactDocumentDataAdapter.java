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

*/package com.osrmt.datalibrary.reqmanager;

import java.sql.*;
import java.util.*;

import com.osrmt.modellibrary.reference.group.ArtifactLevelGroup;
import com.osrmt.modellibrary.reference.group.RelationGroup;
import com.osrmt.modellibrary.reqmanager.*;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;

/**
null
*/
public class ArtifactDocumentDataAdapter extends ArtifactDocumentDbAdapter{ 

	
	public ArtifactDocumentDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	
	

	/**
	 * Gets the partially populated child artifacts
	 * 
	 * @param parentTableRefId
	 * @param parentId
	 * @return
	 * @throws DataAccessException
	 */
	public ArtifactDocumentList getArtifactDocumentList (int artifactId) throws DataAccessException {
		String sql = "select a.* "
			+ " from artifact_document a"
			+ " where a.artifact_id = ?"
			+ " and a.active_ind = 1"
			+ " order by a.line_seq";
		Vector params = new Vector(4);
		params.add(new Integer(artifactId));
		return this.getArtifactDocument(sql, params);
	}	

}