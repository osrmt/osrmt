package com.osrmt.appclient.setup;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class DatabaseAccess implements Driver {
	
		private Driver driver;
		DatabaseAccess(Driver d) {
			this.driver = d;
		}
		public boolean acceptsURL(String u) throws SQLException {
			return this.driver.acceptsURL(u);
		}
		public Connection connect(String u, Properties p) throws SQLException {
			return this.driver.connect(u, p);
		}
		public int getMajorVersion() {
			return this.driver.getMajorVersion();
		}
		public int getMinorVersion() {
			return this.driver.getMinorVersion();
		}
		public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
			return this.driver.getPropertyInfo(u, p);
		}
		public boolean jdbcCompliant() {
			return this.driver.jdbcCompliant();
		}
		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			// TODO Auto-generated method stub
			return null;
		}
		
}

