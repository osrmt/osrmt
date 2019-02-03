//************ AUTO GENERATED DO NOT EDIT *********//
//************ UNLESS YOU SET OVERWRITE IND = 0 IN TABLE EJBLIBRARY *********//
package com.osrmt.ejb.reqmanager;

import javax.ejb.*;

import java.rmi.RemoteException;
import java.util.*;

import com.osframework.appclient.ui.tree.UITreeModel;
import com.osframework.ejb.reference.common.*;
import com.osframework.ejb.system.*;
import com.osframework.framework.logging.*;
import com.osframework.appclient.ui.tree.*;
import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.framework.*;
import  com.osframework.framework.utility.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.system.*;
import com.osframework.ejb.common.*;
import com.osframework.modellibrary.reference.common.ReferenceModel;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.RecordTypeFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.group.ProductFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.datalibrary.reqmanager.*;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.appclient.setting.DataFormatSetting;


public class RequirementManagerBean extends BaseBean implements EntityBean, IRequirementManager {

	private EntityContext context= null;
	static final long serialVersionUID = 1L;
	private RequirementTreeDataAdapter rtda;
	private RequirementTreeHistoryDataAdapter rthda;
	private ArtifactDataAdapter ada;
	private ArtifactHistoryDataAdapter ahda;
	private BaselineDataAdapter bda;
	private ArtifactDocumentDataAdapter adda;
   	private ISystem system;
   	
   	private static RequirementManagerBean requirementBean2Tier = null;

   	private IReferenceMap reference;

	public RequirementManagerBean() {
		try {
			requirementBean2Tier = this;
			reference = ReferenceMapBean.get2TierInstance();
			system = SystemBean.get2TierInstance();
			rtda = new RequirementTreeDataAdapter(reference, security);
			rthda = new RequirementTreeHistoryDataAdapter(reference, security);
			ada = new ArtifactDataAdapter(reference, security);
			ahda = new ArtifactHistoryDataAdapter(reference, security);
			bda = new BaselineDataAdapter(reference, security);
			adda = new ArtifactDocumentDataAdapter(reference, security);
		} catch (Exception e) {
			Debug.LogException(this, e);
		}
	}

	public static RequirementManagerBean get2TierInstance() throws Exception {
		if (requirementBean2Tier == null) {
			requirementBean2Tier = new RequirementManagerBean();
		}
		return requirementBean2Tier;
	}

	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	public void loadCache() {

	}

	public String ejbFind() {
		return "RequirementManagerBean";
}

	public void unsetEntityContext() {
		this.context = null;
}

	public void ejbStore() {
		
	}

	public void ejbLoad() {

	}
	public String ejbCreate() {
		return "RequirementManagerBean";
	}
	public void ejbPostCreate() {

	}
	public String ejbFindByPrimaryKey(String key) {
		return "RequirementManagerBean";
	}

	public void ejbRemove() {

	} 

	public void ejbActivate() {

	}

	public void ejbPassivate() {

	}

    /**
     * Get all items at the root level in the table
     * 
     * ITreeDisplayList list = getTopLevelChildren(TableNameGroup.FEATURE, call)
     * 
     * @param artifactRefId
     * @param call
     * @return
     * @throws java.rmi.RemoteException
     * @throws DataAccessException
     * @throws Exception
     */
	public ArtifactList getTopLevelChildren(int productRefId, int artifactRefId, ServiceCall call) throws DataAccessException, Exception {
		try {
			startService(call);
			ArtifactList list = ada.getTopLevelArtifacts(artifactRefId, productRefId);
			setReference(list, artifactRefId);
			stopService(call);
			return list;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	public void setReference(ArtifactList list, int artifactRefId) throws Exception {
		list.setReferenceDisplay(reference, security);
		/*RecordExtensionColumnList cols = reference.getRecordExtensionColumnList(TableNameGroup.ARTIFACT, artifactRefId);
		//cols.setReferenceDisplay(reference, security);
		//list.setRecordExtensionColumnList(cols);
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel m = (ArtifactModel) e1.nextElement();
			//RecordExtensionList rel = reference.getRecordExtensionList(TableNameGroup.ARTIFACT, m.getArtifactId());
			//rel.setReferenceDisplay(reference, security);
			//m.setRecordExtensionList(rel);
		}
		*/
	}

	/**  
	 *  Return the child features, requirements etc. for a given node in the tree
	 */ 
	public ArtifactList getSameTypeChildren(int parentTableRefId, int parentId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		startService(call);
		ArtifactList list = ada.getSameTypeChildren(parentTableRefId, parentId);
		setReference(list, parentTableRefId);
		stopService(call);
		return list;

	}
	
	/**  
	 *  Return the child features, requirements etc. for a given node in the tree
	 */ 
	public ArtifactList getDependentChildren(int parentTableRefId, int parentId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		startService(call);
		ArtifactList list = ada.getDependentChildren(parentTableRefId, parentId);
		setReference(list, parentTableRefId);
		stopService(call);
		return list;

	}
	
	/**  
	 *  Return the child features, requirements etc. for a given node in the tree
	 */ 
	public ArtifactList getDependentParents(int childTableRefId, int childId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		startService(call);
		ArtifactList list = ada.getDependentParents(childTableRefId, childId);
		setReference(list, childTableRefId);
		stopService(call);
		return list;

	}
	
	/**
	 * Return the list of all related elements to that item e.g. Design, TestCases etc
	 * 
	 * ITreeDisplayList list = getChildren(TableNameGroup.REQUIREMENT, myReqId, call);
	 * 
	 * @param parentTableRefId
	 * @param parentId
	 * @param call
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws DataAccessException
	 * @throws Exception
	 */
	public ArtifactList getChildren(int artifactRefId, int parentId, RelationGroup relation, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call); 
			RequirementTreeList treeList = rtda.getChildren(artifactRefId, parentId, relation, false);
			ArtifactList list = getItems(treeList, true, true);
			stopService(call);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	private void getAllSameChildren(ArtifactList list, ArtifactModel m, Hashtable<Integer, Integer> treeMap, ServiceCall call) throws Exception {
		
		if (treeMap.containsKey("" + m.getArtifactId())) {
			Debug.LogWarning(this, reference.getDisplay(SystemMessageFramework.CIRCULARTREEREFERENCESKIPPING) + " " + m.getArtifactId());
			return;
		} else {
			treeMap.put(new Integer(m.getArtifactId()),new Integer(m.getArtifactId()));
			list.add(m);
		}
		ArtifactList childlist = getSameTypeChildren(m.getArtifactRefId(), m.getArtifactId(), call);
		Enumeration e1 = childlist.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			getAllSameChildren(list, am, treeMap, call);
		}
	}
	
    /**
     * Get the immediate children of the same type as the parentTableRefId
     * 
     * @param parentTableRefId
     * @param parentId
     * @param call
     * @return
     * @throws java.rmi.RemoteException
     * @throws DataAccessException
     * @throws Exception
     */
	public ArtifactList getAllSameChildren(int parentTableRefId, int parentId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call); 
			//TODO far too complicated reference display setting
			//and extension setting need to be done at a different level
			ArtifactList list = new ArtifactList();
			ArtifactModel m = getArtifact(parentId, call);
			getAllSameChildren(list, m, new Hashtable(), call);
					
			//RecordExtensionColumnList cols = reference.getRecordExtensionColumnList(TableNameGroup.ARTIFACT, parentTableRefId);
			//cols.setReferenceDisplay(reference, security);
			//list.setRecordExtensionColumnList(cols);
			stopService(call);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	public ArtifactList getAllItems (int artifactRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactList list = new ArtifactList(); 
			ArtifactList allitems = ada.getAllArtifacts(artifactRefId, getApplicationObject(call).getProductRefId());
			Enumeration e1 = allitems.elements();
			while (e1.hasMoreElements()) {
				ArtifactModel a = (ArtifactModel) e1.nextElement();
				list.add(a);
			}
			setReference(list, artifactRefId);
			stopService(call);
			return list; 
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}		
	}
	
	/**
	 * Create a generic displaylist of all the items 
	 * 
	 * @param rtl
	 * @return
	 * @throws DataAccessException
	private ArtifactList getItems(RequirementTreeList rtl, boolean skipComponent, boolean getChild) throws Exception {
		ArtifactList list = new ArtifactList();
		Enumeration enum1 = rtl.elements();
		int artifactRefId = 0;
		while (enum1.hasMoreElements()) {
			RequirementTreeModel itd = (RequirementTreeModel) enum1.nextElement();
			ArtifactModel a = null;
			if (getChild) {
				a = ada.getArtifact(itd.getChildId());
			} else {
				a = ada.getArtifact(itd.getParentId());
			}
			a.setReferenceDisplay(reference, security);
			//a.setRecordExtensionList(reference.getRecordExtensionList(TableNameGroup.ARTIFACT, a.getArtifactId()));
			artifactRefId = a.getArtifactRefId();
			if (skipComponent && a.getArtifactLevelRefId() == ArtifactLevelGroup.COMPONENT) {
				//skip
			} else {
				list.add(a);
			}
		}
		if (list.size() > 0) {
			//TODO ArtifactList is only valid if all artifacts same type
			setReference(list, artifactRefId);
		}
		return list;
	}
	 */
	
	
	private ArtifactList getItems(RequirementTreeList rtl, boolean skipComponent, boolean getChild) throws Exception {
		Enumeration enum1 = rtl.elements();
		int artifactRefId = 0;
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		while (enum1.hasMoreElements()) {
			RequirementTreeModel itd = (RequirementTreeModel) enum1.nextElement();
			ArtifactModel a = null;
			if (getChild) {
				if (!first) sb.append(", ");
				sb.append(itd.getChildId());
			} else {
				if (!first) sb.append(", ");
				sb.append(itd.getParentId());
			}
			first = false;
		}
		if (sb.toString().length() == 0) {
			return new ArtifactList(1);
		}
		String sql = "select * from artifact where artifact_id in ("
				+ sb.toString() + ") ";
		if (skipComponent) {
			sql += " and not artifact_level_ref_id = " + ArtifactLevelGroup.COMPONENT;
		}
        sql += " order by artifact_seq";
		ArtifactList list = ada.getArtifact(sql);
		if (list.size() > 0) {
			//TODO ArtifactList is only valid if all artifacts same type
			setReference(list, artifactRefId);
		}
		return list;
	}
	
	private void inactivate(ArtifactModel a, DbConnection conn) throws Exception {
		try {
			// get the parent of the same type
			RequirementTreeModel rtm = rtda.getParent(a.getArtifactRefId(), a.getArtifactId());
			if (rtm.getParentId() > 0) {
				rtda.setNewParent(rtm.getParentId(), a.getArtifactId(), a.getArtifactRefId());
			}
			rtda.inactivateRelationships(a.getArtifactId(), a.getArtifactRefId(), conn);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}


	public RequirementTreeModel getRequirementTree(int parentId, int parentTableRefId, RelationGroup relation, int childId, int childTableRefId, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			RequirementTreeModel rtm = rtda.getRequirementTree(parentId, parentTableRefId, relation, childId, childTableRefId);
			stopService(call);
			return rtm;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	public UpdateResult deleteRelationship(RequirementTreeModel rtm, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			UpdateResult result = new UpdateResult();
			if (rtm.getRequirementTreeId()==0) {
				rtm = rtda.getRelationship(rtm.getChildId(), rtm.getParentId(), RelationGroup.get(rtm.getRelationRefId()));
			}
			if (rtm.getRequirementTreeId() > 0) {
				result = rtda.deleteRelationship(rtm);
			}
			stopService(call);
			return result;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}


	public UpdateResult UpdateRequirementTree(RequirementTreeModel rtm, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			rtm.setRecordTypeRefId(RecordTypeFramework.USERRECORD);
			UpdateResult result = new UpdateResult();
			if (rtm.getChildId() == 0 || rtm.getParentId() ==0) {
				Debug.displayGUIMessage(reference.getDisplay(SystemMessageFramework.NORELATIONSHIPSTOTHETOPLEVELALLOWED));
				throw new IllegalArgumentException(reference.getDisplay(SystemMessageFramework.NORELATIONSHIPSTOTHETOPLEVELALLOWED));
			}
			if (rtm.isNotActive()) {
				result = rtda.deleteRelationship(rtm);
			} else {
				if (rtm.getRequirementTreeId() == 0 && rtm.getRelationRefId() == 0) {
					throw new IllegalArgumentException();
				}
				result = UpdateRelationship(rtm, call);
			}
			stopService(call);
			return result;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}


	public UpdateResult UpdateArtifact(ArtifactModel m, ArtifactModel parentArtifact, RelationGroup relation, ServiceCall call, DbConnection conn) throws RemoteException, DataAccessException, Exception {
		m.setRecordTypeRefId(RecordTypeFramework.USERRECORD);
		UpdateResult result = UpdateArtifact(m, call, conn);
		RequirementTreeModel rtm = rtda.getRequirementTree(parentArtifact.getArtifactId(), parentArtifact.getArtifactRefId(), relation, m.getArtifactId(), m.getArtifactRefId(), conn);
		if (rtm.isNew()) {
			RequirementTreeList list = rtda.getChildren(parentArtifact.getArtifactRefId(), parentArtifact.getArtifactId(), relation, true, conn);
			Enumeration e1 = list.elements();
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while (e1.hasMoreElements()) {
				RequirementTreeModel child = (RequirementTreeModel) e1.nextElement();
				ids.add(child.getChildId());
			}
			rtm.setChildId(m.getArtifactId());
			rtm.setChildArtifactRefId(m.getArtifactRefId());
			rtm.setParentId(parentArtifact.getArtifactId());
			rtm.setParentArtifactRefId(parentArtifact.getArtifactRefId());
			rtm.setRelationRefId(relation.getRelationRefId());
			rtda.UpdateRequirementTree(rtm, call, conn);
			ids.add(m.getArtifactId());
			if (m.getArtifactLevelRefId() != ArtifactLevelGroup.COMPONENT) {
				ada.updateArtifactSequence(ids, conn);
			}
		}
		return result;
	}

	public UpdateResult UpdateArtifact(final ArtifactModel m, final ArtifactModel parentArtifact, final RelationGroup relation, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		BeanTransaction transaction = new BeanTransaction() {
			protected void execute(ServiceCall call, DbConnection conn) throws Exception {
				setUpdateResult(UpdateArtifact(m, parentArtifact, relation, call, conn));
				rtda.removeInactiveRelationships(conn);
			}
		};
		transaction.start(call);
		return transaction.getUpdateResult();
	}


	public UpdateResult UpdateArtifact(ArtifactModel m, ServiceCall call, DbConnection conn) throws RemoteException, DataAccessException, Exception {
		m.setRecordTypeRefId(RecordTypeFramework.USERRECORD);
		// need better place to set data type
		if (m.getProductRefId() == 0) {
			m.setProductRefId(getApplicationObject(call).getProductRefId());
		}
		if (m.getArtifactNbr() == 0) {
			m.setArtifactNbr(ada.getNextArtifactNbr(m.getArtifactRefId(), m.getProductRefId(), conn));
		}
		ArtifactModel orig = getArtifact(m.getArtifactId(), conn, call);
		if (!orig.isNew() && !m.isEqualTo(orig)) {
			ArtifactHistoryModel hist = new ArtifactHistoryModel();
			hist.updateWith(orig);
			hist.setHistoryUserId(call.getUserId());
			// save change reason
			hist.setChangeReason(m.getChangeReason());
			ahda.UpdateArtifactHistory(hist, call, conn);
		}
		// never edit a artifact with an existing change reason
		m.setChangeReason(null);
		UpdateResult result = ada.UpdateArtifact(m, call, conn);
		// inactivated artifacts need handling
		if (m.isNotActive()) inactivate(m, conn);
		return result;
	}

	public UpdateResult UpdateArtifact(final ArtifactModel m, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		BeanTransaction transaction = new BeanTransaction() {
			protected void execute(ServiceCall call, DbConnection conn) throws Exception {
				setUpdateResult(UpdateArtifact(m, call, conn));
				rtda.removeInactiveRelationships(conn);
			}
		};
		transaction.start(call);
		return transaction.getUpdateResult();
	}


	public ArtifactModel getArtifact(int id, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactModel a = ada.getArtifact(id);
			a.setReferenceDisplay(reference, security);
			//a.setRecordExtensionList(reference.getRecordExtensionList(TableNameGroup.ARTIFACT, a.getArtifactId()));
			stopService(call);
			return a;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	public ArtifactModel getArtifact(int id, DbConnection conn, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactModel a = ada.getArtifact(id, conn);
			a.setReferenceDisplay(reference, security);
			//a.setRecordExtensionList(reference.getRecordExtensionList(TableNameGroup.ARTIFACT, a.getArtifactId()));
			stopService(call);
			return a;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	public ArtifactList getRelatedItems (int artifactId, RelationGroup relation, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactList list = ada.getRelatedArtifacts(artifactId, relation);
			setReference(list, artifactId);
			stopService(call);
			return list; 
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}		
	}
	
	public ArtifactList getDependentItems (int artifactId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactList list = ada.getDependentArtifacts(artifactId);
			setReference(list, artifactId);
			stopService(call);
			return list; 
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}		
	}
	
	/**  
	 *  Get items dependent on this artifact
	 */ 
	public ArtifactList getParentDependentItems(int artifactId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactList list = ada.getDependentParentArtifacts(artifactId);
			setReference(list, artifactId);
			stopService(call);
			return list; 
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}		 
	}
	
	public ArtifactList getDependentParentItems (int artifactId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactList list = ada.getDependentParentArtifacts(artifactId);
			setReference(list, artifactId);
			stopService(call);
			return list; 
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}		
	}
	
	private ApplicationObject getApplicationObject(ServiceCall call) {
		if (call.getApplication() != null) {
			try {
				ApplicationObject ao = (ApplicationObject) call.getApplication();
				return ao;
			} catch (Exception ex) {
				Debug.LogException(this, ex);
			}
		}
		return new ApplicationObject();
	}

	public ArtifactHistoryList getHistory(int artifactId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactHistoryList list = ahda.getHistory(artifactId);	
			list.setReferenceDisplay(reference, security);
			stopService(call);
			return list;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}		
	}
	
	public ArtifactList getArtifactsNotTraced(TraceModel m, ServiceCall call)  throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactList listFrom = ada.getAllArtifacts(m.getTraceFromArtifactRefId(), m.getProductRefId());	
			ArtifactList listTo = ada.getAllArtifacts(m.getTraceToArtifactRefId(), m.getProductRefId());
			RequirementTreeList tree = rtda.getEntireTree(RelationGroup.get(RelationGroup.DEPENDENT));
			ArtifactMatrix matrix = new ArtifactMatrix(listFrom, listTo, tree);
			ArtifactList notTraced = matrix.getNotTraced();
			stopService(call);
			return notTraced;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}		
	}
	
	public ArtifactList getArtifactsTraced(TraceModel m, ServiceCall call)  throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactList listFrom = ada.getAllArtifacts(m.getTraceFromArtifactRefId(), m.getProductRefId());	
			ArtifactList listTo = ada.getAllArtifacts(m.getTraceToArtifactRefId(), m.getProductRefId());
			RequirementTreeList tree = rtda.getEntireTree(RelationGroup.get(RelationGroup.DEPENDENT));
			//list.setReferenceDisplay(reference, security);
			ArtifactMatrix matrix = new ArtifactMatrix(listFrom, listTo, tree);
			ArtifactList traced = matrix.getTraced();
			stopService(call);
			return traced;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}		
	}
	
	public ArtifactMatrix getArtifactMatrix(TraceModel m, ServiceCall call)  throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactList listFrom = ada.getAllArtifacts(m.getTraceFromArtifactRefId(), m.getProductRefId());	
			ArtifactList listTo = ada.getAllArtifacts(m.getTraceToArtifactRefId(), m.getProductRefId());
			RequirementTreeList tree = rtda.getEntireTree(RelationGroup.get(RelationGroup.DEPENDENT));
			//list.setReferenceDisplay(reference, security);
			ArtifactMatrix matrix = new ArtifactMatrix(listFrom, listTo, tree);
			matrix.buildMatrix();
			stopService(call);
			return matrix;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}		
	}
	
	
	public UpdateResult UpdateRelationship(RequirementTreeModel rtm, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			if (rtm.getRequirementTreeId() == 0) {
				RequirementTreeModel rtmcheck = this.getRequirementTree(rtm.getParentId(), rtm.getParentArtifactRefId(),
						RelationGroup.get(rtm.getRelationRefId()), rtm.getChildId(), rtm.getChildArtifactRefId(), call);
				// relationship exists
				if (rtmcheck.getRequirementTreeId() > 0) {
					return new UpdateResult();
				}
			}
			rtm.setRecordTypeRefId(RecordTypeFramework.USERRECORD);
			return rtda.UpdateRequirementTree(rtm, call);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}		
	}

	//RequirementManagerBean
	/**  
	 *  Return the list of steps for the artifact
	 */ 
	public ArtifactList getStepChildren(int parentId,  ArtifactLevelGroup group, ComponentTypeGroup componentGroup, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call); 
			ArtifactList list = ada.getStepChildren(parentId, group, componentGroup);
			setReference(list, list.getFirst().getArtifactRefId());
			stopService(call);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}	
	
	/**  
	 *  Update all artifacts
	 */ 
	public UpdateResult UpdateArtifact(ArtifactList list, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			Enumeration e1 = list.elements();
			int rowsUpdated = 0;
			//TODO really shouldnt assume product ref id
			int nextArtifactNbr = ada.getNextArtifactNbr(list.getFirst().getArtifactRefId(), getApplicationObject(call).getProductRefId(), conn);
			while (e1.hasMoreElements()) {
				ArtifactModel m = (ArtifactModel) e1.nextElement();
				if (m.getArtifactNbr() == 0) {
					m.setArtifactNbr(nextArtifactNbr);
					nextArtifactNbr++;
				}
				rowsUpdated += UpdateArtifact(m, call, conn).getRowsUpdated();
			}
			stopService(call);
			return new UpdateResult(rowsUpdated,0);
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	public UpdateResult UpdateArtifact(final ArtifactList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		BeanTransaction transaction = new BeanTransaction() {
			protected void execute(ServiceCall call, DbConnection conn) throws Exception {
				UpdateArtifact(list, call, conn);
				rtda.removeInactiveRelationships(conn);
			}
		};
		transaction.start(call);
		return transaction.getUpdateResult();
	}
	
	public UpdateResult UpdateArtifact(ArtifactList list, ArtifactModel parentArtifact, RelationGroup relation, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		Enumeration e1 = list.elements();
		int rowsUpdated = 0;
		int nextArtifactNbr = ada.getNextArtifactNbr(list.getFirst().getArtifactRefId(), getApplicationObject(call).getProductRefId(), conn);
		while (e1.hasMoreElements()) {
			ArtifactModel m = (ArtifactModel) e1.nextElement();
			if (m.getArtifactNbr()==0) {
				m.setArtifactNbr(nextArtifactNbr);
				nextArtifactNbr++;
			}
			rowsUpdated += UpdateArtifact(m, parentArtifact, relation, call, conn).getRowsUpdated();
		}
		return new UpdateResult(rowsUpdated, 0);
	}
	
	public UpdateResult UpdateArtifact(final ArtifactList list, final ArtifactModel parentArtifact, final RelationGroup relation, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		BeanTransaction transaction = new BeanTransaction() {
			protected void execute(ServiceCall call, DbConnection conn) throws Exception {
				UpdateArtifact(list, parentArtifact, relation, call, conn);
				rtda.removeInactiveRelationships(conn);
			}
		};
		transaction.start(call);
		return transaction.getUpdateResult();
	}


	/**  
	 *  Get a baseline
	 */ 
	public BaselineModel getBaseline(int baselineId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			BaselineModel bm = bda.getBaseline(baselineId);
			stopService(call);
			return bm;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	/**  
	 *  Add or update a baseline
	 */ 
	public UpdateResult UpdateBaseline(BaselineModel baseline, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		baseline.setRecordTypeRefId(RecordTypeFramework.USERRECORD);
		return bda.UpdateBaseline(baseline, call, conn);
	}

	/**  
	 *  Add or update a baseline
	 */ 
	public UpdateResult UpdateBaseline(final BaselineModel baseline, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		BeanTransaction transaction = new BeanTransaction() {
			protected void execute(ServiceCall call, DbConnection conn) throws Exception {
				setUpdateResult(UpdateBaseline(baseline, call, conn));
			}
		};
		transaction.start(call);
		return transaction.getUpdateResult();
	}
	

	/**  
	 *  Create a baseline of all artifacts
	 */ 
	public UpdateResult createBaseline(BaselineModel baseline, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		int productRefId = getApplicationObject(call).getProductRefId();
		
		UpdateResult result = new UpdateResult();
		if (productRefId > 0) {
			ArtifactList list = ada.getAllArtifacts(productRefId);
			BaselineModel lastBaseline = bda.getLastBaseline(productRefId, conn);			
			BaselineModel checkBaseline = bda.getBaselineByName(baseline.getBaselineName(), productRefId);
			//TODO add isPersisted
			if (!checkBaseline.isNew()) {
				throw new BaselineExistsException(baseline.getBaselineName() + ":" + baseline.getBaselineId());
			} else {
				//TODO move to client - standardize updates
				baseline.setProductRefId(productRefId);
				int newBaselineId = bda.UpdateBaseline(baseline, call, conn).getPrimaryKeyId();
				result.setPrimaryKeyId(newBaselineId);
				Enumeration e1 = list.elements();
				while (e1.hasMoreElements()) {
					ArtifactModel m = (ArtifactModel) e1.nextElement();
					if (m.getUpdateDt().compareTo(lastBaseline.getBaselineDt())>0) {
						m.setLastUpdatedBaselineId(lastBaseline.getBaselineId());
					}
					ArtifactHistoryModel hist = new ArtifactHistoryModel();
					hist.updateWith(m);
					hist.setBaselineId(newBaselineId);
					hist.setHistoryUserId(call.getUserId());			
					ahda.UpdateArtifactHistory(hist, call, conn);
				}
				// requirement tree
				storeRequirementTreeHistory(productRefId, newBaselineId, call, conn);
				// reference
				reference.storeReferenceHistory(newBaselineId, call, conn);
				// files
				RecordFileList files = system.getProductFiles(productRefId, call);
				String tempDirName = system.createTempBaselineDirectory(productRefId, files, call);
				system.renameBaselineDirectory(tempDirName, newBaselineId, files, conn, call);
			}
		}
		return result;
	}
	
	private void storeRequirementTreeHistory(int productRefId, int baselineId, ServiceCall call, DbConnection conn) throws Exception {
		RequirementTreeList list = rtda.getEntireTree(productRefId);
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			RequirementTreeModel rtm = (RequirementTreeModel) e1.nextElement();
			RequirementTreeHistoryModel h = new RequirementTreeHistoryModel();
			h.updateWith(rtm);
			h.setHistoryUserId(call.getUserId());
			h.setBaselineId(baselineId);
			rthda.UpdateRequirementTreeHistory(h, call, conn);
		}
	}



	/**  
	 *  Create a baseline of all artifacts
	 */ 
	public UpdateResult createBaseline(final BaselineModel baseline, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		BeanTransaction transaction = new BeanTransaction() {
			protected void execute(ServiceCall call, DbConnection conn) throws Exception {
				setUpdateResult(createBaseline(baseline, call, conn));
			}
		};
		transaction.start(call);
		return transaction.getUpdateResult();
	}

	/**  
	 *  Get all baselines for the product
	 */ 
	public BaselineList getBaselines(int productRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			BaselineList list = bda.getBaselines(productRefId);
			stopService(call);
			return list;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	private UITreeNode addChildren(int productRefId, UITreeNode node, ArtifactModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		return addChildren(productRefId, node,m, call, new Hashtable());
	}
	
	
	class CachedRequirementTree {
		private Hashtable parenttable = null;
		private Hashtable childtable = null;
		public CachedRequirementTree(RequirementTreeList tree) {
			buildParentCache(tree);
			buildChildCache(tree);
		}
		public void buildParentCache(RequirementTreeList tree) {
			parenttable = new Hashtable(tree.size()*2);
			Enumeration e1 = tree.elements();
			while (e1.hasMoreElements()) {
				RequirementTreeModel rtm = (RequirementTreeModel) e1.nextElement();
				String key = ArtifactMatrix.getKey(rtm.getParentId(), rtm.getParentArtifactRefId());
				RequirementTreeList list = new RequirementTreeList();
				if (parenttable.containsKey(key)) {
					list = (RequirementTreeList) parenttable.get(key);
				}
				list.add(rtm);
				parenttable.put(key,list);
			}
		}
		
		public void buildChildCache(RequirementTreeList tree) {
			childtable = new Hashtable(tree.size()*2);
			Enumeration e1 = tree.elements();
			while (e1.hasMoreElements()) {
				RequirementTreeModel rtm = (RequirementTreeModel) e1.nextElement();
				String key = ArtifactMatrix.getKey(rtm.getChildId(), rtm.getChildArtifactRefId());
				RequirementTreeList list = new RequirementTreeList();
				if (childtable.containsKey(key)) {
					list = (RequirementTreeList) childtable.get(key);
				}
				list.add(rtm);
				childtable.put(key,list);
			}
		}
		
		public RequirementTreeList getParentChildren(int parentId, int parentArtifactRefId, RelationGroup relation) {
			String key = ArtifactMatrix.getKey(parentId, parentArtifactRefId);
			RequirementTreeList list = new RequirementTreeList();
			if (parenttable.containsKey(key)) {
				RequirementTreeList clist = (RequirementTreeList) parenttable.get(key);
				Enumeration e1 = clist.elements();
				while (e1.hasMoreElements()) {
					RequirementTreeModel rtm = (RequirementTreeModel) e1.nextElement();
					if (rtm.getRelationRefId() == relation.getRelationRefId()) {
						list.add(rtm);
					}
				}
			}
			return list;
		}
		public RequirementTreeList getChildParents(int childId, int childArtifactRefId, RelationGroup relation) {
			String key = ArtifactMatrix.getKey(childId, childArtifactRefId);
			RequirementTreeList list = new RequirementTreeList();
			if (childtable.containsKey(key)) {
				RequirementTreeList clist = (RequirementTreeList) childtable.get(key);
				Enumeration e1 = clist.elements();
				while (e1.hasMoreElements()) {
					RequirementTreeModel rtm = (RequirementTreeModel) e1.nextElement();
					if (rtm.getRelationRefId() == relation.getRelationRefId()) {
						list.add(rtm);
					}
				}
			}
			return list;
		}
	}
	
	private UITreeNode addChildren(int productRefId, UITreeNode node, ArtifactModel m, ServiceCall call, Hashtable<Integer, Integer> treeMap) throws java.rmi.RemoteException, DataAccessException, Exception {
		return addChildren(productRefId, node,m,call,treeMap, null, new ConstrainChildren());
	}
	
	public UITreeModel getRequirementTreeModel(int productRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
			// root node
		UITreeNode root = new UITreeNode();
			if (productRefId == 0) {
				root.setUserObject(reference.getDisplay(SystemMessageFramework.OPENAPRODUCTTOSTART));
			} else {
				root.setUserObject(reference.getDisplay(productRefId));
			}
			if (productRefId != 0) {
				addChildren(productRefId, root, new ArtifactModel(), call, new Hashtable(), new CachedRequirementTree(rtda.getEntireTree(RelationGroup.get(RelationGroup.RELATED))), new ConstrainChildren());
			}
			com.osframework.framework.utility.TimingUtility.stopTransaction("getRequirementTreeModel");
			return new UITreeModel(root);
	}

	/**  
	 *  Return the child features, requirements etc. for a given node in the tree
	 */ 
	public ArtifactList getSameTypeChildren(int parentTableRefId, int parentId, CachedRequirementTree cache) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			RequirementTreeList treeList = cache.getParentChildren(parentId, parentTableRefId, RelationGroup.get(RelationGroup.RELATED));
			ArtifactList list = getItems(treeList, true, true);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Return the child features, requirements etc. for a given node in the tree
	 */ 
	public ArtifactList getDependentChildren(int parentTableRefId, int parentId, CachedRequirementTree cache) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			RequirementTreeList treeList = cache.getParentChildren(parentId, parentTableRefId, RelationGroup.get(RelationGroup.DEPENDENT));
			ArtifactList list = getItems(treeList, true, true);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Return the child features, requirements etc. for a given node in the tree
	 */ 
	public ArtifactList getDependentParents(int childTableRefId, int childId, CachedRequirementTree cache) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			RequirementTreeList treeList = cache.getChildParents(childId, childTableRefId, RelationGroup.get(RelationGroup.DEPENDENT));
			ArtifactList list = getItems(treeList, true, false);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	public ArtifactList getArtifactTypes(int productRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		ArtifactList list = new ArtifactList();
		ApplicationSecurityList asl = security.getAppSecurity(ApplicationFramework.get(ApplicationGroup.ARTIFACTFORM), call);
		Enumeration e1 = asl.elements();
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel acm = (ApplicationSecurityModel) e1.nextElement();
			ApplicationViewModel avm = security.getApplicationView(acm.getApplicationViewId(), call);
			ArtifactModel am = new ArtifactModel();
			am.setProductRefId(productRefId);
			am.setArtifactRefId(avm.getAppTypeRefId());
			am.setArtifactName(avm.getAppTypeRefDisplay());
			list.add(am);
		} 
		return list;
	}
	
	class ConstrainChildren {
		public boolean sameType = true;
		public int depth = 0;
		public int maxDepth = 10;
		public boolean getChildren = true;
		public boolean noCycles = true;
		private int[] targetList =null;
		public void setTargetArtifact(Vector artifactRefIdList) {
			if (artifactRefIdList == null) {
				return;
			}
			targetList = new int[artifactRefIdList.size()];
			for (int i=0; i<artifactRefIdList.size(); i++) {
				targetList[i] = ((Integer)artifactRefIdList.elementAt(i)).intValue();
			}
		}
		public boolean validArtifact(int artifactRefId) {
			if (targetList == null) {
				return true;
			} else {
				for (int i=0; i<targetList.length; i++) {
					if (artifactRefId == targetList[i]) {
						return true;
					}
				}
				return false;
			}
		}
	}
	
	
	private UITreeNode addChildren(int productRefId, UITreeNode node, ArtifactModel m, ServiceCall call, Hashtable<Integer, Integer>  treeMap, CachedRequirementTree cache, ConstrainChildren constraint) throws java.rmi.RemoteException, DataAccessException, Exception {
		DataFormatSetting.initialize();
		ArtifactList list = new ArtifactList();
		constraint.depth++;
		if (m.getArtifactRefId() == 0) {
			list = getArtifactTypes(productRefId, call);
		} else if (m.getArtifactId() ==0) {
			list = getTopLevelChildren(productRefId, m.getArtifactRefId(), call);
		} else {
			if ((treeMap.containsKey(new Integer(m.getArtifactId())) && constraint.noCycles)
					|| (!constraint.noCycles && constraint.depth > constraint.maxDepth)) {
				//Debug.LogError("RequirementServices", reference.getDisplay(SystemMessageFramework.CIRCULARTREEREFERENCESKIPPING) + m.getArtifactId());
				return node;
			} else {
				treeMap.put(new Integer(m.getArtifactId()), new Integer(m.getArtifactId()));
			}
			if (cache == null) {
				if (constraint.sameType) {
					list = getSameTypeChildren(m.getArtifactRefId(), m.getArtifactId(), call);
				} else {
					list = getDependentChildren(m.getArtifactRefId(), m.getArtifactId(), call);
				}
			} else {
				if (constraint.sameType) {
					list = getSameTypeChildren(m.getArtifactRefId(), m.getArtifactId(), cache);
				} else {
					if (constraint.getChildren) {
						list = getDependentChildren(m.getArtifactRefId(), m.getArtifactId(), cache);
					} else {
						list = getDependentParents(m.getArtifactRefId(), m.getArtifactId(), cache);
					}
				}
			}
		}
		list.sortList();
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			am.setLongToStringName(m.isLongToStringName());
			UITreeNode n = new UITreeNode(am);
			addChildren(productRefId, n, am, call, treeMap, cache, constraint);
			if (constraint.validArtifact(am.getArtifactRefId())) {
				node.add(n);
			}
		}
		constraint.depth--;
		return node;

	}

	/**  
	 *  Return the child features, requirements etc. for a given node in the tree
	 */ 
	public ArtifactModel getParent(int childRefId, int childId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call); 
			RequirementTreeModel treeParent = rtda.getParent(childRefId, childId);
			ArtifactModel am = ada.getArtifact(treeParent.getParentId());
			stopService(call);
			return am;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	


	public ArtifactList getParentArtifacts(ArtifactModel artifact, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			ArtifactList list = new ArtifactList();
			startService(call);
			ArtifactModel parent = getParent(artifact.getArtifactRefId(), artifact.getArtifactId(), call);
			while (parent.getArtifactId() > 0) {
				list.add(parent);
				parent = getParent(parent.getArtifactRefId(), parent.getArtifactId(), call);
			}
			stopService(call);
			return list;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	/**  
	 *  Get the traceability tree
	 */ 
	public UITreeModel getTraceTreeModel(TraceTreeCriteria criteria, ArtifactModel branch, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		// root node
		int productRefId = getApplicationObject(call).getProductRefId();
		UITreeNode root = new UITreeNode();
		if (productRefId == 0) {
			root.setUserObject(reference.getDisplay(SystemMessageFramework.OPENAPRODUCTTOSTART));
		} else {
			root.setUserObject(reference.getDisplay(productRefId));
		}
		ConstrainChildren constraint = getConstraint(criteria);
		if (productRefId != 0) {
			ArtifactList starterList = ada.getStarterArtifacts(criteria.getTraceFromArtifactRefId(),
					criteria.getStartTraceWith(), getApplicationObject(call).getProductRefId());
			if (branch != null) {
				starterList = getDescendentMatch(branch, starterList, call);
			}
			Hashtable<Integer, Integer> treeMap = new Hashtable<Integer, Integer>(1024*1024);
			CachedRequirementTree cache = new CachedRequirementTree(rtda.getEntireTree());
			Enumeration e1 = starterList.elements();
			while (e1.hasMoreElements()) {
				ArtifactModel am = (ArtifactModel) e1.nextElement();
				am.setLongToStringName(true);
				UITreeNode node = new UITreeNode(am);
				constraint.depth = 0;
				addChildren(productRefId, node, am, call, treeMap, cache, constraint);
				root.add(node);
			}
		}
		return new UITreeModel(root);
	}
	
	private ConstrainChildren getConstraint(TraceTreeCriteria criteria) {
		ConstrainChildren constraint = new ConstrainChildren();
		constraint.depth = 0;
		constraint.getChildren = criteria.getTraceDirection().getTraceTreeRefId() == TraceTreeGroup.TRACEDOWNTO;
		constraint.sameType = false;
		constraint.noCycles = !criteria.isAllowCircularDependencies();
		constraint.maxDepth = criteria.getMaxTreeDepth();
		if (criteria.getTraceOnlyTo() != null) {
			Vector v = new Vector();
			Enumeration e1 =criteria.getTraceOnlyTo().elements();
			while (e1.hasMoreElements()) {
				ReferenceModel rm = (ReferenceModel) e1.nextElement();
				v.add(new Integer(rm.getRefId()));
			}
			constraint.setTargetArtifact(v);
		} else {
			criteria.setTraceOnlyTo(null);
		}
		return constraint;
	}



	public UpdateResult updateArtifactSequence(final ArrayList<Integer> list, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		;
		BeanTransaction transaction = new BeanTransaction() {
			protected void execute(ServiceCall call, DbConnection conn) throws Exception {
				ada.updateArtifactSequence(list, conn);
			}
		};
		transaction.start(call);
		return transaction.getUpdateResult();
	}

	/**
	 * Get the 
	 */
	public UITreeModel getTopRequirementTreeModel(int productRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		UITreeNode root = new UITreeNode();
		if (productRefId == 0) {
			root.setUserObject(reference.getDisplay(SystemMessageFramework.OPENAPRODUCTTOSTART));
		} else {
			root.setUserObject(reference.getDisplay(productRefId));
		}
		if (productRefId != 0) {
			List<UITreeNode> artifactTypes = getRelatedArtifacts(productRefId, 0, 0, true, false, call);
			for (UITreeNode node : artifactTypes) {
				root.add(node);
			}
		}
		return new UITreeModel(root);
	}

	/**
	 * Get all the related artifacts to artifactId
	 * If artfactRefId is 0 then the list of 'artifact types' will be returned.
	 * If artifactId is 0 then the top level of artifacts will be returned
	 * 
	 * @param artifactRefId should match artifactId's artifact ref id
	 * @param artifactId parent (or child depending if getting parents)
	 * @param sameType get only the same type of artifacts as the artifactId
	 * @param getParents get parents instead of child artifacts
	 * 
	 * @param call
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws DataAccessException
	 * @throws Exception
	 */
	public List<UITreeNode> getRelatedArtifacts(int productRefId, int artifactRefId, int artifactId, boolean sameType, boolean getParents, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		DataFormatSetting.initialize();
		ArtifactList list = null;
		if (artifactRefId == 0) {
			list = getArtifactTypes(productRefId, call);
		} else if (artifactId == 0) {
			list = getTopLevelChildren(productRefId, artifactRefId, call);
		} else {
			if (sameType) {
				list = ada.getSameTypeTreeChildren(artifactRefId, artifactId);
			} else {
				if (getParents) {
					list = getDependentParents(artifactRefId, artifactId, call);
				} else {
					list = getDependentChildren(artifactRefId, artifactId, call);
				}
			}
		}
		List<UITreeNode> nodes = new ArrayList<UITreeNode>(list.size());
		
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			UITreeNode n = new UITreeNode(am);
			nodes.add(n);
		}
		return nodes;
	}
	
	/**
	 * Get the time of the most recent child artifact
	 * 
	 * @param parentTableRefId
	 * @param parentId
	 * @param call
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws DataAccessException
	 * @throws Exception
	 */
	public DbCalendar getAllSameChildrenLastModified(int parentTableRefId, int parentId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ArtifactModel am = ada.getAllSameChildrenLastModified(parentTableRefId, parentId);			
			stopService(call);
			return am.getUpdateDt();			
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}



	public ArtifactDocumentList getArtifactDocumentLines(int artifactId, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactDocumentList list = new ArtifactDocumentList();
			Enumeration e1 = adda.getArtifactDocumentList(artifactId).elements();
			while (e1.hasMoreElements()) {
				ArtifactDocumentModel adm = (ArtifactDocumentModel) e1.nextElement();
				if (adm.getImageRecordFileId() > 0) {
					adm.setImageFile(system.getFiles(TableNameGroup.ARTIFACTDOCUMENT, adm.getArtifactDocumentId(), call).getFirst());
				}
				list.add(adm);
			}
			stopService(call);
			return list;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}


	public UpdateResult UpdateArtifactDocument(final int artifactId, final ArtifactDocumentList lines, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		BeanTransaction transaction = new BeanTransaction() {
			protected void execute(ServiceCall call, DbConnection conn) throws Exception {
				String sql = "delete from artifact_document where artifact_id = ?";
				Vector params = new Vector(1);
				params.add(new Integer(artifactId));
				Db.getAccess().executeUpdate(sql, params, conn);
				setUpdateResult(UpdateArtifactDocument(artifactId, lines, conn, call));
			}
		};
		transaction.start(call);
		return transaction.getUpdateResult();
	}

	private UpdateResult UpdateArtifactDocument(final int artifactId, final ArtifactDocumentList lines, DbConnection conn, ServiceCall call) throws  Exception {
		UpdateResult result = new UpdateResult();
		Enumeration e1 = lines.elements();
		while (e1.hasMoreElements()) {
			ArtifactDocumentModel line = (ArtifactDocumentModel) e1.nextElement();
			line.setArtifactId(artifactId);
			line.setArtifactDocumentId(0);//insert
			adda.UpdateArtifactDocument(line, call, conn);
		}
		return result;
	}
	
    public UpdateResult DeleteArtifacts(List<Integer> artifactIds, ServiceCall call) throws Exception {
    	ArtifactList tempList = new ArtifactList(artifactIds.size()*2);
    	for (Integer artifactId : artifactIds) {
    		ArtifactModel am = this.getArtifact(artifactId, call);
    		tempList.addAll(this.getAllSameChildren(am.getArtifactRefId(), artifactId, call));
    	    tempList.add(am);
    	}
    	java.util.Map map = new Hashtable();
    	ArtifactList deleteList = new ArtifactList(tempList.size());
    	Enumeration e1 = tempList.elements();
    	while (e1.hasMoreElements()) {
    		ArtifactModel am = (ArtifactModel) e1.nextElement();
    		if (!map.containsKey(am.getArtifactId())) {
        		am.setNotActive();
    			deleteList.add(am);
    			map.put(am.getArtifactId(), new Boolean(true));
    		}
    	}
    	return this.UpdateArtifact(deleteList, call);
    }
    
	public int countArtifactsToDelete(List<Integer> artifactIds, ServiceCall call) throws Exception {
    	ArtifactList tempList = new ArtifactList(artifactIds.size()*2);
    	for (Integer artifactId : artifactIds) {
    		ArtifactModel am = this.getArtifact(artifactId, call);
    		tempList.addAll(this.getAllSameChildren(am.getArtifactRefId(), artifactId, call));
    	    tempList.add(am);
    	}
    	java.util.Map map = new Hashtable();
    	ArtifactList deleteList = new ArtifactList(tempList.size());
    	Enumeration e1 = tempList.elements();
    	while (e1.hasMoreElements()) {
    		ArtifactModel am = (ArtifactModel) e1.nextElement();
    		if (!map.containsKey(am.getArtifactId())) {
        		am.setNotActive();
    			deleteList.add(am);
    			map.put(am.getArtifactId(), new Boolean(true));
    		}
    	}
		return deleteList.size();
	}

	public ArtifactModel getArtifactFromHistory(long artifactHistoryId, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ArtifactModel copy = new ArtifactModel();
			ArtifactHistoryModel a = ahda.getArtifactHistory((int) artifactHistoryId);
			a.setReferenceDisplay(reference, security);
			copy.updateWith(a);
			//a.setRecordExtensionList(reference.getRecordExtensionList(TableNameGroup.ARTIFACT, a.getArtifactId()));
			stopService(call);
			return copy;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	private ArtifactList getDescendentMatch(ArtifactModel branch, ArtifactList starterList, ServiceCall call) throws Exception {
		ArtifactList descendents = this.getAllSameChildren(branch.getArtifactRefId(), branch.getArtifactId(), call);
		ArtifactList matchList = new ArtifactList(starterList.size());
		Map<Integer,Boolean> map = getMap(descendents);
		Enumeration e1 = starterList.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			if (map.containsKey(am.getArtifactId())) {
				matchList.add(am);
			}
		}
		return matchList;
	}
	
	private Map<Integer, Boolean> getMap(ArtifactList list) {
		Map<Integer,Boolean> map = new Hashtable<Integer, Boolean>();
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			map.put(am.getArtifactId(), new Boolean(true));
		}
		return map;
	}
	
	private int outlineReportSeq = 0;
	// TODO Note only one person at a time can run a report with outline creation

	public void createOutline(String outlineScript, Map<Integer, Boolean> qualArtifactIds, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		ReferenceDisplayList products = security.getProductList(call);
		ReferenceList artifacts = reference.getReferenceList("Artifact", false);
		Enumeration e1 = products.elements();
		outlineReportSeq = 0;
		while (e1.hasMoreElements()) {
			ReferenceDisplay product = (ReferenceDisplay) e1.nextElement();
			Enumeration e2 = artifacts.elements();
			while (e2.hasMoreElements()) {
				ReferenceModel artRef = (ReferenceModel) e2.nextElement();
				List<Integer> trail = new ArrayList<Integer>();
				ArtifactList topLevel = this.getTopLevelChildren(product.getRefId(), artRef.getRefId(), call);
				createOutline(topLevel, 0, trail, product.getRefId(), artRef.getRefId(), outlineScript, qualArtifactIds, call);
			}
		}
	}
	
	private void createOutline(ArtifactList artifacts, int depth, List<Integer> trail, int productRefId, int artifactRefId, String outlineScript, Map<Integer, Boolean> qualArtifactIds, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		depth++;
		trail.add(depth+1);
		Enumeration e1 = artifacts.elements();
		int previous = 0;
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			trail.set(depth-1, previous);
			if (qualArtifactIds.containsKey(am.getArtifactId())) {
				String outline = getOutline(trail, depth, previous, am, outlineScript);
				ada.updateArtifactOutline(am, outline);
				previous++;
			}
			ada.updateArtifactReportSeq(am, outlineReportSeq);
			outlineReportSeq++;
			ArtifactList children = this.getSameTypeChildren(am.getArtifactRefId(), am.getArtifactId(), call);
			createOutline(children, depth, trail, productRefId, am.getArtifactRefId(), outlineScript, qualArtifactIds, call);
		}
		depth--;
	}
	
	private String getOutline(List<Integer> trail, int depth, int previous, ArtifactModel artifact, String outlineScript) {
		List<Integer> currentTrail = new ArrayList<Integer>(depth);
		for (int i=0; i<depth; i++) {
			currentTrail.add(trail.get(i)+1);
		}
		ControlScript controlScript = new ControlScript();
		controlScript.addReference(currentTrail, "list");
		controlScript.addReference(artifact, "artifact");
		return (String) controlScript.execute(outlineScript, String.class);
	}

}
