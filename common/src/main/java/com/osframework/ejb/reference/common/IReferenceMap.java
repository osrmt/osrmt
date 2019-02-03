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
package com.osframework.ejb.reference.common;

import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.DbConnection;
import com.osframework.datalibrary.common.UpdateResult;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.common.ReferenceDisplay;
import com.osframework.modellibrary.reference.common.ReferenceDisplayList;
import com.osframework.modellibrary.reference.common.ReferenceGroupList;
import com.osframework.modellibrary.reference.common.ReferenceGroupModel;
import com.osframework.modellibrary.reference.common.ReferenceList;
import com.osframework.modellibrary.reference.common.ReferenceModel;
import com.osframework.modellibrary.reference.common.ReferenceTreeList;
import com.osframework.modellibrary.reference.common.ReferenceTreeModel;
import com.osframework.modellibrary.reference.group.ModelColumnFramework;

public interface IReferenceMap
{
    /**  
     *  Returns an list of ReferenceModel matching the group id and including
	 * inactive values if specified.  If no reference codes are found for the
	 * parameters then a valid empty ReferenceList is returned.
	 * 
	 * @param groupId Specify the unique reference group identifier
	 * @param includeInactive set to false to get only active values
	 * @return a collection of ReferenceModel
	 * @throws java.rmi.RemoteException
	 * @throws Exception
     */ 
    public ReferenceList getReferenceList(String group, boolean includeInactive) throws java.rmi.RemoteException, DataAccessException;


    /**  
     *  Returns a ReferenceModel matching the ref id.  If no reference codes 
	 * was found for the id then a valid empty ReferenceModel is returned.
	 * 
	 * @param refId
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws Exception
     */ 
    public ReferenceModel getReference(int refId) throws java.rmi.RemoteException, DataAccessException;


    /**  
     *  Returns the display for the matching ref id.  If no reference codes 
	 * was found for the id then an empty string is returned.
	 * 
	 * @param refId
	 * @return display
	 * @throws java.rmi.RemoteException
	 * @throws Exception
     */ 
    public String getDisplay(int refId) throws java.rmi.RemoteException, DataAccessException;


    /**  
     *  Returns the display list for a group.  If no reference codes
	 * were found then an empty list is returned.
	 * 
	 * @param groupId
	 * @param includeInactive
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws Exception
     */ 
    public ReferenceDisplayList getDisplayList(String group, boolean includeInactive) throws java.rmi.RemoteException, DataAccessException;


    /**  
     *  Returns the display for the matching ref id.  If no reference codes 
	 * was found for the id then an empty model is returned
	 * 
	 * @param refId
	 * @return display
	 * @throws java.rmi.RemoteException
	 * @throws Exception
     */ 
    public ReferenceDisplay getReferenceDisplay(int refId) throws java.rmi.RemoteException, DataAccessException;


    /**  
     *  Return the list of reference for the given parent 
	 * @param parentId
	 * @return
     */ 
    public ReferenceDisplayList getChildren(int parentId) throws java.rmi.RemoteException, DataAccessException;


    /**  
     *  Returns a list of all the ReferenceDisplay children of all
	 * the reference displays in the parentlist.
	 * @param parentList
	 * @return
	 * @throws Exception
     */ 
    public ReferenceDisplayList getChildren(ReferenceDisplayList parentList) throws java.rmi.RemoteException, DataAccessException;


    /**  
     *  Returns the display list for a group.  If no reference codes
	 * were found then an empty list is returned.
	 * 
	 * @param groupId
	 * @param includeInactive
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws Exception
     */ 
    public ReferenceDisplayList getDisplayList(String group, String displayCode, boolean includeInactive) throws java.rmi.RemoteException, DataAccessException;


    /**  
     *  Returns the display list for the subset matching types
     */ 
    public ReferenceDisplayList getDisplayList(String group, int appTypeRefId, boolean includeInactive) throws java.rmi.RemoteException, DataAccessException;


    /**  
     *  Update the reference
     */ 
    public UpdateResult UpdateReference(ReferenceModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get any active reference matching display
     */ 
    public ReferenceDisplayList getDisplayList(String group, String display) throws java.rmi.RemoteException, DataAccessException;


    /**  
     *  Get all reference in the group
     */ 
    public ReferenceList getReferenceList(String referenceGroup, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export system reference
     */ 
    public ReferenceList exportReference(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import reference
     */ 
    public int importReference(ReferenceList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export reference group
     */ 
    public ReferenceGroupList exportReferenceGroup(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import reference groups
     */ 
    public int importReferenceGroup(ReferenceGroupList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export reference tree
     */ 
    public ReferenceTreeList exportReferenceTreeList(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import reference tree
     */ 
    public int importReferenceTree(ReferenceTreeList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the list of reference for a column
     */ 
    public ReferenceDisplayList getDisplayList(ModelColumnFramework modelCol, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update the reference group
     */ 
    public UpdateResult UpdateReferenceGroup(ReferenceGroupModel group, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the reference group
     */ 
    public ReferenceGroupModel getReferenceGroup(int referenceGroupId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the last update from server
     */ 
    public String getLastUpdated() throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the child tree entries
     */ 
    public ReferenceTreeList getChildTrees(long parentId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update a reference tree entry
     */ 
    public UpdateResult updateReferenceTree(ReferenceTreeModel tree, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Store a backup of the reference
     */ 
    public void storeReferenceHistory(int baselineId, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception;


}
