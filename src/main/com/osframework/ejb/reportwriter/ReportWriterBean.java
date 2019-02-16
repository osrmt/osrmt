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
//************ UNLESS YOU SET OVERWRITE IND = 0 IN TABLE EJBLIBRARY *********//
package com.osframework.ejb.reportwriter;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.rmi.RemoteException;

import javax.ejb.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.ControlScript;
import com.osframework.framework.utility.FileProcess;
import com.osframework.framework.utility.FileSystemUtil;
import com.osframework.framework.utility.RuleScript;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SystemServices;
import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.ejb.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.common.ReferenceMapBean;
import com.osframework.ejb.reference.common.ReferenceMapUtil;
import com.osframework.ejb.reference.security.SecurityBean;
import com.osframework.ejb.system.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.RecordTypeFramework;
import com.osframework.modellibrary.reference.group.ReferenceGroup;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.group.TableNameFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.datalibrary.reportwriter.ReportDataAdapter;
import com.osframework.datalibrary.system.*;
import com.osframework.modellibrary.system.*;
import com.osframework.modellibrary.reportwriter.*;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.appclient.services.*;
import com.osrmt.ejb.reqmanager.*;

import net.sf.jasperreports.view.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.engine.xml.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.query.*;
import net.sf.jasperreports.engine.data.*;
import org.w3c.dom.Document;

import java.io.OutputStream;
import com.osrmt.modellibrary.reference.group.*;

public class ReportWriterBean extends BaseBean implements EntityBean, IReportWriter {

	private EntityContext context= null;
	static final long serialVersionUID = 1L;
	private ReportDataAdapter rda;
	
   	private IReferenceMap reference;
   	private IRequirementManager requirement;
   	private ISystem system;
   	
   	private static ReportWriterBean reportWriterBean2Tier = null;

	public ReportWriterBean() {
		try {
			reference = ReferenceMapBean.get2TierInstance();
			requirement = RequirementManagerBean.get2TierInstance();
			system = SystemUtil.getSystem();
			rda = new ReportDataAdapter(reference, security);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	public static synchronized ReportWriterBean get2TierInstance() throws Exception {
		if (reportWriterBean2Tier == null) {
			reportWriterBean2Tier = new ReportWriterBean();
		}
		return reportWriterBean2Tier;
	}

	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	public void loadCache() {

	}

	public String ejbFind() {
		return "ReferenceSearchBean";
}

	public void unsetEntityContext() {
		this.context = null;
}

	public void ejbStore() {
	}

	public void ejbLoad() {
	}
	public String ejbCreate() {
		return "ReportWriterBean";
	}
	public void ejbPostCreate() {

	}
	public String ejbFindByPrimaryKey(String key) {
		return "ReportWriterBean";
	}

	public void ejbRemove() {

	} 

	public void ejbActivate() {

	}

	public void ejbPassivate() {

	}

	   private JasperPrint runReport(String sql, String reportFile, Map reportParams, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		   SQLResult result = null;
	        try{
	        	//TODO FYI for reports to do well make all numeric fields 
	        	//especially _ref_id required
	        	/** Compile 
	            JasperDesign jasperDesign = JRXmlLoader.load(new ByteArrayInputStream(reportFile.getBytes()));
	            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), Db.getAccess().conn);
	            */
	        	JasperPrint jasperPrint = null;
	        	if (sql == null) { 
	        		jasperPrint = JasperFillManager.fillReport(reportFile, reportParams, Db.getAccess().getConnection()); 
	        		return jasperPrint; 
	        	} else {
		        	result = Db.getAccess().executeQuery(sql);
					java.sql.ResultSet rset = result.getRset();
		            jasperPrint = JasperFillManager.fillReport(reportFile, reportParams, new JRResultSetDataSource (rset));
		        	Debug.LogDebug(this,sql);
	        	}
	        	if (jasperPrint == null) {
	        		throw new NullReportException();
	        	}
	            return jasperPrint;
	        } catch(Exception ex) {
				Debug.LogError(this, sql);
				Debug.LogException(this, ex);
				throw ex;
	        } finally {
	        	if (result != null) {
	        		result.close();
	        	}
	        }
	    }
	   
	   public JasperPrint runReport(RecordParameterControlList params, int reportId, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		   return runReport(params, null, reportId, call);
	   }
	   
	   public JasperPrint runReport(RecordParameterControlList params, int reportId, Map reportParams, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		   return runReport(params, null, reportId, reportParams, call);
	   }
	   
   public JasperPrint runReport(RecordParameterControlList params, String overrideSql, int reportId, ServiceCall call) throws RemoteException, DataAccessException, Exception {
	   return runReport(params, overrideSql, reportId, new HashMap(), call);
   }
	   
   public JasperPrint runReport(RecordParameterControlList params, String overrideSql, int reportId, Map reportParams, ServiceCall call) throws RemoteException, DataAccessException, Exception {
        try{
        	ReportModel rm = rda.getReport(reportId);
        	createOutline(rm, call);
    		ReferenceDisplay rd = reference.getReferenceDisplay(TableNameFramework.REPORT);
    		RecordFileList files = system.getFiles(rd.getRefId(), reportId, call);
    		Enumeration e2 = files.elements();
    		if (e2.hasMoreElements()) {
    			RecordFileModel rfm = (RecordFileModel) e2.nextElement();
    			String path = FileProcess.getFilePath(rfm.getStorageDirectory(), rfm.getStorageFileName());
	        	//return runReport(system.getTextFile(rfm, call), call);
    			createReportSql(params, overrideSql, rm);
	        	return runReport(rm.getReportSql(), path, reportParams, call);
    		}
        	throw new NullReportException(String.valueOf(reportId));
        } catch(Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
        }
    }
   
   /**
    * Script should execute setReportSql(<new sql>);
    * @param params
    * @param overrideSql
    * @param rm
    */
   private void createReportSql(RecordParameterControlList params,String overrideSql, ReportModel rm) throws Exception {
	   String sql = null;
	   try {
		   sql = rm.getReportSql();
		   if (overrideSql != null) {
			   sql = overrideSql;
		   }
		   if (params != null && params.size() > 0) {
			   ControlScript script = new ControlScript();
			   script.addReference(params,"params");
			   script.addReference(sql,"sql");
			   script.addReference(rm,"report");
			   script.execute(rm.getSqlParameterScript(), true);
		   }
		   rm.setReportSql(sql);
		   
	   } catch (Exception ex) {
		   Debug.LogException(this, ex);
		   throw ex;
	   }
	   
   }
   
	public ReportList getReports(int reportRefId, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ReportList reports = rda.getReports(reportRefId);
			updateFileName(reports, call);
			stopService(call);
			return reports;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	public ReportList getReports(ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ReportList reports = rda.getReports();
			updateFileName(reports, call);
			stopService(call);
			return reports;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	private void updateFileName(ReportList reports, ServiceCall call)throws Exception {
		Enumeration e1 = reports.elements();
		while (e1.hasMoreElements()) {
			ReportModel rm = (ReportModel) e1.nextElement();
			rm.setFileName(system.getFiles(com.osframework.modellibrary.reference.group.TableNameFramework.REPORT, rm.getReportId(), call).getFirst().getFileName());
			rm.setStorageDirectory(system.getFiles(com.osframework.modellibrary.reference.group.TableNameFramework.REPORT, rm.getReportId(), call).getFirst().getStorageDirectory());
		}
	}
		
	/**  
	 *  Export reports
	 */ 
	public ReportList exportReport(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ReportList list = rda.exportReport();
			stopService(call);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Import reports
	 */ 
	public int importReport(ReportList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			int rows = rda.importReport(list);
			stopService(call);
			return rows;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Update a report
	 */ 
	public UpdateResult UpdateReport(ReportModel report, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			UpdateResult result = new UpdateResult();
			if (report.isNotActive() && report.getReportId() > 0) {
				rda.deleteReport(report.getReportId());
			} else {
				if (report.getReportId() == 0) {
					ReferenceModel rm = new ReferenceModel();
					rm.setReferenceGroup(ReferenceGroup.Report);
					rm.setDisplay(report.getReportRefDisplay());
					int refId = ReferenceServices.updateReference(rm).getPrimaryKeyId();
					report.setReportRefId(refId);
				} else {
					ReferenceModel rm = reference.getReference(report.getReportRefId());
					if (!report.getReportRefDisplay().equals(rm.getDisplay())) {
						rm.setDisplay(report.getReportRefDisplay());
						reference.UpdateReference(rm, call);
					}
				}
				RecordFileModel file = new RecordFileModel();
				if (report.getReportId() > 0) {
					RecordFileList files = SystemServices.getFiles(TableNameFramework.REPORT, report.getReportId());
					file = files.getFirst();
				}
				result = rda.UpdateReport(report, call);
				file.setDescription(report.getReportRefDisplay());
				file.setStorageFileName(report.getFileName());
				file.setFileName(report.getFileName());
				file.setStorageDirectory(report.getStorageDirectory());
				file.setTableId(report.getReportId());
				file.setTableRefId(TableNameFramework.REPORT);
				system.updateFile(file, call);
			}
			stopService(call);
			return result;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Get a report
	 */ 
	public ReportModel getReport(int reportId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ReportModel report = rda.getReport(reportId);
			RecordFileModel file = system.getFiles(com.osframework.modellibrary.reference.group.TableNameFramework.REPORT, report.getReportId(), call).getFirst();
			report.setFileName(file.getFileName());
			report.setStorageDirectory(file.getStorageDirectory());
			stopService(call);
			return report;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;

		}
	}


	public JasperPrint runXMLReport(ArtifactList artifactList, ReportModel report, ServiceCall call) throws RemoteException, DataAccessException, Exception {
        try{
        	createOutline(report, call);
        	RecordFileModel rfm = system.getFiles(TableNameFramework.REPORT, report.getReportId(), call).getFirst();
    		String reportFile = FileProcess.getFilePath(rfm.getStorageDirectory(), rfm.getStorageFileName());
    		if (artifactList == null) {
    			ControlScript selectScript = new ControlScript();
    			artifactList = (ArtifactList) selectScript.execute(report.getXmlSelectScript(), ArtifactList.class);
    		}
    		artifactList = prepareArtifacts(artifactList, call);
			ControlScript fieldScript = new ControlScript();
			fieldScript.addReference(artifactList, "artifacts");
			String xml = (String) fieldScript.execute(report.getXmlFieldScript(), String.class);
			String snippet = (xml == null ? null : (xml.length() > 2000 ? (xml.substring(0,2000)+ "...<snip>") : xml));
			Debug.LogDebug(this, xml == null ? snippet : xml.length() + " bytes " + reportFile + " " + snippet);
			if (com.osframework.datalibrary.common.Access.isDumpSql()) {
				Debug.LogDebug(this, xml);
			}
			InputStream input = new ByteArrayInputStream(xml.getBytes());
			Document document = JRXmlUtils.parse(input);
			HashMap params = new HashMap();
			params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
			params.put("report_directory", rfm.getStorageDirectory());
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile, params);
    		return jasperPrint; 
        } catch(Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
        }
	}

	/**
	 * Prepare artifacts with all data needed for reporting
	 * 
	 * @param list
	 */
	private ArtifactList prepareArtifacts(ArtifactList list, ServiceCall call) throws Exception {
		ArtifactList artifacts = new ArtifactList(list.size());
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			// get the most recent which has a long description
			ArtifactModel artifact = requirement.getArtifact(am.getArtifactId(), call);
			prepareArtifact(artifact, call);
			artifacts.add(artifact);
		}
		return artifacts;
	}
	
	/**
	 * Get context data
	 * 
	 * @param am
	 * @param call
	 * @throws Exception
	 */
	private void prepareArtifact(ArtifactModel am, ServiceCall call) throws Exception {
		// get path back to root
		am.setReportParents(requirement.getParentArtifacts(am, call));
		am.setReportTreeDepth(am.getReportParents().size());
		am.setReportComponentDetails(requirement.getStepChildren(am.getArtifactId(), ArtifactLevelGroup.get(ArtifactLevelGroup.COMPONENT),
				ComponentTypeGroup.get(ComponentTypeGroup.REQUIREMENTDETAIL), call));
		am.setReportComponentUseCaseMainFlows(requirement.getStepChildren(am.getArtifactId(), ArtifactLevelGroup.get(ArtifactLevelGroup.COMPONENT),
				ComponentTypeGroup.get(ComponentTypeGroup.USECASEMAINFLOW), call));
		am.setReportComponentUseCaseAltFlows(requirement.getStepChildren(am.getArtifactId(), ArtifactLevelGroup.get(ArtifactLevelGroup.COMPONENT),
				ComponentTypeGroup.get(ComponentTypeGroup.USECASEALTFLOW), call));
		am.setReportParentDependencies(requirement.getDependentParentItems(am.getArtifactId(), call));
		am.setReportChildDependencies(requirement.getDependentItems(am.getArtifactId(), call));
		am.setReportArtifactDocumentList(requirement.getArtifactDocumentLines(am.getArtifactId(), call));
	}
	
	private void createOutline(ReportModel report, ServiceCall call) throws Exception {
		if (outlineQualify(report)) {
			 Map<Integer, Boolean> qualArtifactIds = rda.qualifyingArtifacts(report.getReportOutlineSql());
			 requirement.createOutline(report.getReportOutlineScript(), qualArtifactIds, call);
		}		
	}
	
	private boolean outlineQualify(ReportModel report) throws Exception {
		// does not qualify due to script
		if (report.getReportOutlineSql() == null || report.getReportOutlineScript() == null
				|| report.getReportOutlineSql().trim().length() == 0 || report.getReportOutlineScript().trim().length() == 0) {
			return false;
		}
		// if it ran only run again if report or artifacts changed
		if (report.getOutlineLastRunDt() != null) {
			long lastExecutionTime = report.getOutlineLastRunDt().getTimeInMillis();
			long reportLastUpdatedTime = report.getUpdateDt().getTimeInMillis();
			if (lastExecutionTime < reportLastUpdatedTime) {
				return true;
			}
			
			if (rda.hasUpdateSince(report.getOutlineLastRunDt())) {
				return true;
			}
			return false;
		} else {
			return true;
		}

	}
}
