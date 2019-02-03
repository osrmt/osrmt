package models;

import java.util.*;
import java.sql.*;
import database.*;

public class EjbLibraryList implements  Enumeration {

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
	
	public void add(EjbLibraryModel tlm) {
		this.list.add(tlm);
	}
	
	
	public void load(Access access) throws SQLException, ClassNotFoundException {
		String sql = "select javaPackage, bean, overwriteBeanInd, tableName, beanType, "
			+ " description, version from EjbLibrary";
		ResultSet rset = access.executeQuery(sql);
		while (rset.next()) {
			EjbLibraryModel mlm = new EjbLibraryModel();
			mlm.setJavaPackage(rset.getString("javaPackage"));
			mlm.setBean(rset.getString("bean"));
			mlm.setBeanType(rset.getString("beanType"));
			mlm.setVersion(rset.getString("version"));
			mlm.setOverwriteBeanInd(rset.getInt("overwriteBeanInd"));
			if (rset.wasNull()) mlm.setVersion(null);
			mlm.setTableName(rset.getString("tableName"));
			if (rset.wasNull()) mlm.setTableName(null);
			mlm.setDescription(rset.getString("description"));
			if (rset.wasNull()) mlm.setDescription(null);
			this.add(mlm);			
		}
		rset.close();
	}

}
