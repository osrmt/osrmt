/*
 * $Id: ArtifactDetailAction.java,v 1.1 2006/08/29 18:48:57 aron-smith Exp $ 
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
import com.osframework.appclient.ui.tree.*;
import com.osrmt.www.NotLoggedInException;
import com.osrmt.www.common.BaseAction;
import com.osrmt.www.common.WebUser;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.www.services.*;
import java.util.*;


/**
 * <p>Validate a user logon.</p>
 *
 * @version $Rev: 164858 $ $Date: 2006/08/29 18:48:57 $
 */
public final class ArtifactDetailAction extends BaseAction {

    /**
     * Name of username field ["username"].
     */
    static String ARTIFACTID = "artifactid";
    static String CRUD = "crud";


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
            Integer artifactid = (Integer) PropertyUtils.getSimpleProperty(form,
            		ARTIFACTID);
            String crud = (String) PropertyUtils.getSimpleProperty(form,
            		CRUD);
            WebUser user = getUser(request);
        
	        ActionMessages errors = new ActionMessages();
	
	        ArtifactModel artifact = new ArtifactModel();
	        // Retrieve artifact tree
	        if (crud.equals("create")) {
	            Integer parentartifactid = (Integer) PropertyUtils.getSimpleProperty(form,
        		"parentartifactid");
	            Integer artifactrefid = (Integer) PropertyUtils.getSimpleProperty(form,
        		"artifactrefid");
	            artifact.setArtifactRefId(artifactrefid.intValue());
		        request.setAttribute("parentartifactid",parentartifactid);
		        request.setAttribute("artifactrefid",artifactrefid);
	        } else {
		        artifact = LocalArtifactServices.getArtifact(artifactid, getServiceCall(request));
	        }
	        String controls = ArtifactDetailController.buildControls(artifact, artifact, getServiceCall(request), crud.equals("read") || crud.equals("delete"));
	        request.setAttribute("artifact",artifact);
	        request.setAttribute("artifactid",artifactid);
	        request.setAttribute("controls",controls);
	        request.setAttribute("crud",crud);
        
        	return mapping.findForward("artifactdetail");
    	} catch (NotLoggedInException l) {
    		return mapping.findForward("NotLoggedInException".toLowerCase());
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		throw ex;
    	}

    }

}
