package com.osframework.appclient.services;

import java.rmi.RemoteException;
import java.util.*;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.osframework.framework.logging.Debug;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.security.*;
import com.osframework.ejb.reportwriter.*;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.common.ReferenceModel;
import com.osframework.modellibrary.reference.group.FileTypeFramework;
import com.osframework.modellibrary.reference.group.RecordTypeFramework;
import com.osframework.modellibrary.reference.group.ReferenceGroup;
import com.osframework.modellibrary.reference.group.TableNameFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.system.*;
import com.osframework.ejb.system.*;
import com.osframework.modellibrary.reportwriter.*;
import com.osrmt.modellibrary.issue.IssueModel;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.modellibrary.issue.*;

public class ReportWriterServices extends BaseService {

	private static IReportWriter getReportWriterRef() throws Exception {
		return ReportWriterUtil.getReportWriter();
	}


	public static ReportList getReports() throws RemoteException, DataAccessException, Exception {
		try { 
			ReportList reports = getReportWriterRef().getReports(getServiceCall());
			return reports;
		} catch (Exception e) { 
			Debug.LogException("SystemServices", e);
			return new ReportList();
		}
	}

	public static ReportModel getReport(int reportId) throws RemoteException, DataAccessException, Exception {
		try { 
			ReportModel report = getReportWriterRef().getReport(reportId, getServiceCall());
			return report;
		} catch (Exception e) { 
			Debug.LogException("SystemServices", e);
			return new ReportModel();
		}
	}

	/*
	   public static JasperPrint runReport(String reportFile) throws RemoteException, DataAccessException, Exception {
	        try{
	            JasperPrint jasperPrint = getReportWriterRef().runReport(reportFile, getServiceCall());
	            return jasperPrint;
	        } catch(Exception ex) {
	        	//TODO get class name from stack
				Debug.LogException("SystemServices", ex);
				return new PrintDocument();
	        }
	    }
	*/
        
           /**
            *  Function added by PSC to pass Map with parameters for reports
            */
	   public static JasperPrint runReport(RecordParameterControlList params, int reportId, Map param) throws RemoteException, DataAccessException, Exception {
	        try{
	            JasperPrint jasperPrint = null;
	        	ReportModel rm = getReport(reportId);
	        	if (rm.isXmlReport()) {
		            jasperPrint = getReportWriterRef().runXMLReport(null, rm, getServiceCall());
	        	} else {
	        		jasperPrint = getReportWriterRef().runReport(params, reportId, param, getServiceCall());
	        	}
	            return jasperPrint;
	        } catch(Exception ex) {
	        	throw ex;
	        }
	    }        
	
	   public static JasperPrint runReport(RecordParameterControlList params, int reportId) throws RemoteException, DataAccessException, Exception {
	        try{
	            JasperPrint jasperPrint = null;
	        	ReportModel rm = getReport(reportId);
	        	if (rm.isXmlReport()) {
		            jasperPrint = getReportWriterRef().runXMLReport(null, rm, getServiceCall());
	        	} else {
	        		jasperPrint = getReportWriterRef().runReport(params, reportId, getServiceCall());
	        	}
	            return jasperPrint;
	        } catch(Exception ex) {
	        	throw ex;
	        }
	    }
	   
	   public static JasperPrint runReport(RecordParameterControlList params, String overrideSql, int reportId) throws RemoteException, DataAccessException, Exception {
	        try{
	            JasperPrint jasperPrint = getReportWriterRef().runReport(params, overrideSql, reportId, getServiceCall());
	            return jasperPrint;
	        } catch(Exception ex) {
	        	throw ex;
	        }
	    }
	   
	   public static JasperPrint runReportWhereOLD(ArtifactList artifacts, int reportId) throws RemoteException, DataAccessException, Exception {
	        try{
	        	JasperPrint jasperPrint = null;
	        	ReportModel rm = getReport(reportId);
	        	if (rm.isXmlReport()) {
		            jasperPrint = getReportWriterRef().runXMLReport(artifacts, rm, getServiceCall());
	        	} else {
		        	String overrideSql = rm.getReportSql();
		        	String whereClause = getArtifactSql(artifacts);
		        	if (whereClause != null && overrideSql != null) {
		        		if (overrideSql.toLowerCase().contains("where")) {
		        			overrideSql += " and " + whereClause;
		        		} else {
		        			overrideSql += " where " + whereClause;
		        		}
		        	}
		            jasperPrint = getReportWriterRef().runReport(new RecordParameterControlList(), overrideSql, reportId, getServiceCall());
	        	}
	            return jasperPrint;
	        } catch(Exception ex) {
	        	throw ex;
	        }
	    }
	   
	   public static JasperPrint runReportWhereOLD(IssueList issues, int reportId) throws RemoteException, DataAccessException, Exception {
	        try{
	        	ReportModel rm = getReport(reportId);
	        	String overrideSql = rm.getReportSql();
	        	String whereClause = getIssueSql(issues);
	        	if (whereClause != null && overrideSql != null) {
	        		if (overrideSql.toLowerCase().contains("where")) {
	        			overrideSql += " and " + whereClause;
	        		} else {
	        			overrideSql += " where " + whereClause;
	        		}
	        	}
	            JasperPrint jasperPrint = getReportWriterRef().runReport(new RecordParameterControlList(), overrideSql, reportId, getServiceCall());
	            return jasperPrint;
	        } catch(Exception ex) {
	        	throw ex;
	        }
	    }
	   
		/**  
		 *  Export reports
		 */ 
		public static ReportList exportReport() {
			try { 
				return getReportWriterRef().exportReport(getServiceCall());
			} catch (Exception e) { 
				Debug.LogException("ReportWriterService", e);
				return new ReportList();
			}
		}

		/**  
		 *  Import reports
		 */ 
		public static int importReport(ReportList list) {
			try { 
				return getReportWriterRef().importReport(list, getServiceCall());
			} catch (Exception e) { 
				Debug.LogException("ReportWriterService", e);
				return 0;
			}
		}
		
		/**  
		 *  Update a report
		 */ 
		public static UpdateResult UpdateReport(ReportModel report) throws Exception {
			try { 
				return getReportWriterRef().UpdateReport(report, getServiceCall());
			} catch (Exception e) { 
				throw e;
			}
		}


		private static String getArtifactSql(ArtifactList artifactList) {
			String whereClause = null;
			int i=0;
			Enumeration e1 = artifactList.elements();
			while (e1.hasMoreElements()) {
				ArtifactModel am = (ArtifactModel) e1.nextElement();
				if (i==0) {
					whereClause = "artifact_id in (" + am.getArtifactId();
				} else {
					whereClause += ", " + am.getArtifactId();
				}
				i++;
			}
			if (i > 0) {
				whereClause += ")";
			}
			return whereClause;
		}
		
		/**
		 * TODO get rid of getArtifactSql/getIssueSql
		 * @return
		 */
		private static String getIssueSql(IssueList issueList) {
			String whereClause = null;
			int i=0;
			Enumeration e1 = issueList.elements();
			while (e1.hasMoreElements()) {
				IssueModel am = (IssueModel) e1.nextElement();
				if (i==0) {
					whereClause = "issue_id in (" + am.getIssueId();
				} else {
					whereClause += ", " + am.getIssueId();
				}
				i++;
			}
			if (i > 0) {
				whereClause += ")";
			}
			return whereClause;
		}
		

}
