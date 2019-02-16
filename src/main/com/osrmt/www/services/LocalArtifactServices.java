package com.osrmt.www.services;
import java.rmi.RemoteException;
import java.util.*;

import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.datalibrary.common.UpdateResult;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.tree.*;
import com.osrmt.www.services.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.services.*;
import com.osrmt.modellibrary.reference.group.RelationGroup;
import com.osrmt.modellibrary.reqmanager.*;

import javax.servlet.http.*;
import org.apache.struts.*;
import org.apache.struts.action.*;

public class LocalArtifactServices extends BaseService {

	public LocalArtifactServices() {
		super();
	}

	public static UITreeModel getArtifactTree(int productRefId, ServiceCall serviceCall) {
		authenticateContainer();
		setApplicationObject(productRefId, serviceCall);
		UITreeModel artifactTree = RequirementServices.getRequirementTreeModel(productRefId, serviceCall);
		return artifactTree;

	}

	public static ArtifactModel getArtifact(Integer artifactId, ServiceCall serviceCall) {
		authenticateContainer();
		ArtifactModel artifact = RequirementServices.getArtifact(artifactId.intValue(), serviceCall);
		return artifact;
	}
	
	public static UpdateResult updateArtifact(ArtifactModel artifact, ServiceCall serviceCall) {
		authenticateContainer();
		convertUTF(artifact);
		return RequirementServices.UpdateArtifact(artifact, serviceCall);
	}
	
	public static UpdateResult updateArtifact(ArtifactModel artifact, int parentArtifactId, ServiceCall serviceCall) throws RemoteException, DataAccessException, Exception {
		authenticateContainer();
		convertUTF(artifact);
		ArtifactModel parentArtifact = new ArtifactModel();
		if (parentArtifactId > 0) {
			parentArtifact = RequirementServices.getArtifact(parentArtifactId, serviceCall);
			return RequirementServices.UpdateArtifact(artifact, parentArtifact, RelationGroup.get(RelationGroup.RELATED), serviceCall);
		} else {
			return RequirementServices.UpdateArtifact(artifact, serviceCall);
		}
	}
	
	
	private static void setApplicationObject(int productRefId, ServiceCall call) {
		ApplicationObject ao = new ApplicationObject();
		ao.setProductRefId(productRefId, LocalReferenceServices.getDisplay(productRefId));
		call.setApplication(ao);
	}
	
    public static ArrayList getParentList(int artifactId, int artifactRefId, int productRefId, ServiceCall call) {
		authenticateContainer();
		ArrayList list = new ArrayList();
		if (productRefId > 0) {
			ArtifactModel product = new ArtifactModel();
			product.setProductRefId(productRefId);
			product.setArtifactName(ReferenceServices.getDisplay(productRefId));
			list.add(product);
		}
		if (artifactRefId > 0) {
			ArtifactModel artifactType = new ArtifactModel();
			artifactType.setProductRefId(productRefId);
			artifactType.setArtifactRefId(artifactRefId);
			artifactType.setArtifactName(ReferenceServices.getDisplay(artifactRefId));
			list.add(artifactType);
		}
		if (artifactId > 0) {
			ArtifactModel am = RequirementServices.getArtifact(artifactId, call);
			ArtifactList parents = RequirementServices.getParentArtifacts(am, call);
            if (parents != null) {
	  			for (int i=parents.size()-1; i>=0; i--) {
					list.add((ArtifactModel) parents.elementAt(i));
				}
				list.add(am);
			}
		}
    	return list;
    }

    public static ArrayList getChildList(int artifactId, int artifactRefId, int productRefId, ServiceCall call) {
		authenticateContainer();
		ArtifactList list = null;
		if (artifactId > 0) {
			list = RequirementServices.getSameTypeChildren(artifactRefId, artifactId, call);
		} else if (artifactRefId > 0) {
			list = RequirementServices.getTopLevelChildren(productRefId, artifactRefId, call);
		} else if (productRefId > 0){
			list = RequirementServices.getArtifactTypes(call);
		} else {
			list = new ArtifactList();
		}
		ArrayList alist = new ArrayList();
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			alist.add(e1.nextElement());
		}
		return alist;
    }
    

}

