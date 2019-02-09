package com.osrmt.appclient.artifact.form;

import java.util.*;

import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.reference.group.*;

public class SystemOptionModel extends ApplicationSettingList implements IControlModel {

	public SystemOptionModel(ApplicationSettingList list) {
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ApplicationSettingModel asm = (ApplicationSettingModel) e1.nextElement();
			ApplicationSettingModel copy = new ApplicationSettingModel();
			copy.updateWith(asm);
			copy.resetModified();
			this.add(copy);
		}
	}

	public SystemOptionModel(int initialCapacity) {
		super(initialCapacity);
	}
	
	private ApplicationSettingModel getSetting(int settingRefId) {
		Enumeration e1 = elements();
		while (e1.hasMoreElements()) {
			ApplicationSettingModel asm = (ApplicationSettingModel) e1.nextElement();
			if (asm.getSettingRefId() == settingRefId) {
				return asm;
			}
		}
		ApplicationSettingModel asm = new ApplicationSettingModel();
		asm.setSettingRefId(settingRefId);
		add(asm);
		return asm;
	}

	private boolean hasSetting(int settingRefId) {
		Enumeration e1 = elements();
		while (e1.hasMoreElements()) {
			ApplicationSettingModel asm = (ApplicationSettingModel) e1.nextElement();
			if (asm.getSettingRefId() == settingRefId) {
				return true;
			}
		}
		return false;		
	}
	
	

	public Object getModelColDataAt(int modelColRefId) {
		switch (modelColRefId) {
		case ModelColumnFramework.SYSTEMOPTIONDISPLAYARTIFACTLONGNAME: 
			return getSetting(ApplicationSettingFramework.DISPLAY_ARTIFACT_LONG_NAME).getValue();
		case ModelColumnFramework.SYSTEMOPTIONLDAPINITIALCONTEXTFACTOR: 
			return getSetting(ApplicationSettingFramework.LDAP_INITIAL_CONTEXT_FACTORY).getValue();
		case ModelColumnFramework.SYSTEMOPTIONLDAPPROVIDERURL: 
			return getSetting(ApplicationSettingFramework.LDAP_PROVIDER_URL).getValue();
		case ModelColumnFramework.SYSTEMOPTIONLDAPSECURITYAUTHENTICATION: 
			return getSetting(ApplicationSettingFramework.LDAP_SECURITY_AUTHENTICATION).getValue();
		case ModelColumnFramework.SYSTEMOPTIONLONGDATETIMEFORMAT: 
			return getSetting(ApplicationSettingFramework.LONGDATETIMEFORMAT).getValue();
		case ModelColumnFramework.SYSTEMOPTIONOSRMTAUTHENTICATION: 
			return getSetting(ApplicationSettingFramework.OSRMT_AUTHENTICATION_METHOD).getValue();
		case ModelColumnFramework.SYSTEMOPTIONSHORTDATEFORMAT: 
			return getSetting(ApplicationSettingFramework.SHORTDATEFORMAT).getValue();
		case ModelColumnFramework.SYSTEMOPTIONSTORAGEDIRECTORY: 
			return getSetting(ApplicationSettingFramework.STORAGEDIRECTORY).getValue();
		case ModelColumnFramework.SYSTEMOPTIONBASELINESTORAGE: 
			return getSetting(ApplicationSettingFramework.STORAGEBASELINE).getValue();
		case ModelColumnFramework.SYSTEMOPTIONOVERRIDEUSERNAME: 
			return getSetting(ApplicationSettingFramework.USERNAME_FIRST_LAST).getValue();
		case ModelColumnFramework.SYSTEMOPTIONIMMEDIATECHILDARTIFACTS: 
			return getSetting(ApplicationSettingFramework.ARTIFACTLISTIMMEDIATECHILDRENFORSELECTED).getValue();
		case ModelColumnFramework.SYSTEMOPTIONRESTRICTPRODUCTACCESS: 
			return getSetting(ApplicationSettingFramework.RESTRICT_PRODUCT_ACCESS).getValue();
		default: Debug.LogError(this,"Model column not accounted for: " + modelColRefId);
		}
		return null;
	}

	public int getModelColDatabaseDataType(int modelColRefId) {
		switch (modelColRefId) {
		case ModelColumnFramework.SYSTEMOPTIONDISPLAYARTIFACTLONGNAME: 
			return getSetting(ApplicationSettingFramework.DISPLAY_ARTIFACT_LONG_NAME).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONLDAPINITIALCONTEXTFACTOR: 
			return getSetting(ApplicationSettingFramework.LDAP_INITIAL_CONTEXT_FACTORY).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONLDAPPROVIDERURL: 
			return getSetting(ApplicationSettingFramework.LDAP_PROVIDER_URL).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONLDAPSECURITYAUTHENTICATION: 
			return getSetting(ApplicationSettingFramework.LDAP_SECURITY_AUTHENTICATION).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONLONGDATETIMEFORMAT: 
			return getSetting(ApplicationSettingFramework.LONGDATETIMEFORMAT).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONOSRMTAUTHENTICATION: 
			return getSetting(ApplicationSettingFramework.OSRMT_AUTHENTICATION_METHOD).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONSHORTDATEFORMAT: 
			return getSetting(ApplicationSettingFramework.SHORTDATEFORMAT).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONSTORAGEDIRECTORY: 
			return getSetting(ApplicationSettingFramework.STORAGEDIRECTORY).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONBASELINESTORAGE: 
			return getSetting(ApplicationSettingFramework.STORAGEBASELINE).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONOVERRIDEUSERNAME: 
			return getSetting(ApplicationSettingFramework.USERNAME_FIRST_LAST).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONIMMEDIATECHILDARTIFACTS: 
			return getSetting(ApplicationSettingFramework.ARTIFACTLISTIMMEDIATECHILDRENFORSELECTED).getDataTypeRefId();
		case ModelColumnFramework.SYSTEMOPTIONRESTRICTPRODUCTACCESS: 
			return getSetting(ApplicationSettingFramework.RESTRICT_PRODUCT_ACCESS).getDataTypeRefId();
		default: Debug.LogError(this,"Model column not accounted for: " + modelColRefId);
		}
		return 0;
	}

	public Object getPrimaryValue() {
		return null;
	}

	public boolean isNew() {
		return false;
	}

	public void setModelColDataAt(Object value, int modelColRefId) {
		switch (modelColRefId) {
		case ModelColumnFramework.SYSTEMOPTIONDISPLAYARTIFACTLONGNAME: 
			getSetting(ApplicationSettingFramework.DISPLAY_ARTIFACT_LONG_NAME).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONLDAPINITIALCONTEXTFACTOR: 
			getSetting(ApplicationSettingFramework.LDAP_INITIAL_CONTEXT_FACTORY).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONLDAPPROVIDERURL: 
			getSetting(ApplicationSettingFramework.LDAP_PROVIDER_URL).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONLDAPSECURITYAUTHENTICATION: 
			getSetting(ApplicationSettingFramework.LDAP_SECURITY_AUTHENTICATION).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONLONGDATETIMEFORMAT: 
			getSetting(ApplicationSettingFramework.LONGDATETIMEFORMAT).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONOSRMTAUTHENTICATION: 
			getSetting(ApplicationSettingFramework.OSRMT_AUTHENTICATION_METHOD).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONSHORTDATEFORMAT: 
			getSetting(ApplicationSettingFramework.SHORTDATEFORMAT).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONSTORAGEDIRECTORY: 
			getSetting(ApplicationSettingFramework.STORAGEDIRECTORY).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONBASELINESTORAGE: 
			getSetting(ApplicationSettingFramework.STORAGEBASELINE).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONIMMEDIATECHILDARTIFACTS:
			getSetting(ApplicationSettingFramework.ARTIFACTLISTIMMEDIATECHILDRENFORSELECTED).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONOVERRIDEUSERNAME:
			getSetting(ApplicationSettingFramework.USERNAME_FIRST_LAST).setValue(value);
			break;
		case ModelColumnFramework.SYSTEMOPTIONRESTRICTPRODUCTACCESS:
			getSetting(ApplicationSettingFramework.RESTRICT_PRODUCT_ACCESS).setValue(value);
			break;
		default: Debug.LogError(this,"Model column not accounted for: " + modelColRefId);
		}	
	}

}
