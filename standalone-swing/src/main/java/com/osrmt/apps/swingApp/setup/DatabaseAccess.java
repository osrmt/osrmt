package com.osrmt.apps.swingApp.setup;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

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
		
}

