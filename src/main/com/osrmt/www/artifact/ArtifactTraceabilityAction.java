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
import com.osframework.datalibrary.common.UpdateResult;

import javax.swing.*;
/**
 *
 * @author Leszek Zborowski
 * @version
 */

public class ArtifactTraceabilityAction extends BaseAction {
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    
    /**
     * inputs
     */
    static String ARTIFACTID = "artifactid";
    static String FROMPOOL = "frompool";//refid
    static String TOPOOL = "topool";//refid
    static String  FROMARTIFACT  = "fromartifact";//refid
    static String  TOARTIFACT = "toartifact";//refid
    static String PRODUCTREFID = "productrefid";
    static String ARTIFACTREFID = "artifactrefid";
    
    /**
     * outputs
     */
    static String ARTIFACTLIST = "artifactlist";
    
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
            int successTrace = 0;
            ActionMessages errors = new ActionMessages();
            WebUser user = getUser(request);
            
            String level = (String) PropertyUtils.getSimpleProperty(form,"level");
            
            Integer productrefid = PropertyUtils.getSimpleProperty(form,
                    PRODUCTREFID) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form,
                    PRODUCTREFID);
            request.setAttribute(PRODUCTREFID,productrefid);
            request.setAttribute("level",level);
            
            
            if (productrefid != null) {
                user.setProductRefId(productrefid.intValue());
            }
            saveUser(request, user);
            
            ArrayList artifactPoolsList = LocalArtifactServices.getChildList(0, 0, productrefid.intValue(), getServiceCall(request));
            request.setAttribute("artifactPoolsList",artifactPoolsList);
            
            if (!level.equalsIgnoreCase("first")){
                Integer frompool = PropertyUtils.getSimpleProperty(form, FROMPOOL) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form, FROMPOOL);
                Integer topool = PropertyUtils.getSimpleProperty(form, TOPOOL) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form, TOPOOL);
                Integer fromartifact = PropertyUtils.getSimpleProperty(form, FROMARTIFACT) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form, FROMARTIFACT);
                Integer toartifact = PropertyUtils.getSimpleProperty(form, TOARTIFACT) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form, TOARTIFACT);
                
//                if (frompool!=0){
                ArrayList fromArtifactList = LocalArtifactServices.getChildList(0, frompool, productrefid.intValue(), getServiceCall(request));
                //System.out.println("fromArtifactList size "+fromArtifactList.size());
                request.setAttribute("fromArtifactList",fromArtifactList);
                request.setAttribute("frompool2",""+frompool.intValue());
                request.setAttribute("topool2",""+topool.intValue());
                
//                }
                
//                if (topool!=0){
                ArrayList toArtifactList = LocalArtifactServices.getChildList(0, topool, productrefid.intValue(), getServiceCall(request));
                //System.out.println("toArtifactList size "+toArtifactList.size());
                request.setAttribute("toArtifactList",toArtifactList);
                request.setAttribute("frompool2",""+frompool.intValue());
                request.setAttribute("topool2",""+topool.intValue());
//                }
                if(fromartifact!=0 && toartifact!=0){
                    request.setAttribute("fromartifact",""+fromartifact.intValue());
                    request.setAttribute("toartifact",""+toartifact.intValue());
                    
                    String action = (String) PropertyUtils.getSimpleProperty(form,"action");
                    
                    RequirementTreeModel rtm = new RequirementTreeModel();
                    rtm.setParentId(fromartifact);
                    rtm.setParentArtifactRefId(frompool.intValue());
                    rtm.setChildId(toartifact);
                    rtm.setChildArtifactRefId(toartifact.intValue());
                    rtm.setRelationRefId(com.osrmt.modellibrary.reference.group.RelationGroup.DEPENDENT);
                    
                    if(action.equalsIgnoreCase("Trace")){
                        com.osframework.datalibrary.common.UpdateResult upd = RequirementServices.UpdateRelationship(rtm);
                        if (upd!=null){
                            successTrace = 1;
                        }
                        else successTrace = 3;
                    } else if (action.equalsIgnoreCase("Untrace")){
                        com.osframework.datalibrary.common.UpdateResult upd = RequirementServices.deleteRelationship(rtm);
                        if (upd!=null){
                            successTrace = 2;
                        }
                        else successTrace =3;
                    }
                    request.setAttribute("successTrace",""+successTrace);
                    //RequirementServices.UpdateRelationship(rtm);
                }
                
                
                
                
                Integer artifactId = PropertyUtils.getSimpleProperty(form, ARTIFACTID) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form, ARTIFACTID);
//            Integer productrefid = PropertyUtils.getSimpleProperty(form,
//                    PRODUCTREFID) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form,
//                    PRODUCTREFID);
                Integer artifactrefid = PropertyUtils.getSimpleProperty(form,
                        ARTIFACTREFID) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form,
                        ARTIFACTREFID);
                
                
                
            }
            
            
            
            
            
            
            
            
            
//            ArrayList artifactPoolsList = LocalArtifactServices.getChildList(artifactId.intValue(), artifactrefid.intValue(), productrefid.intValue(), getServiceCall(request));
            
            
            
            return mapping.findForward(SUCCESS);
        } catch (NotLoggedInException l) {
            return mapping.findForward("NotLoggedInException".toLowerCase());
        } catch (Exception ex) {
            Debug.LogException(this, ex);
            throw ex;
        }
        
        
        
        
    }
}
