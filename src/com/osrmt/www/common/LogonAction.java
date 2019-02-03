/*
 * $Id: LogonAction.java,v 1.1 2006/08/29 18:49:01 aron-smith Exp $ 
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
package com.osrmt.www.common;

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
import com.osframework.modellibrary.reference.security.ApplicationUserModel;
import com.osframework.modellibrary.reference.security.InvalidUserLoginException;
import com.osframework.modellibrary.reference.security.InvalidUserPasswordException;
import com.osframework.framework.logging.*;
import com.osrmt.www.common.BaseAction;
import com.osrmt.www.services.*;

/**
 * <p>Validate a user logon.</p>
 *
 * @version $Rev: 164858 $ $Date: 2006/08/29 18:49:01 $
 */
public final class LogonAction extends BaseAction {

    /**
     * Name of username field ["username"].
     */
    static String USERNAME = "username";

    /**
     * Name of password field ["password"].
     */
    static String PASSWORD = "password";

    // ------------------------------------------------------ Protected Methods

    /**
     * <p>Confirm user credentials. Post any errors and return User object
     * (or null).</p>
     *
     * @param database Database in which to look up the user
     * @param username Username specified on the logon form
     * @param password Password specified on the logon form
     * @param errors ActionMessages queue to passback errors
     *
     * @return Validated User object or null
     * @throws ExpiredPasswordException to be handled by Struts exception
     * processor via the action-mapping
     */
    WebUser authenticateUser(String username,
                           String password, ActionMessages errors) throws ExpiredPasswordException {

    	try {
			ApplicationUserModel usertry = new ApplicationUserModel();
			usertry.setUserLogin(username);
			usertry.setPassword(password);
			WebUser user = new WebUser();
			user.updateWith(LocalSecurityServices.authenticate(usertry));
			
			return user;
		} catch (InvalidUserLoginException e) {
		    errors.add(
			        ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("error.username"));
			return null;
		} catch (InvalidUserPasswordException e) {
		    errors.add(
			        ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("error.password"));
			return null;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return null;
		}

    }


    /**
     * <p>Store User object in client session.
     * If user object is null, any existing user object is removed.</p>
     *
     * @param request The request we are processing
     * @param user The user object returned from the database
     */
    void SaveUser(HttpServletRequest request, WebUser user) {

        HttpSession session = request.getSession();
        session.setAttribute(Constants.USER_KEY, user);

    }

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

        String username = (String) PropertyUtils.getSimpleProperty(form,
                USERNAME);
        String password = (String) PropertyUtils.getSimpleProperty(form,
                PASSWORD);
        ActionMessages errors = new ActionMessages();

        // Retrieve user
        WebUser user = authenticateUser(username,password,errors);

        if (user == null) {
        	return mapping.findForward("invalidlogon");
        } else {
            // Save user object
            SaveUser(request,user);
            // Otherwise, return "success"
            return (findSuccess(mapping));
        }

    }

}
