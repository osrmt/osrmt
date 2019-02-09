/**
 * 
 */
package com.osframework.framework.utility;

import java.io.*;
import java.net.*;
import java.util.*;
import com.osframework.framework.logging.*;

public class ClientUtility {
	
	private static final String directoryFile = "resources.client";
	public static final String login_lastusername = "login.lastusername";
	private static Properties properties = null;

	public static String getProperty(String clientUtilityProperty) {
		try {
			if (properties == null) {
				try {
					InputStream fis = new FileInputStream("client.properties");
					properties = new Properties();
					properties.load(fis);
				} catch (Exception ex) {
					OutputStream out = new FileOutputStream("client.properties");
					properties = new Properties();
					properties.store(out, null);
				}
			}			
			return properties.getProperty(clientUtilityProperty);
		} catch (Exception ex) {
			Debug.LogException("ClientUtility", ex);
			return null;
		}
	}
	
	public static void setProperty(String clientUtilityProperty, String value) {
		try {
			OutputStream out = new FileOutputStream("client.properties");
			properties.put(clientUtilityProperty, value);
			properties.store(out, null);
			
		} catch (Exception ex) {
			Debug.LogException("ClientUtility", ex);
		}
	}
}
