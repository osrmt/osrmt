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

import com.osrmt.modellibrary.reqmanager.*;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;

/**
null
*/
public class BaselineDataAdapter extends BaselineDbAdapter{ 
	
	public BaselineDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	
	
	public BaselineModel getBaselineByName(String baselineName, int productRefId) throws DataAccessException {
		String sql = "select * from baseline"
			+ " where product_ref_id = ?"
			+ " and baseline_name = ?"
			+ " and active_ind = 1";			
		return getBaseline(sql, Db.getParameter(productRefId, baselineName)).getFirst();
	}
	
	public BaselineList getBaselines(int productRefId) throws DataAccessException {
		String sql = "select * from baseline"
			+ " where product_ref_id = ?"
			+ " and active_ind = 1";			
		return getBaseline(sql, Db.getParameter(productRefId));
	}
	
	public BaselineModel getLastBaseline(int productRefId, DbConnection conn) throws DataAccessException {
		String sql = "select count(baseline_id) from baseline"
			+ " where product_ref_id = ?"
			+ " and active_ind = 1";			
		Integer count = (Integer) Db.getSingleValue(sql, Db.getParameter(productRefId), conn);
		if (count.intValue() > 0) {
			sql = "select max(baseline_dt) from baseline"
				+ " where product_ref_id = ?"
				+ " and active_ind = 1";			
			GregorianCalendar baselineDateTime = (GregorianCalendar) Db.getSingleValue(sql, Db.getParameter(productRefId), conn);
			sql = "select * from baseline"
				+ " where product_ref_id = ?"
				+ " and baseline_dt = ?"
				+ " and active_ind = 1";			
			return getBaseline(sql, Db.getParameter(productRefId, baselineDateTime)).getFirst();
		} else {
			return new BaselineModel();
		}
	}
	
}