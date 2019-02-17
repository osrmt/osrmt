/*
    //usage

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
package com.osrmt.ejb.reqmanager;

import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.appclient.ui.tree.*;
import java.util.*;
import javax.swing.tree.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osrmt.modellibrary.reqmanager.*;
import com.osframework.modellibrary.reference.security.*;

public interface LocalRequirementManager extends javax.ejb.EJBLocalObject, IRequirementManager
{
    /**  
     *  Return the list of all related elements to that item e.g. Design, TestCases etc
	 * 
	 * ArtifactList list = getChildren(TableNameGroup.REQUIREMENT, myReqId, call);
	 * 
	 * @param parentTableRefId
	 * @param parentId
	 * @param call
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws DataAccessException
	 * @throws Exception
     */ 
    public ArtifactList getChildren(int parentTableRefId, int parentId, RelationGroup relation, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get all items at the root level in the table
     * 
     * ArtifactList list = getTopLevelChildren(TableNameGroup.FEATURE, call)
     * 
     * @param tableRefId
     * @param call
     * @return
     * @throws java.rmi.RemoteException
     * @throws DataAccessException
     * @throws Exception
     */ 
    public ArtifactList getTopLevelChildren(int productRefId, int artifactRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the immediate children of the same type as the parentTableRefId
     * 
     * @param parentTableRefId
     * @param parentId
     * @param call
     * @return
     * @throws java.rmi.RemoteException
     * @throws DataAccessException
     * @throws Exception
     */ 
    public ArtifactList getSameTypeChildren(int parentTableRefId, int parentId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return the list of all related elements to that item and their children e.g. Design, TestCases etc
	 * 
	 * ArtifactList list = getChildren(TableNameGroup.REQUIREMENT, myReqId, call);
	 * 
	 * @param parentTableRefId
	 * @param parentId
	 * @param call
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws DataAccessException
	 * @throws Exception
     */ 
    public ArtifactList getAllSameChildren(int parentTableRefId, int parentId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return the list of all items in the table
     */ 
    public ArtifactList getAllItems (int tableRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the artifact
     */ 
    public ArtifactModel getArtifact(int id, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update the artifact
     */ 
    public UpdateResult UpdateArtifact(ArtifactModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Add an artifact to a parent
     */ 
    public UpdateResult UpdateArtifact(ArtifactModel m, ArtifactModel parentArtifact, RelationGroup relation, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get a relationship
     */ 
    public RequirementTreeModel getRequirementTree(int parentId, int parentTableRefId, RelationGroup relation, int childId, int childTableRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update a relationship
     */ 
    public UpdateResult UpdateRequirementTree(RequirementTreeModel rtm, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Delete tree
     */ 
    public UpdateResult deleteRelationship(RequirementTreeModel rtm, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return all related artifacts
     */ 
    public ArtifactList getRelatedItems(int artifactId, RelationGroup relation, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return all historic changes for an artifact
     */ 
    public ArtifactHistoryList getHistory(int artifactId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Returns all artifacts untraced to the artifact type
     */ 
    public ArtifactList getArtifactsNotTraced(TraceModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Returns all artifacts trace to the artifact type
     */ 
    public ArtifactList getArtifactsTraced(TraceModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Returns the artifact matrix
     */ 
    public ArtifactMatrix getArtifactMatrix(TraceModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update a relationship
     */ 
    public UpdateResult UpdateRelationship(RequirementTreeModel rtm, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Return the list of steps for the artifact
     */ 
    public ArtifactList getStepChildren(int parentId, ArtifactLevelGroup group, ComponentTypeGroup componentGroup, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update all artifacts
     */ 
    public UpdateResult UpdateArtifact(ArtifactList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update all artifact children
     */ 
    public UpdateResult UpdateArtifact(ArtifactList list, ArtifactModel parentArtifact, RelationGroup relation, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get a baseline
     */ 
    public BaselineModel getBaseline(int baselineId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Add or update a baseline
     */ 
    public UpdateResult UpdateBaseline(BaselineModel baseline, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Create a baseline of all artifacts
     */ 
    public UpdateResult createBaseline(BaselineModel baseline, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get all baselines for the product
     */ 
    public BaselineList getBaselines(int productRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get dependencies i.e. non component child artifacts
     */ 
    public ArtifactList getDependentItems(int artifactId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get items dependent on this one
     */ 
    public ArtifactList getDependentParentItems(int artifactid, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the complete tree model
     */ 
    public UITreeModel getRequirementTreeModel(int productRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get all the parent artifacts in a list ordered from the child
     */ 
    public ArtifactList getParentArtifacts(ArtifactModel artifact, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the types
     */ 
    public ArtifactList getArtifactTypes(int productRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get items dependent on this artifact
     */ 
    public ArtifactList getParentDependentItems(int artifactId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the traceability tree
     */ 
    public UITreeModel getTraceTreeModel(TraceTreeCriteria criteria, ArtifactModel branch, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update the sibling sequence
     */ 
    public UpdateResult updateArtifactSequence(java.util.ArrayList<Integer> list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get partial requirement tree model
     */ 
    public UITreeModel getTopRequirementTreeModel(int productRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get all the related artifacts to artifactId If artfactRefId is 0 then the list of 'artifact types' will be returned. If artifactId is 0 then the top level of artifacts will be returned
     */ 
    public List<UITreeNode> getRelatedArtifacts(int productRefId, int artifactRefId, int artifactId, boolean sameType, boolean getParents, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the most recent date any of the child artifacts have changed
     */ 
    public DbCalendar getAllSameChildrenLastModified(int parentTableRefId, int parentId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the lines to the document
     */ 
    public ArtifactDocumentList getArtifactDocumentLines(int artifactId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update the document lines
     */ 
    public UpdateResult UpdateArtifactDocument(final int artifactId, final ArtifactDocumentList lines, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Delete artifacts
     */ 
    public UpdateResult DeleteArtifacts(List<Integer> artifactIds, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Count number of artifacts that will be delete
     */ 
    public int countArtifactsToDelete(List<Integer> artifactIds, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get an artifact model from history
     */ 
    public ArtifactModel getArtifactFromHistory(long artifactHistoryId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Create an outline using the script for the qualifying artifact ids
     */ 
    public void createOutline(String outlineScript, java.util.Map<Integer, Boolean> qualArtifactIds, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


}
