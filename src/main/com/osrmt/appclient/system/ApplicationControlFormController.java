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
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.services.*;


/**
null
*/
public class ApplicationControlFormController extends ApplicationControlFormBaseController {

 // private MultiColumnList yourCustomControl = new MultiColumnList();
 // private UITreeModel yourCustomModel;
	private ApplicationControlFormActions actions = new ApplicationControlFormActions();
	private ApplicationActionList applicationActionList;
	private ApplicationControlModel original = null;
	private ActionListener okAction = null;
	private JFrame frame;
	
	public ApplicationControlFormController(JFrame frame) {
		super(frame);
		this.frame = frame;
	}

	/**
	 * @param args
	 */
	public void start (ApplicationControlModel m) {
		original = m;
		ApplicationControlList list = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.APPLICATIONCONTROLFORM));
		super.initialize(list, m); 
		addControls();
		addListeners();
		initForm(m);
	}

	private void initForm(ApplicationControlModel m) {
		ApplicationViewModel avm = SecurityServices.getApplicationView(m.getApplicationViewId());
		ui.setLocation(UIProperties.getPoint100_100());
		ui.setSize(UIProperties.getWINDOW_SIZE_800_600());
		ui.setTitle(avm.getApplicationRefDisplay() + " " + avm.getViewRefDisplay() 
				+ " " + ReferenceServices.getMsg(FormTitleFramework.APPLICATIONCONTROL) + " " + m.getControlText() + " " + m.getControlTypeRefDisplay());
		ui.setVisible(true);
	}
	
	public void setActionListener(ActionListener okAction) {
		this.okAction = okAction;
	}

	private void addControls() {
		applicationActionList = actions.getActions(this);
		ApplicationControlFormTools tools = new ApplicationControlFormTools();
		UIToolBar toolBar = tools.getToolBar(applicationActionList);
		ui.setJMenuBar(tools.getMenuBar(applicationActionList));
	}

	private void addListeners() {
		ui.getPanelOkCancel().addOkActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				try {
					SecurityServices.UpdateApplicationControl(getApplicationControl());
					original.updateWith(SecurityServices.getApplicationControl(getApplicationControl().getApplicationControlId()));
					if (okAction != null) {
						okAction.actionPerformed(e);
					}
					ui.dispose();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}				
			}
		});
		ui.getPanelOkCancel().addCancelActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				try {
					ui.dispose();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}				
			}
		});
	}

}