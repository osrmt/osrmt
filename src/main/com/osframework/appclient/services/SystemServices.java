package com.osframework.appclient.services;

import java.rmi.RemoteException;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.FileProcess;
import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reportwriter.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.system.*;
import com.osframework.ejb.system.*;
import com.osrmt.appclient.common.ApplicationObject;

public class SystemServices extends BaseService {

	private static ISystem getSystemRef() throws Exception {
		return SystemUtil.getSystem();
	}

	public static RecordFileList getFiles(int tableRefId, int tableId) {
		try {
			RecordFileList files = getSystemRef().getFiles(tableRefId, tableId, getServiceCall());
			return files;
    	} catch (Exception ex) {
    		Debug.LogException("SystemServices", ex);
			return new RecordFileList();
		}
	}
	
	public static void storeBinaryFile(RecordFileModel m, byte[] content, boolean versionControl) {
		try {
			getSystemRef().storeBinaryFile(m, content, versionControl, getServiceCall());
		} catch (Exception ex) {
    		Debug.LogException("SystemServices", ex);
		}
	}
	
	public static void storeHyperlink(RecordFileModel m) {
		try {
			getSystemRef().updateFile(m, getServiceCall());
		} catch (Exception ex) {
    		Debug.LogException("SystemServices", ex);
		}
	}
	

	public static byte[] getBinaryFile(RecordFileModel m) {
		try {
			return getSystemRef().getBinaryFile(m, getServiceCall());
		} catch (Exception ex) {
    		Debug.LogException("SystemServices", ex);
    		return null;
		}
	}
	
	public static void updateFile(RecordFileModel m, RecordTypeFramework recordType) {
		try {
			//TODO this is needed whenever you cant define at the bean
			//the type of reference - which is needed to manage environments
			m.setRecordTypeRefId(recordType.getRecordTypeRefId());
			getSystemRef().updateFile(m, getServiceCall());
		} catch (Exception ex) {
    		Debug.LogException("SystemServices", ex);
		}
	}

	public static RecordParameterControlList getParameters(RecordParameterModel rpm) throws RemoteException, DataAccessException, Exception {
		try { 
			RecordParameterList parameters = getSystemRef().getParameters(rpm, getServiceCall());
			RecordParameterControlList list = new RecordParameterControlList();
			
			//TODO replace with list.addAll(parameters);
			Enumeration e1 = parameters.elements();
			while (e1.hasMoreElements()) {
				RecordParameterModel m = (RecordParameterModel) e1.nextElement();
				list.add(m);
			}
			return list;
		} catch (Exception e) { 
			Debug.LogException("SystemServices", e);
			return new RecordParameterControlList();
		}
	}
	
	public static RecordFileList exportRecordFile() {
		try {
			return getSystemRef().exportRecordFile(getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("SystemServices", ex);
			return new RecordFileList();
		}
	}


	/**  
	 *  Import all record files
	 */ 
	public static int importRecordFile(RecordFileList list) {
		try { 
			return getSystemRef().importRecordFile(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SystemService", e);
			return 0;
		}
	}

	/**  
	 *  Export record parameters
	 */ 
	public static RecordParameterList exportRecordParameter() {
		try { 
			return getSystemRef().exportRecordParameter(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SystemService", e);
			return new RecordParameterList();
		}
	}

	/**  
	 *  Import record parameters
	 */ 
	public static int importRecordParameter(RecordParameterList list) {
		try { 
			return getSystemRef().importRecordParameter(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SystemService", e);
			return 0;
		}
	}
	



	
	public static void exportSystem(JFrame frame) {
		boolean status = true;
		try {
			FileProcess.exportData(SecurityServices.exportApplicationControl());
			FileProcess.exportData(SecurityServices.exportAppControlTemplate());
			FileProcess.exportData(SecurityServices.exportApplicationCustomControl());
			FileProcess.exportData(SecurityServices.exportApplicationSetting());
			FileProcess.exportData(SecurityServices.exportApplicationSecurity());
			FileProcess.exportData(SecurityServices.exportApplicationUser());
			FileProcess.exportData(SecurityServices.exportApplicationView());
			FileProcess.exportData(SystemServices.exportRecordFile());
			FileProcess.exportData(SystemServices.exportRecordParameter());
			FileProcess.exportData(ReferenceServices.exportReference());
			FileProcess.exportData(ReferenceServices.exportReferenceGroup());
			FileProcess.exportData(ReferenceServices.exportReferenceTreeList());
			FileProcess.exportData(ReportWriterServices.exportReport());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Error occur while referece export");
			Debug.LogException("SystemService", ex);
			status = false;
		} finally {
			if (status) {
				JOptionPane.showMessageDialog(frame, "Referece export done successfully.");
			}
		}
	}

	public static void importSystem() {
		try {
			SecurityServices.importApplicationControl((ApplicationControlList) FileProcess.importData(new ApplicationControlList()));
			SecurityServices.importAppControlTemplate((AppControlTemplateList) FileProcess.importData(new AppControlTemplateList()));
			SecurityServices.importApplicationCustomControl((ApplicationCustomControlList) FileProcess.importData(new ApplicationCustomControlList()));
			SecurityServices.importApplicationSetting((ApplicationSettingList) FileProcess.importData(new ApplicationSettingList()));
			SecurityServices.importApplicationSecurity((ApplicationSecurityList) FileProcess.importData(new ApplicationSecurityList()));
			SecurityServices.importApplicationUser((ApplicationUserList) FileProcess.importData(new ApplicationUserList()));
			SecurityServices.importApplicationView((ApplicationViewList) FileProcess.importData(new ApplicationViewList()));
			SystemServices.importRecordFile((RecordFileList) FileProcess.importData(new RecordFileList()));
			SystemServices.importRecordParameter((RecordParameterList) FileProcess.importData(new RecordParameterList()));
			ReportWriterServices.importReport((ReportList) FileProcess.importData(new ReportList()));
			ReferenceServices.importReferenceGroup((ReferenceGroupList) FileProcess.importData(new ReferenceGroupList()));
			ReferenceServices.importReference((ReferenceList) FileProcess.importData(new ReferenceList()));
			ReferenceServices.importReferenceTree((ReferenceTreeList) FileProcess.importData(new ReferenceTreeList()));
			
		} catch (Exception ex) {
			Debug.LogException("SystemService", ex);
		}
	}
	
	public static String calculateBaseline() throws Exception {
		ApplicationObject ao = (ApplicationObject) Application.getObject();
		return getSystemRef().calculateBaseline(ao.getProductRefId(), getServiceCall());
	}


	public static RecordFileList getProductFiles(int productRefId, ServiceCall call) throws Exception {
		return getSystemRef().getProductFiles(productRefId, getServiceCall());
	}
	

}
