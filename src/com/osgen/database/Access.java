/**
 * Created on 22-Mar-2003
 *
 * Class Access
 * 
 * Access provides:
 *  1) A connection to a JDBC database.
 *  2) Helper methods for executing queries and updates.
 *  3) Caching of prepared statements
 * 
 */
package database;

import java.sql.*;
import java.util.*;

public class Access {
	
	// Database Connection
	public java.sql.Connection conn = null;
	boolean isAccess = false;
	private String connString = null;
	private String username = null;
	private String password = null;
	
	// Cached prepared statements
	private SQLCache sqlCache = new SQLCache();
	
	
	/**
	 * Constructor
	 */
	public Access() {}
	
	
	/**
	 * Connect to an Oracle database instance
	 * @param hostOrIP hostname or IP address where database is located
	 * @param port usually 1521
	 * @param user username with database access
	 * @param password users password
	 * @param databaseInstance database instance (not database name)
	 * @throws SQLException
	 */
	public void connectToOracle(String hostOrIP, String port, String user, String password, String databaseInstance) 
								throws SQLException {
		
		connString ="jdbc:oracle:thin:@" + hostOrIP + ":" + port + ":" + databaseInstance;
		this.username = user;
		this.password = password;
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		conn =  DriverManager.getConnection(connString,this.username ,this.password); 
	}
	
	public void reconnectOracle() throws SQLException {
		try {
			this.conn.close();
		} catch (Exception ex) {
		}
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		conn =  DriverManager.getConnection(connString,this.username ,this.password); 
	}
	/**
	 * Connect to a Microsoft Access database using JDBC-ODBC
	 * @param odbcalias
	 * @param user
	 * @param password
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void connectToAccessDatabase(String fileName, String user, String password) throws ClassNotFoundException, SQLException{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		conn = DriverManager.getConnection("jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)};DBQ=" + fileName, user, password);
		isAccess = true;
	}
	
	
	/**
	 * Create or retreive from cache the prepared statement
	 * @param sql
	 * @return PreparedStatement
	 * @throws SQLException
	 */
	private PreparedStatement getPreparedStatement(String sql) throws SQLException {
		PreparedStatement ps = null;
		if (sqlCache.containsKey(sql)) {
			ps = (PreparedStatement) sqlCache.get(sql);
		} else {
			ps = conn.prepareStatement(sql);
			sqlCache.put(sql,ps);
		}
		return ps;
	}
	
	
	/**
	 * Creates and caches for reuse a PreparedStatement. 
	 * Executes passing 0 or more parameters 
	 * @param sql SQL to be executed
	 * @param parameters Parameters to be substituted for ? in the SQL
	 * @return ResultSet 
	 * @throws SQLException,AccessDataTypeException
	 */
	public synchronized ResultSet executeQuery(String sql, Vector parameters) 
									throws SQLException,AccessDataTypeException {

			PreparedStatement ps = getPreparedStatement(sql);
	
			Enumeration enum1 = parameters.elements();
			// loop variables
			int cnt=1;
			Object o;
			Null n;
			String className;

			// cycle through the Vector of parameters
			while (enum1.hasMoreElements()) {

					o = (Object) enum1.nextElement();
					className = o.getClass().getName();

					if (className.equals("java.lang.Double"))
							ps.setDouble(cnt, ((Double) o).doubleValue());
					else if (className.equals("java.lang.String"))
							ps.setString(cnt, ((String) o));
					else if (className.equals("java.lang.Long")) {
						if (isAccess) {
							throw new AccessDataTypeException ("long not valid for Microsoft Access, use Integer");
						} else { 
							ps.setLong(cnt, ((Long) o).longValue());
						}
					} else if (className.equals("java.lang.Integer"))
							ps.setInt(cnt, ((Integer) o).intValue());							
					else if (className.equals("database.Null")) {
						n = (Null) o;
						o = (Object) n.type;
						className = o.getClass().getName();
						if (className.equals("java.lang.Double"))
								ps.setNull(cnt,java.sql.Types.DOUBLE);
						else if (className.equals("java.lang.String"))
							ps.setNull(cnt,java.sql.Types.VARCHAR);
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


			return ps.executeQuery();
	}

	/**
	 * Creates and caches for reuse a PreparedStatement. 
	 * Executes passing 0 or more parameters 
	 * @param sql
	 * @param parameters
	 * @return int
	 * @throws Exception
	 */
	public synchronized int executeUpdate(String sql, Vector parameters) 
									throws SQLException,AccessDataTypeException {

		PreparedStatement ps = getPreparedStatement(sql);

		Enumeration enum1 = parameters.elements();
		int cnt=1;
		Object o;
		Null n;
		String className;
		ps.clearParameters();

		// cycle through the Vector of parameters and add
		while (enum1.hasMoreElements()) {

				o = (Object) enum1.nextElement();
				className = o.getClass().getName();

				if (className.equals("java.lang.Double"))
						ps.setDouble(cnt, ((Double) o).doubleValue());
				else if (className.equals("java.lang.String"))
						ps.setString(cnt, ((String) o));
				else if (className.equals("java.lang.Long"))
						ps.setLong(cnt, ((Long) o).longValue());
				else if (className.equals("java.lang.Integer"))
					ps.setInt(cnt, ((Integer) o).intValue());
				else if (className.equals("database.CalendarDate")) {
					Calendar c = (Calendar) o;
					ps.setDate(cnt, new java.sql.Date(c.getTimeInMillis()));
				} else if (className.equals("database.Null")) {
					n = (Null) o;
					o = (Object) n.type;
					className = o.getClass().getName();
					if (className.equals("java.lang.Double"))
						ps.setNull(cnt,java.sql.Types.DOUBLE);
					else if (className.equals("java.lang.String"))
						ps.setNull(cnt,java.sql.Types.VARCHAR);
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


		return ps.executeUpdate();
	}


	/**
	 * Execute a SQL query 'as is'
	 * @param sql select SQL to be executed
	 * @return ResultSet
	 * @throws SQLException
	 */
	public synchronized ResultSet executeQuery(String sql) throws SQLException {
		
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(sql);	
	}


	/**
	 * Execute a SQL query 'as is'
	 * @param sql update/delete/insert SQL to be executed
	 * @return int Number of rows updated
	 * @throws SQLException
	 */
	public synchronized int executeUpdate(String sql) throws SQLException {
		
		Statement stmt = conn.createStatement();
		return stmt.executeUpdate(sql);	
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	public void finalize () {
		if (conn!=null) {
			try {
				conn.close();
			} catch (Exception closeException) {
				closeException.toString();
			}
		}
	}
	
	
	/*
	 * Returns the Calendar equivalent of the date.
	 * Returns null if date is null.
	 */
	public static GregorianCalendar getCalendarDate(java.sql.Timestamp date) {
		if (date != null) {
			GregorianCalendar c = new GregorianCalendar();
			c.setTimeInMillis(date.getTime());
			return c;
		} else {
			return null;
		}
	}

}
