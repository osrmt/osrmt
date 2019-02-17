/*
 * Created on 22-Mar-2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package database;

import java.util.*;

/**
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SQLCache extends LinkedHashMap {
	
	// maximum number of SQL statements in cache
	private static final int MAX_ENTRIES = 50;
	static final long serialVersionUID = 1L;
	/* (non-Javadoc)
	 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
	 */
	protected boolean removeEldestEntry(Map.Entry eldest) {
	   return size() > MAX_ENTRIES;
	}

}
