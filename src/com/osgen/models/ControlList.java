package models;

import java.util.*;
import java.sql.*;
import database.*;

public class ControlList implements  Enumeration {

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
	
	public void add(ControlModel bmm) {
		this.list.add(bmm);
	}
	
	public String getFirstModel(String appName) {
		Enumeration e1 = this.elements();
		Hashtable models = new Hashtable();
		String model = "";
		while (e1.hasMoreElements()) {
			ControlModel cm = (ControlModel) e1.nextElement();
			if (cm.getAppName().compareTo(appName)==0) {
				model = cm.getRootModel();
				break;
			}
		}
		return model;
	}
	
	public String getFirstContainer(String appName) {
		Enumeration e1 = this.elements();
		Hashtable models = new Hashtable();
		String container = "";
		while (e1.hasMoreElements()) {
			ControlModel cm = (ControlModel) e1.nextElement();
			if (cm.getAppName().compareTo(appName)==0) {
				container = cm.getContainer();
				break;
			}
		}
		return container;
	}
	
	public String getFirstVariable(String appName) {
		if (getFirstModel(appName).length() > 1) {
			String modelName =getFirstModel(appName).replace("Model",""); 
			return modelName.substring(0,1).toLowerCase() 
			+ modelName.substring(1);
		} else {
			return "m";
		}
	}
	
	public String getModelVariables(String appName) {
		Enumeration e1 = this.elements();
		Hashtable models = new Hashtable();
		while (e1.hasMoreElements()) {
			ControlModel cm = (ControlModel) e1.nextElement();
			if (cm.getAppName().compareTo(appName)==0) {
				if (!models.containsKey(cm.getRootModel())) {
					models.put(cm.getRootModel(),cm.getRootModel());
				}
			}
		}
		Enumeration e2 = models.elements();
		StringBuffer sb = new StringBuffer();
		while (e2.hasMoreElements()) {
			String model = (String) e2.nextElement();
			if (model != null && model.length() > 1) {
				String modelVar = model.substring(0,1).toLowerCase() + model.replace("Model","").substring(1);
				sb.append("	private " + model + " " + modelVar + ";\r\n");
				sb.append("	public " + model + " get" + modelVar.substring(0,1).toUpperCase()
							+ modelVar.substring(1) + "() {\r\n		return "+ modelVar + ";"
							+ "\r\n	}\r\n\r\n");
			}
		}
		return sb.toString();
	}
	
	public void load(Access access) throws SQLException, ClassNotFoundException {
		String sql = "select controlName, rootModel,getter, setter, " +
		" uiControl, appName, listgroup, container,referenceSearch, javaDataType,"
			+ " version from Controllibrary";
		ResultSet rset = access.executeQuery(sql);
		while (rset.next()) {
			ControlModel mlm = new ControlModel();
			mlm.setControlName(rset.getString("controlname"));
			mlm.setRootModel(rset.getString("rootModel"));
			mlm.setGetter(rset.getString("getter"));
			mlm.setSetter(rset.getString("setter"));
			mlm.setUiControl(rset.getString("uicontrol"));
			mlm.setAppName(rset.getString("appName"));
			mlm.setListGroup(rset.getString("listgroup"));
			mlm.setJavaDataType(rset.getString("javadatatype"));
			if (rset.wasNull())	mlm.setListGroup(null);
			mlm.setReferenceSearch(rset.getString("referenceSearch"));
			if (rset.wasNull())	mlm.setReferenceSearch(null);
			mlm.setVersion(rset.getString("version"));
			if (rset.wasNull())	mlm.setVersion(null);
			mlm.setContainer(rset.getString("container"));
			this.add(mlm);			
		}
		rset.close();
	}

}
