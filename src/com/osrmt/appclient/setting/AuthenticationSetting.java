package com.osrmt.appclient.setting;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;


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
