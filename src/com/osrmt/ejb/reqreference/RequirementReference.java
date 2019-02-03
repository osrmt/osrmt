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
package com.osrmt.ejb.reqreference;

import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.ui.tree.*;
import java.util.*;
import javax.swing.tree.*;
import com.osframework.modellibrary.reference.group.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.common.*;

public interface RequirementReference extends javax.ejb.EJBObject, IRequirementReference
{
    /**  
     *  Get all reference groups
     */ 
    public ReferenceGroupList getManagedReferenceGroups(boolean systemWizard, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


}
