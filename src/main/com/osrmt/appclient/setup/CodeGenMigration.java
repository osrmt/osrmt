package com.osrmt.appclient.setup;

import java.util.*;
import java.sql.*;

import com.osframework.datalibrary.common.*;

public class CodeGenMigration {
	
	Access source = new Access();
	DbConnection sourceConn = null;
	Access destination = new Access();
	DbConnection destConn = null;
	
	public CodeGenMigration(ConnectionProperty src, ConnectionProperty dest) {
		try {
			source.connect(src);
			sourceConn = source.getDbConnection();
			SQLResult srcresult = source.executeQuery("select count(*) from REFS",sourceConn);
			srcresult.getRset().close();
			destination.connect(dest);
			destConn = destination.getDbConnection();
			SQLResult destresult = destination.executeQuery("select count(*) from REFS",destConn);
			destresult.getRset().close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			ConnectionList connections = ConnectionList.loadConnections();
			ConnectionProperty source = connections.getEnvironment("source");
			ConnectionProperty destination = connections.getEnvironment("destination");
			if (source == null || destination == null) {
				throw new Exception("connection.xml must contain source and destination environments");
			} else {
				CodeGenMigration migrate = new CodeGenMigration(source, destination);
				migrate.replaceReference();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void replaceReference() throws SQLException, AccessDataTypeException {
		try {
		cleanTable("REFS");
		moveTable("REFS");
		destConn.close();
		} catch (Exception ex) {
			if (destConn != null) {
				destConn.rollback();
			}
		}
	}
	
	private void cleanTable(String tablename) throws SQLException {
		String sql = "delete from " + tablename;
		destination.executeUpdate(sql, destConn);
	}
	
	private int moveTable(String tablename) throws SQLException, AccessDataTypeException {
		String sql = "select * from " + tablename;
		SQLResult result = source.executeQuery(sql, sourceConn);
		ResultSet rset = result.getRset();
		ResultSetMetaData meta = rset.getMetaData();
		String inssql = getInsertSql(meta, tablename);
		int colcount = meta.getColumnCount();
//		rset.close();
//		result = source.executeQuery("select t.* from " + tablename + " t", sourceConn);
		int count = 0;
		while (rset.next()) {
			Vector v = new Vector(colcount);
			for (int i=0; i<colcount; i++) {
				v.add(result.getRset().getObject(i+1));
			}
			destination.executeUpdate(inssql, v, destConn);
			count++;
		}
		rset.close();
		System.out.println(count + " rows replaced in " + tablename);
		return count;
	}
	
	private String getInsertSql(ResultSetMetaData meta, String tablename) throws SQLException {
		String sql = "insert into " + tablename + " (";
		for (int i=0; i<meta.getColumnCount(); i++) {
			String col = meta.getColumnName(i+1);
			if (i> 0) {
				sql += ",";
			}
			sql += col + " ";
		}
		sql += ") values (";
		for (int i=0; i<meta.getColumnCount(); i++) {
			if (i> 0) {
				sql += ",";
			}
			sql += "? ";
		}
		sql += ")";
		return sql;
	}

}
