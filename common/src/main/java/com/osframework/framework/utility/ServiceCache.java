package com.osframework.framework.utility;

/*
ServiceCache holds return values for client applications.

Copyright (C) 2006  Aron Lancout Smith

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/

import java.util.Enumeration;
import java.util.Hashtable;
/**
* <p>ServiceCache is designed for single threaded use.</p> 
* <p>However in the future if/when clients access Services via
* multiple threads then this should be made thread safe.</p>
* <p>Another feature to be implemented is purging of old values
* although not usually required for client UI applications</p>
* 
* <br/>Trace: <a href="/functionality/rm/9549082.html">9549082</a>
*/
public class ServiceCache {

private ReaderWriter lock = new ReaderWriter();

private static final long serialVersionUID = 1L;
/**
 * Default size of the cache
 */
private static final int defaultCacheSize = 4096;
/**
 * Marked true when the cache is being written to
 */
private Hashtable<String, Object> cache = new Hashtable<String, Object>(defaultCacheSize);

/**
 * Return true if the Object represented by the key is stored in the table
 * 
 * @param key
 * @return
 */
public boolean exists(String key) {
	boolean exists = false;
	try {		
		lock.requestRead();
		exists = cache.containsKey(key);
	} finally {
		lock.readFinished();
	}
	return exists;
}

/**
 * Returns an enumeration of the keys in this cache
 * 
 * @return
 */
public Enumeration<String> getKeys() {
	return cache.keys();
}

/**
 * Maps the specified key to the specified value in this cache. 
 * Neither the key nor the value can be null. 
 * 
 * @param key
 * @param result
 */
public void put(String key, Object result) {
	try {
		lock.requestWrite();
		cache.put(key, result);
	} finally {
		lock.writeFinished();
	}
}

/**
 * Removes the key (and its corresponding value) from this hashtable. 
 * This method does nothing if the key is not in the hashtable. 
 * 
 * @param key
 * @param result
 */
public void remove(String key) {
	try {
		lock.requestWrite();
		cache.remove(key);
	} finally {
		lock.writeFinished();
	}
}

/**
 * Returns the value to which the specified key is mapped in this cache. 
 * 
 * @param key
 * @return
 */
public Object get(String key) {
	Object returnValue = null;
	try {		
		lock.requestRead();
		returnValue = cache.get(key);
	} finally {
		lock.readFinished();
	}
	return returnValue;
}

/**
 * Clear the cache
 */
public void clear() {
	 cache = new Hashtable<String, Object>(cache.size() > defaultCacheSize ? cache.size() : defaultCacheSize);
}

/**
 * Cache statsd
 */
public String toString() {
	return "ServiceCache " + this.hashCode() + " size " + cache.size();
}
}
