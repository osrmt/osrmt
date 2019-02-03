package com.osframework.datalibrary.common;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;

import com.osframework.framework.logging.Debug;

/**
 * Db provides access to the database and some general helper methods.
 * The database access will be replaced by the J2EE container pool.
 *
 */
public class Db {

	private static Access access = null;
	private static String environment = null;
	private static Vector connections = new Vector();
	private static int sequenceModifier = 1000000;
	
	public static String getEnvironment() {
		if (environment != null) {
			return environment.trim();
		}
		return environment;
	}
	
	public static void setEnvironment(String env) {
		environment = env;
	}
	
	private static ConnectionProperty property = new ConnectionProperty();

	static {
		try {
			connections = ConnectionList.loadConnections();
		} catch (Exception ex) {
			Debug.LogException("Database Connection", ex, "Unable to load database file: connection.xml " + System.getProperty("user.dir"));
		}
	}
	
	public static DbConnection getConnection() throws SQLException {
		return new DbConnection(getAccess());
	}
	
	/**
	 * Override the connection.xml with the ConnectionProperty
	 * @param cp
	 */
	public static void setConnection(ConnectionProperty cp) {
		connections.clear();
		connections.add(cp);
/*		if (access != null && access.conn != null) {
			try {
				access.conn.close();
			} catch (Exception ex){}
		}
*/		
		access = null;
		
	}
	
	public static boolean emptySchema() throws Exception {
		int rows = countTable("application_control");
		rows += countTable("application_setting");
		rows += countTable("application_user");
		rows += countTable("application_view");
		rows += countTable("application_custom_control");
		rows += countTable("record_file");
		rows += countTable("record_parameter");
		rows += countTable("report");
		rows += countTable("reference");
		rows += countTable("reference_group");
		return rows==0;
	}
	
	public static boolean testConnection(Access access, ConnectionProperty cp) throws Exception {
		DbConnection dbc =new DbConnection(access);
		String sql = "select ref_id from reference where ref_id =0";
		SQLResult result = access.executeQuery(sql);
		result.close();
		return true;
	}
	
	public static int countTable(String tableName) throws DataAccessException {
		try {
			Access access = getAccess();
			String sql = "select count(*) from " + tableName;
			SQLResult result = access.executeQuery(sql);
			ResultSet rset = result.getRset();
			int rows = 0;
			if (rset.next()) {
				rows = rset.getInt(1);
			}
			result.close();
			return rows;
		} catch (Exception ex) {
			throw new DataAccessException(ex);
		}
	}
	
	/**
	 * Load the first active database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Access getAccess() throws SQLException {
		if (access == null) {
			access = new Access();
			//TODO sort by sequence and retry connections
			Enumeration e1 = connections.elements();
			while (e1.hasMoreElements()) {
				ConnectionProperty cp = (ConnectionProperty) e1.nextElement();
				try {
					if (cp.isJ2EE()) {
						access.setConnProperty(cp);
						return access;
					}
					access.connect(cp);
					environment = cp.getEnvironment();
					if (testConnection(access, cp)) {
						return access;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					System.err.flush();
					Debug.LogError("Db","Connection failed: " + cp.toDebug());
					Debug.LogException("Db",ex);
				}
			}
			throw new SQLException("connection.xml has no valid active environments");
		}
		return access;
	}

	public static void setAccess(Access access) {
		Db.access = access;
	}

	
	/*
	 * Converts the int to an integer and stores in a Vector
	 */
	public static Vector getParameter(int i) {
		Vector v = new Vector(1);
		Integer integer = new Integer(i);
		v.add(integer);
		return v;
	}
	
	public static Vector getParameter(String s) {
		Vector v = new Vector(1);
		v.add(s);
		return v;
	}
	
	/*
	 * Converts the int to an integer and stores in a Vector
	 */
	public static Vector getParameter(int i, int j) {
		Vector v = new Vector(2);
		v.add(new Integer(i));
		v.add(new Integer(j));
		return v;
	}
	
	/*
	 * Converts the int to an integer and stores in a Vector
	 */
	public static Vector getParameter(int i, GregorianCalendar c) {
		Vector v = new Vector(2);
		v.add(new Integer(i));
		v.add(c);
		return v;
	}
	
	public static Vector getParameter(int i, String s) {
		Vector v = new Vector(2);
		v.add(new Integer(i));
		v.add(s);
		return v;
	}
	
	public static Vector getParameter(String s, int i) {
		Vector v = new Vector(2);
		v.add(s);
		v.add(new Integer(i));
		return v;
	}
	
	public static Vector getParameter(int i, int j, int k) {
		Vector v = new Vector(3);
		v.add(new Integer(i));
		v.add(new Integer(j));
		v.add(new Integer(k));
		return v;
	}
	
	public static Vector getParameter(int i, int j, int k, int l) {
		Vector v = new Vector(4);
		v.add(new Integer(i));
		v.add(new Integer(j));
		v.add(new Integer(k));
		v.add(new Integer(l));
		return v;
	}
	
	public static Vector getParameter(int i, int j, int k, int l, int m) {
		Vector v = new Vector(5);
		v.add(new Integer(i));
		v.add(new Integer(j));
		v.add(new Integer(k));
		v.add(new Integer(l));
		v.add(new Integer(m));
		return v;
	}
	
	public static Vector getParameter(int i, int j, int k, int l, int m, int n) {
		Vector v = new Vector(6);
		v.add(new Integer(i));
		v.add(new Integer(j));
		v.add(new Integer(k));
		v.add(new Integer(l));
		v.add(new Integer(m));
		v.add(new Integer(n));
		return v;
	}
	
	public static Vector getParameter(String s, String t) {
		Vector v = new Vector(2);
		v.add(s);
		v.add(t);
		return v;
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
	
	public static int getNextRandomSequence() {
		Random rand = new Random(Calendar.getInstance().getTimeInMillis());
		sequenceModifier++;
		return rand.nextInt(10000000) + sequenceModifier;
	}
	
	public static int getNextSequence(int tableRefId) {
		return getNextSequence(tableRefId, 1, null);
	}
	
	public static int getNextSequence(int tableRefId, DbConnection conn) {
		return getNextSequence(tableRefId, 1, conn);
	}
	
	public static Object getSingleValue(String sql) throws DataAccessException {
		SQLResult result = null;
		try {
			result = access.executeQuery(sql);
			ResultSet rset = result.getRset();
			Object value = null;
			if (rset.next()) {
				Object o = rset.getObject(1);
				if (!rset.wasNull()) {
					value = o;
					if (value instanceof Timestamp) {
						Timestamp ts = (Timestamp) o;
						GregorianCalendar c = new GregorianCalendar();
						c.setTimeInMillis(ts.getTime());
						value = c;
					}
				}
			}
			rset.close();
			return value;
		} catch (SQLException e) {
			Debug.LogError("Db", sql);
			throw new DataAccessException(e);
		} finally {
			if (result != null) {
				result.close();
			}
		}
}
	
	/**
	 * Returns Integer, Double, String or GregorianCalendar
	 * @param sql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public static Object getSingleValue(String sql, Vector params) throws Exception {
		return getSingleValue(sql, params, getAccess().getDbConnection());
	}
	

	/**
	 * Returns Integer, Double, String or GregorianCalendar
	 * @param sql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public static Object getSingleValue(String sql, Vector params, DbConnection conn) throws DataAccessException {
		SQLResult result = null;
		try {
			result = getAccess().executeQuery(sql, params, conn);
			ResultSet rset = result.getRset();
			Object value = null;
			if (rset.next()) {
				Object o = rset.getObject(1);
				if (!rset.wasNull()) {
					value = o;
					if (value instanceof Timestamp) {
						Timestamp ts = (Timestamp) o;
						GregorianCalendar c = new GregorianCalendar();
						c.setTimeInMillis(ts.getTime());
						value = c;
					} else if (value instanceof BigDecimal) {
						BigDecimal bd = (BigDecimal) o;
						value = new Integer(bd.intValue());
					} else if (value instanceof Long) {
						Long bd = (Long) o;
						value = new Integer(bd.intValue());
					}
				}
			}
			return value;
		} catch (Exception e) {
			Debug.LogError("Db", sql);
			throw new DataAccessException(e);
		}
}


	public static int getNextSequence(int tableRefId, int batchSize, DbConnection conn) {
		return getNextRandomSequence();
		/*TODO this failed to work with MS Access - too much contention
		while (true) {
			try {
				return retryNextSequence(tableRefId, batchSize);				
			} catch (RetrySequenceException e) {
				//retry
			}
		}
		*/
	}
	/** 
	 * Get a batch from 1 to n of sequential numbers.
	 * e.g. getNextSequence(TableNameFramework.APPLICATIONCONTROL, 20) 
	 * will increment the next sequence by 20
	 * 
	 * @param tableRefId
	 * @return
	 */
	public static int retryNextSequence(int tableRefId, int batchSize) throws RetrySequenceException {
		try {
			Access access = getAccess();
			String sql = "select next_id from sequence_key where table_ref_id = ? ";
			SQLResult result = access.executeQuery(sql, getParameter(tableRefId));
			ResultSet rset = result.getRset();
			try {
				if (rset.next()) {
					int nextId = rset.getInt(1);
					String updateSql = "update sequence_key set next_id = ? where table_ref_id = ? and next_id = ?";
					SQLResult res = access.executeUpdate(updateSql, getParameter(nextId+batchSize, tableRefId, nextId));
					if (res.getRowsUpdated() == 0) {
						throw new RetrySequenceException("" + nextId);
					}
					//access.conn.commit();
					return nextId;
				} else {
					return getNextRandomSequence();
				}
			} finally {
				result.close();
			}
		} catch (RetrySequenceException re) {
			Debug.LogException("Db", re);
			throw re;
		} catch (Exception ex) {
			return getNextRandomSequence();
		}
	}
	
	/*public static void closeConnection() throws SQLException {
		access.conn.commit();
		access.conn.close();
	}
	*/
	
	
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
