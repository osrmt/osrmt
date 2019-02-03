/*
    //usage

    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
package com.osframework.ejb.reportwriter;

import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.appclient.ui.tree.*;
import java.util.*;
import javax.swing.tree.*;
import com.osframework.modellibrary.system.*;
import com.osframework.modellibrary.reportwriter.*;

public interface LocalReportWriter extends javax.ejb.EJBLocalObject, IReportWriter
{
    /**  
     *  Return the list of reports
     */ 
    public ReportList getReports(int reportRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Returns the list of all reports
     */ 
    public ReportList getReports(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Returns the print job
     */ 
    public net.sf.jasperreports.engine.JasperPrint runReport(com.osframework.modellibrary.system.RecordParameterControlList params, String overrideSql, int reportId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Returns a report
     */ 
    public net.sf.jasperreports.engine.JasperPrint runReport(com.osframework.modellibrary.system.RecordParameterControlList params, int reportId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Export reports
     */ 
    public ReportList exportReport(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Import reports
     */ 
    public int importReport(ReportList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Update a report
     */ 
    public UpdateResult UpdateReport(ReportModel report, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get a report
     */ 
    public ReportModel getReport(int reportId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Get an xml based report
     */ 
    public net.sf.jasperreports.engine.JasperPrint runXMLReport(com.osrmt.modellibrary.reqmanager.ArtifactList artifacts, ReportModel report, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Parameters passed to the report
     */ 
    public net.sf.jasperreports.engine.JasperPrint runReport(com.osframework.modellibrary.system.RecordParameterControlList params, String overrideSql, int reportId, Map param,ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


    /**  
     *  Run report with parametes
     */ 
    public net.sf.jasperreports.engine.JasperPrint runReport(com.osframework.modellibrary.system.RecordParameterControlList params, int reportId, Map reportParams, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception;


}
