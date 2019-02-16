package com.osframework.modellibrary.common;

import java.util.*;

import com.osframework.framework.exceptions.NullArgumentException;
import com.osframework.framework.logging.*;
/**
 * 
 * TreeCacheModel provides efficient lookup to 
 * an object by a key value
 * 
 */
public class ListCacheModel {
	
	private Hashtable keyTable = new Hashtable();
	
	public ListCacheModel() {
		
	}
	
	public int size() {
		return keyTable.size();
	}
	
	public ListCacheModel(int initialCapacity) {
		keyTable = new Hashtable(initialCapacity);
	}
	
	/**
	 * Clears the cache
	 */
	public void clear() {
		keyTable.clear();
	}
	
	/**
	 * Add the relationship between key and object 
	 * Will replace existing key to object
	 * @param key
	 * @param parent
	 */
	public void add(int key, Object o) {
		add("" + key, o);
	}
	
	/**
	 * Add the relationship between key and object 
	 * Will replace existing key to object
	 * @param key
	 * @param parent
	 */
	public void add(int key, int i) {
		add("" + key, new Integer(i));
	}
	
	/**
	 * Adds the relationship between key and parent
	 * Will replace existing key to object
	 * @param key
	 * @param parent
	 */
	public void add(String key, Object object) {
		if (key == null || object == null) {
			Debug.LogException(this, new NullArgumentException("invalid key"));
		} else {
			if (keyTable.containsKey(key)) {
				keyTable.remove(key);
			}
			keyTable.put(key, object);
		}
	}
	
	/**
	 * Returns an object for the specified key
	 * @param key
	 * @return empty null if the key is not found
	 */
	public Object get(String key) {
		if (key == null) {
			Debug.LogException(this, new NullArgumentException("invalid key"));
			return null;
		} else {
			return keyTable.get(key);
		}
	}
	
	/**
	 * Returns an object for the key
	 * @param key
	 * @return 0 if the key is not found
	 */
	public Object getObject(int key) {
		return get("" + key);
	}
	
	/**
	 * Returns an integer value of the Object
	 * @param key
	 * @return 0 if the key is not found
	 */
	public int get(int key) {
		try {
			Object o = keyTable.get("" + key);
			if (o != null && o.getClass().getName().compareTo("java.lang.Integer")==0) {
				Integer integer = (Integer) o;
				return integer.intValue();
			} else {
				return 0;
			}
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return 0;
		}
	}

}
