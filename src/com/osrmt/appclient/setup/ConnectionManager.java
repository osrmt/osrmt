package com.osrmt.appclient.setup;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.osframework.appclient.services.SystemServices;
import com.osframework.datalibrary.common.*;
import com.osframework.framework.utility.FileSystemUtil;
import com.osframework.framework.utility.SerializeUtility;

public class ConnectionManager extends ConsoleManager {

	public void updateConnection(ConnectionProperty cp) {
		doption("Enter the environment name ["
				+ cp.getEnvironment() + "]: ");
		cp.setEnvironment(getStringData(cp.getEnvironment()));
		
		doption("Enter the connection type: Jdbc or J2ee [" 
				+ cp.getConnectionType() + "]: ");
		cp.setConnectionType(getStringData(cp.getConnectionType()));
		
		doption("Enter the driver class [" 
				+ cp.getDriverClass() + "]: ");
		cp.setDriverClass(getStringData(cp.getDriverClass()));
		
		doption("Enter the url [" 
				+ cp.getUrl() + "]: ");
		cp.setUrl(getStringData(cp.getUrl()));
		
		doption("Enter the username [" 
				+ cp.getUsername() + "]: ");
		cp.setUsername(getStringData(cp.getUsername()));
		
		doption("Enter the password [" 
				+ cp.getPassword() + "]: ");
		cp.setPassword(getStringData(cp.getPassword()));
		
	}
	
	public boolean testConnection(ConnectionProperty cp) {
		DbConnection dbc = null;
		try {
			Access a =new Access();
			a.connect(cp);
			dbc = a.getDbConnection();
			String sql = "select count(*) from reference";
			a.executeQuery(sql, dbc);
			return true;
		} catch (Exception e) {
			try {
				File file = new File("connection_error.log");
				PrintWriter pw = new PrintWriter(file);
				e.printStackTrace(pw);
				pw.close();
				System.out.println("stack trace written to connection_error.log");
			} catch (Exception ex) {
				
			}
			System.out.println(e.getMessage());
			System.out.flush();
			return false;
		} finally {
			if (dbc != null) {
				try {
					dbc.close();
				} catch (Exception ex) {}
			}
		}
	}
	
	public void saveConnection(ConnectionProperty cp) {
		File file = null;
		try {
			doption("Enter the file name [connection_new.xml]: ");
			String filename = getStringData("connection_new.xml");
			ConnectionList.setConfigFileName(filename);
			ConnectionList list =new ConnectionList();
			list.add(cp);
			ConnectionList.saveConnections(list);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.flush();
		}
	}
	
	public void importReference(ConnectionProperty connectionProperty) {
		try {
			if (connectionProperty.isJ2EE()) { 
				d("connection.xml needs a database not a J2ee connection");
				return;
			}
			d("initializing database defined in connection.xml:");
			d(connectionProperty.getUrl());
			d(connectionProperty.getUsername());
			d(connectionProperty.getEnvironment());
			doption("Target correct? Y|N [Y]: ");
			if (! getStringData("Y").equals("Y")) {
				return;
			}
			Db.setConnection(connectionProperty);
			if (Db.countTable("reference_group") == 0) {
				if (Db.emptySchema()) {
					String msg = "";
					doption("Empty schema located - initialize and populate schema? [Y]: ");
					if (getStringData("Y").equalsIgnoreCase("Y")) {
						SystemServices.importSystem();
					}
				} else {
					d("Schema is not empty - drop database and execute create_schema scripts");
				}
 			} else {
				d("Schema is not empty - drop database and execute create_schema scripts");
 			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.flush();
		}
	}
}

