package com.osframework.ejb.reference.common;

import java.rmi.RemoteException;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.*;
import com.osframework.appclient.services.CacheException;
import com.osframework.datalibrary.common.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.TimedAction;
import com.osframework.datalibrary.reference.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.ejb.common.*;
import com.osrmt.modellibrary.reqmanager.RequirementTreeHistoryModel;

/**
 * ReferenceMapBean provides service to the 
 * reference data
 *
 * TABLES: REFERENCE, REFERENCE_GROUP, REFERENCE_TREE
 */
public class ReferenceMapBean implements EntityBean, IReferenceMap {

	private EntityContext context= null;
	static final long serialVersionUID = 1L;
	/**
	 * Set of all reference IDs - resultset from the REFERENCE_SET query
	 */
	//private ReferenceList referenceList = new ReferenceList();
	/**
	 * Estimated size of the reference table
	 */ 
	//private final int REFERENCE_TABLE_SIZE = 100000;
	private ReferenceTreeDataAdapter rtda;
	private ReferenceDataAdapter rda;
	private ReferenceHistoryDataAdapter rhda;
	private ReferenceGroupDataAdapter rgda;
	private ConcurrentHashMap<Integer, ReferenceModel> refmap = new ConcurrentHashMap<Integer, ReferenceModel>(1024*1024);
	
	private static ReferenceMapBean referenceBean2Tier = null;
	
	/**
	 * Representation of the reference tree (parent-child relationships)
	 */
	//TODO cache on hold
	//private TreeCacheModel treeCache = new TreeCacheModel(REFERENCE_TABLE_SIZE);

	/**
	 * The ReferenceMapBean creates the refence cache
	 * on startup.
	 */
	public ReferenceMapBean() throws Exception {
		try {
			referenceBean2Tier = this;
			rtda = new ReferenceTreeDataAdapter(this, null);
			rda = new ReferenceDataAdapter(this, null);
			rhda = new ReferenceHistoryDataAdapter(this, null);
			rgda = new ReferenceGroupDataAdapter(this, null);
			if (Db.countTable("reference_group") == 0) {
				throw new Exception("empty schema found, run upgrade.bat and initialize database");
			}
			loadCache();
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}	
	
	public static ReferenceMapBean get2TierInstance() throws Exception {
		if (referenceBean2Tier == null) {
			referenceBean2Tier = new ReferenceMapBean();
		}
		return referenceBean2Tier;
	}
	


	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * Loads the entire cache 
	 * 
	 * @throws Exception
	 */
	public void loadCache() throws Exception {
		refmap.clear();
		Runnable loader = new Runnable() {

			public void run() {
				try {
					ReferenceList refs = rda.getAllReference();
					Enumeration e1 = refs.elements();
					while (e1.hasMoreElements()) {
						ReferenceModel rm = (ReferenceModel) e1.nextElement();
						refmap.put(rm.getRefId(), rm);
					}
					} catch (Exception ex) {
						Debug.LogException(this, ex);
					}
			}			
		};
		Thread t = new Thread(loader);
		t.start();
		t.join();
		
	}
	
	public String ejbFind() {
		return "ReferenceMapBean";
	}

	public void unsetEntityContext() {
		this.context = null;	
	}

	public void ejbStore() {
		// Store is executed with every call
		// avoid any debug messages
	}

	public void ejbLoad() {
	}
	public String ejbCreate() {
		return "ReferenceMapBean";
	}
	public void ejbPostCreate() {

	}
	public String ejbFindByPrimaryKey(String key) {
		return "ReferenceMapBean";
	}

	public void ejbRemove() {

	} 

	public void ejbActivate() {

	}

	public void ejbPassivate() {

	}

	/**
	 * Returns an list of ReferenceModel matching the group id and including
	 * inactive values if specified.  If no reference codes are found for the
	 * parameters then a valid empty ReferenceList is returned.
	 * 
	 * @param groupId Specify the unique reference group identifier
	 * @param includeInactive set to false to get only active values
	 * @return a collection of ReferenceModel
	 * @throws java.rmi.RemoteException
	 * @throws Exception 
	 */
	public ReferenceList getReferenceList(String referenceGroup, boolean includeInactive) throws java.rmi.RemoteException, DataAccessException{
		try {
			if (CacheService.isCached(referenceGroup, includeInactive)) {
				return (ReferenceList) CacheService.getCachedResult(referenceGroup, includeInactive);
			} else {
				return (ReferenceList) CacheService.cache(rda.getReferenceList(referenceGroup, includeInactive), referenceGroup, includeInactive);					
			}
		} catch (CacheException ce) {
			return rda.getReferenceList(referenceGroup, includeInactive);
		}
	}

	/**
	 * Returns a ReferenceModel matching the ref id.  If no reference codes 
	 * was found for the id then a valid empty ReferenceModel is returned.
	 * 
	 * @param refId
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 */
	public ReferenceModel getReference(int refId) throws java.rmi.RemoteException, DataAccessException{
		try {
			if (refmap.containsKey(refId)) {
				return refmap.get(refId);
			}
			if (CacheService.isCached(refId)) {
				return (ReferenceModel) CacheService.getCachedResult(refId);
			} else {
				return (ReferenceModel) CacheService.cache(rda.getReference(refId), refId);					
			}
		} catch (CacheException ce) {
			return rda.getReference(refId);
		}
	}


	/**
	 * Returns the display for the matching ref id.  If no reference codes 
	 * was found for the id then an empty string is returned.
	 * 
	 * @param refId
	 * @return display
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 */
	public String getDisplay(int refId) throws java.rmi.RemoteException, DataAccessException{
		if (refId == 0) {
			return "";
		}
		if (refmap.containsKey(refId)) {
			return refmap.get(refId).getDisplay();
		}
		try {
			if (CacheService.isCached(refId)) {
				return (String) CacheService.getCachedResult(refId);
			} else {
				ReferenceModel m  = getReference(refId);
				if (m.getDisplay() == null) {
					return  (String) CacheService.cache("",refId);
				} else {
					return  (String) CacheService.cache(m.getDisplay(),refId);
				}
			}
		} catch (CacheException ce) {
			ReferenceModel m  = getReference(refId);
			if (m.getDisplay() == null) {
				return "";
			} else {
				return m.getDisplay();
			}
		}
	}

	/**
	 * Returns the display for the matching ref id.  If no reference codes 
	 * was found for the id then an empty model is returned
	 * 
	 * @param refId
	 * @return display
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 */
	public ReferenceDisplay getReferenceDisplayResult(int refId) throws java.rmi.RemoteException, DataAccessException{
		ReferenceModel m  = getReference(refId);
		if (m.getDisplay() == null) {
			return new ReferenceDisplay();
		} else {
			ReferenceDisplay rd = new ReferenceDisplay();
			rd.setDisplay(m.getDisplay());
			rd.setRefId(m.getRefId());
			return rd;
		}
	}
	
	/**
	 * Returns the display for the matching ref id.  If no reference codes 
	 * was found for the id then an empty model is returned
	 * 
	 * @param refId
	 * @return display
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 */
	public ReferenceDisplay getReferenceDisplay(int refId) throws java.rmi.RemoteException, DataAccessException{
		try {
			ReferenceModel m  = getReference(refId);
			if (m.getDisplay() != null) {
				ReferenceDisplay rd = new ReferenceDisplay();
				rd.setDisplay(m.getDisplay());
				rd.setRefId(m.getRefId());
				return rd;
			}
			if (CacheService.isCached(refId)) {
				return (ReferenceDisplay) CacheService.getCachedResult(refId);
			} else {
				return (ReferenceDisplay) CacheService.cache(getReferenceDisplayResult(refId), refId);					
			}
		} catch (CacheException ce) {
			return getReferenceDisplayResult(refId);
		}
	}

	/**
	 * Returns the display list for a group.  If no reference codes
	 * were found then an empty list is returned.
	 * 
	 * @param groupId
	 * @param includeInactive
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 */
	public ReferenceDisplayList getDisplayList(String group, boolean includeInactive) throws java.rmi.RemoteException, DataAccessException{		
		
		ReferenceDisplayList displayList = new ReferenceDisplayList();
		Enumeration e1 = getReferenceList(group, includeInactive).elements();
		while (e1.hasMoreElements()) {
			ReferenceModel m = (ReferenceModel) e1.nextElement();
			ReferenceDisplay d = new ReferenceDisplay();
			d.setRefId(m.getRefId());
			d.setDisplay(m.getDisplay());
			displayList.add(d);
		}
		return displayList;
	}
	
	/**
	 * Return the list of reference for the given parent 
	 * @param parentId
	 * @return
	 */
	public ReferenceDisplayList getChildren(int parentId) throws java.rmi.RemoteException, DataAccessException {
		ReferenceDisplayList list = new ReferenceDisplayList();
		ReferenceTreeList treeList = rtda.getReferenceChildren(parentId, TableNameFramework.REFERENCE);
		Enumeration e1 = treeList.elements();
		while (e1.hasMoreElements()) {
			ReferenceTreeModel tree = (ReferenceTreeModel) e1.nextElement();
			ReferenceDisplay rdm = new ReferenceDisplay();
			rdm.setRefId(tree.getTableKeyId());
			rdm.setDisplay(getDisplay(tree.getTableKeyId()));
			list.add(rdm);
		}
		return list;
	}
	
	/**
	 * Returns a list of all the ReferenceDisplay children of all
	 * the reference displays in the parentlist.
	 * @param parentList
	 * @return
	 * @throws Exception
	 */
	public ReferenceDisplayList getChildren(ReferenceDisplayList parentList) throws java.rmi.RemoteException, DataAccessException {
		ReferenceDisplayList list = new ReferenceDisplayList();
		Enumeration e1 = parentList.elements();
		while (e1.hasMoreElements()) {
			ReferenceDisplay rd = (ReferenceDisplay) e1.nextElement();
			ReferenceDisplayList rdl = getChildren(rd.getRefId());
			Enumeration e2 = rdl.elements();
			while (e2.hasMoreElements()) {
				ReferenceDisplay d = (ReferenceDisplay) e2.nextElement();
				list.add(d);
			}
		}
		return list;
	}
	
	/**
	 * Returns the reference id for the given group and code
	 * 
	 * @param groupId
	 * @param displayCode
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws DataAccessException
	 */
    public ReferenceDisplayList getDisplayList(String group, String displayCode, boolean includeInactive) throws java.rmi.RemoteException, DataAccessException {
    	ReferenceList list = rda.getReferenceDisplayListbyCode(group, displayCode, includeInactive);
    	Enumeration e1 = list.elements();
    	ReferenceDisplayList displayList = new ReferenceDisplayList();
    	while (e1.hasMoreElements()) {
    		ReferenceModel m = (ReferenceModel) e1.nextElement();
			ReferenceDisplay rd = new ReferenceDisplay();
			rd.setRefId(m.getRefId());
			rd.setDisplay(m.getDisplay());
			displayList.add(rd);
    	}
    	return displayList;
    }
    
	/**
	 * Returns the reference id for the given group and code
	 * 
	 * @param groupId
	 * @param displayCode
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws DataAccessException
	 */
    public ReferenceDisplayList getDisplayList(String group, String display) throws java.rmi.RemoteException, DataAccessException {
    	ReferenceList list = rda.getReferenceDisplayListbyCode(group, display, false);
    	Enumeration e1 = list.elements();
    	ReferenceDisplayList displayList = new ReferenceDisplayList();
    	while (e1.hasMoreElements()) {
    		ReferenceModel m = (ReferenceModel) e1.nextElement();
			ReferenceDisplay rd = new ReferenceDisplay();
			rd.setRefId(m.getRefId());
			rd.setDisplay(m.getDisplay());
			displayList.add(rd);
    	}
    	return displayList;
    }
    
	/**
	 * Returns the reference id for the given group and application type
	 * 
	 * @param groupId
	 * @param appTypeRefId
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws DataAccessException
	 */
    public ReferenceDisplayList getDisplayList(String group, int appTypeRefId, boolean includeInactive) throws java.rmi.RemoteException, DataAccessException {
    	ReferenceList list = getReferenceList(group, true);
    	Enumeration e1 = list.elements();
    	ReferenceDisplayList displayList = new ReferenceDisplayList();
    	while (e1.hasMoreElements()) {
    		ReferenceModel m = (ReferenceModel) e1.nextElement();
    		if (m.getAppTypeRefId() == appTypeRefId
    				&& (m.isActive() || includeInactive == true)) {
    			ReferenceDisplay rd = new ReferenceDisplay();
    			rd.setRefId(m.getRefId());
    			rd.setDisplay(m.getDisplay());
    			displayList.add(rd);
    		}
    	}
    	return displayList;
    }
    

	public UpdateResult UpdateReference(ReferenceModel m, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		CacheService.clearCache();
		m.setRecordTypeRefId(RecordTypeFramework.USERREFERENCE);
		if (m.getRefId() == 0 && m.getDisplayCode() == null) {
			m.setDisplayCode();
		}
		UpdateResult result = rda.UpdateReference(m, call);
		refmap.put(m.getRefId(), m);
		return result;
	}

	public UpdateResult UpdateReference(ReferenceModel m, ServiceCall call, DbConnection conn) throws RemoteException, DataAccessException, Exception {
		CacheService.clearCache();
		m.setRecordTypeRefId(RecordTypeFramework.USERREFERENCE);
		if (m.getRefId() == 0 && m.getDisplayCode() == null) {
			m.setDisplayCode();
		}
		UpdateResult result = rda.UpdateReference(m, call, conn);
		refmap.put(m.getRefId(), m);
		return result;
	}

	public ReferenceList getReferenceList(String referenceGroup, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			if (CacheService.isCached(referenceGroup)) {
				return (ReferenceList) CacheService.getCachedResult(referenceGroup);
			} else {
				return (ReferenceList) CacheService.cache(rda.getReferenceList(referenceGroup, true), referenceGroup);					
			}
		} catch (CacheException ce) {
			return rda.getReferenceList(referenceGroup, true);
		}
	}

	/**  
	 *  Export system reference
	 */ 
	public ReferenceList exportReference(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			return rda.exportReference();
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
    
	/**  
	 *  Import reference
	 */ 
	public int importReference(ReferenceList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			return rda.importReference(list);
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Export reference group
	 */ 
	public ReferenceGroupList exportReferenceGroup(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			return rgda.exportReferenceGroup();
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Import reference groups
	 */ 
	public int importReferenceGroup(ReferenceGroupList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			return rgda.importReferenceGroup(list);
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Export reference tree
	 */ 
	public ReferenceTreeList exportReferenceTreeList(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			return rtda.exportReferenceTree();
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Import reference tree
	 */ 
	public int importReferenceTree(ReferenceTreeList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			return rtda.importReferenceTree(list);
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	public ReferenceDisplayList getDisplayList(ModelColumnFramework modelCol, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			ReferenceDisplayList list = new ReferenceDisplayList();
			//TODO should specify relationship as well
			ReferenceTreeList rtl = rtda.getReferenceChildren(modelCol.getModelColumnRefId(), TableNameFramework.REFERENCE, ReferenceRelationshipFramework.REFERENCEGROUP, TableNameFramework.REFERENCEGROUP);
			if (rtl.size() > 0) {
				ReferenceTreeModel rtm = rtl.getFirst();
				ReferenceGroupModel g = rgda.getReferenceGroup(rtm.getTableKeyId());
				return getDisplayList(g.getReferenceGroup(), false);
			}
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}


	/**  
	 *  Update the reference group
	 */ 
	public UpdateResult UpdateReferenceGroup(ReferenceGroupModel group, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		return rgda.UpdateReferenceGroup(group, call);
	}

	/**  
	 *  Get the reference group
	 */ 
	public ReferenceGroupModel getReferenceGroup(int referenceGroupId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			return rgda.getReferenceGroup(referenceGroupId);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	public String getLastUpdated() throws RemoteException, DataAccessException, Exception {
		return "26-Mar-2007";
	}
	
	public ReferenceTreeList getChildTrees(long parentId, ServiceCall call) {
		try {
			return rtda.getReferenceChildren((int)parentId, TableNameFramework.REFERENCE);
		} catch (Exception ex) {
			Debug.LogException("ReferenceMapService", ex);
			return new ReferenceTreeList();
		}
	}
	

	public UpdateResult updateReferenceTree(ReferenceTreeModel m, ServiceCall call) throws Exception {
		UpdateResult result = rtda.UpdateReferenceTree(m, call);
		return result;
	}

	public void storeReferenceHistory(int baselineId, ServiceCall call, DbConnection conn) throws Exception {
		ReferenceList list = rda.getReferenceHistoryList();
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ReferenceModel rtm = (ReferenceModel) e1.nextElement();
			ReferenceHistoryModel h = new ReferenceHistoryModel();
			h.updateWith(rtm);
			h.setHistoryUserId(call.getUserId());
			h.setBaselineId(baselineId);
			rhda.UpdateReferenceHistory(h, call, conn);
		}
	}
}
