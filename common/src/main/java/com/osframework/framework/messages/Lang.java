package com.osframework.framework.messages;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

import com.osframework.framework.logging.Debug;

/*
 * The Lang (language) file is used to translate strings into the 
 * language of the locale.  Usage example:
 * 	String errorMsg = Lang.get("Local error message");
 */
public class Lang implements Enumeration {

	//TODO Swap out for a faster hashtable if needed
	private static Properties props = new Properties();
	private Enumeration enum1 = null;
	
	static {
		String fileName = "LanguageBundle.properties";
		try {
			FileInputStream fis =new FileInputStream(new File(fileName)); 
			props.load(fis);
			fis.close();
		} catch (Exception ex) {
			Debug.LogException("LoadProperties", ex, "Unable to load language file: " + fileName);
		}
	}
	
	public static String get(String key) {
		key = alphaNumeric(key);
		if (props.containsKey(key)) {
			return props.get(key).toString();
		} else {
			return key;
		}
	}

	public Enumeration elements() {
		enum1 = props.elements();
		return props.keys();
	}
	
	public boolean hasMoreElements() {
		return enum1.hasMoreElements();
	}
	
	public Object nextElement() {
		return enum1.nextElement();
	}

	private static String alphaNumeric(String s) {
		StringBuffer sb = new StringBuffer(80);
		for (int i=0; i<s.length();i++) {			
			if (Character.isLetterOrDigit(s.charAt(i))) {
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}

}
