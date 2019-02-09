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
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.services.*;


/**
null
*/
public class UserFormController extends UserFormBaseController {

 // private MultiColumnList yourCustomControl = new MultiColumnList();
 // private UITreeModel yourCustomModel;
	private UserFormActions actions;
	private ApplicationActionList applicationActionList;
	private JFrame frame;
	private ActionListener okAction;
		
	public UserFormController(JFrame frame) {
		super(frame);
		this.frame = frame;
		actions = new UserFormActions(frame);
	}
	
	/**
	 * Execute this action on ok after update and just prior to form disposal
	 * Only if panelOnly is false
	 * 
	 * @param l
	 */
	public void setOkAction(ActionListener l) {
		okAction = l;
	}

	/**
	 * @param args
	 */
	public UserFormUI start (ApplicationUserModel m, boolean panelOnly) {
		ApplicationControlList list = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.USERFORM));
		customizeForNewUser(list, m);
		super.initialize(list, m); 
		ui.getPanelNorth().setPreferredSize(new Dimension(1,1));
		applicationActionList = actions.getActions(this);
		if (panelOnly) {
			addPanelControls();
			addPanelListeners();
		} else {
			initForm();
			addControls();
			addListeners();
		}
		return ui;
	}

	public JPanel getMainPanel() {
		return ui.getMainPanel();
	}

	public JMenuBar getMenu() {
		return ui.getJMenuBar();
	}

	private void initForm() {
		ui.setLocation(UIProperties.getPoint200_200());
		ui.setSize(UIProperties.getWINDOW_SIZE_600_400());
		ui.setTitle(ReferenceServices.getMsg(FormTitleFramework.CREATENEWUSER));
		ui.setVisible(true);
	}

	private void addPanelControls() {
		UserFormTools tools = new UserFormTools();
		//UIToolBar toolBar = tools.getToolBar(applicationActionList);
		ui.setJMenuBar(tools.getMenuBar(applicationActionList));
		ui.getPanelOkCancel().getCmdOk().setVisible(false);
		ui.getPanelOkCancel().getCmdCancel().setText(ReferenceServices.getMsg(FormButtonTextFramework.APPLY));
		ui.getChangePassword().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				ApplicationAction aa = applicationActionList.getAction(ActionGroup.USERADMINPASSWORD);
				aa.getActionListener().actionPerformed(e);
			}
		});
		ui.getAssignForms().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				ApplicationAction aa = applicationActionList.getAction(ActionGroup.USERADMINSECURITY);
				aa.getActionListener().actionPerformed(e);
			}
		});
	}

	private void addControls() {
		UserFormTools tools = new UserFormTools();
		//UIToolBar toolBar = tools.getToolBar(applicationActionList);
		//ui.setJMenuBar(tools.getMenuBar(applicationActionList));
	}

	private void addPanelListeners() {
		ui.getPanelOkCancel().getCmdCancel().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				SecurityServices.UpdateUser(getApplicationUser());
				ui.getPanelOkCancel().getCmdCancel().setEnabled(false);
				ISApplicationMediator.getInstance().receive(ISEvent.APPLIEDCHANGES, UIContext.contextUserWizard);
			}
		});
	}

	private void addListeners() {
		ui.getPanelOkCancel().getCmdOk().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				checkNewUserName(getApplicationUser());
				SecurityServices.UpdateUser(getApplicationUser());
				if (okAction != null) {
					okAction.actionPerformed(e);
				}
				ui.dispose();
			}
		});
		ui.getPanelOkCancel().getCmdCancel().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				ui.dispose();
			}
		});
	}
	
	/**
	 * override the ability to put in the username
	 * 
	 * @param list
	 */
	private void customizeForNewUser(ApplicationControlList list, ApplicationUserModel m) {
		Enumeration e1 = list.elements();
		while(e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			if (acm.getControlRefDisplay().compareTo("UserLogin")==0) {
				if (m.getUserId()==0) {
					acm.setNotLocked();
				} else {
					acm.setLocked();
				}
			}
		}
	}
	
	/**
	 * Ensure username is unique for new user
	 * 
	 * @param m
	 * @throws IllegalArgumentException
	 */
	private void checkNewUserName(ApplicationUserModel m) throws IllegalArgumentException {
		if (m.getUserId() == 0) {
			ApplicationUserModel dupUser = SecurityServices.getUserByLogin(m.getUserLogin());
			if (dupUser.getUserId() > 0) {
				throw new IllegalArgumentException(ReferenceServices.getMsg(SystemMessageFramework.DUPLICATEUSERNAME) + m.getUserLogin());
			}
		}
	}

}