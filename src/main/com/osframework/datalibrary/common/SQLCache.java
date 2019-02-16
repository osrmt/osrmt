package com.osframework.datalibrary.common;

import java.util.*;

/**
 * Used to implement a most recently used cache
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
