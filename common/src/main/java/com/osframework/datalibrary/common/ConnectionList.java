package com.osframework.datalibrary.common;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.osframework.framework.logging.Debug;

public class ConnectionList extends Vector implements ComboBoxModel {
	
	private static final long serialVersionUID = 1L;
	private static String configFileName = "connection.xml";
	
	public void addEnvironment(ConnectionProperty connection) {
		this.add(connection);
	}

	public static void saveConnections (ConnectionList connections) throws Exception {
		XMLEncoder encoder = null;
		try {
			String path = configFileName;
			String home = System.getProperty("osrmt_home");
			if (home != null && home.length() > 0) {
				path = home + File.separator + configFileName;
			}
			encoder = new XMLEncoder( 
		          new BufferedOutputStream( 
		            new FileOutputStream(path)));
			
			Enumeration e1 = connections.elements();
			while (e1.hasMoreElements()) {
				ConnectionProperty cp = (ConnectionProperty) e1.nextElement();
				encoder.writeObject(cp);
			}
			encoder.close();
			
		} catch (Exception ex) {
			Debug.LogException("ConnectionProperty", ex);
			encoder.close();
		}
	}

	public static ConnectionList loadConnections () throws Exception {
		XMLDecoder decoder = null;
		try {
			ConnectionList connections = new ConnectionList();
			//decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(configFileName)));
			String path = configFileName;
			String home = System.getProperty("osrmt_home");
			if (home != null && home.length() > 0) {
				path = home + File.separator + configFileName;
			}
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
			try {
		        while (true) {
			        ConnectionProperty ci = (ConnectionProperty) decoder.readObject();
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
			return new ConnectionList();
		}
	}
	
	public int getSize() {
		return size();
	}
	
	Vector dataListeners = new Vector();
	public void addListDataListener(ListDataListener dataListener) {
		dataListeners.add(dataListener);
	}
	
	public void removeListDataListener(ListDataListener dataListener) {
		dataListeners.remove(dataListener);
	}
	
	private Object selectedItem = null;
	public Object getSelectedItem() {
		return selectedItem;
	}
	
	//TODO The reference display and combo box model does
	// not handle multiple selection
	public void setSelectedItem(Object o ) {
		selectedItem = o;
		Enumeration e1 = dataListeners.elements();
		while (e1.hasMoreElements()) {
			ListDataListener l = (ListDataListener) e1.nextElement();
			l.contentsChanged(new ListDataEvent(this, 0, 0, size()));
		}
	}
	
	public Object getElementAt(int index) {
		return this.elementAt(index);
	}

	public static String getConfigFileName() {
		return configFileName;
	}

	public static void setConfigFileName(String configFileName) {
		ConnectionList.configFileName = configFileName;
	}

	public ConnectionProperty getEnvironment(String environment) {
		Enumeration e1 = this.elements();
		while (e1.hasMoreElements()) {
			ConnectionProperty cp = (ConnectionProperty) e1.nextElement();
			if (cp.getEnvironment().equalsIgnoreCase(environment)) {
				return cp;
			}
		}
		return null;
	}

}
