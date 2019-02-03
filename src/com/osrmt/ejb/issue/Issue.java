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
package com.osrmt.ejb.issue;

import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.ui.tree.*;
import java.util.*;
import javax.swing.tree.*;
import com.osframework.modellibrary.reference.group.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osrmt.modellibrary.issue.*;

public interface Issue extends javax.ejb.EJBObject, IIssue
{
    /**  
     *  Update the issue
     */ 
    public UpdateResult UpdateIssue(IssueModel issue, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get an issue
     */ 
    public IssueModel getIssue(int id, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get issues for an artifact
     */ 
    public IssueList getIssuesByArtifact(int artifactId, IssueTypeGroup issueType, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get all issues for a user
     */ 
    public IssueList getIssuesBySubmitUser(int userId, IssueTypeGroup issueType, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get the issues with a search criteria
     */ 
    public IssueList getIssues(IssueCriteria criterias, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get all issues for groups user belongs to
     */ 
    public IssueList getIssuesByAssignedGroup(int userId, IssueTypeGroup issueType, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


}
