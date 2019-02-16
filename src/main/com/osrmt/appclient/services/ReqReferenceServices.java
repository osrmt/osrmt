package com.osrmt.appclient.services;

import java.rmi.RemoteException;
import java.util.*;

import com.osframework.appclient.services.BaseService;
import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.ejb.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.common.ReferenceMapUtil;
import com.osrmt.ejb.reqmanager.*;
import com.osrmt.ejb.reqreference.IRequirementReference;
import com.osrmt.ejb.reqreference.RequirementReferenceUtil;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.ReferenceGroupList;
import com.osrmt.modellibrary.reqmanager.*;;

/**
 * ReferenceServices provides helper functionality to return interfaces
 * to the business entity beans.  
 */
public class ReqReferenceServices extends BaseService {
	
		

    public static ReferenceGroupList getManagedReferenceGroups(boolean systemWizard) {
		try {
			return getReqReference().getManagedReferenceGroups(systemWizard, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("ReqReferenceServices",ex);
			return new ReferenceGroupList();
		}
    }
    
	public static IRequirementReference getReqReference() throws Exception {
		return RequirementReferenceUtil.getRequirementReference();
	}
	
}
