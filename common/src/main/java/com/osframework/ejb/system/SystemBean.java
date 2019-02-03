//************ AUTO GENERATED DO NOT EDIT *********//
//************ UNLESS YOU SET OVERWRITE IND = 0 IN TABLE EJBLIBRARY *********//
package com.osframework.ejb.system;

import java.util.*;
import java.rmi.RemoteException;

import javax.ejb.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.ejb.common.*;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.common.ReferenceMapBean;
import com.osframework.ejb.reference.common.ReferenceMapUtil;
import com.osframework.ejb.reportwriter.ReportWriterBean;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.ApplicationSettingFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.group.ViewFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.datalibrary.system.*;
import com.osframework.modellibrary.system.*;
import com.osrmt.modellibrary.reqmanager.BaselineModel;

import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.*;
import java.io.OutputStream;



public class SystemBean extends BaseBean implements EntityBean, ISystem {

	private EntityContext context= null;
	private static final long serialVersionUID = 1L;
	private RecordFileDataAdapter rfda;
	private RecordFileHistoryDataAdapter rfhda;
	private RecordParameterDataAdapter rpda;
   	private IReferenceMap reference;
   	
   	private static SystemBean systemBean2Tier = null;
   	
	public SystemBean() {
		try {
			reference = ReferenceMapBean.get2TierInstance();
			rfda = new RecordFileDataAdapter(reference, security);
			rfhda = new RecordFileHistoryDataAdapter(reference, security);
			rpda = new RecordParameterDataAdapter(reference, security);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	public static synchronized SystemBean get2TierInstance() throws Exception {
		if (systemBean2Tier == null) {
			systemBean2Tier = new SystemBean();
		}
		return systemBean2Tier;
	}
	
	
	public void setEntityContext(EntityContext context) {
		this.context = context;
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
		return "SystemBean";
	}
	public void ejbPostCreate() {
	}
	public String ejbFindByPrimaryKey(String key) {
		return "SystemBean";
	}

	public void ejbRemove() {

	} 

	public void ejbActivate() {

	}

	public void ejbPassivate() {

	}
	
	public void updateFile(RecordFileModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			rfda.UpdateRecordFile(m, call);
			stopService(call);
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Return all files
	 */ 
	public RecordFileList getFiles(int tableRefId, int tableId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			RecordFileList list = rfda.getActiveFileList(tableRefId, tableId);
			list.setReferenceDisplay(reference, security);
			stopService(call);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	
	/**  
	 *  Return all files
	 */ 
	public String getTextFile(RecordFileModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			String text = FileSystemUtil.getTextContents(m.getStorageDirectory(), m.getStorageFileName());
			stopService(call);
			return text;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Return all files
	 */ 
	public byte[] getBinaryFile(RecordFileModel m, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			byte[] content = FileSystemUtil.getBinaryContents(m.getStorageDirectory(), m.getStorageFileName());
			stopService(call);
			return content;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Return all files
	 */ 
	public void storeBinaryFile(RecordFileModel m, byte[] content, boolean versionControl, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			createStorage(m);
			FileSystemUtil.createBinaryFile(m.getStorageDirectory(), m.getStorageFileName(), content);
			rfda.UpdateRecordFile(m, call);
			stopService(call);
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**
	 * Create a new directory/filename for 
	 * @param m
	 */
	private void createStorage(RecordFileModel m) {
		if (m.getRecordFileId() == 0) {
			String directoryName = "storage";
			try {
				ApplicationSettingList list = security.getSettingsByUser(ApplicationFramework.get(0),
						new ServiceCall());
				Enumeration e1 = list.elements();
				while (e1.hasMoreElements()) {
					ApplicationSettingModel asm = (ApplicationSettingModel) e1.nextElement();
					if (asm.getSettingRefId() == ApplicationSettingFramework.STORAGEDIRECTORY) {
						directoryName = asm.getValueString();
					}
				}
				
			} catch (Exception ex) {
				Debug.LogException(this, ex);
			}
			FileSystemUtil.createDirectory(directoryName);
			m.setStorageDirectory(directoryName);
			m.setStorageFileName(m.getFileName() + "_" + String.valueOf(m.getTableId()));
			//TODO should replace older version
			m.setActiveVersionInd(1);
		}
	}


	public RecordParameterList getParameters(RecordParameterModel rpm, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			RecordParameterList parameters = rpda.getParameters(rpm.getTableRefId(), rpm.getTableId());
			//TODO painful when the next line is forgotten - move to data adapter?
			parameters.setReferenceDisplay(reference, security);
			stopService(call);
			return parameters;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Export all system required record files
	 */ 
	public RecordFileList exportRecordFile(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			RecordFileList list = rfda.exportRecordFile();
			stopService(call);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Import all record files
	 */ 
	public int importRecordFile(RecordFileList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			int rows = rfda.importRecordFile(list);
			stopService(call);
			return rows;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Export record parameters
	 */ 
	public RecordParameterList exportRecordParameter(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			RecordParameterList list = rpda.exportRecordParameter();
			stopService(call);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Import record parameters
	 */ 
	public int importRecordParameter(RecordParameterList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			int rows = rpda.importRecordParameter(list);
			stopService(call);
			return rows;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	public String calculateBaseline(int productRefId, ServiceCall call) throws Exception {
		
		String msg = reference.getDisplay(SystemMessageFramework.NUMBEROFARTIFACTS) + ": " + rfda.countAllArtifacts(productRefId)
		+ "\n" + reference.getDisplay(SystemMessageFramework.STORAGESPACEREQUIRED) + ": " + sumSpace(productRefId, call)
		+ "\n" + reference.getDisplay(SystemMessageFramework.BASELINESTORAGEDIRECTORY) + ": " + getBaselineStorageDirectory() 
		+ "\n" + reference.getDisplay(SystemMessageFramework.PRESSYESTOSTARTTHEBASELINE);
		return msg;
	}
	
	private String getBaselineStorageDirectory() throws Exception {
		ApplicationSettingList asl = security.getSettingsByUser(ApplicationSettingFramework.STORAGEBASELINE, ApplicationFramework.get(0), 0, ViewFramework.get(0), new ServiceCall(new ApplicationUserModel()));
		if (asl.size() > 0 && asl.getFirst().getValueString() != null) {
			return asl.getFirst().getValueString();
		}
		return "baselinestorage";
	}
	
	private String sumSpace(int productRefId, ServiceCall call) throws Exception {
		RecordFileList files = rfda.getAllFiles(productRefId);
		Enumeration e1 = files.elements();
		long space = 0;
		while (e1.hasMoreElements()) {
			RecordFileModel file = (RecordFileModel) e1.nextElement();
			long size = FileSystemUtil.getSize(file.getStorageDirectory(), file.getStorageFileName());
			space+=size;
		}
		FileSystemUtil.createDirectory(getBaselineStorageDirectory());
		if (space < 1024*1024) {
			return "< 1 MB";
		} else {
			return "" + ((long) Math.floor(space/1024/1024)+1) + " MB";
		}
	}
	
	public RecordFileList getProductFiles(int productRefId, ServiceCall call) throws Exception {
		RecordFileList files = rfda.getAllFiles(productRefId);
		return files;
	}

	public String createTempBaselineDirectory(int productRefId, RecordFileList files, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		Random rand = new Random(Calendar.getInstance().getTimeInMillis());
		String tempBaselineDir = FileProcess.getFilePath(getBaselineStorageDirectory(), "tmp" + rand.nextInt());
		FileSystemUtil.createDirectory(tempBaselineDir);
		Enumeration e1 = files.elements();
		while (e1.hasMoreElements()) {
			RecordFileModel file = (RecordFileModel) e1.nextElement();
			FileSystemUtil.copyFile(file.getStorageDirectory(), file.getStorageFileName(), tempBaselineDir);	
		}
		return tempBaselineDir;
	}

	public void renameBaselineDirectory(String tempDirName, int baselineId, RecordFileList files, DbConnection conn, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		Enumeration e1 = files.elements();
		while (e1.hasMoreElements()) {
			RecordFileModel file = (RecordFileModel) e1.nextElement();
			RecordFileHistoryModel h = new RecordFileHistoryModel();
			h.updateWith(file);
			h.setHistoryUserId(call.getUserId());
			h.setBaselineId(baselineId);
			rfhda.UpdateRecordFileHistory(h, call, conn);
		}
		String newBaselineDir = FileProcess.getFilePath(getBaselineStorageDirectory(), "baseline" + baselineId);
		FileSystemUtil.renameDirectory(tempDirName, newBaselineDir);
		
	}


	/**  
	 *  Return all files
	 */ 
	public RecordFileModel getRecordFile(int recordFileId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			RecordFileModel record = rfda.getRecordFile(recordFileId);
			stopService(call);
			return record;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}


}
