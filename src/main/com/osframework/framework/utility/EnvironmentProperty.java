package com.osframework.framework.utility;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.beans.*;

import com.osframework.framework.logging.Debug;

public class EnvironmentProperty implements Serializable {
	
	private static String envFileName = "environment.xml";
	
	/**
	 * Environment connecting to - ideally matches environment database 
	 * is connecting to
	 */
	private String environment = "demo";
	/**
	 * Host/node/IP of server to connect to
	 */
	private String serverIP = "localhost";
	/**
	 * Port on server to connect to
	 */
	private String serverPort = "1099";
	/**
	 * Client server if true will connect to the database directly
	 */
	private boolean clientServer = false;
	/**
	 * Indicates this environment is active
	 */
	private boolean isActive = true;
	
	public static void saveEnvironments (Vector connections) throws Exception {
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder( 
		          new BufferedOutputStream( 
		            new FileOutputStream(envFileName)));
			
			Enumeration e1 = connections.elements();
			while (e1.hasMoreElements()) {
				EnvironmentProperty cp = (EnvironmentProperty) e1.nextElement();
				encoder.writeObject(cp);
			}
			encoder.close();
			
		} catch (Exception ex) {
			Debug.LogException("ConnectionProperty", ex);
			encoder.close();
		}
	}
	
	public static Vector loadEnvironments () throws Exception {
		XMLDecoder decoder = null;
		try {
			Vector connections = new Vector();
			decoder = new XMLDecoder( 
				      new BufferedInputStream( 
						        new FileInputStream(envFileName)));
			try {
		        while (true) {
			        EnvironmentProperty ci = (EnvironmentProperty) decoder.readObject();
			        if (ci.isActive()) {
			        	connections.add(ci);
			        }
		        }
			} catch (ArrayIndexOutOfBoundsException ae) {
				return connections;
			}
		} catch (Exception ex) {
			Debug.LogException("ConnectionProperty", ex);
			decoder.close();
			throw ex;
		}
	}

	
	public boolean isClientServer() {
		return clientServer;
	}

	public void setClientServer(boolean clientServer) {
		this.clientServer = clientServer;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String toString() {
		return this.getEnvironment() + "\t"
		+ this.getServerIP() + "\t" 
		+ this.getServerPort();	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
