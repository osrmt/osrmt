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

*/package com.osframework.datalibrary.system;

import java.sql.*;
import java.util.*;

import com.osframework.modellibrary.system.*;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;

/**
null
*/
public class RecordParameterDataAdapter extends RecordParameterDbAdapter{ 

	public RecordParameterDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	

	public RecordParameterList getParameters(int tableRefId, int tableId) throws DataAccessException {
		String sql = "select * from record_parameter where "
			+ " table_ref_id = ? and table_id = ? "
			+ " and active_ind = 1";
		return getRecordParameter(sql, Db.getParameter(tableRefId, tableId));
	}

}