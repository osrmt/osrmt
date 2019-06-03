package com.osframework.appclient.ui.common;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.beans.*;

import com.osframework.datalibrary.common.ConnectionList;
import com.osframework.framework.logging.Debug;

public class EnvironmentProperty implements Serializable  {
	
	/**
	 * Environment label e.g. Demo, Production etc.
	 */
	private String environment = "default";
	
	/**
	 * Determines if this connection is active
	 */
	private boolean active = true;
	
	/**
	 * Remote connections of the same environment name 
	 * will access URLs in this order
	 */
	private int accessSequence = 0;
	
	/**
	 * Host name or IP address e.g. localhost, 127.0.0.1, 192.168.1.104 etc  
	 */
	private String remoteHost = "127.0.0.1";
	
	private int remotePort = 1099;
	
	private static String configFileName = "environment.xml";
	
	public static void saveEnvironemnts (ConnectionList environments) throws Exception {
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder( 
		          new BufferedOutputStream( 
		            new FileOutputStream(configFileName)));
			
			Enumeration e1 = environments.elements();
			while (e1.hasMoreElements()) {
				EnvironmentProperty cp = (EnvironmentProperty) e1.nextElement();
				encoder.writeObject(cp);
			}
			encoder.close();
			
		} catch (Exception ex) {
			Debug.LogException("EnvironmentProperty", ex);
			if (encoder != null) {
				encoder.close();
			}
			else {
				Debug.LogError("EnvironmentProperty", "Unable to open the encoder "); // Noncompliant; NullPointerException will be thrown
			}
		}
	}
	
	public static ConnectionList loadEnvironments() throws Exception {
		XMLDecoder decoder = null;
		try {
			ConnectionList environments = new ConnectionList();
			decoder = new XMLDecoder( 
				      new BufferedInputStream( 
						        new FileInputStream(configFileName)));
			try {
		        while (true) {
		        	EnvironmentProperty ci = (EnvironmentProperty) decoder.readObject();
			        if (ci.isActive()) {
			        	environments.add(ci);
			        }
			        
		        }
			} catch (ArrayIndexOutOfBoundsException ae) {
				return environments;
			}
		} catch (Exception ex) {
			Debug.LogException("EnvironmentProperty", ex);
			decoder.close();
			throw ex;
		}
		finally {
			if (decoder != null) {
				decoder.close();
			}
		}
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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public String toString() {
		return this.getEnvironment() + "\t"
		+ this.getAccessSequence() + "\t" 
		+ this.getRemoteHost() + "\t"
		+ this.getRemotePort() + "\t";
	}
	
}
