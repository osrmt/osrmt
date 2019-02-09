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

*/
package com.osframework.modellibrary.reference.security;

import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ResultColumnList;


/**
null
*/
public class ApplicationUserGroupModel extends ApplicationUserGroupDataModel implements Comparable {

	private static final long serialVersionUID = 1L;
	
	private String userDisplay  = null;
	
	public static ResultColumnList getResultColumnList() {
		ResultColumnList columns = ApplicationUserGroupDataModel.getResultColumnList();
		columns.addColumn("userDisplay", String.class);
		return columns;
	}
	

	
	public ApplicationUserGroupModel() {

	}

	public int compareTo(Object m) {
		return 0;

	}
	
	/** 
	 * Flags this field as being modified
	 */ 
	@Override
	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
		try {
			super.setReferenceDisplay(reference, security);
			if (security != null) setUserDisplay(security.getUser(this.getUserId()).getUserLogin());
		} catch (Exception e) {
			Debug.LogException(this, e);
		}
	}	
	
	public String getUserDisplay() {
		return userDisplay;
	}

	public void setUserDisplay(String userDisplay) {
		this.userDisplay = userDisplay;
	}

	public Object getDataAt(int i) {
		switch (i) {
		case 16: return userDisplay;
		}
		return super.getDataAt(i);
	}
	
}