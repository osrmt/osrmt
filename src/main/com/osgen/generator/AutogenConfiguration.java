package generator;

import java.util.*;
import java.sql.*;

import models.AutogenModel;
import models.TableLibraryModel;

import utilities.FileSystemUtil;
import database.Access;

public class AutogenConfiguration {

	private MainGenerator maingen = null;

	public AutogenConfiguration(MainGenerator m) {
		super();
		this.maingen = m;
	}
	
	public void CreateSearchColumns() throws Exception {
		
		String sql = "select *"
			+ " from autogen";

		ResultSet rset = Db.access.executeQuery(sql);
		while (rset.next()) {
			String tableName = rset.getString("table_name").toLowerCase();
			String applicationName = rset.getString("application_name");
			int searchColumnInd = rset.getInt("search_column_ind");
			int controlLibraryInd = rset.getInt("control_library_ind");
			AutogenModel m = new AutogenModel();
			m.setApplicationName(applicationName);
			m.setTableName(tableName);
			m.setSearchColumnsOnly(controlLibraryInd==0);
			if (searchColumnInd == 1) {
				int ref_id = MainGenerator.getSequence("REF_ID","REFERENCE");
				UpdateReferenceApplication(m, ref_id);
				UpdateApplicationSecurity(m, MainGenerator.getSequence("APPLICATION_SECURITY_ID","APPLICATION_SECURITY"), ref_id);
				UpdateApplicationControl(m, ref_id, ref_id+1);
				UpdateReferenceControls(m, ref_id+1);
			}
			if (controlLibraryInd == 1) {
				int ref_id = MainGenerator.getSequence("REF_ID","REFERENCE");
				UpdateControlLibrary(m);
			}
		}
		rset.close();
		sql = "update autogen set search_column_ind = 0, control_library_ind = 0 where autogen_id = 3";
		Db.access.executeUpdate(sql);
	}
	
	private void UpdateControlLibrary(AutogenModel m) throws Exception {
		int seq = 0;
		String sql = "insert into controllibrary"
			+ " (appname, controlname, rootmodel, getter, setter, javadatatype, uicontrol) "
			+ " values (?, ?, ?, ?, ?, ?, ?)";
		Enumeration e1 = maingen.getTableLibraryList().elements();
		while (e1.hasMoreElements()) {
			TableLibraryModel t = (TableLibraryModel) e1.nextElement();
			if (t.getTableName().compareTo(m.getTableName())==0) {				
				Vector params = new Vector();
				params.add(m.getApplicationName());
				params.add(t.getJavaFieldName().substring(0,1).toUpperCase()
						+ t.getJavaFieldName().substring(1));
				params.add(m.getModelName());
				params.add(m.getObjectName() + ".get" + t.getJavaName() + "()");
				if (t.getJavaType().endsWith("Calendar")) {
					params.add(m.getObjectName() + ".set" + t.getJavaName() + "(CalendarUtility.Parse(p, CalendarUtility.ShortDateFormat()));");
				} else {
					params.add(m.getObjectName() + ".set" + t.getJavaName() + "(p);");
				}
				params.add(t.getJavaType().replace("GregorianCalendar","DbCalendar"));
				params.add(m.getControl(t.getJavaType()));
				Db.access.executeUpdate(sql, params);
				seq++;
			}
		}
	}
	
	private void UpdateApplicationSecurity(AutogenModel m, int as_id, int ref_id) throws Exception  {
		String sql = "insert into application_security "
			+ " (application_security_id, table_key_id, table_ref_id, application_ref_id)"
			+ " values (?, ?, ?, ?)";
		Vector params = new Vector();
		params.add(new Integer(as_id));
		params.add(new Integer(1));
		params.add(new Integer(getTableRef("APPLICATIONUSER")));
		params.add(new Integer(ref_id));
		Db.access.executeUpdate(sql, params);
	}
	
	private int getTableRef(String tableName) throws Exception {
		String sql = "select ref_id from reference where reference_group = 'TableName' and ucase(display_code) = '"
			+ tableName + "'";
		ResultSet rset = Db.access.executeQuery(sql);
		int nbr =0;
		if (rset.next()) {
			nbr = rset.getInt(1);
		}
		rset.close();
		return nbr;
	}
	
	private void UpdateReferenceControls(AutogenModel m, int ref_id) throws Exception {
		int seq = 0;
		Enumeration e1 = maingen.getTableLibraryList().elements();
		while (e1.hasMoreElements()) {
			TableLibraryModel t = (TableLibraryModel) e1.nextElement();
			if (t.getTableName().compareTo(m.getTableName())==0) {
				Vector params = new Vector();
				params.add(new Integer(ref_id+seq));
				params.add("Controls");
				params.add(m.getApplicationKey());
				params.add(new Integer(seq));
				if (m.isSearchColumnsOnly()) {
					params.add(t.getJavaFieldName().substring(0,1).toLowerCase()
						+ t.getJavaFieldName().substring(1));
				} else {
					params.add(t.getJavaFieldName().substring(0,1).toUpperCase()
							+ t.getJavaFieldName().substring(1));
				}
				Db.access.executeUpdate(MainGenerator.getInsertReference(), params);
				seq++;
			}
		}
	}
	
	private void UpdateApplicationControl(AutogenModel m, int app_id, int ref_id) throws Exception {
		int seq = 0;
		String sql = "insert into application_control"
			+ " (application_ref_id, display_sequence, control_ref_id, control_text) "
			+ " values (?, ?, ?, ?)";
		Enumeration e1 = maingen.getTableLibraryList().elements();
		while (e1.hasMoreElements()) {
			TableLibraryModel t = (TableLibraryModel) e1.nextElement(); 
			if (t.getTableName().compareTo(m.getTableName())==0) {				
				Vector params = new Vector();
				params.add(new Integer(app_id));
				params.add(new Integer(seq));
				params.add(new Integer(ref_id+seq));
				params.add(t.getJavaFieldName());
				Db.access.executeUpdate(sql, params);
				seq++;
			}
		}
	}
	
	private void UpdateReferenceApplication(AutogenModel m, int ref_id) throws Exception {
		Vector params = new Vector();
		params.add(new Integer(ref_id));
		params.add("Application");
		params.add(m.getApplicationKey());
		params.add(new Integer(0));
		params.add(m.getApplicationName());
		Db.access.executeUpdate(MainGenerator.getInsertReference(), params);
		
	}
	
}
