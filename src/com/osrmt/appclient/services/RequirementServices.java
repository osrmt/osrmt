package com.osrmt.appclient.services;

import java.io.File;
import java.rmi.RemoteException;
import java.util.*;

import com.osframework.appclient.ui.tree.*;
import com.osframework.appclient.services.BaseService;
import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.UpdateResult;
import com.osrmt.appclient.artifact.form.ArtifactExportModel;
import com.osrmt.appclient.common.*;
import com.osrmt.ejb.reqmanager.*;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.SerializeUtility;
import com.osframework.modellibrary.common.*;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.modellibrary.reference.group.*;

/**
 * ReferenceServices provides helper functionality to return interfaces
 * to the business entity beans.  
 */
public class RequirementServices extends BaseService {
	
		
	
    /**  
     *  Return the list of reference for the given parent 
	 * @param parentId
	 * @return
     */ 
	public static ArtifactList getChildren(int parentTableRefId, int parentId, RelationGroup relation, ServiceCall call) {
		try {
			return getReqManager().getChildren(parentTableRefId, parentId, relation, call);
		} catch (Exception ex) {
			Debug.LogException("RequirementServices",ex);
			return new ArtifactList();
		}
	}

    /**  
     *  Return the list of reference for the given parent 
	 * @param parentId
	 * @return
     */ 
	public static ArtifactList getChildren(int parentTableRefId, int parentId, RelationGroup relation) {
		return getChildren(parentTableRefId, parentId, relation, getServiceCall());
	}

    /**  
     *  Return the list of reference for the given parent 
	 * @param parentId
	 * @return
     */ 
	public static ArtifactList getSameTypeChildren(int parentTableRefId, int parentId) {
			return getSameTypeChildren(parentTableRefId, parentId, getServiceCall());
	}

    /**  
     *  Return the list of reference for the given parent 
	 * @param parentId
	 * @return
     */ 
	public static ArtifactList getSameTypeChildren(int parentTableRefId, int parentId, ServiceCall call) {
		try {
			return getReqManager().getSameTypeChildren(parentTableRefId, parentId, call);
		} catch (Exception ex) {
			Debug.LogException("RequirementServices",ex);
			return new ArtifactList();
		}
	}

	public static ArtifactList getTopLevelChildren(int productRefId, int artifactRefid) {
		return getTopLevelChildren(productRefId, artifactRefid, getServiceCall());
	}
	
	public static ArtifactList getTopLevelChildren(int productRefId, int artifactRefid, ServiceCall call) {
		try {
			return getReqManager().getTopLevelChildren(productRefId, artifactRefid, call);
		} catch (Exception ex) {
			Debug.LogException("RequirementServices",ex);
			return new ArtifactList();
		}
	}
	
	public static ArtifactList getAllItems(int artifactRefid) {
		try {
			return getReqManager().getAllItems(artifactRefid, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("RequirementServices",ex);
			return new ArtifactList();
		}
	}
	
	public static IRequirementManager getReqManager() throws Exception {
		return RequirementManagerUtil.getRequirementManager();
	}
		

   
    public static boolean MoveArtifact (int oldParentId, int oldParentTableRefId, int newParentId, int newParentTableRefId,int childId, int childTableRefId) {
    	try {
    		//Update existing relationship
    		if (newParentTableRefId != oldParentTableRefId) {
    			return false;
    		} else if (oldParentId > 0 && newParentId > 0) {
	    		RequirementTreeModel rtm = getReqManager().getRequirementTree(oldParentId, oldParentTableRefId, RelationGroup.get(RelationGroup.RELATED), childId, childTableRefId, getServiceCall() ); 
	    		rtm.setParentId(newParentId);
	    		if (oldParentTableRefId != newParentTableRefId) {
	    			rtm.setRelationRefId(RelationGroup.DEPENDENT);
	    		}
	    		return getReqManager().UpdateRequirementTree(rtm, getServiceCall()).getRowsUpdated() == 1;
    		} else if (newParentId > 0){
    			// Create new relationship
    			RequirementTreeModel rtm = new RequirementTreeModel();
    			rtm.setChildId(childId);
    			rtm.setParentId(newParentId);
    			rtm.setChildArtifactRefId(childTableRefId);
    			rtm.setParentArtifactRefId(newParentTableRefId);
    			rtm.setRelationRefId(RelationGroup.RELATED);
	    		return getReqManager().UpdateRequirementTree(rtm, getServiceCall()).getRowsUpdated() == 1;
    		} else if (oldParentId > 0){
    			// Delete relationship
    			RequirementTreeModel rtm = new RequirementTreeModel();
    			rtm.setChildId(childId);
    			rtm.setParentId(oldParentId);
    			rtm.setRelationRefId(RelationGroup.RELATED);
	    		return getReqManager().deleteRelationship(rtm, getServiceCall()).getRowsUpdated() == 1;
    		}  else {
    			// ignore not needed
    			return true;
    		}
    	} catch (Exception ex) {
    		Debug.LogException("RequirementServices", ex);
    		return false;
    	}
    }


    /**  
     *  Update the design
     */ 
    public static UpdateResult UpdateArtifact(ArtifactModel m) {
    	return UpdateArtifact(m, getServiceCall());
    }
    
    public static UpdateResult DeleteArtifacts(List<Integer> artifactIds) throws Exception {
    	return getReqManager().DeleteArtifacts(artifactIds, getServiceCall());
    }
    
    /**  
     *  Update the design
     */ 
    public static UpdateResult UpdateArtifact(ArtifactModel m, ServiceCall call) {
    	try {
    		return getReqManager().UpdateArtifact(m, call);
    	} catch (Exception ex) {
    		Debug.LogException("RequirementServices", ex);
    		return new UpdateResult();
    	}
    }
    
    /**  
     *  Update the design
     */ 
    public static UpdateResult UpdateArtifact(ArtifactList list) {
    	try {
    		return UpdateArtifact(list, true);
    	} catch (Exception ex) {
    		Debug.LogException("RequirementServices", ex);
    		return new UpdateResult();
    	}
    }
    
    /**  
     *  Update the design
     */ 
    public static UpdateResult UpdateArtifact(ArtifactList list, boolean suppressException) throws Exception {
    	try {
    		return getReqManager().UpdateArtifact(list, getServiceCall());
    	} catch (Exception ex) {
    		if (suppressException) {
        		Debug.LogException("RequirementServices", ex);
        		return new UpdateResult();
    		} else {
    			throw ex;
    		}    		
    	}
    }
    
    /**  
     *  Update the design
     * @throws Exception 
     * @throws DataAccessException 
     * @throws RemoteException 
     */ 
    public static UpdateResult UpdateArtifact(ArtifactModel m, ArtifactModel parentArtifact, RelationGroup relation) throws RemoteException, DataAccessException, Exception {
    	return UpdateArtifact(m, parentArtifact, relation, getServiceCall());
    }
    
    /**  
     *  Update the design
     * @throws Exception 
     * @throws DataAccessException 
     * @throws RemoteException 
     */ 
    public static UpdateResult UpdateArtifact(ArtifactModel m, ArtifactModel parentArtifact, RelationGroup relation, ServiceCall call) throws RemoteException, DataAccessException, Exception {
    	return getReqManager().UpdateArtifact(m, parentArtifact, relation, call);
    }
    
	public static UpdateResult deleteRelationship(RequirementTreeModel rtm) {
		try {
			return getReqManager().deleteRelationship(rtm, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("ReqquirementServices", ex);
			return new UpdateResult();
		}
	}
	
	public static ArtifactModel getArtifact(int id) {
		if (id == 0) {
			return new ArtifactModel();
		} else {
			return getArtifact(id, getServiceCall());
		}
	}
	
	public static ArtifactModel getArtifact(int id, ServiceCall call) {
		try {
			return getReqManager().getArtifact(id, call);
		} catch (Exception ex) {
			Debug.LogException("RequirementServices", ex);
			return new ArtifactModel();
		}
	}
	
	public static ArtifactList getRelatedItems(int artifactId, int relationRefId) {
		try {			
			
			return getReqManager().getRelatedItems(artifactId, RelationGroup.get(relationRefId), getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("RequirementServices",ex);
			return new ArtifactList();
		}
	}
	
	public static ArtifactHistoryList getHistory(int artifactId) {
		try {
			if (artifactId > 0) {
				return getReqManager().getHistory(artifactId, getServiceCall());
			} else {
				return new ArtifactHistoryList();
			}
		} catch (Exception ex) {
			Debug.LogException("RequirementServices",ex);
			return new ArtifactHistoryList();
		}
	}

	public static ArtifactList getArtifactsNotTraced(TraceModel m) {
		try {
			return getReqManager().getArtifactsNotTraced(m, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("RequirementServices", ex);
			return new ArtifactList();
		}		
	}

	public static ArtifactList getArtifactsTraced(TraceModel m)   {
		try {
			return getReqManager().getArtifactsTraced(m, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("RequirementServices", ex);
			return new ArtifactList();
		}		
	}

	public static ArtifactMatrix getArtifactMatrix(TraceModel m) {
		try {
			return getReqManager().getArtifactMatrix(m, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("RequirementServices", ex);
			return new ArtifactMatrix(new ArtifactList(),new ArtifactList(), new RequirementTreeList());
		}		
	}

	public static UpdateResult UpdateRelationship(RequirementTreeModel rtm) throws Exception {
		try {
			return getReqManager().UpdateRequirementTree(rtm, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("RequirementServices", ex);
			throw ex;
		}		
	}


	//RequirementManagerService
	/**  
	 *  Return the list of steps for the artifact
	 */ 
	public static ArtifactList getStepChildren(int parentId, ComponentTypeGroup componentGroup) {
		try { 			
			ArtifactLevelGroup group = ArtifactLevelGroup.get(ArtifactLevelGroup.COMPONENT);
			return getReqManager().getStepChildren(parentId, group, componentGroup, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("RequirementServices", e);
			return null;
		}
	}
	
	/**  
	 *  Update all artifact children
	 */ 
	public static UpdateResult UpdateArtifact(ArtifactList list, ArtifactModel parentArtifact, RelationGroup relation, boolean suppressException) throws Exception {
		try { 
			return getReqManager().UpdateArtifact(list, parentArtifact, relation, getServiceCall());
		} catch (Exception e) {
			if (suppressException) {
				Debug.LogException("RequirementServices", e);
				return new UpdateResult();
			} else {
				throw e;
			}
		}
	}

	/**  
	 *  Create a baseline of all artifacts
	 */ 
	public static UpdateResult createBaseline(String baselineName) {
		try { 
			BaselineModel baseline = new BaselineModel();
			baseline.setBaselineName(baselineName);
			return getReqManager().createBaseline(baseline, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("RequirementServices", e);
			return null;
		}
	}
	
	/**  
	 *  Get all baselines for the product
	 */ 
	public static BaselineList getBaselines(int productRefId) {
		try { 
			return getReqManager().getBaselines(productRefId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return new BaselineList();
		}
	}
	
	/**  
	 *  Get dependencies i.e. non component child artifacts
	 */ 
	public static ArtifactList getDependentItems(int artifactId) {
		try { 
			return getReqManager().getDependentItems(artifactId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return new ArtifactList();
		}
	}
	
	/**  
	 *  Get dependencies i.e. non component child artifacts
	 */ 
	public static ArtifactList getParentDependentItems(int artifactId) {
		try { 
			return getReqManager().getParentDependentItems(artifactId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return new ArtifactList();
		}
	}
	
	/**  
	 *  Get dependencies i.e. non component child artifacts
	 */ 
	public static ArtifactList getDependentParentItems(int artifactId) {
		try { 
			return getReqManager().getDependentParentItems(artifactId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return new ArtifactList();
		}
	}
	
	public static boolean exportArtifacts(ArtifactList list, ArtifactExportModel export) {
		File file = null;
		try {
			if (export.isRemovePrimaryKey()) {
				removePrimaryKey(list);
			}
			file = new File(export.getFilePath());
			file.createNewFile();
			SerializeUtility.serializeXML(file, list.elements());
			return true;
		} catch (Exception ex) {
			Debug.LogException("RequirementServices", ex);
			return false;
		}
	}
	
	private static void removePrimaryKey(ArtifactList list) {
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			am.setArtifactId(0);
			am.setArtifactNbr(0);
		}
	}
	
	public static ArtifactList importArtifacts(String filename) {
		File file = null;
		Vector v = new Vector();
		try {
			file = new File(filename);
			SerializeUtility.deserializeXML(file, v);
			ArtifactList list = new ArtifactList();
			Enumeration e1 = v.elements();
			while (e1.hasMoreElements()) {
				ArtifactModel am = (ArtifactModel) e1.nextElement();
				if (ApplicationObject.getApplicationProductRefId() > 0) {
					am.setProductRefId(ApplicationObject.getApplicationProductRefId());
				}
				list.add(am);
			}
			return list;
		} catch (Exception ex) {
			Debug.LogException("RequirementServices", ex);
			return new ArtifactList();
		}
	}
	
	/**  
	 *  Get the complete tree model
	 */ 
	public static UITreeModel getRequirementTreeModel(int productRefId) {
		return getRequirementTreeModel(productRefId, getServiceCall());
	}

	/**  
	 *  Get the complete tree model
	 */ 
	public static UITreeModel getRequirementTreeModel(int productRefId, ServiceCall call) {
		try { 
			return getReqManager().getRequirementTreeModel(productRefId, call);
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return null;
		}
	}

	/**  
	 *  Add the children to the node and return the same node
	public static UITreeNode addChildren(UITreeNode node, ArtifactModel m) {
		try { 
			return getReqManager().addChildren(node, m, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return new UITreeNode();
		}
	}
	 */ 

	/**  
	 *  Get all the parent artifacts in a list ordered from the child
	 */ 
	public static ArtifactList getParentArtifacts(ArtifactModel artifact) {
		return getParentArtifacts(artifact, getServiceCall(0));
	}

	/**  
	 *  Get all the parent artifacts in a list ordered from the child
	 */ 
	public static ArtifactList getParentArtifacts(ArtifactModel artifact, ServiceCall call) {
		try { 
			if (artifact == null) {
				throw new NullArtifactException();
			}
			return getReqManager().getParentArtifacts(artifact, call);
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return null;
		}
	}

	/**  
	 *  Get the types
	 */ 
	public static ArtifactList getArtifactTypes(ServiceCall call) {
		try { 
			
			return getReqManager().getArtifactTypes(((ApplicationObject) call.getApplication()).getProductRefId(), call);
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return null;
		}
	}


	/**  
	 *  Get the types
	 */ 
	public static ArtifactList getArtifactTypes() {
		return getArtifactTypes(getServiceCall());
	}
	
	public static UITreeModel getTraceTreeModel(TraceTreeCriteria criteria) {
		return getTraceTreeModel(criteria, null, getServiceCall());
	}

	public static UITreeModel getTraceTreeModel(TraceTreeCriteria criteria, ArtifactModel branch, ServiceCall call) {
		try { 
			return getReqManager().getTraceTreeModel(criteria, branch, call);
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return null;
		}
	}
	
	public static UITreeModel getTraceTreeModel(TraceTreeCriteria criteria, ArtifactModel branch) {
		try { 
			return getReqManager().getTraceTreeModel(criteria, branch, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return null;
		}
	}
	
	public static UITreeModel getTraceTreeModel(TraceTreeCriteria criteria, ServiceCall call) {
		try { 
			return getReqManager().getTraceTreeModel(criteria, null, call);
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return null;
		}
	}
	
	public static UpdateResult updateArtifactSequence(java.util.ArrayList list) {
		try { 
			return getReqManager().updateArtifactSequence(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return new UpdateResult();
		}
	}
	
	/**  
	 *  Get the complete tree model
	 */ 
	public static UITreeModel getTopRequirementTreeModel(int productRefId) {
		try { 
			return getReqManager().getTopRequirementTreeModel(productRefId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("RequirementManagerService", e);
			return null;
		}
	}

	/**  
	 *  Get all the related artifacts to artifactId If artfactRefId is 0 then the list of 'artifact types' will be returned. If artifactId is 0 then the top level of artifacts will be returned
	 */ 
	public static List<UITreeNode> getRelatedArtifacts(int productRefId, ArtifactModel artifact, boolean sameType, boolean getParents) throws Exception {
		try { 
			return getReqManager().getRelatedArtifacts(productRefId, artifact.getArtifactRefId(), artifact.getArtifactId(), sameType, getParents, getServiceCall());
		} catch (Exception e) { 
			throw e;
		}
	}
	
	public static ArtifactList getAllSameChildren(int parentTableRefId, int parentId) {
		try {
			ArtifactList list = null;
			if (parentId == 0) {
				list = getReqManager().getAllItems(parentTableRefId, getServiceCall());
			} else {
				list = getReqManager().getAllSameChildren(parentTableRefId, parentId, getServiceCall());
			}
			
			return list;
		} catch (Exception ex) {
			Debug.LogException("RequirementServices",ex);
			return new ArtifactList();
		}
	}
	
	public static ArtifactDocumentList getArtifactDocumentLines(int artifactId) throws Exception {
		return getReqManager().getArtifactDocumentLines(artifactId, getServiceCall());
	}

	public static UpdateResult UpdateArtifactDocument(final int artifactId, final ArtifactDocumentList lines) throws RemoteException, DataAccessException, Exception {
		return getReqManager().UpdateArtifactDocument(artifactId, lines, getServiceCall());
	}
	
	public static ArtifactList getImmediateSameChildren(int productRefId, int parentTableRefId, int parentId) {
		try {
			ArtifactList list = null;
			if (parentId == 0) {
				list = getReqManager().getTopLevelChildren(productRefId, parentTableRefId, getServiceCall());
			} else {
				list = getReqManager().getChildren(parentTableRefId, parentId, RelationGroup.get(RelationGroup.RELATED), getServiceCall());
			}
			
			return list;
		} catch (Exception ex) {
			Debug.LogException("RequirementServices",ex);
			return new ArtifactList();
		}
	}
	
	public static int countArtifactsToDelete(List<Integer> artifactIds) throws Exception {
		return getReqManager().countArtifactsToDelete(artifactIds, getServiceCall());
	}

	public static ArtifactModel getArtifactFromHistory(int id) {
		try {
			return getReqManager().getArtifactFromHistory(id, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("RequirementServices", ex);
			return null;
		}
	}
	
}
