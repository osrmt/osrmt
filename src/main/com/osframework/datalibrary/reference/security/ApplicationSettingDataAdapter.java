//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.datalibrary.reference.security;

import java.sql.*;
import java.util.*;

import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.logging.*;
import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.ViewFramework;
import com.osframework.modellibrary.reference.security.*;

public class ApplicationSettingDataAdapter extends ApplicationSettingDbAdapter{ 

	public ApplicationSettingDataAdapter(IReferenceMap reference, ISecurity security) {
		super(reference, security);
	}	

	public ApplicationSettingList getApplicationSetting(int tableKeyId, int tableRefId, ApplicationFramework application, int appTypeRefId, ViewFramework view)  throws DataAccessException {
		String sql = "select a.*"
				+ " from application_setting a, application_view av"
				+ " where a.table_key_id = ?" 
				+ " and a.table_ref_id = ?"
				+ " and a.application_view_id = av.application_view_id"
				+ " and av.app_type_ref_id = ?"
				+ " and av.application_ref_id = ?"
				+ " and av.view_ref_id = ?"
				+ " and a.active_ind = 1";
		Vector params = new Vector(4);
		params.add(new Integer(tableKeyId));
		params.add(new Integer(tableRefId));
		params.add(new Integer(appTypeRefId));
		params.add(new Integer(application.getApplicationRefId()));
		params.add(new Integer(view.getViewRefId()));
		return getApplicationSetting(sql, params);		
	}
	
	public ApplicationSettingList getApplicationSetting(int settingRefId, int tableKeyId, int tableRefId, ApplicationFramework application, int appTypeRefId, ViewFramework view)  throws DataAccessException {
		String sql = "select a.*"
				+ " from application_setting a, application_view av"
				+ " where a.table_key_id = ?" 
				+ " and a.table_ref_id = ?"
				+ " and a.setting_ref_id = ?"
				+ " and a.application_view_id = av.application_view_id"
				+ " and av.app_type_ref_id = ?"
				+ " and av.application_ref_id = ?"
				+ " and av.view_ref_id = ?"
				+ " and a.active_ind = 1";
		
		Vector params = new Vector(4);
		params.add(new Integer(tableKeyId));
		params.add(new Integer(tableRefId));
		params.add(new Integer(settingRefId));
		params.add(new Integer(appTypeRefId));
		params.add(new Integer(application.getApplicationRefId()));
		params.add(new Integer(view.getViewRefId()));
		return getApplicationSetting(sql, params);		
	}
	
	public ApplicationSettingList getGlobalSettings() throws DataAccessException {
		String sql = "select * from application_setting where active_ind = 1";
		return getApplicationSetting(sql);
	}
}