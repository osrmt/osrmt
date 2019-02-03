/*
 * $Id: SaveIssueAction.java,v 1.1 2006/08/29 18:48:56 aron-smith Exp $ 
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
package com.osrmt.www.issue;

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
import com.osrmt.modellibrary.issue.*;
import java.util.*;

/**
 * <p>Validate a user logon.</p>
 *
 * @version $Rev: 164858 $ $Date: 2006/08/29 18:48:56 $
 */
public final class SaveIssueAction extends BaseAction {


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
	        Integer issueId = (Integer) PropertyUtils.getSimpleProperty(form,
	        "issueid");
	        String crud = (String) PropertyUtils.getSimpleProperty(form,
	        "crud");
	        ActionMessages errors = new ActionMessages();
	
	        int newIssueId = 0;
	        if (crud.equals("create")) {
	            Integer issuetyperefid = PropertyUtils.getSimpleProperty(form,
	            "issuetyperefid") == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form,
	            "issuetyperefid");
	            Integer artifactid = PropertyUtils.getSimpleProperty(form,
	            "artifactid") == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form,
	            "artifactid");
	        	IssueModel issue = new IssueModel();
	        	issue.setIssueTypeRefId(issuetyperefid.intValue());
	        	issue.setArtifactId(artifactid.intValue());
	        	issue.setProductRefId(getUser(request).getProductRefId());
	        	mapParameters(issue, request);
	        	newIssueId = LocalIssueServices.updateIssue(issue, getServiceCall(request)).getPrimaryKeyId();
	        } else if (crud.equals("update")) {
		        IssueModel issue = LocalIssueServices.getIssue(issueId, getServiceCall(request));
			    mapParameters(issue, request);
		        LocalIssueServices.updateIssue(issue, new ServiceCall(getUser(request)));
		        newIssueId = issueId.intValue();
	        } else if (crud.equals("delete")) {
		        IssueModel issue = LocalIssueServices.getIssue(issueId, new ServiceCall(getUser(request)));
			    issue.setNotActive();
		        LocalIssueServices.updateIssue(issue, new ServiceCall(getUser(request)));
		        LocalSecurityServices.printStats();
	        } else {
	        	Debug.LogError(this, "Null issueid");
	        }
	        
		    if (newIssueId > 0) {
		        request.setAttribute("controls","issue updated");
		    } else {
		        request.setAttribute("controls","issue deleted");
		    }
	        request.setAttribute("issue",new IssueModel());
	        request.setAttribute("crud","read");
    
    		return mapping.findForward("issuedetail");
    	} catch (NotLoggedInException l) {
    		return mapping.findForward("NotLoggedInException".toLowerCase());
    	} catch (Exception ex) {
    		throw ex;
    	}
    }
    
    private void mapParameters(IssueModel issue, HttpServletRequest request) throws Exception {
    	Enumeration e1 = request.getParameterNames();
    	String paramName = null;
    	String value = null;
    	while (e1.hasMoreElements()) {
    		try {
	    		paramName = (String) e1.nextElement();
	    		if (paramName.startsWith("modelRefId")) {
	    			value = request.getParameter(paramName);
	    			int modelRefId = Integer.parseInt(paramName.replace("modelRefId",""));
	    			int databaseDataType = issue.getModelColDatabaseDataType(modelRefId);
	    			Object newValue = Converter.convertModelValue(databaseDataType, value);
	    			if (Converter.isDifferent(newValue, issue.getModelColDataAt(modelRefId))) {
		    			issue.setModelColDataAt(newValue, modelRefId);
	    			}
	    		}
    		} catch (Exception ex) {
    			Debug.LogException(this, ex, "Param " + paramName + " value " + value);
    			throw ex;
    		}
    	}
    }

}
