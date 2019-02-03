package com.osframework.modellibrary.common;

import java.util.ArrayList;
import java.util.Hashtable;

import com.osframework.framework.exceptions.NullArgumentException;
import com.osframework.framework.logging.Debug;
/**
 * 
 * TreeCacheModel provides efficient lookup to 
 * related pairs of values (e.g. ref_id and parent_id)
 * 
 */
public class TreeCacheModel {
	
	private Hashtable keyTable = new Hashtable();
	private Hashtable parentTable = new Hashtable();
	
	public TreeCacheModel() {
		
	}
	
	/**
	 * Clears the cache
	 */
	public void clear() {
		keyTable.clear();
		parentTable.clear();
	}

	/**
	 * Constructs a new tree cachemodel with the 
	 * specified capacity
	 * @param initialCapacity
	 */
	public TreeCacheModel(int initialCapacity) {
		keyTable = new Hashtable(initialCapacity);
		parentTable = new Hashtable(initialCapacity);
	}
	
	/**
	 * Returns the number of child+parent entries
	 * @return
	 */
	public int size() {
		return keyTable.size() + parentTable.size();
	}
	/**
	 * Add the relationship between key and parent
	 * @param key
	 * @param parent
	 */
	public void add(int key, int parent) {
		add("" + key, "" + parent);
	}
	
	/**
	 * Add the relationship between key and parent
	 * @param key
	 * @param parent
	 */
	public void add(String key, String parent) {
		addRelationship(key, parent, true);
		addRelationship(parent, key, false);
	}
	
	private void addRelationship(String key, String parent, boolean useKeyTable) {
		if (key == null || parent == null) {
			Debug.LogException(this, new NullArgumentException("invalid key"));
		} else {
			Object o = null;
			if (useKeyTable) {
				o = keyTable.get(key);
			} else {
				o = parentTable.get(key);
			}
			if (o == null) {
				ArrayList list = new ArrayList();
				list.add(parent);
				if (useKeyTable) {
					keyTable.put(key, list);
				} else {
					parentTable.put(key, list);
				}
			} else {
				ArrayList list = (ArrayList) o;
				list.add(parent);
			}
		}
	}
	
	/**
	 * Returns an arraylist of string values for the
	 * key.
	 * @param key
	 * @return empty arraylist if the key is not found
	 */
	public ArrayList getParent(String key) {
		if (key == null) {
			Debug.LogException(this, new NullArgumentException("invalid key"));
			return new ArrayList();
		} else {
			return (ArrayList) keyTable.get(key);
		}
	}
	
	/**
	 * Returns an arraylist of string values for the
	 * key.
	 * @param key
	 * @return empty arraylist if the key is not found
	 */
	public ArrayList getChildren(String key) {
		if (key == null) {
			Debug.LogException(this, new NullArgumentException("invalid key"));
			return new ArrayList();
		} else {
			return (ArrayList) parentTable.get(key);
		}
	}
	
	/**
	 * Returns an integer array of parent values
	 * @param key
	 * @return null if the key is not found
	 */
	public int[] getParent(int key) {
		try {
			Object o = keyTable.get("" + key);
			if (o != null) {
				ArrayList list = (ArrayList) o;
				int[] intList = new int[list.size()];
				for (int i=0; i<list.size(); i++) {
					intList[i] = Integer.parseInt((String) list.get(i));
				}
				return intList;
			} else {
				return null;
			}
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return null;
		}
	}

	/**
	 * Returns an integer array of child values
	 * @param key
	 * @return null if the key is not found
	 */
	public int[] getChildren(int key) {
		try {
			Object o = parentTable.get("" + key);
			if (o != null) {
				ArrayList list = (ArrayList) o;
				int[] intList = new int[list.size()];
				for (int i=0; i<list.size(); i++) {
					intList[i] = Integer.parseInt((String) list.get(i));
				}
				return intList;
			} else {
				return null;
			}
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return null;
		}
	}

}
