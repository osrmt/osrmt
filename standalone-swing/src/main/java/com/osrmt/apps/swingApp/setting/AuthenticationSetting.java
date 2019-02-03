package com.osrmt.apps.swingApp.setting;

import com.osframework.appclient.services.SecurityServices;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.ApplicationSettingFramework;
import com.osframework.modellibrary.reference.security.ApplicationSettingList;


public class AuthenticationSetting extends BaseSetting {
	
	private static boolean ldap = false;

	public static void initialize() {
		try {
			ApplicationSettingList asl = SecurityServices.getSetting(ApplicationFramework.get(0),
					ApplicationSettingFramework.OSRMT_AUTHENTICATION_METHOD);
			if (asl.size() > 0 && asl.getFirst().getValueString() != null) {
				ldap = asl.getFirst().getValueString().equalsIgnoreCase("LDAP");
			}
		} catch (Exception ex) {
			Debug.LogException("AuthenticationSetting", ex);
		}
	}

	public static boolean isLdap() {
		return ldap;
	}

	
}
