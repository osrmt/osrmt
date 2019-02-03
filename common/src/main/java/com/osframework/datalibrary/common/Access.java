package com.osframework.datalibrary.common;
//TODO Transaction management for Oracle

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.osframework.framework.logging.Debug;

public class Access {
	
	private ConnectionProperty connProperty = null;
	private String connPoolName = "osrmt connection pool";
	private static boolean dumpSql = false;
	
		/**
	 * Constructor
	 */
	public Access() {}
	
	synchronized
	private DbConnection reopenConnection(DbConnection conn) throws SQLException {
		if (conn != null && conn.getConnection() != null && conn.getConnection().isClosed()) {
			new Exception().printStackTrace();
			try {
				Debug.LogWarning(this,"closed connection");
				conn.close();
			} catch (Exception ex) {}
			finally {
				throw new SQLException();
			}
		}
		return conn;
	}
	
	/**
	 * Creates and caches for reuse a PreparedStatement. 
	 * Executes passing 0 or more parameters 
	 * @param sql SQL to be executed
	 * @param parameters Parameters to be substituted for ? in the SQL
	 * @return ResultSet 
	 * @throws SQLException,AccessDataTypeException
	 */
	public synchronized SQLResult executeQuery(String sql, Vector parameters) 
									throws SQLException,AccessDataTypeException {
		return executeQuery(sql, parameters, getDbConnection());
	}
	/**
	 * Creates and caches for reuse a PreparedStatement. 
	 * Executes passing 0 or more parameters 
	 * @param sql SQL to be executed
	 * @param parameters Parameters to be substituted for ? in the SQL
	 * @return ResultSet 
	 * @throws SQLException,AccessDataTypeException
	 */
	public synchronized SQLResult executeQuery(String sql, Vector parameters, DbConnection conn) 
									throws SQLException,AccessDataTypeException {
		
			conn = reopenConnection(conn);
			
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
	
			Enumeration enum1 = parameters.elements();
			// loop variables
			int cnt=1;
			Object o;
			Null n;
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
					else if (className.endsWith("Null")) {
						n = (Null) o;
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
				Debug.LogDebug(this, Db.getFormattedException(new SQLException("Conn: " + conn + " connection: " + conn==null?"null":""+conn.getConnection()),sql, parameters));
			}
			

			ResultSet rset = ps.executeQuery();
			
			return new SQLResult(ps, rset, conn);
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
			DbConnection conn = null;
			try {
				conn = this.getDbConnection();
				return executeUpdate(sql, parameters, conn);
			} finally {
				if (conn != null) {
					conn.close();
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
	public synchronized SQLResult executeUpdate(String sql, Vector parameters, DbConnection conn) 
									throws SQLException,AccessDataTypeException {

		conn = reopenConnection(conn);
				
		PreparedStatement ps = conn.getConnection().prepareStatement(sql);

		Enumeration enum1 = parameters.elements();
		int cnt=1;
		Object o;
		Null n;
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
				else if (className.endsWith("DbCalendar")) {
					Calendar c = (Calendar) o;
					ps.setTimestamp(cnt, new Timestamp(c.getTimeInMillis()));
				} else if (className.equals("java.util.Calendar")) {
					Calendar c = (Calendar) o;
					ps.setTimestamp(cnt, new Timestamp(c.getTimeInMillis()));
				} else if (className.endsWith("Null")) {
					n = (Null) o;
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
					
				} else {
						throw new AccessDataTypeException (className + " not accounted for in executeUpdate");
				}

				cnt++;
		}

		if (dumpSql) {
			Debug.LogDebug(this, Db.getFormattedException(new SQLException("Conn: " + conn + " connection: " + conn==null?"null":""+conn.getConnection()),sql, parameters));
		}

		int rows = ps.executeUpdate();
		try {
			ps.close();
		} catch (Exception ex){}
		return new SQLResult(rows);
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
		return executeQuery(sql, getDbConnection());
	}

	/**
	 * Execute a SQL query 'as is'
	 * @param sql select SQL to be executed
	 * @return ResultSet
	 * @throws SQLException
	 */
	public synchronized SQLResult executeQuery(String sql, DbConnection conn) throws SQLException {
		
		conn = reopenConnection(conn);
		
		if (dumpSql) {
			Debug.LogDebug(this, Db.getFormattedException(new SQLException("Conn: " + conn + " connection: " + conn==null?"null":""+conn.getConnection()),sql));
		}
		Statement stmt = conn.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		return new SQLResult(stmt, rset, conn);
	}


	/**
	 * Execute a SQL query 'as is'
	 * @param sql update/delete/insert SQL to be executed
	 * @return int Number of rows updated
	 * @throws SQLException
	 */
	public synchronized SQLResult executeUpdate(String sql) throws SQLException {
		DbConnection conn = null;
		try {
			conn = this.getDbConnection();
			return executeUpdate(sql, conn);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	/**
	 * Execute a SQL query 'as is'
	 * @param sql update/delete/insert SQL to be executed
	 * @return int Number of rows updated
	 * @throws SQLException
	 */
	public synchronized SQLResult executeUpdate(String sql, DbConnection conn) throws SQLException {
		
		if (dumpSql) {
			Debug.LogDebug(this, Db.getFormattedException(new SQLException("Conn: " + conn + " connection: " + conn==null?"null":""+conn.getConnection()),sql));
		}
		conn = reopenConnection(conn);
				
		Statement stmt = conn.getConnection().createStatement();
		int rows = stmt.executeUpdate(sql);
		try {
			stmt.close();
		} catch (Exception ex){}
		return new SQLResult(rows, conn);
	}

	public ConnectionProperty getConnProperty() {
		return connProperty;
	}

	public void setConnProperty(ConnectionProperty cp) {
		connProperty = cp;
	}

	public DbConnection getDbConnection() throws SQLException{
		return new DbConnection(this);
	}

	public Connection getConnection() throws SQLException{
		Connection conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:" + connPoolName, connProperty.getUsername(),connProperty.getPassword());
		conn.setAutoCommit(false);
		return conn;
	}

	public void connect(ConnectionProperty cp) throws Exception {
		try {
			this.connProperty = cp;
			
			Class.forName("org.apache.commons.dbcp.PoolingDriver");
			ObjectPool connectionPool = new GenericObjectPool(null);
			PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(cp.getUrl(),connProperty.getUsername(),connProperty.getPassword());
			PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,connectionPool,null,null,false,true);
			driver.registerPool(connPoolName,connectionPool);
			Driver d = (Driver)Class.forName(cp.getDriverClass()).newInstance();
			DriverManager.registerDriver(new DatabaseAccess(d));
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	public void dumpReserved() {
		try {
		Debug.LogDebug("Access", "Keywords: " + getConnection().getMetaData().getSQLKeywords());
		Debug.LogDebug("Access", "Product:Version:Quotes: " + getConnection().getMetaData().getDatabaseProductName() + ":" 
					+ getConnection().getMetaData().getDatabaseProductVersion() + ":" 
					+ getConnection().getMetaData().getIdentifierQuoteString());
		} catch (Exception ex) {
		}
	}


	public static boolean isDumpSql() {
		return dumpSql;
	}


	public static void setDumpSql(boolean dumpSql) {
		Access.dumpSql = dumpSql;
	}

/*    public static void printDriverStats() {
    	try {
	        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
	        ObjectPool connectionPool = driver.getConnectionPool("example");
	        
	        Debug.LogDebug("Access", "NumActive: " + connectionPool.getNumActive());
	        Debug.LogDebug("Access", "NumIdle: " + connectionPool.getNumIdle());
    	} catch (Exception ex) {}
    }
    
	protected void finalize() throws Throwable {
		super.finalize();
		shutdownDriver();
	}
	
    public void shutdownDriver() throws Exception {
    	try {
	        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
	        driver.closePool(connPoolName);
    	} catch (Exception ex){};
    }
*/
	
	
}

