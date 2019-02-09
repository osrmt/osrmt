package com.osframework.datalibrary.common;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.beans.*;


public class ConnectionProperty implements Serializable  {
	
	private static final long serialVersionUID = 0L;
	
	public static final String JDBCConnectionType = "jdbc";
	public static final String J2EEConnectionType = "j2ee";
	
	/**
	 * Environment label e.g. Demo, Production etc.
	 */
	private String environment;
	/**
	 * Environment label e.g. Demo, Production etc.
	 */
	private String connectionType;
	/**
	 * False for MS Access, True for almost all other databases.
	 */
	private boolean connectToURL = true;

	/**
	 * URL to connect to remote database (or MS Access local file)
	 * e.g. jdbc:oracle:thin:@localhost:1521:prod1
	 */
	private String url = null; // "jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)};DBQ=osrmt.mdb";

	/**
	 * Driver implementation - class name - must be in the classpath!
	 */
	private String driverClass; // = "sun.jdbc.odbc.JdbcOdbcDriver";
	
	/**
	 * Determines if this connection is active
	 */
	private boolean active;
	
	/**
	 * Determines if this connection is active
	 */
	private boolean unicodeConnection;
	
	/**
	 * Database connections of the same environment name 
	 * will access URLs in this order
	 */
	private int accessSequence = 0;
	
	private String username; // = "admin";
	
	private String password;// = "";
	
	public ConnectionProperty() {
	}
	
	public int getAccessSequence() {
		return accessSequence;
	}

	public void setAccessSequence(int accessSequence) {
		this.accessSequence = accessSequence;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isConnectToURL() {
		return connectToURL;
	}

	public void setConnectToURL(boolean connectToURL) {
		this.connectToURL = connectToURL;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isUnicodeConnection() {
		return unicodeConnection;
	}

	public void setUnicodeConnection(boolean unicodeConnection) {
		this.unicodeConnection = unicodeConnection;
	}

	public String getUrl() {
		if (isUnicodeConnection()) {
			return url + "?useUnicode=true&characterEncoding=UTF-8";
		} else {
			return url;
		}
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	
	public boolean isJ2EE() {
		return this.connectionType.equalsIgnoreCase("J2EE");
	}

	public String toDebug() {
		return "Environment: " + this.getEnvironment()
		+ "\t" + "Connection type: " + this.getConnectionType() 
		+ "\t" + "Driver class: " + this.getDriverClass()
		+ "\t" + "Url: " + this.getUrl()
		+ "\t" + "Username: " + this.getUsername();
	}

	public String toString() {
		return this.getEnvironment();
	}
	
	
}
