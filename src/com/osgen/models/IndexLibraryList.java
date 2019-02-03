package models;

import java.util.*;
import java.sql.*;
import database.*;

public class IndexLibraryList implements  Enumeration {

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
	
	public void add(IndexLibraryModel tlm) {
		this.list.add(tlm);
	}
	
	public String getPrimaryKey(String tableName){
		Enumeration e1 = this.elements();
		while (e1.hasMoreElements()) {
			IndexLibraryModel ilm = (IndexLibraryModel) e1.nextElement();
			if (ilm.getTableName().compareTo(tableName)==0
					&& ilm.getPrimaryInd() == 1) {
				return ilm.getColumnName();
			}
		}
		return null;
	}
	
	public void load(Access access) throws SQLException, ClassNotFoundException {
		String sql = "select columnName, columnPosition, foreignInd, "
			+ "indexName, primaryInd, tableName, uniqueInd, "
			+ " version from IndexLibrary "
			+ " order by tableName, primaryInd desc, uniqueInd desc, indexName, columnPosition";
		ResultSet rset = access.executeQuery(sql);
		while (rset.next()) {
			IndexLibraryModel mlm = new IndexLibraryModel();
			mlm.setColumnName(rset.getString("columnName"));
			mlm.setColumnPosition(rset.getInt("columnPosition"));
			mlm.setForeignInd(rset.getInt("foreignInd"));
			mlm.setIndexName(rset.getString("indexName"));
			mlm.setPrimaryInd(rset.getInt("primaryInd"));
			mlm.setTableName(rset.getString("tableName"));
			mlm.setUniqueInd(rset.getInt("uniqueInd"));
			mlm.setVersion(rset.getString("version"));
			if (rset.wasNull()) mlm.setVersion(null);
			this.add(mlm);			
		}
		rset.close();
	}

}
