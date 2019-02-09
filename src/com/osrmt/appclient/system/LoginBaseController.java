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
package com.osrmt.appclient.system;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;

import com.osframework.modellibrary.reference.security.*;
import com.osrmt.appclient.common.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.RecordTypeFramework;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.appclient.services.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.services.*;


/**
null
*/
public class LoginBaseController {
	
	private ApplicationUserModel applicationUser;
	public ApplicationUserModel getApplicationUser() {
		return applicationUser;
	}

	protected RuleScript script = null;
	protected LoginUI ui = new LoginUI();

	
	public void initialize (ApplicationControlList controls, ApplicationUserModel m) {
		this.applicationUser = m;
		script = new RuleScript(null,null);
		initializeUI(controls);
	}
	
	private void initializeUI(ApplicationControlList controls) {
		java.util.Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			if (rm.getControlRefDisplay().compareTo("UserName")==0) {
				UITextField field = getUserName();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					if (rm.getDefaultValue() != null) {
						field.setText(rm.getDefaultValue());
					}
					script.bind(field, rm);
 				}
				ui.setUserName(field);
			}
			if (rm.getControlRefDisplay().compareTo("Password")==0) {
				UIPasswordField field = getPassword();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setPassword(field);
			}
			if (rm.getControlRefDisplay().compareTo("Environment")==0) {
				UIComboBox field = getEnvironment();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setEnvironment(field);
			}
		}
		ui.addControls(controls);
		ui.setLocationRelativeTo(null);
	}
	
	
	private UITextField getUserName() {
		try {
			UITextField control = new UITextField(10);
			control.setText(applicationUser.getUserLogin());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationUser.setUserLogin(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UIPasswordField getPassword() {
		try {
			UIPasswordField control = new UIPasswordField();
			control.setText(applicationUser.getPassword());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationUser.setPassword(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIPasswordField();
		}
	}

	private UIComboBox getEnvironment() {
		try {
			ReferenceDisplayList list = new ReferenceDisplayList();
			UIComboBox control = new UIComboBox(list);
			new UIComboDocument(control, null);
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIComboBox();
		}
	}


	
}
