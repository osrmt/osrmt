package com.osframework.ejb.reference.security;

import javax.ejb.*;
import javax.swing.ListModel;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.osframework.framework.logging.*;
import com.osframework.framework.utility.TimedAction;
import com.osframework.appclient.services.CacheException;
//import com.osframework.appclient.services.ReferenceServices;
//import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.components.UIItem;
import com.osframework.datalibrary.reference.common.*;
import com.osframework.datalibrary.reference.security.*;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.common.*;
import com.osframework.ejb.common.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.framework.exceptions.*;
import com.osrmt.appclient.setting.AuthenticationSetting;
import com.osrmt.appclient.setting.DataFormatSetting;


public class SecurityBean implements EntityBean, ISecurity {

	private EntityContext context= null;
	private ReferenceDataAdapter rda;
	private ApplicationSecurityDataAdapter asda;
	private ApplicationSettingDataAdapter aseda;
	private ApplicationUserDataAdapter uda ;
	private ApplicationControlDataAdapter acda;
	private ApplicationSettingDataAdapter atda;
	private ApplicationCustomControlDataAdapter accda;
	private ApplicationViewDataAdapter avda;
	private ApplicationUserGroupDataAdapter ugda ;
	private AppControlTemplateDataAdapter actda;
	
	private static final long serialVersionUID = 1L;
	
	private IReferenceMap reference;
	private ReferenceMapBean localReference;
	private boolean ldapAuthentication = false;
	private boolean nameFormatFirstLast = false;
	private boolean restrictProductAccess = false;
	private ConcurrentHashMap<Integer, ApplicationUserModel> usermap = new ConcurrentHashMap<Integer, ApplicationUserModel>(1024*1024);

	private static SecurityBean securityBean2Tier = null;
	
	public SecurityBean() throws Exception {
		try {
			securityBean2Tier = this;
			//TODO document decision to instantiate beans directly
			// in order to process transactions
			localReference = ReferenceMapBean.get2TierInstance();
			reference = ReferenceMapBean.get2TierInstance();
			rda = new ReferenceDataAdapter(reference, this);
			uda = new ApplicationUserDataAdapter(reference, this);
			acda = new ApplicationControlDataAdapter(reference, this);
			accda = new ApplicationCustomControlDataAdapter(reference, this);
			avda = new ApplicationViewDataAdapter(reference, this);
			asda = new ApplicationSecurityDataAdapter(reference, this, avda);
			aseda = new ApplicationSettingDataAdapter(reference, this);
			uda = new ApplicationUserDataAdapter(reference, this);
			ugda = new ApplicationUserGroupDataAdapter(reference, this);
			atda = new ApplicationSettingDataAdapter(reference, this);
			actda = new AppControlTemplateDataAdapter(reference, this);
			ApplicationSettingList asl = getSettingsByUser(ApplicationSettingFramework.OSRMT_AUTHENTICATION_METHOD, ApplicationFramework.get(0), 0, ViewFramework.get(0), new ServiceCall(new ApplicationUserModel()));
			if (asl.size() > 0 && asl.getFirst().getValueString() != null) {
				this.ldapAuthentication = asl.getFirst().getValueString().equalsIgnoreCase("LDAP");
			}
			asl = getSettingsByUser(ApplicationSettingFramework.USERNAME_FIRST_LAST, ApplicationFramework.get(0), 0, ViewFramework.get(0), new ServiceCall(new ApplicationUserModel()));
			if (asl.size() > 0 ) {
				this.nameFormatFirstLast = asl.getFirst().getValueInt() == 1;
				ApplicationUserModel.setFirstNameLastFormat(this.nameFormatFirstLast);
			}
			asl = getSettingsByUser(ApplicationSettingFramework.RESTRICT_PRODUCT_ACCESS, ApplicationFramework.get(0), 0, ViewFramework.get(0), new ServiceCall(new ApplicationUserModel()));
			if (asl.size() > 0 && asl.getFirst().getValueInt() ==1) {
				this.restrictProductAccess = true;
			}

			if (Db.countTable("reference_group") == 0) {
				throw new Exception("empty schema found, run upgrade.bat and initialize database");
			}
			loadcache();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	public SecurityBean(IReferenceMap reference) {
		try {
			this.reference = reference;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	public static SecurityBean get2TierInstance() throws Exception {
		if (securityBean2Tier == null) {
			securityBean2Tier = new SecurityBean();
		}
		return securityBean2Tier;
	}
	
	private void loadcache() throws Exception {
		Runnable loader = new Runnable() {

			public void run() {
				try {
					ApplicationUserList asl = uda.getAllUsers();
					Enumeration e1 = asl.elements();
					while (e1.hasMoreElements()) {
						ApplicationUserModel aum = (ApplicationUserModel) asl.nextElement();
						usermap.put(aum.getUserId(), aum);
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
		Thread thread = new Thread(loader);
		thread.start();
		thread.join();
	}
	
	/**
	 * Retreive the position->application->action authorization
	 * @throws DataAccessException
	private void loadApplicationCache() throws Exception {
       	if (reference == null) {
       		throw new Exception("null reference");
       	}
       	ReferenceDisplayList positionList = reference.getDisplayList(ReferenceGroup.Position,false);
       	Enumeration e1 = positionList.elements();
       	while (e1.hasMoreElements()) {
       		// position id
       		ReferenceDisplay position = (ReferenceDisplay) e1.nextElement();
       		
	       	ReferenceDisplayList roles = reference.getChildren(position.getRefId());
	       	ReferenceDisplayList workflows = reference.getChildren(roles);
	       	ReferenceDisplayList actions = reference.getChildren(workflows);
	       	Enumeration e3 = actions.elements();
	       	while (e3.hasMoreElements()) {
	       		ReferenceDisplay action = (ReferenceDisplay) e3.nextElement();
	       		//TODO Handle this key more gracefully
	       		String key = getAuthKey(position.getRefId(),action.getRefId());
	       		//if (authCache.get(key)==null){
	       		//	authCache.add(key, "");
	       		//}
	       	}
       	}
       	
	}
	 */
	
	/**
	 * Generates a key from these 3 models
	 * @param position
	 * @param application
	 * @param action
	 */
	private String getAuthKey(int positionRefId, int actionRefId) {
		return positionRefId + ":"
			+ actionRefId;
	}
	
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}
	
	public String ejbFind() {
		return "SecurityBean";
}

	public void unsetEntityContext() {
		this.context = null;
}

	public String ejbCreate() {
		return "SecurityBean";
	}
	
	public void ejbStore() {
		// Store is executed with every call
		// avoid any debug messages
	}

	public void ejbLoad() {
	}
	
	public void ejbPostCreate() {
	}
	
	public String ejbFindByPrimaryKey(String key) {
		return "SecurityBean";
	}


	public void ejbRemove() {

	} 

	public void ejbActivate() {

	}

	public void ejbPassivate() {

	}
	
	/**
	 * Returns true if the position is authorized to execution the action
	 * for the specified application.
	 * @param positionRefId
	 * @param applicationRefId
	 * @param actionRefId
	 * @return
	 */
	public void authorize(int positionRefId, int actionRefId) throws RemoteException, DataAccessException, AuthorizeException{
		//String key = getAuthKey(positionRefId, actionRefId);
		//if (authCache.get(key)==null && actionRefId > 0) {
		//	throw new AuthorizeException(positionRefId + ":" + actionRefId);
		//}
		//TODO cache security
		if (actionRefId > 0) {
	       	ReferenceDisplayList roles = reference.getChildren(positionRefId);
	       	ReferenceDisplayList workflows = reference.getChildren(roles);
	       	ReferenceDisplayList actions = reference.getChildren(workflows);
	       	Enumeration e3 = actions.elements();
	       	while (e3.hasMoreElements()) {
	       		ReferenceDisplay action = (ReferenceDisplay) e3.nextElement();
	       		if (action.getRefId() == actionRefId) {
	       			return;
	       		}
	       	}
	       	throw new AuthorizeException(positionRefId + ":" + actionRefId);
		}
	}
	
	/**
	 * Return the security references which the user has access to for the
	 * application.
	 * 
	 * @param user
	 * @param applicationRefId
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 */
	public ApplicationSecurityList getAppSecurity(ApplicationFramework application, ServiceCall call) throws DataAccessException, Exception {		
		try {
			//TODO Additional calls if by position fails
			ApplicationSecurityList asl = asda.getSecurity(call.getUserId(), TableNameFramework.APPLICATIONUSER, application);
			if (asl.size()==0) {
				asl = asda.getSecurity(call.getUser().getPositionRefId(), TableNameFramework.REFERENCE, application);
			}
			if (asl.size()==0) {
				asl = asda.getSecurity(0, TableNameFramework.APPLICATIONUSER, 0, application);
			}
			setReferenceDisplay(asl, call);
			return asl;
		} catch (Exception e) {
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	private void setReferenceDisplay(ApplicationSecurityList asl, ServiceCall call) throws Exception {
		asl.setReferenceDisplay(reference, this);
		Enumeration e1 = asl.elements();
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();
			ApplicationViewModel avm = getApplicationView(asm.getApplicationViewId(), call);
			asm.setApplicationRefId(avm.getApplicationRefId());
			asm.setApplicationRefDisplay(avm.getApplicationRefDisplay());
			asm.setViewRefId(avm.getViewRefId());
			asm.setViewRefDisplay(avm.getViewRefDisplay());
			asm.setAppTypeRefId(avm.getAppTypeRefId());
			asm.setAppTypeRefDisplay(avm.getAppTypeRefDisplay());
		}
	}

	/**
	 * Return the security references which the user has access to for the
	 * application.
	 * 
	 * If an security row of USER and user id 0 exists that will be used if all else fails
	 * 
	 * @param user
	 * @param applicationRefId
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 */
	public ApplicationSecurityList getAppSecurity(ApplicationFramework application, int appTypeRefId, ServiceCall call) throws java.rmi.RemoteException, Exception {		
		try {
			//TODO Additional calls if by position fails - perhaps by location
			ApplicationSecurityList asl = asda.getSecurity(call.getUserId(), TableNameFramework.APPLICATIONUSER, appTypeRefId, application);
			if (asl.size()==0) {
				asl = asda.getSecurity(call.getUser().getPositionRefId(), TableNameFramework.REFERENCE, appTypeRefId, application);
			}			
			if (asl.size()==0) {
				asl = asda.getSecurity(0, TableNameFramework.APPLICATIONUSER, appTypeRefId, application);
			}
			setReferenceDisplay(asl, call);
			return asl;
		} catch (Exception e) {
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**
	 * Return the security references which the user has access to for the
	 * application.
	 * 
	 * @param user
	 * @param applicationRefId
	 * @param viewRefId
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws Exception
	 */
	public ApplicationSecurityList getAppSecurity(ApplicationFramework application, int appTypeRefId, ViewFramework view, ServiceCall call) throws java.rmi.RemoteException, Exception {		
		try {
			//TODO Additional calls if by position fails
			if (call == null) {
				throw new NullPointerException("ServiceCall");
			}
			ApplicationSecurityList asl = asda.getSecurity(call.getUserId(), TableNameFramework.APPLICATIONUSER, appTypeRefId, application, view);
			if (asl.size()==0 && call != null) {
				asl = asda.getSecurity(call.getPositionRefId(), TableNameFramework.REFERENCE, appTypeRefId, application, view);
			}
			setReferenceDisplay(asl, call);
			return asl;
		} catch (Exception e) {
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**
	 *  Returns the full user model if the user name and password combination is valid
	 *  
	 * @param user with the UserLogin and Password not null
	 * @return User model
	 * @throws java.rmi.RemoteException
	 * @throws InvalidUserLoginException if the user login name is not found
	 * @throws InvalidUserPasswordException if the password does not match the user login name
	 */
	public ApplicationUserModel authenticate(ApplicationUserModel user) throws java.rmi.RemoteException, Exception, InvalidUserLoginException, InvalidUserPasswordException {
		try { 
			if (user.getUserLogin() == null || user.getPassword() == null) {
				throw new InvalidUserLoginException();
			}
			ApplicationUserModel m = uda.getByUsername(user.getUserLogin());
			if (m.getUserLogin() == null || m.isNotActive()) {
				throw new InvalidUserLoginException(user.getUserLogin());
			} else {
				if (ldapAuthentication) {
					new LdapAuthentication().authenticate(user.getUserLogin(), user.getPassword());
					return m;
				} else {
					if (m.getPassword().trim().compareToIgnoreCase(user.getPassword().trim())==0) {
						return m;
					} else {
						throw new InvalidUserPasswordException(user.getUserLogin());
					}
				}
			}
		} catch (InvalidUserLoginException iue) {
			Debug.LogWarning(this, iue.toString());
			throw iue;
		//} catch (InvalidUserPasswordException iupe) {
		//	Debug.LogWarning(this, iupe.toString());
		//	throw iupe;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**
	 * Returns the list of application controls
	 * 
	 * @param group
	 * @return
	 */
	public ApplicationControlList getAppControls(ViewFramework view, ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, Exception {
		return getAppControls(view, 0, application, call);
	}
	
	/**
	 * Returns the list of application controls
	 * 
	 * @param group
	 * @return
	 */
	public ApplicationControlList getAppControls(ViewFramework view, int appTypeRefId, ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, Exception {
		try {
			startService(call);
			ApplicationControlList controls = acda.getControlsByApplication(view, appTypeRefId, application);
			if (controls.size() == 0) {
				Debug.LogDebug(this, localReference.getDisplay(SystemMessageFramework.APPLICATIONACCESSDENIED) + " " + 
								application.getApplicationRefId() + " = " + reference.getDisplay(application.getApplicationRefId())  + " " + reference.getDisplay(appTypeRefId));
			} else {
				setReadOnly(controls, view, appTypeRefId, application, call);
			}
			controls.setReferenceDisplay(reference, this);
			stopService(call);
			return controls;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}

	}
	
	/**
	 * Determine if the application security is set to readonly
	 * and if so disable all controls
	 * 
	 * @param controls
	 * @param viewRefId
	 * @param appTypeRefId
	 * @param applicationRefId
	 */
	private void setReadOnly (ApplicationControlList controls, ViewFramework view, int appTypeRefId, ApplicationFramework application, ServiceCall call) throws Exception {
		ApplicationSecurityList securityList = getAppSecurity(application, appTypeRefId, view, call);
		Enumeration e1 = securityList.elements();
		if (e1.hasMoreElements()) {
			ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();
			if (asm.isReadOnly()) {
				Enumeration e2 = controls.elements();
				while (e2.hasMoreElements()) {
					ApplicationControlModel acm = (ApplicationControlModel) e2.nextElement();
					acm.setDisabled();
				}
			}
		}
		
	}
	
	/**
	 * Returns the list of application controls for the first
	 * security reference found
	 * 
	 * @param group
	 * @return
	 */
	public ApplicationControlList getAppControlsByUser(ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, Exception{
		return getAppControlsByUser(application, 0, call);
	}
	
	/**
	 * Returns the list of application controls for the first
	 * security reference found
	 * 
	 * @param group
	 * @return
	 */
	public ApplicationControlList getAppControlsByUser(ApplicationFramework application, int appTypeRefId, ServiceCall call) throws java.rmi.RemoteException, Exception{
		try {
			startService(call);
			ApplicationControlList controls = new ApplicationControlList();
			ApplicationSecurityList asl = this.getAppSecurity(application, appTypeRefId, call);
			Enumeration e1 = asl.elements();
			if (e1.hasMoreElements()) {
				ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();
				ApplicationViewModel avm = getApplicationView(asm.getApplicationViewId(), call);
		    	controls = getAppControls(ViewFramework.get(avm.getViewRefId()), appTypeRefId, application, call);
			}
			if (e1.hasMoreElements()) {
				Debug.LogWarning(this, "User has multiple security references for application " + application.getApplicationRefId() + " " + call.getUser() + new Exception().getStackTrace().toString());
			}			
			// sort by sequence
			stopService(call);
			if (controls.size()==0) {
				Debug.LogDebug(this, 
						localReference.getDisplay(SystemMessageFramework.APPLICATIONACCESSDENIED) + " " + 
						application.getApplicationRefId() + " = " + reference.getDisplay(application.getApplicationRefId())  + " " + reference.getDisplay(appTypeRefId));
			}
			return controls;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}

	}
	
	public int getAppControlSourceRefId(ViewFramework view, ApplicationFramework application, String controlName, ServiceCall call) throws java.rmi.RemoteException, Exception{
		return getAppControlSourceRefId(view, 0, application, controlName, call);
	}

	
	public int getAppControlSourceRefId(ViewFramework view, int appTypeRefId, ApplicationFramework application, String controlName, ServiceCall call) throws java.rmi.RemoteException, Exception{
		try {
			startService(call);
			ApplicationControlList controls = getAppControls(view, appTypeRefId, application, call);
			Enumeration e1 = controls.elements();
			int sourceRefId = 0;
			while (e1.hasMoreElements()) {
				ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
				if (acm.getControlRefDisplay().compareTo(controlName)==0) {
					sourceRefId = acm.getSourceRefId();
					break;
				}
			}
			stopService(call);
			return sourceRefId;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	
	
	protected void startService(ServiceCall call) throws RemoteException, DataAccessException, AuthorizeException {
		Statistics.startCall();
		if (call.getUser() != null && call.getUser() != null) {
			authorize(call.getUser().getPositionRefId(), call.getActionRefId());
		} else {
			authorize(0, call.getActionRefId());
		}
	}
	
	protected void stopService(ServiceCall call) throws RemoteException, DataAccessException, AuthorizeException {
		Statistics.stopCall();
	}

	public void printStatistics() throws RemoteException, RemoteException, Exception {
		try {
			Debug.LogDebug(this, Statistics.statsToString(true));
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	

    public ApplicationUserModel getUser(int userId) throws java.rmi.RemoteException, java.rmi.RemoteException, Exception {
		try {
			if (usermap.containsKey(userId)) {
				return usermap.get(userId);
			}
			if (CacheService.isCached(userId)) {
				return (ApplicationUserModel) CacheService.getCachedResult(userId);
			} else {
				ApplicationUserModel user = uda.getApplicationUser(userId);
				return (ApplicationUserModel) CacheService.cache(user, userId);					
			}
		} catch (CacheException ce) {
			return uda.getApplicationUser(userId);
		}
    }

    public ApplicationUserList getAllUsers(ServiceCall call) throws java.rmi.RemoteException, java.rmi.RemoteException, Exception {
    	try {
    		startService(call);
    		ApplicationUserList list = uda.getAllUsers();
    		stopService(call);
    		return list;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		throw ex;
    	}
    }

	public UpdateResult UpdateUser(ApplicationUserModel m, ServiceCall call, DbConnection conn) throws RemoteException, DataAccessException, Exception {
		m.setRecordTypeRefId(RecordTypeFramework.USERREFERENCE);
		return uda.UpdateApplicationUser(m,call);
	}
	
	public UpdateResult UpdateUser(final ApplicationUserModel m, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		UpdateResult result = UpdateUser(m, call, conn);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	public ReferenceDisplayList getViews(int appTypeRefId, ApplicationFramework application, ServiceCall call) throws RemoteException, DataAccessException, Exception  {
    	try {
    		startService(call);
    		ReferenceDisplayList list = avda.getViews(reference, appTypeRefId, application);
    		stopService(call);
    		return list;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		throw ex;
    	}
	}

	public ReferenceDisplayList getAppTypes(ApplicationFramework application, ServiceCall call) throws RemoteException, DataAccessException, Exception  {
    	try {
    		startService(call);
    		ReferenceDisplayList list = avda.getAppTypes(reference, application);
    		stopService(call);
    		return list;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		throw ex;
    	}
	}
	
	//TODO standard template for Update bean methods
	public UpdateResult UpdateApplicationControl(ApplicationControlList acl, ServiceCall call, DbConnection conn) throws RemoteException, DataAccessException, Exception {
		Enumeration e1 = acl.elements();
		int rowsUpdated = 0;
		while (e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			acm.setRecordTypeRefId(RecordTypeFramework.USERREFERENCE);
			rowsUpdated += acda.UpdateApplicationControl(acm, call, conn).getRowsUpdated();
		}
		return new UpdateResult(rowsUpdated,0);
	}
	

	public UpdateResult UpdateApplicationControl(final ApplicationControlList acl, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		UpdateResult result = UpdateApplicationControl(acl, call, conn);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	public UpdateResult UpdateApplicationControl(ApplicationControlModel m, ServiceCall call, DbConnection conn) throws RemoteException, DataAccessException, Exception {
		m.setRecordTypeRefId(RecordTypeFramework.USERREFERENCE);
		return acda.UpdateApplicationControl(m, call, conn);
	}
	
	public UpdateResult UpdateApplicationControl(final ApplicationControlModel m, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		UpdateResult result = UpdateApplicationControl(m, call, conn);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	public ApplicationControlModel getApplicationControl(int applicationControlId, ServiceCall call)  throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ApplicationControlModel acm = acda.getApplicationControl(applicationControlId);
			stopService(call);
			return acm;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	public ApplicationUserModel getUserByLogin(String username, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ApplicationUserModel user = uda.getByUsername(username);
			stopService(call);
			return user;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	//TODO instead of a partially populated ApplicationSecurityList should
	//base a model on the query and just have the 3 fields
	public ApplicationViewList getApplicationViewsByType(ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ApplicationViewList asl = avda.getApplicationViewsByType();
			stopService(call);
			return asl;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	public ApplicationSecurityList getAppSecurity(ApplicationUserModel user, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ApplicationSecurityList asl = asda.getAppSecurity(user);
			stopService(call);
			return asl;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	public ApplicationSecurityList getAppSecurity(PositionFramework position, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ApplicationSecurityList asl = asda.getAppSecurity(position);
			stopService(call);
			return asl;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	public ApplicationSecurityList getAppSecurityGlobal(ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ApplicationSecurityList asl = asda.getAppSecurityGlobal();
			stopService(call);
			return asl;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}

	public UpdateResult UpdateApplicationSecurity(ApplicationSecurityModel asm, ServiceCall call, DbConnection conn) throws RemoteException, DataAccessException, Exception {
		asm.setRecordTypeRefId(RecordTypeFramework.USERREFERENCE);
		return asda.UpdateApplicationSecurity(asm, call, conn);
	}

	public UpdateResult UpdateApplicationSecurity(final ApplicationSecurityModel asm, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		UpdateResult result = UpdateApplicationSecurity(asm, call, conn);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}

	public UpdateResult duplicateApplicationControlView(final String newViewName, final ViewFramework view, final int appTypeRefId, final ApplicationFramework application,  ServiceCall call) throws RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		UpdateResult result = duplicateApplicationControlView(newViewName, view, appTypeRefId, application, call, conn);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	public UpdateResult duplicateApplicationControlView(String newViewName, ViewFramework view, int appTypeRefId, ApplicationFramework application,  ServiceCall call, DbConnection conn) throws RemoteException, DataAccessException, Exception {
		
		// Create the new reference view
		ReferenceModel rm = new ReferenceModel();
		rm.setDisplay(newViewName);
		rm.setReferenceGroup("View");
		UpdateResult result = localReference.UpdateReference(rm,call, conn);
		
		// Create the application view
		ApplicationViewModel avm = new ApplicationViewModel();
		avm.setApplicationRefId(application.getApplicationRefId());
		avm.setViewRefId(result.getPrimaryKeyId());
		avm.setAppTypeRefId(appTypeRefId);
		UpdateResult updateView = avda.UpdateApplicationView(avm, call, conn);

		// Copy the controls
		ApplicationControlList copiedAcl = getAppControls(view, appTypeRefId, application, call);
		ApplicationControlList acl = new ApplicationControlList();
		Enumeration e1 = copiedAcl.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			ApplicationControlModel m = new ApplicationControlModel();
			m.updateWith(acm);
			m.setApplicationControlId(0);
			m.setApplicationViewId(updateView.getPrimaryKeyId());
			acl.add(m);
		}
		return UpdateApplicationControl(acl, call, conn);
	}

	
	public ApplicationUserList exportSysemUser(ServiceCall call) throws RemoteException, Exception {
		try {
			startService(call);
			ApplicationUserList users = uda.exportApplicationUser();
			stopService(call);
			return users;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	public int importApplicationUser(ApplicationUserList list, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			int rows = uda.importApplicationUser(list);
			stopService(call);
			return rows;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	
	/**  
	 *  Export all system required application controls
	 */ 
	public ApplicationControlList exportApplicationControl(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationControlList acl = acda.exportApplicationControl();
			stopService(call);
			return acl;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Export all system required application controls
	 */ 
	public AppControlTemplateList exportAppControlTemplate(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			AppControlTemplateList acl = actda.exportAppControlTemplate();
			stopService(call);
			return acl;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Export all system required application controls
	 */ 
	public ApplicationCustomControlList exportApplicationCustomControl(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationCustomControlList acl = accda.exportApplicationCustomControl();
			stopService(call);
			return acl;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Import all controls
	 */ 
	public int importApplicationControl(ApplicationControlList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			int rows = acda.importApplicationControl(list);
			stopService(call);
			return rows;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Import all controls
	 */ 
	public int importApplicationCustomControl(ApplicationCustomControlList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			int rows = accda.importApplicationCustomControl(list);
			stopService(call);
			return rows;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Export all system required application security
	 */ 
	public ApplicationSecurityList exportApplicationSecurity(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationSecurityList list = asda.exportApplicationSecurity();
			stopService(call);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Export all system required application security
	 */ 
	public ApplicationViewList exportApplicationView(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationViewList list = avda.exportApplicationView();
			stopService(call);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Import all application security
	 */ 
	public int importApplicationSecurity(ApplicationSecurityList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			int rows = asda.importApplicationSecurity(list);
			stopService(call);
			return rows;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Import all application security
	 */ 
	public int importApplicationView(ApplicationViewList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			int rows = avda.importApplicationView(list);
			stopService(call);
			return rows;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Export all application settings
	 */ 
	public ApplicationSettingList exportApplicationSetting(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationSettingList list = atda.exportApplicationSetting();
			stopService(call);
			return list;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Import all applicaiton setting
	 */ 
	public int importApplicationSetting(ApplicationSettingList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			int rows = atda.importApplicationSetting(list);
			stopService(call);
			return rows;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Import all applicaiton setting
	 */ 
	public int importAppControlTemplate(AppControlTemplateList list, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			int rows = actda.importAppControlTemplate(list);
			stopService(call);
			return rows;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Get the application custom control
	 */ 
	public ApplicationCustomControlModel getApplicationCustomControl(int id, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationCustomControlModel control = accda.getApplicationCustomControl(id);
			stopService(call);
			return control;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  SQL is logged to debug file or not
	 */ 
	public void debugSql(boolean debugOn, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			Access.setDumpSql(debugOn);
			stopService(call);
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**  
	 *  Get the application view
	 */ 
	public ApplicationViewModel getApplicationView(int applicationViewId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationViewModel view = avda.getApplicationView(applicationViewId);
			stopService(call);
			return view;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	/**
	 * Returns the list of application controls for the first
	 * security reference found
	 * 
	 * @param group
	 * @return
	 */
	public ApplicationSettingList getSettingsByUser(ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, Exception{
		return getSettingsByUser(application, 0, ViewFramework.get(0), call);
	}
	
	/**
	 * Returns the list of application controls for the first
	 * security reference found
	 * 
	 * @param group
	 * @return
	 */
	public ApplicationSettingList getSettingsByUser(ApplicationFramework application, int appTypeRefId, ServiceCall call) throws java.rmi.RemoteException, Exception{
		return getSettingsByUser(application, appTypeRefId, ViewFramework.get(0), call);
	}
	
	/**
	 * Returns the list of application controls for the first
	 * security reference found
	 * 
	 * @param group
	 * @return
	 */
	public ApplicationSettingList getSettingsByUser(ApplicationFramework application, int appTypeRefId, ViewFramework view, ServiceCall call) throws java.rmi.RemoteException, Exception{
		try {
			//TODO Additional calls if by position fails - perhaps by location
			ApplicationSettingList asl = aseda.getApplicationSetting(call.getUserId(), TableNameFramework.APPLICATIONUSER, application, appTypeRefId, view);
			if (asl.size()==0) {
				asl = aseda.getApplicationSetting(call.getUser().getPositionRefId(), TableNameFramework.REFERENCE, application, appTypeRefId, view);
			}			
			if (asl.size()==0) {
				asl = aseda.getApplicationSetting(0, TableNameFramework.APPLICATIONUSER, application, appTypeRefId, view);
			}
			asl.setReferenceDisplay(reference, this);
			return asl;
		} catch (Exception e) {
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Application setting
	 */ 
	public ApplicationSettingList getSettingsByUser(int settingRefId, ApplicationFramework application, int appTypeRefId, ViewFramework view, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			//TODO Get setting to see if only one call e.g. global needed
			ApplicationSettingList asl = aseda.getApplicationSetting(settingRefId, call.getUserId(), TableNameFramework.APPLICATIONUSER, application, appTypeRefId, view);
			if (asl.size()==0) {
				asl = aseda.getApplicationSetting(settingRefId, call.getUser().getPositionRefId(), TableNameFramework.REFERENCE, application, appTypeRefId, view);
			}			
			if (asl.size()==0) {
				asl = aseda.getApplicationSetting(settingRefId, 0, TableNameFramework.APPLICATIONUSER, application, appTypeRefId, view);
			}
			asl.setReferenceDisplay(reference, this);
			return asl;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	
	/**  
	 *  Update the custom control
	 */ 
	public UpdateResult UpdateCustomControl(ApplicationCustomControlModel accm, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		UpdateResult result = UpdateCustomControl(accm, call, conn);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	/**  
	 *  Update the custom control
	 */ 
	public UpdateResult UpdateCustomControl(ApplicationCustomControlModel accm, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		accm.setRecordTypeRefId(RecordTypeFramework.USERREFERENCE);
		return accda.UpdateApplicationCustomControl(accm, call, conn);
	}
	
	/**  
	 *  Update the application view
	 */ 
	public UpdateResult UpdateApplicationView(ApplicationViewList list, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		Enumeration e1 = list.elements();
		int rowsUpdated = 0;
		while (e1.hasMoreElements()) {
			ApplicationViewModel acm = (ApplicationViewModel) e1.nextElement();
			acm.setRecordTypeRefId(RecordTypeFramework.USERREFERENCE);
			rowsUpdated += avda.UpdateApplicationView(acm, call, conn).getRowsUpdated();
		}
		return new UpdateResult(rowsUpdated,0);
	}

	public UpdateResult UpdateApplicationView(final ApplicationViewList acl, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		UpdateResult result = UpdateApplicationView(acl, call, conn);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	public UpdateResult UpdateApplicationSecurity(final ApplicationSecurityList acl, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		UpdateResult result = UpdateApplicationSecurity(acl, call, conn);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	/**  
	 *  Update the application view
	 */ 
	public UpdateResult UpdateApplicationSecurity(ApplicationSecurityList list, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		Enumeration e1 = list.elements();
		int rowsUpdated = 0;
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel acm = (ApplicationSecurityModel) e1.nextElement();
			acm.setRecordTypeRefId(RecordTypeFramework.USERREFERENCE);
			rowsUpdated += asda.UpdateApplicationSecurity(acm, call, conn).getRowsUpdated();
		}
		return new UpdateResult(rowsUpdated,0);
	}

	/**  
	 *  Get the application view
	 */ 
	public ApplicationSecurityModel getApplicationSecurity(int applicationSecurityId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationSecurityModel Security = asda.getApplicationSecurity(applicationSecurityId);
			stopService(call);
			return Security;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	public boolean duplicateApplicationSecurity(int fromPositionRefId, int toPositionRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		boolean result = duplicateApplicationSecurity(fromPositionRefId, toPositionRefId, conn, call);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	/**  
	 *  Duplicate application security settings
	 */ 
	public boolean duplicateApplicationSecurity(int fromPositionRefId, int toPositionRefId, DbConnection conn, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ApplicationSecurityList fromList = asda.getAppSecurity(PositionFramework.get(fromPositionRefId));
			asda.deleteApplicationSecurity(PositionFramework.get(toPositionRefId), conn);
			Enumeration e1 = fromList.elements();
			while (e1.hasMoreElements()) {
				ApplicationSecurityModel asfrom = (ApplicationSecurityModel) e1.nextElement();
				ApplicationSecurityModel asNew = new ApplicationSecurityModel();
				asNew.updateWith(asfrom);
				asNew.setTableKeyId(toPositionRefId);
				asNew.setApplicationSecurityId(0);
				asda.UpdateApplicationSecurity(asNew,call, conn);
			}
			stopService(call);
			return true;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
	

	public boolean duplicateArtifactControls(int fromArtifactRefId, int toArtifactRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		boolean result = duplicateArtifactControls(fromArtifactRefId, toArtifactRefId, conn, call);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	class ViewPair {
		public int fromId = 0;
		public int toId = 0;
		public ViewPair(int from, Integer to) {
			this.fromId = from; this.toId = to.intValue();
		}
	}
	
	
	public void deleteTarget(int toArtifactRefId, DbConnection conn, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		ApplicationViewList toViewList = avda.getApplicationViews(ArtifactFramework.get(toArtifactRefId), conn);
		Enumeration e1 = toViewList.elements();
		while (e1.hasMoreElements()) {
			ApplicationViewModel avto = (ApplicationViewModel) e1.nextElement();
			acda.deleteApplicationControl(avto, conn);
		}
		
		avda.deleteApplicationView(ArtifactFramework.get(toArtifactRefId), conn);
	}
	
	synchronized
	public boolean duplicateArtifactControls(int fromArtifactRefId, int toArtifactRefId, DbConnection conn, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			deleteTarget(toArtifactRefId, conn, call);
			ApplicationViewList fromViewList = avda.getApplicationViews(ArtifactFramework.get(fromArtifactRefId), conn);
			avda.deleteApplicationView(ArtifactFramework.get(toArtifactRefId), conn);
			Enumeration e1 = fromViewList.elements();
			
			Vector viewsupdated = new Vector<Integer>();
			while (e1.hasMoreElements()) {
				ApplicationViewModel avfrom = (ApplicationViewModel) e1.nextElement();
				
				ApplicationViewModel avnew = new ApplicationViewModel();
				avnew.updateWith(avfrom);
				avnew.setAppTypeRefId(toArtifactRefId);
				avnew.setApplicationViewId(0);
				UpdateResult result = avda.UpdateApplicationView(avnew, call, conn);
				viewsupdated.add(new ViewPair(avfrom.getApplicationViewId(), result.getPrimaryKeyId()));
			}		
			Enumeration e2 = viewsupdated.elements();
			while (e2.hasMoreElements()) {
				ViewPair viewpair = (ViewPair) e2.nextElement();
				ApplicationViewModel avm = new ApplicationViewModel();
				avm.setApplicationViewId(viewpair.fromId);
				ApplicationControlList acl = acda.getApplicationControls(avm);
				Enumeration e3 = acl.elements();
				while (e3.hasMoreElements()) {
					ApplicationControlModel acm = (ApplicationControlModel) e3.nextElement();
					ApplicationControlModel acmnew = new ApplicationControlModel();
					acmnew.updateWith(acm);
					acmnew.setApplicationViewId(viewpair.toId);
					acmnew.setApplicationControlId(0);
					acda.UpdateApplicationControl(acmnew, call, conn);
				}
			}
			stopService(call);
			return true;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			throw ex;
		}
	}
		
	public void clearSystemCache()  throws java.rmi.RemoteException, DataAccessException, Exception {
		CacheService.clearCache();
	}

	public String getEnvironment() throws RemoteException, DataAccessException, Exception {
		return Db.getEnvironment();
	}

	/**  
	 *  Get the application view
	 */ 
	public ApplicationUserGroupModel getApplicationUserGroup(int applicationUserGroupId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationUserGroupModel userGroup = ugda.getApplicationUserGroup(applicationUserGroupId);
			stopService(call);
			return userGroup;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	/**  
	 *  Update the custom control
	 */ 
	public UpdateResult updateApplicationUserGroup(ApplicationUserGroupModel accm, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		UpdateResult result = UpdateUserGroup(accm, call, conn);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	/**  
	 *  Update the custom control
	 */ 
	public UpdateResult UpdateUserGroup(ApplicationUserGroupModel accm, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		accm.setRecordTypeRefId(RecordTypeFramework.USERREFERENCE);
		if (accm.isNotActive()) {
			UpdateResult result = new UpdateResult();
			result.setRowsUpdated(ugda.DeleteApplicationUserGroup(accm, conn));
			return result;
		} else {
			return ugda.UpdateApplicationUserGroup(accm, call, conn);
		}
	}

	/**  
	 *  Get the application view
	 */ 
	public ApplicationUserGroupList getUserGroup(int userGroupRefId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationUserGroupList userGroup = ugda.getUserGroup(userGroupRefId);
			stopService(call);
			return userGroup;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}


	//SecurityBean
	/**  
	 *  Get the application view
	 */ 
	public ApplicationViewModel getApplicationView(ViewFramework view, int appTypeRefId, ApplicationFramework application, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try {
			startService(call);
			ApplicationViewModel avm = avda.getApplicationView(view, appTypeRefId, application);
			stopService(call);
			return avm;
		} catch (Exception ex) {

			Debug.LogException(this, ex);

			throw ex;

		}
	}

	/**  
	 *  Get the application view
	 */ 
	public ApplicationSettingList getGlobalSettings(ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationSettingList settings = atda.getGlobalSettings();
			stopService(call);
			return settings;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}

	public UpdateResult UpdateApplicationSetting(final ApplicationSettingList list, ServiceCall call) throws RemoteException, DataAccessException, Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		UpdateResult result = UpdateApplicationSetting(list, call, conn);
    		stopService(call);
    		return result;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	/**  
	 *  Update the application view
	 */ 
	public UpdateResult UpdateApplicationSetting(ApplicationSettingList list, ServiceCall call, DbConnection conn) throws java.rmi.RemoteException, DataAccessException, Exception {
		Enumeration e1 = list.elements();
		int rowsUpdated = 0;
		while (e1.hasMoreElements()) {
			ApplicationSettingModel acm = (ApplicationSettingModel) e1.nextElement();
			rowsUpdated += atda.UpdateApplicationSetting(acm, call, conn).getRowsUpdated();
		}
		CacheService.clearCache();
		TimedAction ta = new TimedAction(1.0) {
			@Override
			public void executeTask() {
				AuthenticationSetting.initialize();
				DataFormatSetting.initialize();
			}
		};
		return new UpdateResult(rowsUpdated,0);
	}
	
	/**  
	 *  Get the application custom control
	 */ 
	public ApplicationCustomControlModel getApplicationCustomControlByRef(int refId, ServiceCall call) throws java.rmi.RemoteException, DataAccessException, Exception {
		try { 
			startService(call);
			ApplicationCustomControlModel control = accda.getApplicationCustomControlByRef(refId);
			stopService(call);
			return control;
		} catch (Exception e) { 
			Debug.LogException(this, e);
			throw e;
		}
	}
	
	public UpdateResult addNewArtifact(String newArtifactName, int copiedArtifactRefId, ServiceCall call) throws Exception {
		DbConnection conn = null;
    	try {
    		startService(call);
    		CacheService.clearCache();
    		conn = Db.getConnection();
    		ReferenceModel m = new ReferenceModel();
    		m.setDisplay(newArtifactName);
    		m.setDisplayCode();
    		m.setReferenceGroup(ReferenceGroup.Artifact);
    		UpdateResult r = rda.UpdateReference(m, call, conn);
    		asda.addNewArtifact(newArtifactName, r.getPrimaryKeyId(), copiedArtifactRefId, conn, call);
    		
    		stopService(call);
    		return new UpdateResult();
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		if (conn != null) {
    			conn.rollback();
    		}
    		throw ex;
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}

	public ApplicationUserGroupList getAllUserGroups(ServiceCall call) throws Exception {
    	try {
    		startService(call);
    		ApplicationUserGroupList list = ugda.getAllUserGroups();
    		stopService(call);
    		return list;
    	} catch (Exception ex) {
    		Debug.LogException(this, ex);
    		throw ex;
    	}

	}

	
	public boolean isReadOnlyControlList(ApplicationFramework application, int appTypeRefId, ServiceCall call) throws Exception {
		ApplicationSecurityList securityList = getAppSecurity(application, appTypeRefId, ViewFramework.get(0), call);
		Enumeration e1 = securityList.elements();
		if (e1.hasMoreElements()) {
			ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();
			if (asm.isReadOnly()) {
				return true;
			}
		}
		return false;
	}

	public ReferenceDisplayList getProductList(ServiceCall call) throws RemoteException, DataAccessException, Exception {
		
		if (restrictProductAccess) {
			ReferenceList list = rda.getProductList(call.getUserId());
			ReferenceDisplayList products = new ReferenceDisplayList(list.size());
			Enumeration e1 = list.elements();
			while (e1.hasMoreElements()) {
				ReferenceModel rm = (ReferenceModel) e1.nextElement();
				ReferenceDisplay rd = new ReferenceDisplay();
				rd.setRefId(rm.getRefId());
				rd.setDisplay(rm.getDisplay());
				products.add(rd);
			}
			return products;
		} else {
			ReferenceDisplayList list = localReference.getDisplayList("Product", false);
			return list;
		}
	}	
	
	public void addNewArtifactField(ReferenceDisplay artifact, ReferenceDisplayList fields, ServiceCall call) throws Exception {
		Enumeration e1 = fields.elements();
		while (e1.hasMoreElements()) {
			ReferenceDisplay field = (ReferenceDisplay) e1.nextElement();
			AppControlTemplateModel t = actda.getTemplateByRef(field);
			ApplicationControlModel acm = new ApplicationControlModel();
			acm.setApplicationViewId(this.getApplicationView(ViewFramework.get(0), artifact.getRefId(), ApplicationFramework.get(134), call).getApplicationViewId());
			acm.setDisplaySequence(0); // pop at the top
			
			acm.setAppControlUserDefinedId(t.getAppControlUserDefinedId());
			acm.setApplicationCustomControlId(t.getApplicationCustomControlId());
			acm.setControlDescription(t.getControlDescription());
			acm.setControlFormat(t.getControlFormat());
			acm.setControlRefId(t.getControlRefId());
			acm.setControlText(t.getControlText());
			acm.setControlTypeRefId(t.getControlTypeRefId());
			acm.setDefaultValue(t.getDefaultValue());
			acm.setDisabledInd(t.getDisabledInd());
			acm.setFocusGainedScript(t.getFocusGainedScript());
			acm.setFocusLostScript(t.getFocusLostScript());
			acm.setGrowHeight(t.getGrowHeight());
			acm.setGrowWidth(t.getGrowWidth());
			acm.setImagePath(t.getImagePath());
			acm.setInitScript(t.getInitScript());
			acm.setLockedInd(t.getLockedInd());
			acm.setModelColumnRefId(t.getModelColumnRefId());
			acm.setRequiredInd(t.getRequiredInd());		
			acm.setScrollpaneInd(t.getScrollpaneInd());
			acm.setSourceRefId(t.getSourceRefId());
			acm.setUnitHeight(t.getUnitHeight());
			acm.setUnitWidth(t.getUnitWidth());
			acm.setVisibleInd(t.getVisibleInd());
			this.UpdateApplicationControl(acm, call);
			
		}
	}

}
