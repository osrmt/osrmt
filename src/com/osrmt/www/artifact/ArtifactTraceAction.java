/**    Copyright (C) 2006  PSC (Poland Solution Center)
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 */

package com.osrmt.www.artifact;

import com.osframework.appclient.ui.components.MultiColumnList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import com.osframework.modellibrary.common.ResultList;
import com.osframework.modellibrary.reference.group.ApplicationFramework;

import com.osrmt.appclient.services.RequirementServices;

import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.TraceTypeGroup;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.security.ApplicationUserModel;
import com.osframework.modellibrary.reference.security.InvalidUserLoginException;
import com.osframework.modellibrary.reference.security.InvalidUserPasswordException;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.framework.logging.*;
import com.osframework.appclient.ui.tree.*;
import com.osrmt.www.NotLoggedInException;
import com.osrmt.www.common.*;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.www.services.*;
import java.util.*;
import org.apache.struts.action.ActionMessages;
import org.jboss.deployment.JARDeployerMBean;

import javax.swing.*;
/**
 *
 * @author Leszek Zborowski
 * @version
 */

public class ArtifactTraceAction extends BaseAction {
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    
    /**
     * inputs
     */
    static String ARTIFACTID = "artifactid";
    static String PRODUCTREFID = "productrefid";
    static String ARTIFACTREFID = "artifactrefid";
    
    /**
     * outputs
     */
    static String ARTIFACTLIST = "artifactlist";
    
    private TraceModel traceModel = new TraceModel();
    private MultiColumnList traceList = new MultiColumnList();
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    public ActionForward execute(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        try {
            ActionMessages errors = new ActionMessages();
            WebUser user = getUser(request);
            
            Integer artifactId = PropertyUtils.getSimpleProperty(form,
                    ARTIFACTID) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form,
                    ARTIFACTID);
            Integer productrefid = PropertyUtils.getSimpleProperty(form,
                    PRODUCTREFID) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form,
                    PRODUCTREFID);
            Integer artifactrefid = PropertyUtils.getSimpleProperty(form,
                    ARTIFACTREFID) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form,
                    ARTIFACTREFID);
            
            if (productrefid != null) {
                user.setProductRefId(productrefid.intValue());
            }
            saveUser(request, user);
            
            ArrayList artifactList = LocalArtifactServices.getChildList(artifactId.intValue(), artifactrefid.intValue(), productrefid.intValue(), getServiceCall(request));
            
            request.setAttribute(ARTIFACTLIST,artifactList);
            request.setAttribute(PRODUCTREFID,productrefid);
            
            if(PropertyUtils.getSimpleProperty(form, "action").equals("update")) {
                request.setAttribute("action","update");
                this.traceModel.setProductRefId(productrefid);
                this.traceModel.setTraceFromArtifactRefId(Integer.parseInt((String)PropertyUtils.getSimpleProperty(form, "from")));
                this.traceModel.setTraceToArtifactRefId(Integer.parseInt((String)PropertyUtils.getSimpleProperty(form, "to")));
                request.setAttribute("from2",(String)PropertyUtils.getSimpleProperty(form, "from"));
                request.setAttribute("to2",(String)PropertyUtils.getSimpleProperty(form, "to"));
                ArtifactList list = new ArtifactList();
                if(PropertyUtils.getSimpleProperty(form, "traceType").equals("1")){
                    this.traceModel.setTraceTypeRefId(TraceTypeGroup.NOTTRACED);
                    list = RequirementServices.getArtifactsNotTraced(traceModel);
                    ApplicationControlList acl1 = LocalSecurityServices.getAppControlsByUser(traceModel.getTraceFromArtifactRefId(), ApplicationFramework.get(ApplicationGroup.ARTIFACTSEARCHRESULTS), getServiceCall(request));
                    list.setColumnOrder(acl1);
                    request.setAttribute("traceTable", HtmlControl.getTable(list));
                } else if (PropertyUtils.getSimpleProperty(form, "traceType").equals("3")) {
                    this.traceModel.setTraceTypeRefId(TraceTypeGroup.TRACED);
                    list = RequirementServices.getArtifactsTraced(traceModel);
                    ApplicationControlList acl1 = LocalSecurityServices.getAppControlsByUser(traceModel.getTraceFromArtifactRefId(), ApplicationFramework.get(ApplicationGroup.ARTIFACTSEARCHRESULTS), getServiceCall(request));
                    list.setColumnOrder(acl1);
                    request.setAttribute("traceTable", HtmlControl.getTable(list));   
                } else if (PropertyUtils.getSimpleProperty(form, "traceType").equals("2")) {
                    this.traceModel.setTraceTypeRefId(TraceTypeGroup.MATRIX);
                    ResultList resultList = RequirementServices.getArtifactMatrix(traceModel);
                    request.setAttribute("traceTable", HtmlControl.getTable(resultList));
                }
            }
            
            return mapping.findForward(SUCCESS);
        } catch (NotLoggedInException l) {
            return mapping.findForward("NotLoggedInException".toLowerCase());
        } catch (Exception ex) {
            Debug.LogException(this, ex);
            throw ex;
        }
        
        
        
        
    }
}
