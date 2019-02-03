package com.osframework.datalibrary.reference.security;

import java.util.Enumeration;
import java.util.Vector;

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.DataUpdateException;
import com.osframework.datalibrary.common.Db;
import com.osframework.datalibrary.common.DbConnection;
import com.osframework.datalibrary.common.SQLResult;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.PositionFramework;
import com.osframework.modellibrary.reference.group.TableNameFramework;
import com.osframework.modellibrary.reference.group.ViewFramework;
import com.osframework.modellibrary.reference.security.ApplicationSecurityList;
import com.osframework.modellibrary.reference.security.ApplicationSecurityModel;
import com.osframework.modellibrary.reference.security.ApplicationUserModel;
import com.osframework.modellibrary.reference.security.ApplicationViewModel;

/**
null
*/
public class ApplicationSecurityDataAdapter extends ApplicationSecurityDbAdapter{ 

	private ApplicationViewDataAdapter avda;
	
	public ApplicationSecurityDataAdapter(IReferenceMap reference, ISecurity security, ApplicationViewDataAdapter avda) {
		super(reference, security);
		this.avda = avda;
	}	

	public ApplicationSecurityList getSecurity(int tableKeyId, int tableRefId, ApplicationFramework application)  throws DataAccessException {
		//TODO Dont select single fields on the data adapters
		//if they map to ModelLists which are hash tables - 
		//otherwise may overwrite an entry.  In fact get rid of hash tables
		String sql = "select a.*"
				+ " from application_security a, reference r2, reference r3, application_view av"
				+ " where a.table_key_id = ?" 
				+ " and a.table_ref_id = ?"
				+ " and a.application_view_id = av.application_view_id"
				+ " and av.application_ref_id = ?"
				+ " and a.active_ind = 1"
				+ " and av.view_ref_id = r2.ref_id"
				+ " and av.app_type_ref_id = r3.ref_id"
				+ " order by r3.display_sequence, r2.display_sequence";
		Vector params = new Vector(3);
		params.add(new Integer(tableKeyId));
		params.add(new Integer(tableRefId));
		params.add(new Integer(application.getApplicationRefId()));
		return addView(getApplicationSecurity(sql, params));		
	}
	
	
	public ApplicationSecurityList getSecurity(int tableKeyId, int tableRefId, int appTypeRefId, ApplicationFramework application)  throws DataAccessException {
		//TODO Dont select single fields on the data adapters
		//if they map to ModelLists which are hash tables - 
		//otherwise may overwrite an entry
		String sql = "select a.*"
				+ " from application_security a, reference r2, application_view av"
				+ " where a.table_key_id = ?" 
				+ " and a.table_ref_id = ?"
				+ " and a.application_view_id = av.application_view_id"
				+ " and av.app_type_ref_id = ?"
				+ " and av.application_ref_id = ?"
				+ " and a.active_ind = 1"
				+ " and av.view_ref_id = r2.ref_id"
				+ " order by r2.display_sequence";
		Vector params = new Vector(4);
		params.add(new Integer(tableKeyId));
		params.add(new Integer(tableRefId));
		params.add(new Integer(appTypeRefId));
		params.add(new Integer(application.getApplicationRefId()));
		return addView(getApplicationSecurity(sql, params));		
	}
	
	public ApplicationSecurityList getSecurity(int tableKeyId, int tableRefId, int appTypeRefId, ApplicationFramework application, ViewFramework view)  throws DataAccessException {
		//TODO Dont select single fields on the data adapters
		//if they map to ModelLists which are hash tables - 
		//otherwise may overwrite an entry
		String sql = "select a.*"
				+ " from application_security a, application_view av"
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
		return addView(getApplicationSecurity(sql, params));		
	}
	
	public ApplicationSecurityList getAppSecurity(ApplicationUserModel user) throws DataAccessException {
		String sql = "select a.*"
			+ " from application_security a"
			+ " where a.active_ind = 1"
			+ "   and a.table_ref_id = ?"
			+ "   and a.table_key_id = ?";
		return addView(getApplicationSecurity(sql, Db.getParameter(TableNameFramework.APPLICATIONUSER, user.getUserId())));
	}

	public ApplicationSecurityList getAppSecurity(PositionFramework position) throws DataAccessException {
		String sql = "select a.*"
			+ " from application_security a"
			+ " where a.active_ind = 1"
			+ "   and a.table_ref_id = ?"
			+ "   and a.table_key_id = ?";
		return addView(getApplicationSecurity(sql, Db.getParameter(TableNameFramework.REFERENCE, position.getPositionRefId())));
	}

	public ApplicationSecurityList getAppSecurityGlobal() throws DataAccessException {
		String sql = "select a.*"
			+ " from application_security a"
			+ " where a.active_ind = 1"
			+ "   and a.table_ref_id = ?"
			+ "   and a.table_key_id = 0";
		return addView(getApplicationSecurity(sql, Db.getParameter(TableNameFramework.APPLICATIONUSER)));
	}
	
	private ApplicationSecurityList addView(ApplicationSecurityList list) throws DataAccessException {
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel acm = (ApplicationSecurityModel) e1.nextElement();
			if (acm.getApplicationViewId() > 0) {
				ApplicationViewModel avm = avda.getApplicationView(acm.getApplicationViewId());
					acm.setApplicationRefId(avm.getApplicationRefId());
					acm.setApplicationRefDisplay(avm.getApplicationRefDisplay());
					acm.setAppTypeRefId(avm.getAppTypeRefId());
					acm.setAppTypeRefDisplay(avm.getAppTypeRefDisplay());
					acm.setViewRefId(avm.getViewRefId());
					acm.setViewRefDisplay(avm.getViewRefDisplay());
			}
		}
		return list;
	}
	
	public int deleteApplicationSecurity(PositionFramework position, DbConnection conn) throws DataUpdateException {
		SQLResult result = null;
		try {
			int nbrRows = 0;
			if (position.getPositionRefId() > 0) {
				nbrRows = Db.getAccess().executeUpdate("delete from application_security where table_key_id = ? and table_ref_id = ?"
						, Db.getParameter(position.getPositionRefId(), TableNameFramework.REFERENCE), conn).getRowsUpdated();
			}
			return nbrRows;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw new DataUpdateException(ex);
		}
	}

	public int addNewArtifact(String newArtifactName, int newArtifactRefId, int copiedArtifactRefId, DbConnection conn, ServiceCall call) throws Exception {
		int avbaseid = Db.getNextSequence(TableNameFramework.APPLICATIONVIEW, conn);
		String sql = "insert into application_view (application_view_id, application_ref_id, app_type_ref_id, view_ref_id, init_script, validation_script, create_user_id) " +
					" select application_view_id+" + avbaseid + " as application_view_id2, application_ref_id, " + newArtifactRefId + " as app_type_ref_id, " +
					" view_ref_id, init_script, validation_script, " +  call.getUserId() + " as create_user_id " +
					" from application_view where app_type_ref_id = " + copiedArtifactRefId + " and active_ind = 1";
		SQLResult result = Db.getAccess().executeUpdate(sql, conn);
		int rowsInserted = result.getRowsUpdated();
		
		int accbaseid = Db.getNextSequence(TableNameFramework.APPLICATIONCONTROL, conn);
		String sqlc = "insert into application_control (application_control_id, application_view_id, display_sequence, control_ref_id, "
		+ "control_type_ref_id, control_text, control_description, model_column_ref_id, "
		+ "application_custom_control_id, app_control_user_defined_id, control_format, source_ref_id, "
		+ "default_value, locked_ind, disabled_ind, required_ind, "
		+ "visible_ind, init_script, focus_lost_script, focus_gained_script, "
		+ "image_path, scrollpane_ind, grow_height, grow_width, "
		+ "unit_width, unit_height, create_user_id) "
		+ "select application_control_id+"+accbaseid+" as application_control_id2, application_view_id+" + avbaseid + " as application_view_id2, display_sequence, control_ref_id, "
		+ "control_type_ref_id, control_text, control_description, model_column_ref_id, "
		+ "application_custom_control_id, app_control_user_defined_id, control_format, source_ref_id, "
		+ "default_value, locked_ind, disabled_ind, required_ind, "
		+ "visible_ind, init_script, focus_lost_script, focus_gained_script, "
		+ "image_path, scrollpane_ind, grow_height, grow_width, "
		+ "unit_width, unit_height, " + call.getUserId() + " as create_user_id "
		+ "from application_control where active_ind = 1 and application_view_id in ( "
		+ "select application_view_id from application_view where app_type_ref_id = " + copiedArtifactRefId + ")";
		SQLResult result2 = Db.getAccess().executeUpdate(sqlc, conn);
		rowsInserted += result2.getRowsUpdated();
		
		int asbaseid = Db.getNextSequence(TableNameFramework.APPLICATIONSECURITY, conn);
		String sqls = "insert into application_security (application_security_id, table_key_id, table_ref_id, application_view_id, "
		+ "read_only_ind, create_user_id) "
		+ "select application_security_id+"+asbaseid+" as application_security_id2, table_key_id, table_ref_id, application_view_id+" + avbaseid + " as application_view_id2, "
		+ "read_only_ind, " + call.getUserId() + " as create_user_id " 
		+ "from application_security where active_ind = 1 and application_view_id in ( "
		+ " select application_view_id from application_view where app_type_ref_id = " + copiedArtifactRefId + ")";
		SQLResult result3 = Db.getAccess().executeUpdate(sqls, conn);
		rowsInserted += result3.getRowsUpdated();

		return rowsInserted;
	}
	
	
}