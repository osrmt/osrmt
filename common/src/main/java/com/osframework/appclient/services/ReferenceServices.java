package com.osframework.appclient.services;

import java.util.Enumeration;

import com.osframework.datalibrary.common.UpdateResult;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.common.ReferenceMapUtil;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.reference.common.ReferenceDisplay;
import com.osframework.modellibrary.reference.common.ReferenceDisplayList;
import com.osframework.modellibrary.reference.common.ReferenceGroupList;
import com.osframework.modellibrary.reference.common.ReferenceGroupModel;
import com.osframework.modellibrary.reference.common.ReferenceList;
import com.osframework.modellibrary.reference.common.ReferenceModel;
import com.osframework.modellibrary.reference.common.ReferenceTreeList;
import com.osframework.modellibrary.reference.common.ReferenceTreeModel;
import com.osframework.modellibrary.reference.group.ModelColumnFramework;
import com.osframework.modellibrary.reference.group.ReferenceGroup;
import com.osframework.modellibrary.reference.group.ReferenceRelationshipFramework;
import com.osframework.modellibrary.reference.group.TableNameFramework;

/**
 * ReferenceServices provides helper functionality to return interfaces
 * to the business entity beans.  
 */
public class ReferenceServices extends BaseService {
	
	
	/**
	 * Returns the active reference id for the given group and displaycode
	 * 
	 * @param group
	 * @param displayCode
	 * @return
	 */
	public static ReferenceDisplayList getDisplayList(String group, String displayCode, boolean includeInactive) {
		try {
			return getReference().getDisplayList(group, displayCode, includeInactive);
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices", ex);
			return new ReferenceDisplayList();
		}
	}
	
	/**
	 * Returns the active reference id for the given group and sub types
	 * 
	 * @param group
	 * @param displayCode
	 * @return
	 */
	public static ReferenceDisplayList getDisplayList(String group, int appTypeRefId, boolean includeInactive) {
		try {
			return getReference().getDisplayList(group, appTypeRefId, includeInactive);
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices", ex);
			return new ReferenceDisplayList();
		}
	}
	
	/**
	 * Returns a reference list with the matching group and display code
	 * 
	 * @param displayCode
	 * @return
	 */
	public static ReferenceList getActiveReferenceList(String group, String displayCode) throws Exception {
		ReferenceList filtered = new ReferenceList();
		ReferenceList list = getActiveReferenceList(group);
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ReferenceModel rm = (ReferenceModel) e1.nextElement();
			if (rm.getDisplayCode() == null && displayCode == null) {
				filtered.add(rm);
			} else if (rm.getDisplayCode() != null && displayCode != null
					&& rm.getDisplayCode().compareTo(displayCode)==0) {
				filtered.add(rm);
			}
		}
		return filtered;
	}
	
	/**
	 * Returns a reference list with the matching group and display code
	 * 
	 * @param displayCode
	 * @return
	 */
	public static ReferenceList getActiveReferenceList(int appTypeRefId, String group, String displayCode) throws Exception {
		ReferenceList filtered = new ReferenceList();
		ReferenceList list = getActiveReferenceList(group, displayCode);
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ReferenceModel rm = (ReferenceModel) e1.nextElement();
			if (rm.getAppTypeRefId() == appTypeRefId) {
				filtered.add(rm);
			}
		}
		return filtered;
	}
	
    /**  
     *  Returns the display list for a group.  If no reference codes
	 * were found then an empty list is returned.
	 * 
	 * @param groupId
	 * @param includeInactive
	 * @throws Exception
     */ 
	public static ReferenceDisplayList getDisplayList(String group, boolean includeInactive) throws Exception {
		try {
			return getReference().getDisplayList(group, includeInactive);
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return new ReferenceDisplayList();
		}
	}
	
    /**  
     *  Return the list of reference for the given parent 
	 * @param parentId
	 * @return
     */ 
	public static ReferenceDisplayList getChildren(int parentId) {
		try {
			return getReference().getChildren(parentId);
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return new ReferenceDisplayList();
		}
	}
	
	/**
     *  Returns the display for the matching ref id.  If no reference codes 
	 * was found for the id then an empty string is returned.
	 * 
	 * @param refId
	 * @return
	 * @throws Exception
	 */
	public static String getDisplay(int refId) {
		if (refId == 0) {
			return "";
		}
		try {
			try {
				if (isCached(refId)) {
					return (String) getCachedResult(refId);
				} else {
					return (String) cache(getReference().getDisplay(refId), refId);					
				}
			} catch (CacheException ce) {
				return getReference().getDisplay(refId);
			}
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return "";
		}
	}
	
	/**
     *  Returns the first display for the matching ref id.  If no reference codes 
	 * was found for the id then an empty string is returned.
	 * 
	 * @param refId
	 * @return
	 * @throws Exception
	 */
	public static ReferenceDisplay getDisplay(String referenceGroup, String displayCode) {
		try {

			ReferenceDisplayList list = getReference().getDisplayList(referenceGroup, displayCode, false);
			return list.getFirst();
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return new ReferenceDisplay();
		}
	}
	
	/**
     *  Returns the first display for the matching ref id.  If no reference codes 
	 * was found for the id then an empty string is returned.
	 * 
	 * @param refId
	 * @return
	 * @throws Exception
	 */
	public static ReferenceDisplay getReferenceDisplay(int refId) {
		ReferenceDisplay rd = new ReferenceDisplay();
		rd.setRefId(refId);
		rd.setDisplay(getDisplay(refId));
		return rd;
	}
	
	/**
	 * Returns an active reference list with the matching group
	 * 
	 * @param displayCode
	 * @return
	 */
	public static ReferenceList getActiveReferenceList(String group){
		try {
			return getReference().getReferenceList(group, false);
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return new ReferenceList();
		}
	}
	
	/**
	 * Returns an active reference list with the matching group
	 * 
	 * @param displayCode
	 * @return
	 */
	public static ReferenceList getActiveReferenceList(int appTypeRefId, String group){
		try {
			ReferenceList filtered = new ReferenceList();
			ReferenceList list = getActiveReferenceList(group);
			Enumeration e1 = list.elements();
			while (e1.hasMoreElements()) {
				ReferenceModel rm = (ReferenceModel) e1.nextElement();
				if (rm.getAppTypeRefId() == appTypeRefId) {
					filtered.add(rm);
				}
			}
			return filtered;
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return new ReferenceList();
		}
	}
	
	public static IReferenceMap getReference() throws Exception {
		return ReferenceMapUtil.getReferenceMap();
	}
	
	public static UpdateResult updateReference(ReferenceModel m, boolean clearCache) throws Exception {
		UpdateResult result = ReferenceMapUtil.getReferenceMap().UpdateReference(m, getServiceCall());
		if (clearCache) {
			SecurityServices.clearCache();
		}
		return result;
	}
	
	public static UpdateResult updateReference(ReferenceModel m) throws Exception {
		return updateReference(m, true);
	}
	

		
	/**
     *  Returns the display for the matching ref id.  If no reference codes 
	 * was found for the id then an empty string is returned.
	 * 
	 * @param refId
	 * @return
	 * @throws Exception
	 */
	public static ReferenceModel getReference(int refId) {
		try {
			try {
				if (isCached(refId)) {
					return (ReferenceModel) getCachedResult(refId);
				} else {
					return (ReferenceModel) cache(getReference().getReference(refId), refId);					
				}
			} catch (CacheException ce) {
				return getReference().getReference(refId);
			}
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return new ReferenceModel();
		}
	}
	
    public static ReferenceDisplayList getDisplayList(String group, String display) {
		try {
			return getReference().getDisplayList(group, display);
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return new ReferenceDisplayList();
		}
    }
	
	public static ReferenceList getReferenceList(String referenceGroup) {
		try {
			return getReference().getReferenceList(referenceGroup, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return new ReferenceList();
		}
	}



	/**  
	 *  Export system reference
	 */ 
	public static ReferenceList exportReference() {
		try { 
			return getReference().exportReference(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return new ReferenceList();
		}
	}


	/**  
	 *  Import reference
	 */ 
	public static int importReference(ReferenceList list) {
		try { 
			return getReference().importReference(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return 0;
		}
	}


	/**  
	 *  Export reference group
	 */ 
	public static ReferenceGroupList exportReferenceGroup() {
		try { 
			return getReference().exportReferenceGroup(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return new ReferenceGroupList();
		}
	}

	/**  
	 *  Import reference groups
	 */ 
	public static int importReferenceGroup(ReferenceGroupList list) {
		try { 
			return getReference().importReferenceGroup(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return 0;
		}
	}


	/**  
	 *  Export reference tree
	 */ 
	public static ReferenceTreeList exportReferenceTreeList() {
		try { 
			return getReference().exportReferenceTreeList(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return new ReferenceTreeList();
		}
	}


	/**  
	 *  Import reference tree
	 */ 
	public static int importReferenceTree(ReferenceTreeList list) {
		try { 
			return getReference().importReferenceTree(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return 0;
		}
	}
	
	public static String getMsg(String msg, String context) {
		return getMsg(msg + ": " + context);
	}
	
	public static String getMsg(int systemMessageRefId, String context) {
		return getDisplay(systemMessageRefId) + ": " + context;
	}
	
	public static String getMsg(int systemMessageRefId) {
		return getDisplay(systemMessageRefId);
	}
	
	/**
	 * Gets the display associated with the system message. 
	 * If the ReferenceModel.getCode(msg) is not found in the reference table
	 * it will be added.
	 * 
	 * @param msg
	 * @return
	 */
	public static String getMsg(String msg) {
		try {
			String displayCode = ReferenceModel.getDisplayCode(msg);
			ReferenceDisplayList list = getReference().getDisplayList(ReferenceGroup.SystemMessage, displayCode, false);
			Enumeration e1 = list.elements();
			if (e1.hasMoreElements()) {
				ReferenceDisplay rd = (ReferenceDisplay) e1.nextElement();
				return rd.getDisplay();
			} else {
				ReferenceModel rm = new ReferenceModel();
				rm.setDisplay(msg);
				rm.setDisplayCode(displayCode);
				rm.setReferenceGroup(ReferenceGroup.SystemMessage);
				updateReference(rm);
				return msg;
			}
		} catch (Exception e) {
			Debug.LogException("ReferenceMapService",e);
			return msg;
		}
	}

	/**  
	 *  Get the list of reference for a column
	 */ 
	public static ReferenceDisplayList getDisplayList(ModelColumnFramework modelCol) {
		try { 
			return getReference().getDisplayList(modelCol, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return null;
		}
	}
	
	public static void setReference(Enumeration e1) {
		try {
			while (e1.hasMoreElements()) {
				IControlModel m = (IControlModel) e1.nextElement();
				m.setReferenceDisplay(getReference(), SecurityServices.getSecurityRef());
			}
		} catch (Exception e) {
			Debug.LogException("ReferenceServices", e);			
		}
	}
	
	public static UpdateResult updateReferenceGroup(ReferenceGroupModel m) throws Exception {
		return ReferenceMapUtil.getReferenceMap().UpdateReferenceGroup(m, getServiceCall());
	}
	
	
	/**  
	 *  Get the reference group
	 */ 
	public static ReferenceGroupModel getReferenceGroup(int referenceGroupId) {
		try { 
			return ReferenceMapUtil.getReferenceMap().getReferenceGroup(referenceGroupId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return null;
		}
	}
	
	public static String getLastUpdated() {
		try { 
			return ReferenceMapUtil.getReferenceMap().getLastUpdated();
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return null;
		}
	}

	public static ReferenceTreeList getChildTrees(int parentId) {
		try {
			return ReferenceMapUtil.getReferenceMap().getChildTrees(parentId, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("ReferenceMapService", ex);
			return new ReferenceTreeList();
		}
	}

	public static UpdateResult updateReferenceTree(ReferenceTreeModel m) throws Exception {
		m.setParentTableRefId(TableNameFramework.REFERENCE);
		m.setRelationshipRefId(ReferenceRelationshipFramework.REFERENCEGROUP);
		m.setTableRefId(TableNameFramework.REFERENCE);
		UpdateResult result = ReferenceMapUtil.getReferenceMap().updateReferenceTree(m, getServiceCall());
		return result;
	}
	

	
}
