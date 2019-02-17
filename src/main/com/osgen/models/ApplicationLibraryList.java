package models;

import java.util.*;
import java.sql.*;
import database.*;

public class ApplicationLibraryList implements  Enumeration {

	private Vector list = new Vector(1000);
	private Enumeration enumeration;
	
	public Enumeration elements() {
		enumeration = list.elements();
		return enumeration;
	}
	
	public Object nextElement() {
		return enumeration.nextElement();
	}
	
	public boolean hasMoreElements() {
		return enumeration.hasMoreElements();
	}
	
	public void add(ApplicationModel bmm) {
		this.list.add(bmm);
	}
	
	public void load(Access access) throws SQLException, ClassNotFoundException {
		String sql = "select appname, javapackage,modelImport,template,description,"
			+ " controlpanel,securityRefInd,tabsInd,JPanelInd, dialogInd, "
			+ " userDefinedModel,formActionsInd, controlWidth, version from applicationlibrary";
		ResultSet rset = access.executeQuery(sql);
		while (rset.next()) {
			ApplicationModel mlm = new ApplicationModel();
			mlm.setAppName(rset.getString("appname"));
			mlm.setJavaPackage(rset.getString("javapackage"));
			mlm.setControlPanel(rset.getString("controlpanel"));
			mlm.setVersion(rset.getString("version"));
			if (rset.wasNull()) mlm.setVersion(null);
			mlm.setModelImport(rset.getString("modelImport"));
			if (rset.wasNull()) mlm.setModelImport(null);
			mlm.setUserDefinedModel(rset.getString("userDefinedModel"));
			if (rset.wasNull()) mlm.setUserDefinedModel(null);
			mlm.setTemplate(rset.getString("template"));
			mlm.setTabsInd(rset.getInt("tabsInd"));
			mlm.setDescription(rset.getString("description"));
			if (rset.wasNull()) mlm.setDescription(null);
			mlm.setSecurityRefInd(rset.getInt("securityRefInd"));
			mlm.setFormActionsInd(rset.getInt("formActionsInd"));
			mlm.setJPanelInd(rset.getInt("JPanelInd"));
			mlm.setDialogInd(rset.getInt("DialogInd"));
			mlm.setControlWidth(rset.getInt("controlWidth"));
			this.add(mlm);			
		}
		rset.close();
	}

}
