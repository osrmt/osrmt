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
import java.util.*;

import com.osframework.modellibrary.reference.security.*;
import com.osrmt.appclient.system.*;
import com.osrmt.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.reference.common.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.services.*;


/**
null
*/
public class UserFormActions {

	private UserFormController controller;
	private JFrame frame;
	
	public UserFormActions(JFrame frame) {
		this.frame = frame;
	}

	public ApplicationActionList getActions(UserFormController c) {
		controller = c;

		ApplicationActionList actions = new ApplicationActionList();
		actions.addApplicationAction(getAdminPassword()); 
		actions.addApplicationAction(getAdminSecurity()); 
		return actions;
	}
	
	private ApplicationAction getAdminPassword() {
		return new ApplicationAction(ActionGroup.USERADMINPASSWORD,
				null,
				new UIActionListener(frame) {
				public void actionExecuted(ActionEvent ae) throws Exception {
					ChangePasswordFormController cpc = new ChangePasswordFormController(frame);
					cpc.setNoOldPassword();
					cpc.start(controller.getApplicationUser());
				}
			});
	}

	private ApplicationAction getAdminSecurity() {
		return new ApplicationAction(ActionGroup.USERADMINSECURITY,
				null,
				new UIActionListener(frame) {
				public void actionExecuted(ActionEvent ae) throws Exception {
					ApplicationSecurityUserFormController cpc = new ApplicationSecurityUserFormController(frame);
					cpc.start(controller.getApplicationUser());
				}
			});
	}

}