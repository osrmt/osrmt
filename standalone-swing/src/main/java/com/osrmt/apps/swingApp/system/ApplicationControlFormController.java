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
package com.osrmt.apps.swingApp.system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.ApplicationActionList;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.controls.UIToolBar;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;
import com.osframework.modellibrary.reference.security.ApplicationViewModel;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;


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