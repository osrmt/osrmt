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

import java.sql.*;
import java.util.*;

import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
/**
null
*/
public class AppControlTemplateDataAdapter extends AppControlTemplateDbAdapter{ 

	
	public AppControlTemplateDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}

	public AppControlTemplateModel getTemplateByRef(ReferenceDisplay controlTemplate) throws Exception {
		String sql = "select * from application_control_template where control_ref_id = ? ";
		return getAppControlTemplate(sql, Db.getParameter(controlTemplate.getRefId())).getFirst();
		
	}
}