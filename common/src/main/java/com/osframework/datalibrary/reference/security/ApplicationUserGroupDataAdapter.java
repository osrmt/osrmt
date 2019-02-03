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

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.Db;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.reference.security.ApplicationUserGroupList;

/**
null
*/
public class ApplicationUserGroupDataAdapter extends ApplicationUserGroupDbAdapter{ 

	public ApplicationUserGroupDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}
	
	public ApplicationUserGroupList getUserGroup(int userGroupRefId) throws DataAccessException {
		String sql = "select a.* from application_user_group a"
			+ " where a.active_ind = 1"
			+ " and a.user_group_ref_id = ?";
		return getApplicationUserGroup(sql, Db.getParameter(userGroupRefId));
	}

	public ApplicationUserGroupList getAllUserGroups() throws DataAccessException {
		String sql = "select * from application_user_group where application_user_group_id > 0";
		return this.getApplicationUserGroup(sql);
	}
	

}