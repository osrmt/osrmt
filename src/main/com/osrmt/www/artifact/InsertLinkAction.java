/*
 * InsertLinkAction.java
 *
 * Created on February 28, 2007, 10:35 PM
 */

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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

import com.osrmt.www.common.BaseAction;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SystemServices;
import com.osframework.framework.utility.*;
import com.osframework.modellibrary.system.*;
import com.osframework.modellibrary.reference.group.FileTypeFramework;
import org.apache.commons.beanutils.PropertyUtils;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.FileSystemUtil;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.action.SessionActionMapping;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;
import java.util.Enumeration;
import com.osrmt.www.common.*;
import com.osrmt.www.services.*;
import com.osframework.modellibrary.common.ServiceCall;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;
import com.osframework.modellibrary.system.RecordFileList;
import com.osframework.modellibrary.reference.group.*;
import com.osrmt.www.NotLoggedInException;
/**
 *
 * @author Jacek Kowalczyk kowalczykj@tt.com.pl
 * @version
 */

public class InsertLinkAction extends BaseAction {
    
    /* forward name="success" path="" */
    private final static String linkinserted = "linkinserted";
    
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
        
        int tableRefId = TableNameFramework.ARTIFACT;
        
        int tableId = 0;
        
        Integer artifactId = (Integer) PropertyUtils.getSimpleProperty(form,
                "artifactid");//tableid
        Integer parentArtifactId = (Integer) PropertyUtils.getSimpleProperty(form,
                "parentartifactid");
        Integer artifactRefId = (Integer) PropertyUtils.getSimpleProperty(form,
                "artifactrefid");
        
        int artifactid = artifactId.intValue();
        tableId = artifactid;
        int artifactrefid = artifactRefId.intValue();
        int parentartifactid = parentArtifactId.intValue();
        
        String crud = (String) PropertyUtils.getSimpleProperty(form,
                "crud");
        String urllink = (String) PropertyUtils.getSimpleProperty(form, "urllink");
        String description = (String) PropertyUtils.getSimpleProperty(form, "description");
        String filename = (String) PropertyUtils.getSimpleProperty(form, "filename");
//        DynaValidatorForm df = (DynaValidatorForm) form;
//            FormFile attachmentF   = (FormFile) df.get("attachmentF");
        
        
        try {
            WebUser user = getUser(request);
            
            RecordFileModel m = new RecordFileModel();
            m.setFileTypeRefId(FileTypeFramework.HYPERLINK);
            m.setSourceFile(urllink);
            m.setFileName(filename);
            m.setTableId(artifactid);//tableid
            m.setTableRefId(tableRefId);
            m.setDescription(description);
            
            LocalArtifactServices.authenticateContainer();
            SystemServices.storeHyperlink(m);
            
            String paramsss = "Link URL: "+urllink+
                     "\nfilename: "+filename+"" +
                    "\nFileTypeRefId: "+FileTypeFramework.HYPERLINK+"" +
                    "\nartifactid: "+artifactid+"" +
                    "\ncrud: "+crud+
                    "\nartifactrefid: "+artifactrefid+"" +
                    "\nparentartifactid: "+parentartifactid +
                    "\ntablerefid: "+tableRefId;
            
            
            RecordFileList list = SystemServices.getFiles(tableRefId, tableId);
            int recordFileListSize = list.getRowCount();
            java.util.List recordslist = new java.util.ArrayList();
            for (int index=0;index<recordFileListSize;index++){
                RecordFileModel recordFileModel = list.elementAt(index);
                recordslist.add(recordFileModel);
                //System.out.println("recordFileModel data: "+recordFileModel.getDescription());
            }
            
            request.setAttribute("recordslist",recordslist);
            request.setAttribute("urllink",urllink);
            request.setAttribute("crud",crud);
            request.setAttribute("artifactid",""+artifactid);
            //System.out.println(crud);
            //System.out.println(artifactid);
            
            
        }catch (NotLoggedInException nle){
            
            ActionErrors errors = new ActionErrors();
            errors.add("exception", new ActionMessage("error.exception"));
            addErrors(request,errors);
            
            Debug.LogException(this, nle);
            return mapping.findForward("Errors");
        } catch (Exception ex) {
            
            Debug.LogException(this, ex);
            return mapping.findForward("Errors");
        }
        ///////////////////////////
        
        return mapping.findForward(linkinserted);
        
    }
}
