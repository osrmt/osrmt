package com.osrmt.appclient.setup;

import java.util.*;
import java.sql.*;

import com.osframework.datalibrary.common.*;

public class DatabaseMigration  extends ConsoleManager {
	
	JDBCAccess source = null;
	JDBCAccess destination = null;
	
	public DatabaseMigration(ConnectionProperty src, ConnectionProperty dest) {
		try {
			source = new JDBCAccess(src);
			destination = new JDBCAccess(dest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		try {
			ConnectionList.setConfigFileName("migration.xml");			
			ConnectionList connections = ConnectionList.loadConnections();
			ConnectionProperty source = connections.getEnvironment("source");
			ConnectionProperty destination = connections.getEnvironment("destination");
			if (source == null || destination == null) {
				throw new Exception("connection.xml must contain source and destination environments");
			} else {
				DatabaseMigration migrate = new DatabaseMigration(source, destination);
				migrate.replaceReference(getTableList(args[1]));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Vector getTableList(String tables) {
		Vector v = new Vector();
		String[] list = tables.split(",");
		for (int i=0;i<list.length; i++) {
			String s = (String) list[i];
			v.add(s);
		}
		return v;
	}
	
	public void replaceReference(Vector tables) throws SQLException, AccessDataTypeException {
		try {
			destination.conn.setAutoCommit(false);
			checkTables();
			Enumeration e1 = tables.elements();
			while (e1.hasMoreElements()) {
				String tablename = (String) e1.nextElement();
				try {
					countTable(tablename);
					moveTable(tablename);
				} catch (SkipSourceTableException  sste) {
				}
			}
			destination.conn.commit();
			destination.conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (destination.conn != null) {
				destination.conn.rollback();
			}
		}
	}
	
	private void checkTables() throws Exception {
		String sql = "select count(*) from reference";
		SQLResult result = destination.executeQuery(sql);
		if (result.getRset().next()) {
			int count = result.getRset().getInt(1);	
			if (count > 0) {
				throw new Exception ("destination database is not empty");
			}
		} else {
			result.getRset().close();
		}
	}
	
	private void countTable(String tablename) throws SkipSourceTableException {
		try {
			String sql = "select count(*) from " + tablename;
			SQLResult result = source.executeQuery(sql);
			result.getRset().close();
		} catch (Exception ex) {
			throw new SkipSourceTableException();
		}
	}
	
	private int moveTable(String tablename) throws SQLException, AccessDataTypeException {
		String sql = "select * from " + tablename;
		SQLResult result = source.executeQuery(sql);
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
				Object o = rset.getObject(i+1);
				if (o == null) {
					if (meta.getColumnClassName(i+1).equals(String.class.getName())) {
						o = new DataNull(new String());
					} else if (meta.getColumnClassName(i+1).equals(Integer.class.getName())) {
							o = new DataNull(new Integer(0));
					} else if (meta.getColumnClassName(i+1).equals(Double.class.getName())) {
						o = new DataNull(new Double(0));
					} else if (meta.getColumnClassName(i+1).equals(java.sql.Timestamp.class.getName())) {
						o = new DataNull(new java.sql.Timestamp(0));
					} else if (meta.getColumnClassName(i+1).equals(java.lang.Short.class.getName())) {
						o = new DataNull(new Integer(0));
					} else {
						System.out.println(meta.getColumnName(i+1) + ":" + meta.getColumnClassName(i+1));
					}
				}
				v.add(o);
			}
			destination.executeUpdate(inssql, v);
			count++;
		}
		rset.close();
		if (tablename.equals("REFS")) {
			String sqlins = "insert into refs(ref_id, display_sequence, app_type_ref_id, reference_group,ref_key,key_sequence)" //,create_dt, create_user_id, update_dt, update_user_id"
//					+ " , version, active_ind, deleted_ind, system_version_nbr, record_type_ref_id)" 
					+ " VALUES (0,0,0,'NULL','NULL',0)"; //,'01/01/2006',0,'01/01/2006',0,0,1,0,0,1);";
			try {
				destination.executeUpdate(sqlins);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(count + " rows migrated to " + tablename);
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
