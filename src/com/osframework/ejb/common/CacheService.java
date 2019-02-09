package com.osframework.ejb.common;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.util.concurrent.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;


public class CacheService {
	
	private static ConcurrentHashMap<String, Object> serviceCache = new ConcurrentHashMap<String, Object>(1024*1024);
	
	//TODO sharing between beans may be problematic - name clash/synchronization
	public static void clearCache() {
		serviceCache.clear();
	}
	
	public static boolean isCached(int param1) throws CacheException {
		String cacheKey = getCacheKey(param1);
		return serviceCache.containsKey(cacheKey);
	}
	
	public static boolean isCached(String param1) throws CacheException {
		String cacheKey = getCacheKey(param1);
		return serviceCache.containsKey(cacheKey);
	}
	
	public static boolean isCached(int param1, int param2) throws CacheException {
		String cacheKey = getCacheKey(param1, param2);
		return serviceCache.containsKey(cacheKey);
	}
	
	public static boolean isCached(String param1, boolean param2) throws CacheException {
		String cacheKey = getCacheKey(param1, param2);
		return serviceCache.containsKey(cacheKey);
	}
	
	public static boolean isCached(int param1, int param2, int param3) throws CacheException {
		String cacheKey = getCacheKey(param1, param2, param3);
		return serviceCache.containsKey(cacheKey);
	}
	
	private static String getCacheKey(int param1) throws CacheException {		
		Throwable t = new Throwable();
		if (t.getStackTrace().length > 2) {
			StackTraceElement e = t.getStackTrace()[2];
			return e.getMethodName() + ":" + param1;
		} else {
			throw new CacheException();
		}
	}
	
	private static String getCacheKey(String param1) throws CacheException {		
		Throwable t = new Throwable();
		if (t.getStackTrace().length > 2) {
			StackTraceElement e = t.getStackTrace()[2];
			return e.getMethodName() + ":" + param1;
		} else {
			throw new CacheException();
		}
	}
	
	private static String getCacheKey(int param1, int param2) throws CacheException {		
		Throwable t = new Throwable();
		if (t.getStackTrace().length > 2) {
			StackTraceElement e = t.getStackTrace()[2];
			return e.getMethodName() + ":" + param1 + ":" + param2;
		} else {
			throw new CacheException();
		}
	}
	
	private static String getCacheKey(String param1, boolean param2) throws CacheException {		
		Throwable t = new Throwable();
		if (t.getStackTrace().length > 2) {
			StackTraceElement e = t.getStackTrace()[2];
			return e.getMethodName() + ":" + param1 + ":" + param2;
		} else {
			throw new CacheException();
		}
	}
	
	private static String getCacheKey(int param1, int param2, int param3) throws CacheException {		
		Throwable t = new Throwable();
		if (t.getStackTrace().length > 2) {
			StackTraceElement e = t.getStackTrace()[2];
			return e.getMethodName() + ":" + param1 + ":" + param2 + ":" + param3;
		} else {
			throw new CacheException();
		}
	}
	
	public static Object cache(Object result, int param1) throws CacheException {
		String cacheKey = getCacheKey(param1);
		serviceCache.put(cacheKey, result);
		return result;
	}
	
	public static Object cache(Object result, String param1) throws CacheException {
		String cacheKey = getCacheKey(param1);
		serviceCache.put(cacheKey, result);
		return result;
	}
	
	public static Object cache(Object result, int param1, int param2) throws CacheException {
		String cacheKey = getCacheKey(param1, param2);
		serviceCache.put(cacheKey, result);
		return result;
	}
	
	public static Object cache(Object result, String param1, boolean param2) throws CacheException {
		String cacheKey = getCacheKey(param1, param2);
		serviceCache.put(cacheKey, result);
		return result;
	}
	
	public static Object cache(Object result, int param1, int param2, int param3) throws CacheException {
		String cacheKey = getCacheKey(param1, param2, param3);
		serviceCache.put(cacheKey, result);
		return result;
	}
	
	public static Object getCachedResult(int param1) throws CacheException {
		String cacheKey = getCacheKey(param1);
		return serviceCache.get(cacheKey);
	}
	
	public static Object getCachedResult(String param1) throws CacheException {
		String cacheKey = getCacheKey(param1);
		return serviceCache.get(cacheKey);
	}
	
	public static Object getCachedResult(int param1, int param2) throws CacheException {
		String cacheKey = getCacheKey(param1, param2);
		return serviceCache.get(cacheKey);
	}
	
	public static Object getCachedResult(String param1, boolean param2) throws CacheException {
		String cacheKey = getCacheKey(param1, param2);
		return serviceCache.get(cacheKey);
	}
	
	public static Object getCachedResult(int param1, int param2, int param3) throws CacheException {
		String cacheKey = getCacheKey(param1, param2, param3);
		return serviceCache.get(cacheKey);
	}
	

}
