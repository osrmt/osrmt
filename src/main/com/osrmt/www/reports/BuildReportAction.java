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
package com.osrmt.www.reports;

import com.osrmt.www.utilty.WebFileUtil;
import com.osframework.appclient.services.ReportWriterServices;
import com.osframework.appclient.services.SystemServices;
import com.osframework.appclient.ui.components.MultiColumnList;
import com.osframework.ejb.reportwriter.*;
import com.osframework.framework.utility.FileProcess;
import com.osframework.modellibrary.reference.group.TableNameFramework;
import com.osframework.modellibrary.reportwriter.ReportList;
import com.osframework.modellibrary.reportwriter.ReportModel;
import com.osframework.modellibrary.system.RecordFileModel;
import com.osframework.modellibrary.system.RecordParameterControlList;
import com.osframework.modellibrary.system.RecordParameterModel;
import com.osrmt.www.services.LocalReportWriterServices;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

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
import org.apache.struts.validator.DynaValidatorForm;
import org.jboss.deployment.JARDeployerMBean;

import javax.swing.*;
/**
 *
 * @author Leszek Zborowski
 * @version
 */

public class BuildReportAction extends BaseAction {
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    
    /**
     * inputs
     */
    static String ARTIFACTID = "artifactid";
    static String PRODUCTREFID = "productrefid";
    static String ARTIFACTREFID = "artifactrefid";
    static String REPORTLIST = "reportlist";
    
    private Parameter[] parameters;
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
            
            Integer productrefid = PropertyUtils.getSimpleProperty(form,
                    PRODUCTREFID) == null ? new Integer(0) : (Integer) PropertyUtils.getSimpleProperty(form,
                    PRODUCTREFID);
            
            ActionMessages errors = new ActionMessages();
            
            WebUser user = getUser(request);
            if (productrefid != null) {
                user.setProductRefId(productrefid.intValue());
            }
            saveUser(request, user);
            
            ReportList reports = LocalReportWriterServices.getReports(getServiceCall(request));
            ApplicationControlList acl1 = LocalSecurityServices.getAppControlsByUser(0, ApplicationFramework.get(ApplicationGroup.REPORTLISTCOLUMNS), getServiceCall(request));
            reports.setColumnOrder(acl1);
            ArrayList rep = new ArrayList();
            for(int i=0; i<reports.getRowCount(); i++) {
                ReportModel model = reports.elementAt(i);
                Report report = new Report(model.getReportRefDisplay(), ""+model.getReportId(), "1");
                rep.add(report);
            }
            request.setAttribute("reportList",rep);
            
            if(PropertyUtils.getSimpleProperty(form, "step").equals("1")) {
                String type = (String)PropertyUtils.getSimpleProperty(form, "type");
                ReportWriterBean rwb = new ReportWriterBean();
                Random ran = new Random(Calendar.getInstance().getTimeInMillis());
                String filename = "report" + ran.nextLong();
                boolean isHtml=false;
                try {
                	int reportId = ((Integer)PropertyUtils.getSimpleProperty(form, "reportid")).intValue();
                    JasperPrint jasperPrint = ReportWriterServices.runReport(new RecordParameterControlList(), reportId, new HashMap());
                    if (jasperPrint == null) {
                    	throw new NullReportException(String.valueOf(reportId));
                    }
                    if(((String)PropertyUtils.getSimpleProperty(form, "type")).equals("pdf")){
                    	filename = filename + ".pdf";
                        JasperExportManager.exportReportToPdfFile(jasperPrint,FileProcess.getTemporaryDirectory() + filename);
                    }else if(((String)PropertyUtils.getSimpleProperty(form, "type")).equals("html")) {
                    	filename = filename + ".html";
                        JasperExportManager.exportReportToHtmlFile(jasperPrint,FileProcess.getTemporaryDirectory() + filename);
                        isHtml=true;
                    }else {
                    }
                    if(!new File(servlet.getServletContext().getRealPath("")+"/reports/output").exists()){
                        new File(servlet.getServletContext().getRealPath("")+"/reports/output").mkdir();
                    }
                    WebFileUtil.copyFile(new File(FileProcess.getTemporaryDirectory() + filename), 
                    		new File(servlet.getServletContext().getRealPath("")+"/reports/output/" + filename));
                    WebFileUtil.copyDirectory(new File(FileProcess.getTemporaryDirectory() + filename + "_files"), 
                    		new File(servlet.getServletContext().getRealPath("")+"/reports/output/" + filename + "_files"));
                    response.sendRedirect("reports/output/"+ filename);
                    return null;
                } catch (net.sf.jasperreports.engine.JRException ffe) {
                    ffe.printStackTrace();
                } catch (Exception ex1) {
                    Debug.LogException(this, ex1);
                    ex1.printStackTrace();
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
    
    
    private boolean checkParameters(ReportModel rm) throws Exception {
        try {
            String path = FileProcess.getFilePath(rm.getStorageDirectory(), rm.getFileName());
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(path);
            JasperPrint jasperPrint = null;
            JRParameter[] para = jasperReport.getParameters();
            int j = 0;
            HashMap paramMap = new HashMap();
            for(int i=0; i<para.length; i++) {
                JRParameter param = para[i];
                if (param.isForPrompting() && !param.isSystemDefined()) {
                    Parameter par = new Parameter();
                    par.setName(param.getName());
                    par.setArtifactId(jasperReport.getQuery().getText());
                    paramMap.put(j, par);
                    j++;
                }
            }
            if(paramMap.size()>0){
                this.parameters = new Parameter[paramMap.size()];
                for(int i=0; i<this.parameters.length; i++) {
                    this.parameters[i] = (Parameter)paramMap.get(i);
                }
                return true;
            }
            return false;
        } catch (JRException ex) {
            Debug.LogException(this, ex);
            throw ex;
        } catch(Exception ex) {
            Debug.LogException(this, ex);
            throw ex;
        }
    }
    
    public class Report {
        private String name;
        private String reportid;
        private String step;
        private String type;
        
        Report(String name, String report, String step){
            this.name = name;
            this.reportid = report;
            this.step = step;
        }
        
        public String getName() {
            return name;
        }
        
        public String getReportid() {
            return reportid;
        }
        
        public String getStep() {
            return step;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public void setReportid(String reportid) {
            this.reportid = reportid;
        }
        
        public void setStep(String step) {
            this.step = step;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
    }
    
    
    
}
