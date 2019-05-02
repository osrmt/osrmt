package com.osframework.appclient.services;

import java.rmi.RemoteException;
import java.util.*;
import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.ejb.reference.security.*;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.*;
import com.osframework.datalibrary.common.*;
import com.osframework.modellibrary.common.ServiceCall;
import com.osframework.modellibrary.reference.common.ReferenceDisplayList;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.common.*;

/**
 * SecurityServices provides helper functionality to return interfaces
 * to the business entity beans.  
 */
public class SecurityServices extends BaseService {
	
	private static boolean debugOn = false;
	
	static {
		try {
			ApplicationSettingList asl = SecurityServices.getSetting(ApplicationFramework.get(0), 0, ApplicationSettingFramework.USERNAME_FIRST_LAST);
			if (asl.size() > 0 ) {
				boolean nameFormatFirstLast = asl.getFirst().getValueInt() == 1;
				ApplicationUserModel.setFirstNameLastFormat(nameFormatFirstLast);
			}
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
		}
	}
	
	public static ApplicationUserModel authenticate(ApplicationUserModel user, boolean suppressException, boolean ldap) throws Exception, InvalidUserLoginException, InvalidUserPasswordException {
		if (user.getPassword() == null || user.getPassword().length() == 0) {
			Debug.LogWarning("SecurityServices", ReferenceServices.getDisplay(SystemMessageFramework.INVALIDUSERNAME)
					+ " or " 
					+ ReferenceServices.getDisplay(SystemMessageFramework.INVALIDPASSWORD));
			if (suppressException) {
    			return null;
    		} else {
    			throw new InvalidUserLoginException();
    		}
		}
		if (!ldap) {
			byte[] bytes = user.getPassword().getBytes();
			user.setPassword(SecurityUtility.hashPassword(bytes));
		}
		// authenticate
    	try {
    		user = getSecurityRef().authenticate(user);
    	} catch (InvalidUserLoginException e1) {
    		Debug.LogWarning("SecurityServices", ReferenceServices.getDisplay(SystemMessageFramework.INVALIDUSERNAME) + " or " + ReferenceServices.getDisplay(SystemMessageFramework.INVALIDPASSWORD));
    		if (suppressException) {
    			return null;
    		} else {
    			throw e1;
    		}
    	} catch (InvalidUserPasswordException e2) {
    		Debug.LogWarning("SecurityServices", ReferenceServices.getDisplay(SystemMessageFramework.INVALIDUSERNAME)+" or "+ReferenceServices.getDisplay(SystemMessageFramework.INVALIDPASSWORD));
    		if (suppressException) {
    			return null;
    		} else {
    			throw e2;
    		}
    	}
    	return user;
	}
	
	public static ApplicationUserList getAllUsers() {
		try {
			ApplicationUserList users = getSecurityRef().getAllUsers(getServiceCall());
			return users;
		} catch (Exception ex) {
			Debug.LogException("SecurityServices",ex);
			return new ApplicationUserList();
		}
	}
	//TODO update all updates to return true/false
	public static boolean UpdateUser(ApplicationUserModel m) {
		try {
			boolean result = getSecurityRef().UpdateUser(m, getServiceCall()).getRowsUpdated()==1;
			clearCache();
			return result;
		} catch (Exception ex) {
			Debug.LogException("SecurityServices",ex);
			return false;
		}
	}

	public static UpdateResult UpdateApplicationControl(ApplicationControlModel m) {
		try {
			UpdateResult result = getSecurityRef().UpdateApplicationControl(m, getServiceCall());
			clearCache();
			return result;
		} catch (Exception ex) {
			Debug.LogException("SecurityServices",ex);
			return new UpdateResult();
		}
	}

	public static UpdateResult UpdateApplicationCustomControl(ApplicationCustomControlModel m) {
		try {
			UpdateResult result = getSecurityRef().UpdateCustomControl(m, getServiceCall());
			clearCache();
			return result;
		} catch (Exception ex) { 
			Debug.LogException("SecurityServices",ex);
			return new UpdateResult();
		}
	}

	public static UpdateResult UpdateApplicationControl(ApplicationControlList list) {
		try {
			UpdateResult result = getSecurityRef().UpdateApplicationControl(list, getServiceCall());
			clearCache();
			return result;
		} catch (Exception ex) {
			Debug.LogException("SecurityServices",ex);
			return new UpdateResult();
		}
	}

	public static ApplicationSecurityList getAppSecurity(ApplicationFramework application, ServiceCall call) {
		try {
			ApplicationSecurityList asl = getSecurityRef().getAppSecurity(application, call);
			return asl;
    	} catch (Exception ex) {
    		Debug.LogException("SecurityServices", ex);
			return new ApplicationSecurityList();
		}
	}
	
	
	public static ApplicationSecurityList getAppSecurity(ApplicationFramework application) {
		return getAppSecurity(application, getServiceCall());
	}
	
	
	public static ApplicationSecurityList getAppSecurity(int appTypeRefId, ApplicationFramework application) {
		return getAppSecurity(appTypeRefId, application, getServiceCall());
	}
	
	public static ApplicationSecurityList getAppSecurity(int appTypeRefId, ApplicationFramework application, ServiceCall call) {
		try {
			ApplicationSecurityList asl = getSecurityRef().getAppSecurity(application, appTypeRefId, call);
			return asl;
    	} catch (Exception ex) {
    		Debug.LogException("SecurityServices", ex);
			return new ApplicationSecurityList();
		}
	}
	
	
	public static int getAppControlSourceRefId(ViewFramework view, ApplicationFramework application, String controlName) {
		try {
			return getSecurityRef().getAppControlSourceRefId(view, application, controlName, getServiceCall());
    	} catch (Exception ex) {
    		Debug.LogException("SecurityServices", ex);
			return 0;
		}
	}
	
	public static ApplicationControlList getAppControls(ApplicationFramework application, boolean throwException) throws Exception {
		return getAppControls(ViewFramework.get(0),application, true);
	}
	
	public static ApplicationControlList getAppControls(ApplicationFramework application) {
		try {
			return getAppControls(ViewFramework.get(0),application, false);
		} catch (Exception ex) {
    		Debug.LogException("SecurityServices", ex);
			return new ApplicationControlList();
		}
	}
	
	public static ApplicationControlList getAppControls(ViewFramework view, ApplicationFramework application, boolean throwException) throws Exception {
		try {
			try {
				if (isCached(view.getViewRefId(), application.getApplicationRefId())) {
					return (ApplicationControlList) getCachedResult(view.getViewRefId(), application.getApplicationRefId());
				} else {
					return (ApplicationControlList) cache(getSecurityRef().getAppControls(view, application, getServiceCall(0)), view.getViewRefId(), application.getApplicationRefId());					
				}
			} catch (CacheException ce) {
				return getSecurityRef().getAppControls(view, application, getServiceCall(0));
			}
    	} catch (Exception ex) { 
    		if (throwException) {
    			throw ex;
    		}
    		Debug.LogException("SecurityServices", ex);
    		return new ApplicationControlList();
		}
	}
	
	public static ApplicationControlList getAppControls(ViewFramework view, int appTypeRefId, ApplicationFramework application) {
		return getAppControls(view, appTypeRefId, application, false);
	}
	
	public static ApplicationControlList getAppControls(ViewFramework view, int appTypeRefId, ApplicationFramework application, boolean refreshCache) {
		try {
			try {
				if (isCached(view.getViewRefId(), appTypeRefId, application.getApplicationRefId())) {
					return (ApplicationControlList) getCachedResult(view.getViewRefId(), appTypeRefId, application.getApplicationRefId());
				} else {
					return (ApplicationControlList) cache(getSecurityRef().getAppControls(view, appTypeRefId, application, getServiceCall()), view.getViewRefId(), appTypeRefId, application.getApplicationRefId());					
				}
			} catch (CacheException ce) {
				ApplicationControlList list = getSecurityRef().getAppControls(view, appTypeRefId, application, getServiceCall(0));
				addView(list);
				return list;
			}
    	} catch (Exception ex) { 
    		Debug.LogException("SecurityServices", ex);
			return new ApplicationControlList();
		}
	}
	
	private static void addView(ApplicationControlList list) {
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			if (acm.getApplicationViewId() > 0) {
				ApplicationViewModel avm = getApplicationView(acm.getApplicationViewId());
					acm.setApplicationRefId(avm.getApplicationRefId());
					acm.setApplicationRefDisplay(avm.getApplicationRefDisplay());
					acm.setAppTypeRefId(avm.getAppTypeRefId());
					acm.setAppTypeRefDisplay(avm.getAppTypeRefDisplay());
					acm.setViewRefId(avm.getViewRefId());
					acm.setViewRefDisplay(avm.getViewRefDisplay());
			}
		}
	}
	
	public static ApplicationControlList getAppControlsByUser(ApplicationFramework application) {
		return getAppControlsByUser(application, getServiceCall());
	}
	
	
	public static ApplicationControlList getAppControlsByUser(ApplicationFramework application, ServiceCall call) {
		try {
			try {
				if (isCached(application.getApplicationRefId())) {
					return (ApplicationControlList) getCachedResult(application.getApplicationRefId());
				} else {
					return (ApplicationControlList) cache(getSecurityRef().getAppControlsByUser(application, call), application.getApplicationRefId());					
				}
			} catch (CacheException ce) {
				return getSecurityRef().getAppControlsByUser(application, call);
			}
    	} catch (Exception ex) {
    		Debug.LogException("SecurityServices", ex);
			return new ApplicationControlList();
		}
	}
	
	public static ApplicationControlList getAppControlsByUser(int appTypeRefId, ApplicationFramework application) {
		return getAppControlsByUser(appTypeRefId, application, getServiceCall());
	}
	
	public static ApplicationControlList getAppControlsByUser(int appTypeRefId, ApplicationFramework application, ServiceCall call) {
		try {
			try {
				if (isCached(appTypeRefId, application.getApplicationRefId())) {
					return (ApplicationControlList) getCachedResult(appTypeRefId, application.getApplicationRefId());
				} else {
					return (ApplicationControlList) cache(getSecurityRef().getAppControlsByUser(application, appTypeRefId, call),appTypeRefId, application.getApplicationRefId());					
				}
			} catch (CacheException ce) {
				return getSecurityRef().getAppControlsByUser(application, appTypeRefId, call);
			}
    	} catch (Exception ex) { 
    		Debug.LogException("SecurityServices", ex);
			return new ApplicationControlList();
		}
	}
	
	public static ApplicationSettingList getAppSettings(ApplicationFramework application) throws DataAccessException {
		try {
			return getSecurityRef().getSettingsByUser(application, getServiceCall());
    	} catch (Exception ex) {
    		Debug.LogException("SecurityServices", ex);
			return new ApplicationSettingList();
		}
	}
	
	public static void printStats() {
		try {
			getSecurityRef().printStatistics();			
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
		}
	}
	
	public static ISecurity getSecurityRef() throws Exception {
		return SecurityUtil.getSecurity();
	}

	
    public static ApplicationUserModel getUser(int userId) throws java.rmi.RemoteException, java.rmi.RemoteException, Exception {
		try {
			if (isCached(userId)) {
				return (ApplicationUserModel) getCachedResult(userId);
			} else {
				return (ApplicationUserModel) cache(getSecurityRef().getUser(userId), userId);					
			}
		} catch (CacheException ce) {
			return getSecurityRef().getUser(userId);
		}
    }
    
	public static ReferenceDisplayList getViews(int appTypeRefId, ApplicationFramework application) {
    	try {
    		return getSecurityRef().getViews(appTypeRefId, application, getServiceCall());
    	} catch (Exception ex) {
    		Debug.LogException("SecurityServices", ex);
    		//TODO need to communicate the error
    		return new ReferenceDisplayList();
    	}
	}

	public static ReferenceDisplayList getAppTypes(ApplicationFramework application) {
    	try {
    		return getSecurityRef().getAppTypes(application, getServiceCall());
    	} catch (Exception ex) {
    		Debug.LogException("SecurityServices", ex);
    		return new ReferenceDisplayList();
    	}
	}

	public static ApplicationControlModel getApplicationControl(int applicationControlId) {
		try {
			return getSecurityRef().getApplicationControl(applicationControlId, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
			return new ApplicationControlModel();
		}
	}

	public static ApplicationUserModel getUserByLogin(String username) {
		try {
			return getSecurityRef().getUserByLogin(username, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
			return new ApplicationUserModel();
		}
	}
	
	public static ApplicationSecurityList getAppSecurity(ApplicationUserModel user) {
		try {
			ApplicationSecurityList asl = getSecurityRef().getAppSecurity(user, getServiceCall());
			return asl;
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
			return new ApplicationSecurityList();
		}
	}

	public static ApplicationSecurityList getAppSecurityByPosition(PositionFramework position) {
		try {
			ApplicationSecurityList asl = getSecurityRef().getAppSecurity(position, getServiceCall());
			return asl;
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
			return new ApplicationSecurityList();
		}
	}

	public static ApplicationSecurityList getAppSecurityGlobal() {
		try {
			ApplicationSecurityList asl = getSecurityRef().getAppSecurityGlobal(getServiceCall());
			return asl;
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
			return new ApplicationSecurityList();
		}
	}

	public static boolean UpdateApplicationSecurity(ApplicationSecurityModel asm) {
		try {
			return getSecurityRef().UpdateApplicationSecurity(asm, getServiceCall()).getRowsUpdated() == 1;
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
			return false;
		}
	}
	
	public static ApplicationViewList getApplicationViewsByType() {
		try {
			ApplicationViewList asl = getSecurityRef().getApplicationViewsByType(getServiceCall());
			return asl;
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
			return new ApplicationViewList();
		}
	}
	
	public static UpdateResult duplicateApplicationControlView(String newViewName, ViewFramework view, int appTypeRefId, ApplicationFramework application) { 
		try {
			return getSecurityRef().duplicateApplicationControlView(newViewName, view, appTypeRefId, application, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
			return new UpdateResult();
		}
	}
	
	public static ApplicationUserList exportApplicationUser() {
		try {
			return getSecurityRef().exportSysemUser(getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
			return new ApplicationUserList();
		}
	}
	
	public static void importApplicationUser(ApplicationUserList list) {
		try {
			getSecurityRef().importApplicationUser(list, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
		}
	}
	


	/**  
	 *  Export all system required application controls
	 */ 
	public static ApplicationControlList exportApplicationControl() {
		try { 
			return getSecurityRef().exportApplicationControl(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new ApplicationControlList();
		}
	}

	/**  
	 *  Export all system required application controls
	 */ 
	public static AppControlTemplateList exportAppControlTemplate() {
		try { 
			return getSecurityRef().exportAppControlTemplate(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new AppControlTemplateList();
		}
	}


	/**  
	 *  Import all controls
	 */ 
	public static int importApplicationControl(ApplicationControlList list) {
		try { 
			return getSecurityRef().importApplicationControl(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return 0;
		}
	}

	/**  
	 *  Export all system required application controls
	 */ 
	public static ApplicationCustomControlList exportApplicationCustomControl() {
		try { 
			return getSecurityRef().exportApplicationCustomControl(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new ApplicationCustomControlList();
		}
	}


	/**  
	 *  Import all controls
	 */ 
	public static int importApplicationCustomControl(ApplicationCustomControlList list) {
		try { 
			return getSecurityRef().importApplicationCustomControl(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return 0;
		}
	}

	/**  
	 *  Export all system required application security
	 */ 
	public static ApplicationSecurityList exportApplicationSecurity() {
		try { 
			return getSecurityRef().exportApplicationSecurity(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new ApplicationSecurityList();
		}
	}

	/**  
	 *  Export all system required application security
	 */ 
	public static ApplicationViewList exportApplicationView() {
		try { 
			return getSecurityRef().exportApplicationView(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new ApplicationViewList();
		}
	}


	/**  
	 *  Import all application security
	 */ 
	public static int importApplicationSecurity(ApplicationSecurityList list) {
		try { 
			return getSecurityRef().importApplicationSecurity(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return 0;
		}
	}

	/**  
	 *  Import all application security
	 */ 
	public static int importAppControlTemplate(AppControlTemplateList list) {
		try { 
			return getSecurityRef().importAppControlTemplate(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return 0;
		}
	}


	/**  
	 *  Import all application security
	 */ 
	public static int importApplicationView(ApplicationViewList list) {
		try { 
			return getSecurityRef().importApplicationView(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return 0;
		}
	}


	/**  
	 *  Export all application settings
	 */ 
	public static ApplicationSettingList exportApplicationSetting() {
		try { 
			return getSecurityRef().exportApplicationSetting(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new ApplicationSettingList();
		}
	}


	/**  
	 *  Import all applicaiton setting
	 */ 
	public static int importApplicationSetting(ApplicationSettingList list) {
		try { 
			return getSecurityRef().importApplicationSetting(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return 0;
		}
	}

	/**  
	 *  Get the application custom control
	 */ 
	public static ApplicationCustomControlModel getApplicationCustomControl(int id) {
		return getApplicationCustomControl(id, getServiceCall());
	}
	
	/**  
	 *  Get the application custom control
	 */ 
	public static ApplicationCustomControlModel getApplicationCustomControl(int id, ServiceCall call) {
		try { 
			try {
				if (isCached(id)) {
					return (ApplicationCustomControlModel) getCachedResult(id);
				} else {
					return (ApplicationCustomControlModel) cache(getSecurityRef().getApplicationCustomControl(id, call), id);					
				}
			} catch (CacheException ce) {
				return getSecurityRef().getApplicationCustomControl(id, call);
			}
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new ApplicationCustomControlModel();
		}
	}

	/**  
	 *  SQL is logged to debug file or not
	 */ 
	public static void toggleDebugSql() {
		try { 
			debugOn = !debugOn;
			getSecurityRef().debugSql(debugOn, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return;
		}
	}
	
	/**  
	 *  Get the application view
	 */ 
	public static ApplicationViewModel getApplicationView(int applicationViewId) {
		try { 
			try {
				if (isCached(applicationViewId)) {
					return (ApplicationViewModel) getCachedResult(applicationViewId);
				} else {
					return (ApplicationViewModel) cache(getSecurityRef().getApplicationView(applicationViewId, getServiceCall()), applicationViewId);					
				}
			} catch (CacheException ce) {
				return getSecurityRef().getApplicationView(applicationViewId, getServiceCall());
			}
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new ApplicationViewModel();
		}
	}

	/**  
	 *  Get the application view
	 */ 
	public static ApplicationSettingList getSetting(ApplicationFramework application, int settingRefId) {
		return getSetting(application, 0, settingRefId);
	}

	/**  
	 *  Get the application view
	 */ 
	public static ApplicationSettingList getSetting(ApplicationFramework application, int appTypeRefId, int settingRefId) {
		try { 
			try {
				if (isCached(application.getApplicationRefId(), appTypeRefId, settingRefId)) {
					return (ApplicationSettingList) getCachedResult(application.getApplicationRefId(), appTypeRefId, settingRefId);
				} else {
					return (ApplicationSettingList) cache(getSecurityRef().getSettingsByUser(settingRefId, application, appTypeRefId, ViewFramework.get(0), getServiceCall()), application.getApplicationRefId(), appTypeRefId, settingRefId);					
				}
			} catch (CacheException ce) {
				return getSecurityRef().getSettingsByUser(settingRefId, application, appTypeRefId, ViewFramework.get(0), getServiceCall());
			}
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new ApplicationSettingList();
		}
	}
	
	public static void clearSecurityCache() {
		clearCache();
	}

	public static UpdateResult UpdateApplicationView(ApplicationViewList list) {
		try {
			UpdateResult result = getSecurityRef().UpdateApplicationView(list, getServiceCall());
			clearCache();
			return result;
		} catch (Exception ex) {
			Debug.LogException("SecurityServices",ex);
			return new UpdateResult();
		}
	}
	
	/**  
	 *  Update the application security
	 */ 
	public static UpdateResult UpdateApplicationSecurity(ApplicationSecurityList list) {
		try { 
			return getSecurityRef().UpdateApplicationSecurity(list, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new UpdateResult();
		}
	}
	
	/**  
	 *  Get the application security
	 */ 
	public static ApplicationSecurityModel getApplicationSecurity(int applicationSecurityId) {
		try { 
			return getSecurityRef().getApplicationSecurity(applicationSecurityId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return null;
		}
	}

	/**  
	 *  Duplicate application security settings
	 */ 
	public static boolean duplicateApplicationSecurity(int fromPositionRefId, int toPositionRefId) {
		try { 
			return getSecurityRef().duplicateApplicationSecurity(fromPositionRefId, toPositionRefId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return false;
		}
	}

	/**  
	 *  Duplicate application security settings
	 */ 
	public static boolean duplicateArtifactControls(int fromArtifactRefId, int toArtifactRefId) {
		try { 
			return getSecurityRef().duplicateArtifactControls(fromArtifactRefId, toArtifactRefId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return false;
		}
	}

	private static void clearSystemCache() {
		try {
			getSecurityRef().clearSystemCache();
		} catch (Exception ex) {
			Debug.LogException("SecurityService", ex);
		}
	}
	
	public static void clearCache() {
		clearLocalCache();
		clearSystemCache();
	}
	
	private static String environment = null;
	public static String getEnvironment() {
		try {
			if (environment == null) {
				environment = getSecurityRef().getEnvironment();
			}
			return environment;
		} catch (Exception ex) {
			Debug.LogException("SecurityService", ex);
			return "";
		}
	}
	
	/**  
	 *  Get the application security
	 */ 
	public static ApplicationUserGroupModel  getApplicationUserGroup(int applicationUserGroupId) {
		try { 
			return getSecurityRef().getApplicationUserGroup(applicationUserGroupId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return null;
		}
	}

	
	/**  
	 *  Update the application security
	 */ 
	public static UpdateResult updateApplicationUserGroup(ApplicationUserGroupModel userGroup) {
		try { 
			return getSecurityRef().updateApplicationUserGroup(userGroup, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new UpdateResult();
		}
	}

	/**  
	 *  Get the application security
	 */ 
	public static ApplicationUserGroupList getUserGroup(int userGroupRefId) {
		try { 
			return getSecurityRef().getUserGroup(userGroupRefId, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return null;
		}
	}

	/**  
	 *  Get the application view
	 */ 
	public static ApplicationViewModel getApplicationView(ViewFramework view, int appTypeRefId, ApplicationFramework application) {
		try { 
			return getSecurityRef().getApplicationView(view, appTypeRefId, application, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new ApplicationViewModel();
		}
	}

	/**  
	 *  Get the application security
	 */ 
	public static ApplicationSettingList getGlobalSettings() {
		try { 
			return getSecurityRef().getGlobalSettings(getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return null;
		}
	}
	
	/**  
	 *  Update the application security
	 */ 
	public static UpdateResult UpdateApplicationSetting(ApplicationSettingList settings) {
		try { 
			clearCache();
			return getSecurityRef().UpdateApplicationSetting(settings, getServiceCall());
		} catch (Exception e) { 
			Debug.LogException("SecurityService", e);
			return new UpdateResult();
		}
	}


	/**  
	 *  Get the list of reference for a column
	 */ 
	public static ReferenceDisplayList getUserList() {
		try { 
			ReferenceDisplayList list = new ReferenceDisplayList();
			ApplicationUserList users = getSecurityRef().getAllUsers(getServiceCall());
			Enumeration e1 = users.elements();
			while (e1.hasMoreElements()) {
				ApplicationUserModel user = (ApplicationUserModel) e1.nextElement();
				ReferenceDisplay rd = new ReferenceDisplay();
				rd.setRefId(user.getUserId());
				rd.setDisplay(user.getDisplayName());
				list.add(rd);
			}
			return list;
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return null;
		}
	}
	
	public static ApplicationCustomControlModel getApplicationCustomControlByRef(int customControlRefId) throws Exception {
		return getSecurityRef().getApplicationCustomControlByRef(customControlRefId, getServiceCall(0));
	}
	
	public static UpdateResult addNewArtifact(String newArtifactName, int copiedArtifactRefId) throws Exception {
		return getSecurityRef().addNewArtifact(newArtifactName, copiedArtifactRefId, getServiceCall(0));
	}
	
	/**  
	 *  Get the list of reference for a column
	 */ 
	public static ReferenceDisplayList getUserGroupList() {
		try { 
			ReferenceDisplayList list = new ReferenceDisplayList();
			ApplicationUserGroupList users = getSecurityRef().getAllUserGroups(getServiceCall());
			Enumeration e1 = users.elements();
			while (e1.hasMoreElements()) {
				ApplicationUserGroupModel user = (ApplicationUserGroupModel) e1.nextElement();
				ReferenceDisplay rd = new ReferenceDisplay();
				rd.setRefId(user.getApplicationUserGroupId());
				rd.setDisplay(user.getUserGroupRefDisplay());
				list.add(rd);
			}
			return list;
		} catch (Exception e) { 
			Debug.LogException("ReferenceMapService", e);
			return null;
		}
	}
	
	public static boolean isReadOnlyControlList(ApplicationFramework application, int appTypeRefId) {
		try {
			return getSecurityRef().isReadOnlyControlList(application, appTypeRefId, getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("SecurityServices", ex);
			return false;
		}
	}
	
	   /**  
     *  Returns the display list for a group.  If no reference codes
	 * were found then an empty list is returned.
	 * 
	 * @param groupId
	 * @param includeInactive
	 * @throws Exception
     */ 
	public static ReferenceDisplayList getProductList() throws Exception {
		try {
			return getSecurityRef().getProductList(getServiceCall());
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return new ReferenceDisplayList();
		}
	}
	
	   /**  
     *  Returns the display list for a group.  If no reference codes
	 * were found then an empty list is returned.
	 * 
	 * @param groupId
	 * @param includeInactive
	 * @throws Exception
     */ 
	public static ReferenceList getProductRefList() throws Exception {
		try {
			ReferenceList list = new ReferenceList();
			ReferenceDisplayList rdl = getSecurityRef().getProductList(getServiceCall());
			Enumeration e1 = rdl.elements();
			while (e1.hasMoreElements()) {
				ReferenceDisplay rd = (ReferenceDisplay) e1.nextElement();
				ReferenceModel rm = new ReferenceModel();
				rm.setRefId(rd.getRefId());
				rm.setDisplay(rd.getDisplay());
				list.add(rm);
			}
			return list;
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
			return new ReferenceList();
		}
	}
	
	public static void addNewArtifactField(ReferenceDisplay artifact, ReferenceDisplayList fields) throws Exception {
		try {
			getSecurityRef().addNewArtifactField(artifact, fields, getServiceCall());
			clearCache();
		} catch (Exception ex) {
			Debug.LogException("ReferenceServices",ex);
		}
	}
}
