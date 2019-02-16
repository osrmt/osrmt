package com.osframework.appclient.services;

import java.util.Hashtable;

import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.security.ApplicationControlList;

public class BaseService {
	
	private static Hashtable serviceCache = new Hashtable(1024);

	public BaseService() {
		super();
	}

	public static ServiceCall getServiceCall(int actionRefId) {
		ServiceCall call = new ServiceCall();
		call.setUser(Application.getUser());
		call.setActionRefId(actionRefId);
		call.setApplication(Application.getObject());
		return call;
	}
	
	public static ServiceCall getServiceCall() {
		//TODO reconsider using 0 for a dummy action
		return getServiceCall(0);
	}
	
	protected static void clearLocalCache() {
		serviceCache.clear();
	}
	
	protected static boolean isCached(int param1) throws CacheException {
		String cacheKey = getCacheKey(param1);
		return serviceCache.containsKey(cacheKey);
	}
	
	protected static boolean isCached(int param1, int param2) throws CacheException {
		String cacheKey = getCacheKey(param1, param2);
		return serviceCache.containsKey(cacheKey);
	}
	
	protected static boolean isCached(int param1, int param2, int param3) throws CacheException {
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
	
	private static String getCacheKey(int param1, int param2) throws CacheException {		
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
	
	protected static Object cache(Object result, int param1) throws CacheException {
		String cacheKey = getCacheKey(param1);
		serviceCache.put(cacheKey, result);
		return result;
	}
	
	protected static Object cache(Object result, int param1, int param2) throws CacheException {
		String cacheKey = getCacheKey(param1, param2);
		serviceCache.put(cacheKey, result);
		return result;
	}
	
	protected static Object cache(Object result, int param1, int param2, int param3) throws CacheException {
		String cacheKey = getCacheKey(param1, param2, param3);
		serviceCache.put(cacheKey, result);
		return result;
	}
	
	protected static Object getCachedResult(int param1) throws CacheException {
		String cacheKey = getCacheKey(param1);
		return serviceCache.get(cacheKey);
	}
	
	protected static Object getCachedResult(int param1, int param2) throws CacheException {
		String cacheKey = getCacheKey(param1, param2);
		return serviceCache.get(cacheKey);
	}
	
	protected static Object getCachedResult(int param1, int param2, int param3) throws CacheException {
		String cacheKey = getCacheKey(param1, param2, param3);
		return serviceCache.get(cacheKey);
	}
	
}
