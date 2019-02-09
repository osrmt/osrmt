package com.osframework.framework.utility;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.beans.*;

import com.osframework.framework.logging.Debug;

public class ClientProperty implements Serializable {
	
	private static String envFileName = "client.xml";
	
	/**
	 * Port on server to connect to
	 */
	private String username = "";
	
	public static void saveClients (Vector connections) throws Exception {
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder( 
		          new BufferedOutputStream( 
		            new FileOutputStream(envFileName)));
			
			Enumeration e1 = connections.elements();
			while (e1.hasMoreElements()) {
				ClientProperty cp = (ClientProperty) e1.nextElement();
				encoder.writeObject(cp);
			}
			encoder.close();
			
		} catch (Exception ex) {
			Debug.LogException("ConnectionProperty", ex);
			encoder.close();
		}
	}
	
	public static ClientProperty loadClient() {
		try {
			Vector v = loadClients();
			if (v.size() > 0) {
				Enumeration e1 = v.elements();
				return (ClientProperty) e1.nextElement();
			} else {
				return new ClientProperty();
			}
		} catch (Exception ex) {
			return new ClientProperty();
		}
	}
	
	public static Vector loadClients () throws Exception {
		XMLDecoder decoder = null;
		try {
			Vector connections = new Vector();
			decoder = new XMLDecoder( 
				      new BufferedInputStream( 
						        new FileInputStream(envFileName)));
			try {
		        while (true) {
			        ClientProperty ci = (ClientProperty) decoder.readObject();
		        	connections.add(ci);
		        }
			} catch (ArrayIndexOutOfBoundsException ae) {
				return connections;
			}
		} catch (Exception ex) {
			Debug.LogException("ConnectionProperty", ex);
			decoder.close();
			return new Vector();
		}
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		return this.getUsername() + "\t";
	}

	
}
