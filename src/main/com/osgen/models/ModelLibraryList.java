package models;

import java.util.*;
import java.sql.*;
import database.*;

public class ModelLibraryList implements  Enumeration {

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
	
	public void add(ModelLibraryModel tlm) {
		this.list.add(tlm);
	}
	
	
	public void load(Access access) throws SQLException, ClassNotFoundException {
		String sql = "select javaPackage, tableInd, tableName, parentTable, "
			+ " setParentId, tableModelInd,listModelInd,description,extendVectorInd,"
			+ " userDefinedInd, version from ModelLibrary where activeind = 1";
		ResultSet rset = access.executeQuery(sql);
		while (rset.next()) {
			ModelLibraryModel mlm = new ModelLibraryModel();
			mlm.setJavaPackage(rset.getString("javaPackage"));
			mlm.setTableInd(rset.getInt("tableInd"));
			mlm.setTableName(rset.getString("tableName"));
			mlm.setVersion(rset.getString("version"));
			if (rset.wasNull()) mlm.setVersion(null);
			mlm.setParentTable(rset.getString("parentTable"));
			if (rset.wasNull()) mlm.setParentTable(null);
			mlm.setSetParentId(rset.getString("setParentId"));
			if (rset.wasNull()) mlm.setSetParentId(null);
			mlm.setTableModelInd(rset.getInt("tableModelInd"));
			mlm.setExtendVectorInd(rset.getInt("extendVectorInd"));
			mlm.setUserDefinedInd(0);
			mlm.setListModelInd(rset.getInt("listModelInd"));
			mlm.setDescription(rset.getString("description"));
			if (rset.wasNull()) mlm.setDescription(null);
			this.add(mlm);			
		}
		rset.close();
	}

}
