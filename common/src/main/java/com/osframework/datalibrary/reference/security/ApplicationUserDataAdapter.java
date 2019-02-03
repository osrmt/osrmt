//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.datalibrary.reference.security;

import java.util.Enumeration;

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.Db;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.reference.security.ApplicationUserList;
import com.osframework.modellibrary.reference.security.ApplicationUserModel;

public class ApplicationUserDataAdapter extends ApplicationUserDbAdapter{ 
	
	public ApplicationUserDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}
	
	public ApplicationUserList getAllUsers() throws DataAccessException {
		String sql = "select * from application_user where user_id > 0";
		return this.getApplicationUser(sql);
	}
	
	public ApplicationUserList getApplicationUserByLastName(String lastName) throws DataAccessException {
		String sql = "select * from application_user where last_name like ?";
		return this.getApplicationUser(sql, Db.getParameter(lastName));
	}
	
	public ApplicationUserList getApplicationUserByFirstName(String firstName) throws DataAccessException {
		String sql = "select * from application_user where first_name like ?";
		return this.getApplicationUser(sql, Db.getParameter(firstName));
	}
	
	public ApplicationUserModel getByUsername(String userlogin) throws DataAccessException {
		String sql = "select * from application_user where user_login = ?";
		ApplicationUserList list = this.getApplicationUser(sql, Db.getParameter(userlogin));
		Enumeration e1 = list.elements();
		if (e1.hasMoreElements()) {
			return (ApplicationUserModel) e1.nextElement();	
		} else {
			return new ApplicationUserModel();
		}
		
	}
}