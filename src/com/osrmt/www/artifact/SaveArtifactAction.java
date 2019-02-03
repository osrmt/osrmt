/*
 * $Id: SaveArtifactAction.java,v 1.1 2006/08/29 18:48:57 aron-smith Exp $ 
 *
 * Copyright 2000-2005 Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.osrmt.www.artifact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.osframework.appclient.services.SecurityServices;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.security.ApplicationUserModel;
import com.osframework.modellibrary.reference.security.InvalidUserLoginException;
import com.osframework.modellibrary.reference.security.InvalidUserPasswordException;
import com.osframework.framework.logging.*;
import com.osrmt.www.NotLoggedInException;
import com.osrmt.www.common.*;
import com.osrmt.www.services.*;
import com.osrmt.modellibrary.reqmanager.*;
import java.util.*;

/**
 * <p>Validate a user logon.</p>
 *
 * @version $Rev: 164858 $ $Date: 2006/08/29 18:48:57 $
 */
public final class SaveArtifactAction extends BaseAction {


    // --------------------------------------------------------- Public Methods

    /**
     * Use "username" and "password" fields from ActionForm to retrieve a User
     * object from the database. If credentials are not valid, or database
     * has disappeared, post error messages and forward to input.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if the application business logic throws
     *  an exception
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response)
        throws Exception {

    	try {
	        Integer artifactId = (Integer) PropertyUtils.getSimpleProperty(form,
	        "artifactid");
	        Integer parentArtifactId = (Integer) PropertyUtils.getSimpleProperty(form,
	        "parentartifactid");
	        String crud = (String) PropertyUtils.getSimpleProperty(form,
	        "crud");
	        ActionMessages errors = new ActionMessages();
	
	        int newArtifactId = 0;
	        if (crud.equals("create")) {
	            Integer artifactrefid = (Integer) PropertyUtils.getSimpleProperty(form,
	            "artifactrefid");
	        	ArtifactModel artifact = new ArtifactModel();
	        	artifact.setArtifactRefId(artifactrefid.intValue());
	        	artifact.setProductRefId(getUser(request).getProductRefId());
	        	mapParameters(artifact, request);
	        	newArtifactId = LocalArtifactServices.updateArtifact(artifact, parentArtifactId.intValue(), getServiceCall(request)).getPrimaryKeyId();
	        } else if (crud.equals("update")) {
		        ArtifactModel artifact = LocalArtifactServices.getArtifact(artifactId, getServiceCall(request));
			    mapParameters(artifact, request);
		        LocalArtifactServices.updateArtifact(artifact, new ServiceCall(getUser(request)));
		        newArtifactId = artifactId.intValue();
	        } else if (crud.equals("delete")) {
		        ArtifactModel artifact = LocalArtifactServices.getArtifact(artifactId, new ServiceCall(getUser(request)));
			    artifact.setNotActive();
		        LocalArtifactServices.updateArtifact(artifact, new ServiceCall(getUser(request)));
		        LocalSecurityServices.printStats();
	        } else {
	        	Debug.LogError(this, "Null artifactid");
	        }
	        
		    if (newArtifactId > 0) {
		        request.setAttribute("controls","artifact updated");
		    } else {
		        request.setAttribute("controls","artifact deleted");
		    }
	        request.setAttribute("artifact",new ArtifactModel());
	        request.setAttribute("crud","read");
    
    		return mapping.findForward("artifactdetail");
    	} catch (NotLoggedInException l) {
    		return mapping.findForward("NotLoggedInException".toLowerCase());
    	} catch (Exception ex) {
    		throw ex;
    	}
    }
    
    private void mapParameters(ArtifactModel artifact, HttpServletRequest request) throws Exception {
    	Enumeration e1 = request.getParameterNames();
    	String paramName = null;
    	String value = null;
    	while (e1.hasMoreElements()) {
    		try {
	    		paramName = (String) e1.nextElement();
	    		if (paramName.startsWith("modelRefId")) {
	    			value = new String(request.getParameter(paramName).getBytes("UTF-8"),"UTF-8");
	    			int modelRefId = Integer.parseInt(paramName.replace("modelRefId",""));
	    			int databaseDataType = artifact.getModelColDatabaseDataType(modelRefId);
	    			Object newValue = Converter.convertModelValue(databaseDataType, value);
	    			if (Converter.isDifferent(newValue, artifact.getModelColDataAt(modelRefId))) {
		    			artifact.setModelColDataAt(newValue, modelRefId);
	    			}
	    		}
    		} catch (Exception ex) {
    			Debug.LogException(this, ex, "Param " + paramName + " value " + value);
    			throw ex;
    		}
    	}
    }

}
