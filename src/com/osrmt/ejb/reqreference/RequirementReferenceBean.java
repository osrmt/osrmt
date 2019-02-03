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
//************ UNLESS YOU SET OVERWRITE IND = 0 IN TABLE EJBLIBRARY *********//
package com.osrmt.ejb.reqreference;

import java.util.Enumeration;

import javax.ejb.*;
import com.osframework.framework.logging.*;
import com.osframework.datalibrary.common.*;
import com.osframework.datalibrary.reference.common.ReferenceGroupDataAdapter;
import com.osframework.modellibrary.common.*;
import com.osframework.ejb.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.common.ReferenceMapBean;
import com.osframework.ejb.reference.common.ReferenceMapUtil;
import com.osframework.ejb.system.SystemUtil;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.common.*;
import com.osrmt.ejb.issue.IssueBean;
import com.osrmt.modellibrary.reference.group.ReferenceCategoryGroup;
import com.osrmt.modellibrary.reference.group.ReferenceModificationGroup;



public class RequirementReferenceBean extends BaseBean implements EntityBean, IRequirementReference {

	private EntityContext context= null;
	static final long serialVersionUID = 1L;
	private ReferenceGroupDataAdapter rgda;
	private IReferenceMap reference;
	private static RequirementReferenceBean reqrefBean2Tier = null;
	
	public RequirementReferenceBean() {
		try {
			reference = ReferenceMapBean.get2TierInstance();
			rgda = new ReferenceGroupDataAdapter(reference, security);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	public static RequirementReferenceBean get2TierInstance() throws Exception {
		if (reqrefBean2Tier == null) {
			reqrefBean2Tier = new RequirementReferenceBean();
		}
		return reqrefBean2Tier;
	}

	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	public void loadCache() {

	}

	public String ejbFind() {
		return "ReferenceSearchBean";
}

	public void unsetEntityContext() {
		this.context = null;
}

	public void ejbStore() {

	}

	public void ejbLoad() {

	}
	public String ejbCreate() {
		return "RequirementReferenceBean";
	}
	public void ejbPostCreate() {

	}
	public String ejbFindByPrimaryKey(String key) {
		return "RequirementReferenceBean";
	}

	public void ejbRemove() {

	} 

	public void ejbActivate() {

	}

	public void ejbPassivate() {

	}

	/**  
	 *  Get all reference groups
	 */ 
	public ReferenceGroupList getManagedReferenceGroups(boolean systemWizard, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ReferenceGroupList managedList = new ReferenceGroupList();
			ReferenceGroupList list = rgda.getReferenceGroups();
			Enumeration e1 = list.elements();
			while (e1.hasMoreElements()) {
				ReferenceGroupModel m = (ReferenceGroupModel) e1.nextElement();
				if (isSystemWizard(m) && systemWizard) {
					managedList.add(m);
				} else if (!isSystemWizard(m) && !systemWizard) {
					managedList.add(m);
				}
			}
			stopService(call);
			return managedList;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	private boolean isSystemWizard(ReferenceGroupModel m) {
		return m.getCategoryRefId() == ReferenceCategoryGroup.SYSTEM
		|| m.getCategoryRefId() == ReferenceCategoryGroup.FRAMEWORK
		|| m.getCategoryRefId() == ReferenceCategoryGroup.SECURITY;
	}
}
