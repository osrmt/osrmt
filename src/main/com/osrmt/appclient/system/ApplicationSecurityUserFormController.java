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

import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import com.osframework.modellibrary.reference.security.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.system.*;
import com.osrmt.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.services.*;


/**
null
*/
public class ApplicationSecurityUserFormController extends ApplicationSecurityUserFormBaseController {

 // private MultiColumnList yourCustomControl = new MultiColumnList();
 // private UITreeModel yourCustomModel;
	//TODO this class does not need the actions, ui and base
	//TODO we need the application security form as well
	//TODO swap out all the JFrame for a UIFrame
	private JFrame frame;
	private MultiColumnList uilist = new MultiColumnList();
	private PanelAddRemove listPanel = new PanelAddRemove(PanelAddRemove.NO_RIGHT_SIDE_BUTTONS);
	private ApplicationUserModel selectedUser;
	private ReferenceModel selectedPosition; 
	private boolean isUser = true;
	
	public ApplicationSecurityUserFormController(JFrame frame) {
		super(frame);
		this.frame = frame;
	}

	/**
	 * @param args
	 */
	public void start (ApplicationUserModel m) {
		try {
			selectedUser = m;
			//ApplicationControlList list = SecurityServices.getAppControls(ApplicationGroup.YourApp);
			ApplicationControlList list = new ApplicationControlList();
			super.initialize(list, new ApplicationSecurityModel()); 
			addListeners();
			initForm();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	/**
	 * @param args
	 */
	public void start (ReferenceModel position) {
		try {
			this.selectedPosition = position;
			isUser = false;
			ApplicationControlList list = new ApplicationControlList();
			super.initialize(list, new ApplicationSecurityModel()); 
			addListeners();
			initForm();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	private void initForm() throws Exception {
		ui.setLocation(UIProperties.getPoint200_200());
		ui.setSize(UIProperties.getWINDOW_SIZE_600_400());
		ui.setTitle(ReferenceServices.getMsg(FormTitleFramework.USERAPPLICATIONSECURITY));
		ui.getPanelCenter().add(getListPanel(), BorderLayout.CENTER);
	}

	private void addListeners() {
		ui.getPanelOkCancel().getCmdOk().setVisible(false);
		ui.getPanelOkCancel().getCmdCancel().setText(ReferenceServices.getMsg(FormButtonTextFramework.CLOSE));
		ui.getPanelOkCancel().getCmdCancel().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				ui.dispose();
			}
		});
		listPanel.getPropertiesButton().setVisible(false);
		listPanel.getAddButton().addActionListener(getAddSecurityListener());
		listPanel.getRemoveButton().addActionListener(getRemoveSecurityListener());
	}
	
	public JPanel getListPanel() throws Exception {
		uilist.setTableModel(getCurrentList(), 70);
		listPanel.setListControl(uilist);
		listPanel.setSize(UIProperties.getDIALOG_SIZE_450_330());
		return listPanel;
	}
	
	private ApplicationSecurityList getCurrentList() {
		try {
			ApplicationControlList acl = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.APPSECURITYUSERCOLUMNS));
			if (isUser) {
				ApplicationSecurityList asl = SecurityServices.getAppSecurity(selectedUser);
				appendNew(asl, SecurityServices.getAppSecurityByPosition(PositionFramework.get(selectedUser.getPositionRefId())));
				appendNew(asl, SecurityServices.getAppSecurityGlobal());
				asl.setColumnOrder(acl);
				return asl;
			} else {
				ApplicationSecurityList asl = new ApplicationSecurityList();
				appendNew(asl, SecurityServices.getAppSecurityByPosition(PositionFramework.get(selectedPosition.getRefId())));
				appendNew(asl, SecurityServices.getAppSecurityGlobal());
				asl.setColumnOrder(acl);
				return asl;
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new ApplicationSecurityList();
		}
	}
	
	
	private ActionListener getAddSecurityListener() {
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				UICenterSouthDialog addDialog = new UICenterSouthDialog(ui, false);
				addDialog.setSize(UIProperties.getWINDOW_SIZE_600_400());
				addDialog.setTitle(ReferenceServices.getMsg(FormTitleFramework.ADDSECURITYACCESS));
				MultiColumnList adduilist = new MultiColumnList();
				adduilist.setTableModel(getAddList(), 70);
				addDialog.getCenterPanel().add(adduilist, BorderLayout.CENTER);
				addDialog.getSouthPanel().add(getAddOkCancel(addDialog, adduilist), BorderLayout.CENTER);
				addDialog.setVisible(true);
			}
		};
	}
	
	private ActionListener getRemoveSecurityListener() {
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				if(uilist.isRowSelected()) {
					Enumeration e1 = uilist.getSelectedRows().elements();
					while (e1.hasMoreElements()) {
						ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();
						asm.setNotActive();
						SecurityServices.UpdateApplicationSecurity(asm);
						uilist.setTableModel(getCurrentList(), 70);
					}
				}
			}
		};
	}
	
	private PanelOkCancel getAddOkCancel(final UICenterSouthDialog addDialog, final MultiColumnList adduilist) {
		PanelOkCancel panel = new PanelOkCancel();
		panel.addCancelActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDialog.dispose();
			}
		});
		panel.addOkActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (adduilist.isRowSelected()) {
					Enumeration e1 = adduilist.getSelectedRows().elements();
					while (e1.hasMoreElements()) {
						ApplicationSecurityModel asl = (ApplicationSecurityModel) e1.nextElement();
						ApplicationSecurityModel newSecurity = new ApplicationSecurityModel();
						newSecurity.setApplicationViewId(asl.getApplicationViewId());
						newSecurity.setNotReadOnly();
						if (isUser) {
							newSecurity.setTableKeyId(selectedUser.getUserId());
							newSecurity.setTableRefId(TableNameGroup.APPLICATIONUSER);
						} else {
							newSecurity.setTableKeyId(selectedPosition.getRefId());
							newSecurity.setTableRefId(TableNameGroup.REFERENCE);
						}
						SecurityServices.UpdateApplicationSecurity(newSecurity);
					}
					TimedAction ta = new TimedAction(0.25) {
						@Override
						public void executeTask() {
							uilist.setTableModel(getCurrentList(), 70);
						}
					};
					addDialog.dispose();
				}
			}
		});
		return panel;
	}
	
	private ApplicationSecurityList getAddList() {
		try {
			ApplicationControlList acl = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.APPSECURITYUSERCOLUMNS));
			ApplicationSecurityList newList = new ApplicationSecurityList();
			ApplicationSecurityList current = getCurrentList();
			ApplicationViewList all = SecurityServices.getApplicationViewsByType();
			
			Enumeration e1 = all.elements();
			while (e1.hasMoreElements()) {
				ApplicationViewModel avm = (ApplicationViewModel) e1.nextElement();
				if (!inList(current, avm, true)) {
					ApplicationSecurityModel asm = new ApplicationSecurityModel();
					asm.setApplicationRefId(avm.getApplicationRefId());
					asm.setApplicationRefDisplay(avm.getApplicationRefDisplay());
					asm.setAppTypeRefId(avm.getAppTypeRefId());
					asm.setAppTypeRefDisplay(avm.getAppTypeRefDisplay());
					asm.setViewRefId(avm.getViewRefId());
					asm.setViewRefDisplay(avm.getViewRefDisplay());
					asm.setApplicationViewId(avm.getApplicationViewId());
					newList.add(asm);
				}
			}
			newList.setColumnOrder(acl);
			return newList;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new ApplicationSecurityList();
		}
	}

	
	private void appendNew (ApplicationSecurityList original, ApplicationSecurityList added) {
		Enumeration e1 = added.elements();
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel m = (ApplicationSecurityModel) e1.nextElement();
			ApplicationViewModel avm = SecurityServices.getApplicationView(m.getApplicationViewId());
			if (!inList(original, avm, false)) {
				original.add(m);
			}
		}
	}
	
	/**
	 * Returns true if asm's application and app type is in asl
	 * 
	 * @param asl
	 * @param asm
	 * @return
	 */
	private boolean inList(ApplicationSecurityList asl, ApplicationViewModel avm, boolean matchView) {
		// = SecurityServices.getApplicationView(asm.getApplicationViewId());
		Enumeration e1 = asl.elements();
		boolean found = false;
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel m = (ApplicationSecurityModel) e1.nextElement();
			ApplicationViewModel a = SecurityServices.getApplicationView(m.getApplicationViewId());
			if (a.getAppTypeRefId() == avm.getAppTypeRefId() && a.getApplicationRefId() == avm.getApplicationRefId()
					&& (a.getViewRefId() == avm.getViewRefId() || matchView == false)) {
				found = true;
			} 
		}
		return found;
	}

}