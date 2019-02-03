package com.osframework.datalibrary.common;

import java.sql.Connection;
import java.sql.SQLException;

import com.osframework.framework.logging.Debug;


public class DbConnection {
	Connection connection = null;
	Access access = null;
	
	public DbConnection(Access a) {
		this.access = a;
	}
	
	public Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = access.getConnection();
		}
		return connection;
	}
	
	public void rollback() {
		try {
			if (connection != null) {
				try {
					connection.rollback();
				} finally {
					connection.close();
					connection = null;
				}
			}
		} catch (Exception ex) {}
	}

	public void close() {
		try {
			try {
				if (connection != null) {
					if (!connection.isClosed()) {
						connection.commit();
					} else {
						Debug.LogException(this, new Exception(), "Connection is already closed");
					}
				}
			} finally {
				if (connection != null) {
					if (!connection.isClosed()) {
						connection.close();
					}
				}
			}
		} catch (Exception ex) {
			Debug.LogException(this,ex);
		}
	}
	
}

