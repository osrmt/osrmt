/**
 * 
 */
package com.osrmt.ejb.reqmanager;

import java.util.*;

import com.osframework.modellibrary.common.DbCalendar;
import com.osrmt.modellibrary.reqmanager.*;
import com.osframework.framework.utility.*;
/**
 * ArtifactCache holds a caches artifact results sets.
 * Cache is cleared when an artifact is updated.
 *
 */
public class ArtifactCacheDisabled {

	/**
	 * Singleton
	 */
	private static ArtifactCacheDisabled ArtifactCacheDisabled = null;
	/**
	 * Cached artifact result set
	 */
	private Hashtable<Object, CachedInstance> cachedresults = new Hashtable<Object, CachedInstance>();
	/**
	 * Cached artifact models
	 */
	private Hashtable<Integer, ArtifactModel> cache = new Hashtable<Integer, ArtifactModel>(10000);
	/**
	 * Maximum cache size before clearing
	 */
	private static int maxCacheSize = 5000;
	/**
	 * Synchronize access to the cache
	 */
	private ReaderWriter sync = new ReaderWriter();
	
	/**
	 * Singleton
	 */
	private ArtifactCacheDisabled() {
		
	}
	
	/**
	 * Singleton
	 */
	public static ArtifactCacheDisabled getInstance() {
		if (ArtifactCacheDisabled == null) {
			ArtifactCacheDisabled = new ArtifactCacheDisabled();
		}
		return ArtifactCacheDisabled;
	}
	
	private class CachedInstance {
		/**
		 * Most recent date an artifact was modified
		 */
		private DbCalendar mostRecentDate = null;
		/**
		 * Result set
		 */
		private List<Integer> artifactids = null;
		
		public CachedInstance(DbCalendar mostRecentDate, List<Integer> ids) {
			this.mostRecentDate = mostRecentDate;
			this.artifactids = ids;
		}
		
	}
	
	/**
	 * Determines if there is a cached list and it is not stale
	 * 
	 * @param key
	 * @param mostRecent
	 * @return
	 */
	public boolean isFreshCache(Object key, DbCalendar mostRecent) {
		try {
			sync.requestRead();
			boolean fresh = false;
			if (cachedresults.containsKey(key)) {
				DbCalendar cachedMostRecent = cachedresults.get(key).mostRecentDate;
				if (mostRecent.getTimeInMillis() <= cachedMostRecent.getTimeInMillis()) {
					fresh = true;
				} else {
					fresh = false;
				}
			} else {
				fresh = false;
			}
			return fresh;
		} finally {
			sync.readFinished();
		}
	}
	
	/**
	 * Get the cached list for the matching key
	 * 
	 * @param key
	 * @return
	 */
	public ArtifactList getCachedList(Object key) {
		try {
			sync.requestRead();
			List<Integer> ids = cachedresults.get(key).artifactids;
			ArtifactList list = new ArtifactList(ids.size());
			for (Integer id : ids) {
				ArtifactModel am = cache.get(id);
				list.add(am);
			}
			return list;
		} finally {
			sync.readFinished();
		}
	}
	
	/**
	 * Overwrite if necessary and store the artifact list as a cache
	 * 
	 * @param list
	 * @param key
	 * @param mostRecent
	 */
	public void cache(ArtifactList list, Object key, DbCalendar mostRecent) {
		try {
			sync.requestWrite();
			if (cache.size() > maxCacheSize) {
				cachedresults.clear();
				cache.clear();
			}
			if (cachedresults.containsKey(key)) {
				cachedresults.remove(key);
			}
			List<Integer> ids = new ArrayList<Integer>(list.size());
			Enumeration e1 = list.elements();
			while (e1.hasMoreElements()) {
				ArtifactModel am = (ArtifactModel) e1.nextElement();
				ids.add(am.getArtifactId());
				if (cache.containsKey(am.getArtifactId())) {
					cache.remove(key);
				}
				cache.put(am.getArtifactId(), am);
			}
			cachedresults.put(key, new CachedInstance(mostRecent, ids));
		} finally {
			sync.writeFinished();
		}
	}

	/**
	 * Clear the cache contents
	 * 
	 *
	 */
	private void clearCache() {
		try {
			cachedresults.clear();
			cache.clear();
		} finally {
			sync.writeFinished();
		}
	}
	
}
