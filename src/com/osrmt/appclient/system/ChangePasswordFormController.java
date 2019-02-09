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

*/package com.osrmt.appclient.system;

import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import com.osframework.modellibrary.reference.security.*;
import com.osrmt.appclient.system.*;
import com.osrmt.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.services.*;
import com.osrmt.appclient.setting.*;


/**
null
*/
public class ChangePasswordFormController extends ChangePasswordFormBaseController {

	private ApplicationUserModel user;
	private JFrame frame;
	private boolean authenticateOldPassword = true;
	private ActionListener oklistener;  
	
	public ChangePasswordFormController(JFrame frame) {
		super(frame);
		this.frame = frame; 
	}
	
	public void setNoOldPassword() {
		authenticateOldPassword = false;
	}
	
	/**
	 * @param args
	 */
	public void start (ApplicationUserModel m) {
		this.user = m;
		ui.setControlColumns(1);
		ApplicationControlList list = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.CHANGEPASSWORDFORM));
		if (!authenticateOldPassword) {
			Enumeration e1 = list.elements();
			while (e1.hasMoreElements()) {
				ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
				//TODO replace OldPassword with diffent scheme
				if (acm.getControlRefDisplay().compareTo("OldPassword")==0) {
					list.remove(acm);
				}
			}
		}
		super.initialize(list, new ChangePasswordModel()); 
		addControls();
		addListeners();
		initForm();
	}

	private void initForm() {
		ui.setLocation(UIProperties.getPoint200_200());
		ui.setSize(UIProperties.getDIALOG_SIZE_450_330());
		ui.setTitle(ReferenceServices.getMsg(FormTitleFramework.CHANGEPASSWORD));
		ui.setVisible(true);
	}

	private void addControls() {
	}
	
	private boolean changePassword() {
		// put in old password
		try {
			user.setPassword(getChangePassword().getOldPassword());
			if (authenticateOldPassword) {
				user = SecurityServices.authenticate(user, true, AuthenticationSetting.isLdap());
			} 
			if ( user != null && getChangePassword().getNewPassword() != null && getChangePassword().getNewPassword().length() > 0) {
				if (getChangePassword().getNewPassword().compareTo(
						getChangePassword().getNewPasswordConfirm())==0) {
					user.setPassword(SecurityUtility.hashPassword(getChangePassword().getNewPassword().getBytes()));
					if (!authenticateOldPassword) {
						user.setPasswordReset();
					} else {
						user.setNotPasswordReset();
					}
					if (SecurityServices.UpdateUser(user)) {
						return true;
					}
				}
			} 
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
		return false;
	}

	private void addListeners() {
		ui.getPanelOkCancel().getCmdOk().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				if (changePassword()) {
					if (oklistener != null) {
						oklistener.actionPerformed(e);
					}
					ui.dispose();
				}
			}
		});
		ui.getPanelOkCancel().getCmdCancel().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				ui.dispose();
			}
		});
	}

	public ActionListener getOklistener() {
		return oklistener;
	}

	public void setOklistener(ActionListener oklistener) {
		this.oklistener = oklistener;
	}
	

}