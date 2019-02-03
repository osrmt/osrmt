package models;

import java.util.*;

public class MessageModel {
	
	private String message = null;
	private Hashtable list = new Hashtable();
	
	/*
	 * Returns the language translation of msg
	 */
	public String translate(String language, String msg) {
		if (list.containsKey(key(language, msg))) {
			return list.get(key(language, msg)).toString();
		}
		return "";
	}
	
	/*
	 * Adds a language translation of msg
	 */
	public void add(String language, String msg, String translation) {
		this.message = msg;
		if (list.containsKey(key(language, msg))) {
			list.remove(key(language, msg));
		}
		list.put(key(language, msg), translation);
	}
	
	/*
	 * Creates a key from language and msg
	 */
	public static String key(String language, String msg) {
		return alphaNumeric(language + "_" + msg);
	}
	
	public static String key(String s) {
		return alphaNumeric(s);
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
