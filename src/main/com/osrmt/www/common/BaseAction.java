/*
 * $Id: BaseAction.java,v 1.1 2006/08/29 18:49:01 aron-smith Exp $ 
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.osrmt.www.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.security.ApplicationUserModel;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.www.NotLoggedInException;
import com.osrmt.www.common.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.osrmt.www.common.*;
import com.osrmt.www.services.LocalReferenceServices;

/**
 * Base Action for MailReader application.
 *
 * @version $Rev: 54929 $ $Date: 2006/08/29 18:49:01 $
 */
public class BaseAction extends Action {

    // ----------------------------------------------------- Instance Variables

    /**
     * The <code>Log</code> instance for this application.
     */
    protected Log log = LogFactory.getLog(Constants.PACKAGE);

    // ------------------------------------------------------ Protected Methods


    /**
     * Return the local or global forward named "failure"
     * or null if there is no such forward.
     * @param mapping Our ActionMapping
     * @return Return the mapping named "failure" or null if there is no such mapping.
     */
    protected ActionForward findFailure(ActionMapping mapping) {
        return (mapping.findForward(Constants.FAILURE));
    }


    /**
     * Return the mapping labeled "success"
     * or null if there is no such mapping.
     * @param mapping Our ActionMapping
     * @return Return the mapping named "success" or null if there is no such mapping.
     */
    protected ActionForward findSuccess(ActionMapping mapping) {
        return (mapping.findForward(Constants.SUCCESS));
    }

    protected WebUser getUser(HttpServletRequest request) throws NotLoggedInException {

        HttpSession session = request.getSession();
        WebUser user = (WebUser) session.getAttribute(Constants.USER_KEY);
        if (user == null) {
    		throw new NotLoggedInException();
        }
        return user;
    }

    protected void saveUser(HttpServletRequest request, WebUser user) {

        HttpSession session = request.getSession();
        session.setAttribute(Constants.USER_KEY, user);

    }
    
    protected ServiceCall getServiceCall (HttpServletRequest request) throws NotLoggedInException {
    	WebUser user = getUser(request);
    	ServiceCall call = new ServiceCall(user);
    	ApplicationObject ao = new ApplicationObject();
    	ao.setProductRefId(user.getProductRefId(),LocalReferenceServices.getDisplay(user.getProductRefId()));
    	call.setApplication(ao);
    	return call;
    }

}
