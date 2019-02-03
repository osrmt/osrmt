package models;

import java.util.*;
import java.sql.*;
import database.*;

public class TableLibraryList implements  Enumeration {

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
	
	public void add(TableLibraryModel tlm) {
		this.list.add(tlm);
	}
	
	
	public void load(Access access) throws SQLException, ClassNotFoundException {
		String sql = "select columnName, columnSize, comments, dbType, "
			+ " defaultValue, javaType, requiredInd, tableInd, tableName, requirementNumber,"
			+ " version from tablelibrary";
		ResultSet rset = access.executeQuery(sql);
		while (rset.next()) {
			TableLibraryModel tlm = new TableLibraryModel();
			tlm.setColumnName(rset.getString("columnName"));
			tlm.setColumnSize(rset.getInt("columnSize"));
			tlm.setComments(rset.getString("comments"));
			if (rset.wasNull()) tlm.setComments("");
			tlm.setRequirementNumber(rset.getString("requirementNumber"));
			if (rset.wasNull()) tlm.setRequirementNumber("");
			tlm.setDbType(rset.getString("dbType"));
			tlm.setDefaultValue(rset.getString("defaultValue"));
			if (rset.wasNull()) tlm.setDefaultValue(null);
			tlm.setJavaType(rset.getString("javaType"));
			tlm.setRequiredInd(rset.getInt("requiredInd"));
			tlm.setTableInd(rset.getInt("tableInd"));
			tlm.setTableName(rset.getString("tableName"));
			tlm.setVersion(rset.getString("version"));
			if (rset.wasNull()) tlm.setVersion(null);
			this.add(tlm);			
		}
		rset.close();
	}

}
