package com.osrmt.appclient.setup;

import java.sql.*;
import java.util.*;

import com.osframework.framework.logging.*;
import com.osframework.datalibrary.common.*;

public class JDBCAccess {
	
	private ConnectionProperty connProperty = null;
	private static boolean dumpSql = false;
	public Connection conn = null;
	
	/**
	 * Constructor
	 */
	public JDBCAccess(ConnectionProperty connProperty) throws Exception {		
		this.connProperty = connProperty;
		connect();
	}
	
	
	/**
	 * Creates and caches for reuse a PreparedStatement. 
	 * Executes passing 0 or more parameters 
	 * @param sql SQL to be executed
	 * @param parameters Parameters to be substituted for ? in the SQL
	 * @return ResultSet 
	 * @throws SQLException,DataAccessDataTypeException
	 */
	public synchronized SQLResult executeQuery(String sql, Vector parameters) 
									throws SQLException,AccessDataTypeException {
		return executeQuery(sql, parameters, conn);
	}
	/**
	 * Creates and caches for reuse a PreparedStatement. 
	 * Executes passing 0 or more parameters 
	 * @param sql SQL to be executed
	 * @param parameters Parameters to be substituted for ? in the SQL
	 * @return ResultSet 
	 * @throws SQLException,AccessDataTypeException
	 */
	public synchronized SQLResult executeQuery(String sql, Vector parameters, Connection conn) 
									throws SQLException,AccessDataTypeException {
		
			PreparedStatement ps = conn.prepareStatement(sql);
	
			Enumeration enum1 = parameters.elements();
			// loop variables
			int cnt=1;
			Object o;
			DataNull n;
			String className;

			// cycle through the Vector of parameters
			while (enum1.hasMoreElements()) {

					o = (Object) enum1.nextElement();
					if (o == null) {
						throw new AccessDataTypeException("null parameter used");
					}
					className = o.getClass().getName();

					if (className.equals("java.lang.Double"))
						ps.setDouble(cnt, ((Double) o).doubleValue());
					else if (className.equals("java.lang.String"))
						ps.setString(cnt, ((String) o));
					else if (className.equals("java.lang.Long")) {
						ps.setLong(cnt, ((Long) o).longValue());
					} else if (className.equals("java.util.GregorianCalendar")) {
						Calendar c = (Calendar) o;
						Timestamp ts = new Timestamp(c.getTimeInMillis());
						ps.setTimestamp(cnt, ts);
					} else if (className.endsWith("DbCalendar")) {
						Calendar c = (Calendar) o;
						Timestamp ts = new Timestamp(c.getTimeInMillis());
						ps.setTimestamp(cnt, ts);
					} else if (className.equals("java.util.Calendar")) {
						Calendar c = (Calendar) o;
						Timestamp ts = new Timestamp(c.getTimeInMillis());
						ps.setTimestamp(cnt, ts);
					} else if (className.equals("java.lang.Integer"))
							ps.setInt(cnt, ((Integer) o).intValue());							
					else if (className.endsWith("DataNull")) {
						n = (DataNull) o;
						o = (Object) n.type;
						className = o.getClass().getName();
						if (className.equals("java.lang.Double"))
								ps.setNull(cnt,java.sql.Types.DOUBLE);
						else if (className.equals("java.lang.String"))
							ps.setNull(cnt,java.sql.Types.VARCHAR);
						else if (className.equals("java.util.Calendar"))
							ps.setNull(cnt,java.sql.Types.TIMESTAMP);
						else if (className.equals("java.util.GregorianCalendar"))
							ps.setNull(cnt,java.sql.Types.TIMESTAMP);
						else if (className.endsWith("DbCalendar"))
							ps.setNull(cnt,java.sql.Types.TIMESTAMP);
						else if (className.equals("java.lang.Long"))
							ps.setNull(cnt,java.sql.Types.BIGINT);
						else if (className.equals("java.lang.Integer"))
							ps.setNull(cnt,java.sql.Types.INTEGER);
						else 
							throw new AccessDataTypeException (className + " not accounted for in executeUpdate");									
							
				} else
							throw new AccessDataTypeException (className + " not accounted for in executeQuery");

					cnt++;
			}

			if (dumpSql) {
				Debug.LogDebug(this, getFormattedException(new SQLException("Conn: " + conn + " connection: " + conn==null?"null":""+conn),sql, parameters));
			}
			

			ResultSet rset = ps.executeQuery();
			
			return new SQLResult(ps, rset);
	}

	/**
	 * Creates and caches for reuse a PreparedStatement. 
	 * Executes passing 0 or more parameters 
	 * @param sql
	 * @param parameters
	 * @return int
	 * @throws Exception
	 */
	public synchronized SQLResult executeUpdate(String sql, Vector parameters) 
									throws SQLException,AccessDataTypeException {
		try {
			return executeUpdate(sql, parameters, conn);
		} finally {
			if (conn != null) {
				//conn.close();
			}
		}
	}
	/**
	 * Creates and caches for reuse a PreparedStatement. 
	 * Executes passing 0 or more parameters 
	 * @param sql
	 * @param parameters
	 * @return int
	 * @throws Exception
	 */
	public synchronized SQLResult executeUpdate(String sql, Vector parameters, Connection conn) 
									throws SQLException,AccessDataTypeException {

		PreparedStatement ps = null;
		try {
				
		ps = conn.prepareStatement(sql);

		Enumeration enum1 = parameters.elements();
		int cnt=1;
		Object o;
		DataNull n;
		String className;
		ps.clearParameters();

		// cycle through the Vector of parameters and add
		while (enum1.hasMoreElements()) {

				o = (Object) enum1.nextElement();
				if (o == null) {
					throw new AccessDataTypeException("null parameter used");
				}
				className = o.getClass().getName();

				if (className.equals("java.lang.Double"))
						ps.setDouble(cnt, ((Double) o).doubleValue());
				else if (className.equals("java.lang.String"))
						ps.setString(cnt, ((String) o));
				else if (className.equals("java.lang.Long"))
						ps.setLong(cnt, ((Long) o).longValue());
				else if (className.equals("java.lang.Integer"))
					ps.setInt(cnt, ((Integer) o).intValue());
				else if (className.equals("java.util.GregorianCalendar")) {
					Calendar c = (Calendar) o;
					ps.setTimestamp(cnt, new Timestamp(c.getTimeInMillis()));
				}
				else if (className.equals("java.sql.Timestamp")) {
					java.sql.Timestamp c = (java.sql.Timestamp) o;
					ps.setTimestamp(cnt, new Timestamp(c.getTime()));
				}
				else if (className.endsWith("DbCalendar")) {
					Calendar c = (Calendar) o;
					ps.setTimestamp(cnt, new Timestamp(c.getTimeInMillis()));
				} else if (className.equals("java.util.Calendar")) {
					Calendar c = (Calendar) o;
					ps.setTimestamp(cnt, new Timestamp(c.getTimeInMillis()));
				} else if (className.endsWith("DataNull")) {
					n = (DataNull) o;
					o = (Object) n.type;
					className = o.getClass().getName();
					if (className.equals("java.lang.Double"))
						ps.setNull(cnt,java.sql.Types.DOUBLE);
					else if (className.equals("java.lang.String"))
						ps.setNull(cnt,java.sql.Types.VARCHAR);
					else if (className.equals("java.util.Calendar"))
						ps.setNull(cnt,java.sql.Types.TIMESTAMP);
					else if (className.equals("java.util.GregorianCalendar"))
						ps.setNull(cnt,java.sql.Types.TIMESTAMP);
					else if (className.equals("java.sql.Timestamp"))
						ps.setNull(cnt,java.sql.Types.TIMESTAMP);
					else if (className.endsWith("DbCalendar"))
						ps.setNull(cnt,java.sql.Types.TIMESTAMP);
					else if (className.equals("java.lang.Long"))
						ps.setNull(cnt,java.sql.Types.BIGINT);
					else if (className.equals("java.lang.Integer"))
						ps.setNull(cnt,java.sql.Types.INTEGER);
					else 
						throw new AccessDataTypeException (className + " not accounted for in executeUpdate");
					
				} else {
						throw new AccessDataTypeException (className + " not accounted for in executeUpdate");
				}

				cnt++;
		}

		if (dumpSql) {
			System.out.println( getFormattedException(new SQLException("Conn: " + conn + " connection: " + conn==null?"null":""+conn),sql, parameters));
		}

		int rows = ps.executeUpdate();
		return new SQLResult(rows);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception ex){ex.printStackTrace();}
		}
	}


	/**
	 * Execute a SQL query 'as is'
	 * Caller must close SQlResult
	 * 
	 * @param sql select SQL to be executed
	 * @return ResultSet
	 * @throws SQLException
	 */
	public synchronized SQLResult executeQuery(String sql) throws SQLException {
		return executeQuery(sql, conn);
	}

	/**
	 * Execute a SQL query 'as is'
	 * @param sql select SQL to be executed
	 * @return ResultSet
	 * @throws SQLException
	 */
	public synchronized SQLResult executeQuery(String sql, Connection conn) throws SQLException {
		
		if (dumpSql) {
			Debug.LogDebug(this, getFormattedException(new SQLException("Conn: " + conn + " connection: " + conn==null?"null":""+conn),sql));
		}
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		return new SQLResult(stmt, rset);
	}


	/**
	 * Execute a SQL query 'as is'
	 * @param sql update/delete/insert SQL to be executed
	 * @return int Number of rows updated
	 * @throws SQLException
	 */
	public synchronized SQLResult executeUpdate(String sql) throws SQLException {
		try {
			return executeUpdate(sql, conn);
		} finally {
		}
	}
	
	/**
	 * Execute a SQL query 'as is'
	 * @param sql update/delete/insert SQL to be executed
	 * @return int Number of rows updated
	 * @throws SQLException
	 */
	public synchronized SQLResult executeUpdate(String sql, Connection conn) throws SQLException {
		
		if (dumpSql) {
			Debug.LogDebug(this, getFormattedException(new SQLException("Conn: " + conn + " connection: " + conn==null?"null":""+conn),sql));
		}
				
		Statement stmt = conn.createStatement();
		int rows = stmt.executeUpdate(sql);
		try {
			stmt.close();
		} catch (Exception ex){}
		return new SQLResult(rows);
	}

	public ConnectionProperty getConnProperty() {
		return connProperty;
	}

	public void setConnProperty(ConnectionProperty cp) {
		connProperty = cp;
	}

	public void connect() throws Exception {
		try {
			Class.forName(connProperty.getDriverClass());
			Driver d = (Driver)Class.forName(connProperty.getDriverClass()).newInstance();
			DriverManager.registerDriver(new DatabaseAccess(d));
			conn =  DriverManager.getConnection(connProperty.getUrl(), connProperty.getUsername(),connProperty.getPassword()); 
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	

	public static boolean isDumpSql() {
		return dumpSql;
	}


	public static void setDumpSql(boolean dumpSql) {
		JDBCAccess.dumpSql = dumpSql;
	}

	
	public static String getFormattedException(SQLException e, String sql) {
		return getFormattedException(e,sql,new Vector(1));
	}
	
	/**
	 * Returns the format
	 * <SQL error> <sql> <parameters>
	 * @param e
	 * @param sql
	 * @param params
	 * @return
	 */
	public static String getFormattedException(SQLException e, String sql, Vector params) {
		try {
			StringBuffer sb = new StringBuffer(256);
			if (e!=null) {
				sb.append(e.getMessage());
			}
			sb.append(" ");
			sb.append(sql);
			if (params != null) {
				Enumeration e1 = params.elements();
				while (e1.hasMoreElements()) {
					Object param = e1.nextElement();
					if (param !=null) {
						sb.append(",");
						if (param instanceof String) {
							sb.append("'" + param.toString() + "'");
						} else {
							sb.append(param.toString());
						}
					} else {
						sb.append(",");
						sb.append("null");
					}
				}
			}
			return sb.toString();
			
		} catch (Exception ex) {
			if (e != null) {
				return e.toString() + " " + sql;
			} else {
				return sql;
			}
		}
	}
}

