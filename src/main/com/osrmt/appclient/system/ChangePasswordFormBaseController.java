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
public class ChangePasswordFormBaseController {
	
	private ChangePasswordModel changePassword;
	public ChangePasswordModel getChangePassword() {
		return changePassword;
	}

	protected RuleScript script = null;
	protected ChangePasswordFormUI ui;

	public ChangePasswordFormBaseController(JFrame frame) {
		 ui = new ChangePasswordFormUI(frame);
	}

	
	public void initialize (ApplicationControlList controls, ChangePasswordModel m) {
		this.changePassword = m;
		script = new RuleScript(null,null);
		initializeUI(controls);
	}
	
	private void initializeUI(ApplicationControlList controls) {
		java.util.Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			if (rm.getControlRefDisplay().compareTo("OldPassword")==0) {
				UIPasswordField field = getOldPassword();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setOldPassword(field);
			}
			if (rm.getControlRefDisplay().compareTo("NewPasswordConfirm")==0) {
				UIPasswordField field = getNewPasswordConfirm();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setNewPasswordConfirm(field);
			}
			if (rm.getControlRefDisplay().compareTo("NewPassword")==0) {
				UIPasswordField field = getNewPassword();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setNewPassword(field);
			}
		}
		ui.addControls(controls);
		ui.setLocationRelativeTo(null);
	}
	
	
	private UIPasswordField getOldPassword() {
		try {
			UIPasswordField control = new UIPasswordField();
			control.setText(changePassword.getOldPassword());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					changePassword.setOldPassword(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIPasswordField();
		}
	}

	private UIPasswordField getNewPasswordConfirm() {
		try {
			UIPasswordField control = new UIPasswordField();
			control.setText(changePassword.getNewPasswordConfirm());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					changePassword.setNewPasswordConfirm(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIPasswordField();
		}
	}

	private UIPasswordField getNewPassword() {
		try {
			UIPasswordField control = new UIPasswordField();
			control.setText(changePassword.getNewPassword());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					changePassword.setNewPassword(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIPasswordField();
		}
	}

	
}
